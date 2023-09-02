package com.ujbj.qqservice.service;

import com.ujbj.qqcommon.Message;
import com.ujbj.qqcommon.MessageType;
import com.ujbj.qqcommon.User;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 该类的一个对象和某个客户端保持通信
 */
public class ServerConnectClientThread extends Thread {

    // 需要知道该线程和哪个用户建立连接
    // 连接到服务端的用户id
    private String userId;
    private Socket socket;

    private boolean loop = true;

    public ServerConnectClientThread(String userId, Socket socket) {
        this.userId = userId;
        this.socket = socket;
    }

    public void setLoop(boolean loop) {
        this.loop = loop;
    }

    @Override
    public void run() {
        while (loop) {
            try {
                // 启动一个线程，接收用户发的消息
                ObjectInputStream ois =
                        new ObjectInputStream(socket.getInputStream());
                Message rMessage = (Message) ois.readObject();

                // 判断message类型并做出相应的处理
                if (rMessage.getMessageType().equals(MessageType.MESSAGE_GET_ONLINE_FRIEND)) {
                    // 请求返回在线用户列表 消息类型
                    System.out.println("\n" + rMessage.getSender() + "请求用户列表");
                    ObjectOutputStream oos =
                            new ObjectOutputStream(socket.getOutputStream());

                    Message sMessage = new Message();
                    sMessage.setMessageType(MessageType.MESSAGE_GET_ONLINE_FRIEND);
                    sMessage.setContent(ManageClientThreads.getUserIds());
                    sMessage.setGetter(sMessage.getSender());
                    oos.writeObject(sMessage);

                } else if (rMessage.getMessageType().equals(MessageType.MESSAGE_CLIENT_EXIT)) {
                    System.out.println("\n" + rMessage.getSender() + "退出客户端");
                    // 退出当前客户端
                    ObjectOutputStream oos =
                            new ObjectOutputStream(socket.getOutputStream());

                    Message sMessage = new Message();
                    sMessage.setMessageType(MessageType.MESSAGE_CLIENT_EXIT);
                    sMessage.setGetter(rMessage.getSender());
                    oos.writeObject(sMessage);
                    ManageClientThreads.getServerConnectClientThread(rMessage.getSender()).socket.close();
                    ManageClientThreads.removeServerConnectClientThread(rMessage.getSender());
                    break;
                } else if (rMessage.getMessageType().equals(MessageType.MESSAGE_COMM_MES)) {
                    // 私聊类型
                    String getter = rMessage.getGetter();
                    String sender = rMessage.getSender();

                    if (ManageClientThreads.isExist(getter)) {
                        System.out.println("\n服务端: " + sender + "向" + getter + "发送消息: " + rMessage.getContent());
                        ObjectOutputStream oos =
                                new ObjectOutputStream(ManageClientThreads.getServerConnectClientThread(getter).socket.getOutputStream());
                        oos.writeObject(rMessage);
                    } else {
                        QQServer.addOffLineUser(rMessage.getGetter(), rMessage);
                        System.out.println("\n离线消息已添加到服务端");
                    }
                } else if (rMessage.getMessageType().equals(MessageType.MESSAGE_TO_ALL_MES)) {
                    // 群发类型

                    // 群发的消息目前只有文字类型，设为群发类型实际是为了让服务器知道我要发给所有是
                    // 一个普通的消息类型
                    rMessage.setMessageType(MessageType.MESSAGE_COMM_MES);

                    ConcurrentHashMap.KeySetView<String, User> users = QQServer.getValidUsers().keySet();
                    Iterator<String> iterator = users.iterator();

                    while (iterator.hasNext()) {
                        String user = iterator.next();

                        // 不能等于自己
                        if (!user.equals(rMessage.getSender())) {
                            if (ManageClientThreads.isExist(user)) {
                                ServerConnectClientThread t = ManageClientThreads.getServerConnectClientThread(user);
                                ObjectOutputStream oos = new ObjectOutputStream(t.socket.getOutputStream());
                                oos.writeObject(rMessage);
                            } else {
                                QQServer.addOffLineUser(user, rMessage);
                                System.out.print("\n离线消息已添加到服务端");
                            }
                        }

                    }
                } else if (rMessage.getMessageType().equals(MessageType.MESSAGE_FILE_MES)) {
                    // 传输的是文件类型
                    if (ManageClientThreads.isExist(rMessage.getGetter())) { // 存在
                        ObjectOutputStream oos =
                                new ObjectOutputStream(ManageClientThreads.getServerConnectClientThread(rMessage.getGetter()).socket.getOutputStream());
                        oos.writeObject(rMessage);
                    } else { // 不存在
                        QQServer.addOffLineUser(rMessage.getGetter(), rMessage);
                        System.out.println("\n离线消息已添加到服务端");
                    }
                } else if (rMessage.getMessageType().equals(MessageType.MESSAGE_CLIENT_EXIT_FORCE)) {
                    ManageClientThreads.getServerConnectClientThread(rMessage.getSender()).socket.close();
                    ManageClientThreads.removeServerConnectClientThread(rMessage.getSender());
                    break;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.print("\n请选择: ");
        }
    }

    public Socket getSocket() {
        return socket;
    }

    public String getUserId() {
        return userId;
    }
}
