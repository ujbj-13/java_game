package com.ujbj.qqclient.service;

import com.ujbj.qqcommon.Message;
import com.ujbj.qqcommon.MessageType;
import com.ujbj.qqcommon.User;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

public class UserClientService {

    private User u = new User();
    private Socket socket;
    private static String ip = "122.51.57.13";
    private static int port = 9898;
    // inet 10.0.4.11  netmask 255.255.252.0  broadcast 10.0.7.255
    // 122.51.57.13

    public static void setIP(String setIp) {
        ip = setIp;
    }
    public static void setPort(int setPort) {
        port = setPort;
    }

    /**
     * 将user对象传入服务端，验证用户是否正确，并等待接收服务端返回的message对
     * 象，通过返回的消息对象来判断是否登录成功，成功: 启动一个线程，并添加到集合
     * 进行管理; 失败: 关闭socket。
     * @param userId 用户id
     * @param password 用户密码
     * @return 如果是验证成功返true，失败false
     */
    public boolean checkUser(String userId, String password) {
        boolean b = false;

        u.setUserId(userId);
        u.setPassword(password);

        // 连接服务器 发送 u 对象
        try {
            // 发送 user 对象
            socket = new Socket(InetAddress.getByName(ip), port);
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            oos.writeObject(u);

            // 接收服务器发送的消息 对象
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            Message message = (Message) ois.readObject();

            if (message.getMessageType().equals(MessageType.MESSAGE_LOGIN_SUCCEED)) {
                // 登录成功
                // 创建一个和服务器端保持通信的线程 -> 创建一个类 ClientConnectServerThread
                ClientConnectServerThread clientConnectServerThread =
                        new ClientConnectServerThread(socket);
                // 启动线程
                clientConnectServerThread.start();
                // 需要一个类来管理线程
                ManageClientConnectServerThread.addClientConnectServerThread
                        (userId, clientConnectServerThread);

                b = true;
            } else if (message.getMessageType().equals(MessageType.MESSAGE_LOGIN_FAIL)) {
                // 登录失败
                // 关闭 Socket
                socket.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return b;
    }

    // 向服务端请求在线用户列表
    public void onlineFriendList() {
        Message message = new Message();
        // 设置消息类型: 请求返回在线用户列表
        message.setMessageType(MessageType.MESSAGE_GET_ONLINE_FRIEND);
        message.setSender(u.getUserId());

        // 返回一个用来连接服务端的线程
        ClientConnectServerThread clientConnectServerThread =
                ManageClientConnectServerThread.getClientConnectServerThread(u.getUserId());
        // 获取socket

        try {
            ObjectOutputStream oos =
                    new ObjectOutputStream(clientConnectServerThread.getSocket().getOutputStream());
            oos.writeObject(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void logout() {
        Message message = new Message();
        message.setMessageType(MessageType.MESSAGE_CLIENT_EXIT);
        message.setSender(u.getUserId());

        // 向服务端发送退出消息
        try {
            ObjectOutputStream oos =
                    new ObjectOutputStream(ManageClientConnectServerThread.getClientConnectServerThread(u.getUserId()).getSocket().getOutputStream());
            oos.writeObject(message);

            System.exit(0);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    // 进行私聊
    public void commMes(String sender, String getter, String content) {

        Message message = new Message();
        message.setMessageType(MessageType.MESSAGE_COMM_MES);
        message.setSender(sender);
        message.setGetter(getter);
        message.setContent(content);

        try {
            System.out.println("我向" + message.getGetter() + "发送: " + message.getContent());
            ObjectOutputStream oos =
                    new ObjectOutputStream(ManageClientConnectServerThread.getClientConnectServerThread(sender).getSocket().getOutputStream());
            oos.writeObject(message);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // 进行群发
    public void allMes(String sender, String content) {
        // 新建一个要发送的消息类
        Message message = new Message();
        message.setSender(sender);
        message.setMessageType(MessageType.MESSAGE_TO_ALL_MES);
        message.setContent(content);

        try {
            ObjectOutputStream oos =
                    new ObjectOutputStream(ManageClientConnectServerThread.getClientConnectServerThread(sender).getSocket().getOutputStream());
            oos.writeObject(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 发送文件
    public void sendFile(String sender, String getter, String src, String dest) {
        File file = new File(src);

        if (!file.isFile()) {
            System.out.println("文件不存在");
            return;
        }
        if (file.length() > 1 * 1024 * 1024 * 1024) {
            System.out.println("文件过大");
            return;
        }

        // 用来存放文件
        byte[] fileByte = new byte[(int) file.length()];
        FileInputStream fis = null;

        try {
            // 将文件从磁盘读取到程序中
            fis = new FileInputStream(src);
            fis.read(fileByte); // 写入fileByte数组中
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                fis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // 新建一个消息对象
        Message message = new Message();
        message.setMessageType(MessageType.MESSAGE_FILE_MES);
        message.setSender(sender);
        message.setGetter(getter);
        message.setFileByte(fileByte);
        message.setContent(file.getParent());
        System.out.println(file.getParent());
        message.setDest(dest);

        // 向服务端传文件
        try {
            ObjectOutputStream oos =
                    new ObjectOutputStream(ManageClientConnectServerThread.getClientConnectServerThread(sender).getSocket().getOutputStream());
            oos.writeObject(message);
            System.out.println("\n我向" + getter + "发送文件" + file.getName());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
