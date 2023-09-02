package com.ujbj.qqclient.service;

import com.ujbj.qqcommon.Message;
import com.ujbj.qqcommon.MessageType;

import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;

/**
 * 用户线程，用来接收服务端消息
 * 会启一个线程接收消息
 * 创建需要传入一个Socket对象，
 * 并提供返回传入的Socket对象
 */
public class ClientConnectServerThread extends Thread {
    private Socket socket;

    // 接收一个Socket 对象
    public ClientConnectServerThread(Socket socket) {
        this.socket = socket;
    }

    public Socket getSocket() {
        return socket;
    }

    @Override
    public void run() {
        // 因为Thread需要在后台和服务器通信，因此我们while循环
        while (true) {
            try {
                // 客户端线程，等待从读取从服务器端发送的消息
                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());

                // 如果服务器没有发送Message对象，线程会阻塞在这里
                Message message = (Message) ois.readObject();

                // 判断message类型并做出相应的处理
                if (message.getMessageType().equals(MessageType.MESSAGE_GET_ONLINE_FRIEND)) {
                    // 请求返回在线用户列表
                    // 消息内容 用户信息用空格分隔开
                    String content = message.getContent();

                    String[] userList = content.split(" ");

                    System.out.print("\n用户在线列表: ");
                    // 打印出用户信息
                    for (int i = 0; i < userList.length; i++) {
                        System.out.print(userList[i] + "\t");
                    }
                    System.out.println();
                } else if (message.getMessageType().equals(MessageType.MESSAGE_COMM_MES)) {
                    // 接收用户发的消息
                    System.out.print("\n" + message.getSender() + "发送消息: ");
                    System.out.println(message.getContent());
                } else if (message.getMessageType().equals(MessageType.MESSAGE_CLIENT_EXIT)) {
                    // 退出客户端
                    break;
                } else if (message.getMessageType().equals(MessageType.MESSAGE_FILE_MES)) {
                    // 文件类型
                    FileOutputStream fos = new FileOutputStream(message.getDest());
                    fos.write(message.getFileByte());
                    fos.close();
                    System.out.println("\n已接收" + message.getSender() + "发送的文件，保存在" + message.getDest());
                } else if (message.getMessageType().equals(MessageType.MESSAGE_OFFLINE_MES)) {
                    // 离线类型
                    List<Message> list = message.getList();
                    for (Message m : list) {
                        if (m.getMessageType().equals(MessageType.MESSAGE_COMM_MES)) {
                            System.out.print("\n" + m.getSender() + "发送消息: ");
                            System.out.print(m.getContent());
                        } else if (m.getMessageType().equals(MessageType.MESSAGE_FILE_MES)) {
                            // 文件类型
                            FileOutputStream fos = new FileOutputStream(m.getDest());
                            fos.write(m.getFileByte());
                            fos.close();
                            System.out.println("\n已接收" + m.getSender() + "发送的文件，保存在" + m.getDest());
                        }
                    }
                    System.out.println();
                } else if (message.getMessageType().equals(MessageType.MESSAGE_OFFLINE_ALL_USER_MES)) {
                    System.out.print("\n服务端已关闭");
                    System.exit(0);
                } else if (message.getMessageType().equals(MessageType.MESSAGE_CLIENT_EXIT_FORCE)) {
                    System.out.print("\n你被强制下线 :(");
                    Message m = new Message();
                    m.setMessageType(MessageType.MESSAGE_CLIENT_EXIT_FORCE);
                    m.setSender(message.getGetter());
                    ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                    oos.writeObject(m);

                    System.exit(0);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            System.out.print("请选择: ");
        }
    }
}
