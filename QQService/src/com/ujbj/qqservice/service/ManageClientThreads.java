package com.ujbj.qqservice.service;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

/**
 * 该类用于管理和客户端通信的线程
 */
public class ManageClientThreads {
    private static HashMap<String, ServerConnectClientThread> hm = new HashMap<>();

    // 添加线程对象 hashMap
    public static void addServerConnectClientThread(String userId, ServerConnectClientThread serverConnectClientThread) {
        hm.put(userId, serverConnectClientThread);
    }
    public static void removeServerConnectClientThread(String userId) {
        hm.remove(userId);
    }

    public static ServerConnectClientThread getServerConnectClientThread(String userId) {
        return hm.get(userId);
    }

    // 返回所有的userId
    public static String getUserIds() {
        Set<String> users = hm.keySet();
        StringBuffer sb = new StringBuffer();

        Iterator<String> iterator = users.iterator();
        while (iterator.hasNext()) {
            sb.append(iterator.next() + " ");
        }

        return sb.toString();
    }

    // 获取hm
    public static HashMap<String, ServerConnectClientThread> getHm() {
        return hm;
    }

    public static boolean isExist(String userId) {
        // 不等于空，存在返回true，否则 false
        return hm.get(userId) != null;
    }


}
