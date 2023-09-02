package com.ujbj.qqclient.view;

import com.ujbj.qqclient.service.UserClientService;
import com.ujbj.util.Utility;

import java.io.IOException;
import java.util.Scanner;

public class QQView extends Thread {
    public static void main(String[] args) {
        // (\_/)
        // (·.·)
        // />m<\

        new QQView().mainMenu();

        System.out.println("客户端退出...");
    }

    private boolean loop = true;
    private static String key = "";
    private Scanner sc = new Scanner(System.in);
    // 该对象用于登录服务/注册用户
    private UserClientService userClientService = new UserClientService();

    public void mainMenu() {
        while (loop) {
            System.out.println("=====欢迎登录网络通信系统=====");
            System.out.println("0. 退出系统");
            System.out.println("1. 登录系统");
            System.out.print("请选择: ");
            key = Utility.readString(50);
            switch (key) {
                case "1" -> {
                    System.out.print("请输入用户名: ");
                    String username = Utility.readString(50);
                    System.out.print("请输入密 码: ");
                    String password = Utility.readString(50);

                    // 使用 UserClientService 类验证该用户是否合法
                    if (userClientService.checkUser(username, password)) {
                        while (loop) {
                            System.out.println("\n=====网络通信系统二级菜单(用户 " + username + " 登录成功)=====");
                            System.out.println("0. 退出系统");
                            System.out.println("1. 显示在线用户列表");
                            System.out.println("2. 私聊消息");
                            System.out.println("3. 群发消息");
                            System.out.println("4. 发送文件");
                            System.out.print("请选择: ");
                            // 没有下一行才能选择
                            key = Utility.readString(1);
                            switch (key) {
                                case "1" -> {
                                    // 显示在线用户列表
                                    userClientService.onlineFriendList();
                                }
                                case "2" -> {
                                    // 私聊消息
                                    System.out.print("要发送的用户: ");
                                    String getter = Utility.readString(50);

                                    System.out.print("要发送的内容: ");
                                    String content = Utility.readString(50);

                                    userClientService.commMes(username, getter, content);
                                }
                                case "3" -> {
                                    // 群发消息
                                    System.out.print("输入你要群发的内容: ");
                                    String content = Utility.readString(50);

                                    userClientService.allMes(username, content);
                                }
                                case "4" -> {
                                    // 发送文件
                                    System.out.print("要发送在线用户: ");
                                    String getter = Utility.readString(50);
                                    System.out.print("要发送文件的路径: ");
                                    String src = Utility.readString(50);
                                    System.out.print("要发送文件对方的路径: ");
                                    String dest = Utility.readString(50);

                                    userClientService.sendFile(username, getter, src, dest);
                                }
                                case "0" -> {
                                    userClientService.logout();
                                    loop = false;
                                }
                            }
                        }
                    } else {
                        System.out.println("登录失败");
                    }
                }
                case "0" -> {
                    loop = false;
                }
            }
        }
    }

}
