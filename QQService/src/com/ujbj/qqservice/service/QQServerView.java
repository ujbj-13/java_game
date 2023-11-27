package com.ujbj.qqservice.service;

import com.ujbj.qqcommon.Message;
import com.ujbj.qqcommon.MessageType;
import com.ujbj.qqcommon.User;
import com.ujbj.util.Utility;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;

public class QQServerView implements Runnable {
    private static boolean loop = true;

    public static boolean getLoop() {
        return loop;
    }

    private void menu() {
        System.out.println("=====服务端=====");
        System.out.println("0. 退出系统");
        System.out.println("1. 发送公告");
        System.out.println("2. 下线用户");
        System.out.println("3. 显示在线用户");
    }

    public void mainMenu() {
        while (loop) {
            menu();
            System.out.print("请选择: ");
            String key = Utility.readString(5);
            switch (key) {
                case "1": {
                    announcement();
                    break;
                }
                case "2": {
                    System.out.print("输入要强制下线的用户: ");
                    String user = Utility.readString(50);
                    QQServer.offLineUser(user);
                    break;
                }
                case "3": {
                    System.out.print("当前在线用户: " + ManageClientThreads.getUserIds() + "\n");
                    break;
                }
                case "0": {
                    System.out.println("关闭服务端");
                    QQServer.offLineUser();
                    System.exit(0);
                    break;
                }
            }
        }
    }

    public void announcement() {
        ConcurrentHashMap.KeySetView<String, User> users = QQServer.getValidUsers().keySet();

        Message message = new Message();
        message.setMessageType(MessageType.MESSAGE_COMM_MES);
        message.setSender("服务端");

        System.out.print("输入要推送的给用户的信息: ");
        String content = Utility.readString(100);
        message.setContent(content);

        try {
            Iterator<String> usersIt = users.iterator();

            while (usersIt.hasNext()) {
                String user = usersIt.next();

                if (ManageClientThreads.isExist(user)) {
                    message.setGetter(user);
                    System.out.println(message.getContent());

                    ObjectOutputStream oos =
                            new ObjectOutputStream(ManageClientThreads.getServerConnectClientThread(user).getSocket().getOutputStream());
                    oos.writeObject(message);
                } else {
                    QQServer.addOffLineUser(user, message);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void run() {
        mainMenu();
    }
}
