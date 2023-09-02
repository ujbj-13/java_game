package com.ujbj.qqservice.service;

import com.ujbj.qqcommon.Message;
import com.ujbj.qqcommon.MessageType;
import com.ujbj.qqcommon.User;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 服务器，等待客户端的连接，并保持通信
 */
public class QQServer {
    private ServerSocket ss;
    private QQServerView QQServerview = new QQServerView();
    // 创建一个集合用来存放用户 带线程安全
    private static ConcurrentHashMap<String, User> validUsers = new ConcurrentHashMap<>();
    private static ConcurrentHashMap<String, ArrayList<Message>> offLineUser = new ConcurrentHashMap<>();

    static {
        validUsers.put("100", new User("100", "123456"));
        validUsers.put("200", new User("200", "123456"));
        validUsers.put("300", new User("300", "123456"));
        validUsers.put("张三", new User("张三", "123456"));
        validUsers.put("李四", new User("李四", "123456"));
    }

    private boolean checkUser(String userId, String password) {
        User user = validUsers.get(userId);

        if (user == null) {
            return false;
        }
        if (!user.getPassword().equals(password)) {
            return false;
        }
        if (ManageClientThreads.getUserIds().indexOf(userId) != -1) { // 该用户在线不能再登录
            return false;
        }

        return true;
    }

    public QQServer() {
        try {
            // 服务端在9999端口监听
            System.out.println("服务端在9999端口监听中...");
            ss = new ServerSocket(9999);
            // 推送消息
            new Thread(QQServerview).start();

            // 不停去获取 socket 对象
            while (QQServerView.getLoop()) {
                // 等侍客户端连接
                Socket socket = ss.accept();
                // 连接成功... 接收user对象
                // 用户第一次连接传输的是，是个user对象
                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                ObjectOutputStream oss = new ObjectOutputStream(socket.getOutputStream());
                // 接收一个user 对象
                User user = (User) ois.readObject();
                // 不管登录是否成功都会发送一个消息对象
                Message message = new Message();

                // 假设只能有是 userId 100 password 123 登录
                if (checkUser(user.getUserId(), user.getPassword())) {
                    System.out.print("\n用户" + user.getUserId() + "登录成功...");

                    // 登录成功
                    // 设置 登录成功 消息类型，并回传给用户
                    message.setMessageType(MessageType.MESSAGE_LOGIN_SUCCEED);
                    oss.writeObject(message);

                    // 创建一个线程，和客户端保持通信
                    ServerConnectClientThread serverConnectClientThread =
                            new ServerConnectClientThread(user.getUserId(), socket);
                    serverConnectClientThread.start();
                    // 把该线程对象，放入到一个集合中，进行管理
                    ManageClientThreads.addServerConnectClientThread(user.getUserId(), serverConnectClientThread);

                    // 发送离线消息
                    sendMessage(user.getUserId(), socket);
                } else {
                    System.out.print("\n" + user.getUserId() + "用户登录失败" + "\t密码: " + user.getPassword());
                    // 登录失败
                    message.setMessageType(MessageType.MESSAGE_LOGIN_FAIL);
                    oss.writeObject(message);
                    // 关闭socket
                    socket.close();
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                ss.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public static ConcurrentHashMap<String, ArrayList<Message>> getAllOffLineUser() {
        return offLineUser;
    }

    public static boolean isExist(String userId) {
        return offLineUser.get(userId) != null;
    }

    public static void addOffLineUser(String userId, Message message) {
        if (isExist(userId)) {
            offLineUser.get(userId).add(message);
        } else if (validUsers.get(userId) != null) {
            ArrayList<Message> messages = new ArrayList<>();
            messages.add(message);
            offLineUser.put(userId, messages);
        }
    }

    public static void sendMessage(String userId, Socket socket) {
        if (!isExist(userId)) {
            return;
        }

        Message message = new Message();
        message.setMessageType(MessageType.MESSAGE_OFFLINE_MES);
        message.setGetter(userId);

        ArrayList<Message> messages = offLineUser.get(userId);
        try {
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());

            for (int i = 0; i < messages.size(); i++) {
                Message m = messages.get(i);
                message.addMessage(m);
            }
            oos.writeObject(message);

            offLineUser.remove(userId);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ConcurrentHashMap<String, User> getValidUsers() {
        return validUsers;
    }

    public static void offLineUser() {
        Collection<ServerConnectClientThread> threads = ManageClientThreads.getHm().values();

        Iterator<ServerConnectClientThread> iterator = threads.iterator();

        while (iterator.hasNext()) {
            Message message = new Message();
            message.setMessageType(MessageType.MESSAGE_OFFLINE_ALL_USER_MES);
            ServerConnectClientThread thread = iterator.next();

            try {
                ObjectOutputStream oos = new ObjectOutputStream(thread.getSocket().getOutputStream());
                oos.writeObject(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void offLineUser(String userId) {
        if (!ManageClientThreads.isExist(userId)) {
            System.out.println("\n当前用户不在线");
            return;
        }

        Message message = new Message();
        message.setMessageType(MessageType.MESSAGE_CLIENT_EXIT_FORCE);
        message.setGetter(userId);
        ServerConnectClientThread thread = ManageClientThreads.getServerConnectClientThread(userId);

        try {
            ObjectOutputStream oos = new ObjectOutputStream(thread.getSocket().getOutputStream());
            oos.writeObject(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
