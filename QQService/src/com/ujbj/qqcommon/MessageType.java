package com.ujbj.qqcommon;

public interface MessageType {
    String MESSAGE_LOGIN_SUCCEED = "1"; // 表示登录成功
    String MESSAGE_LOGIN_FAIL = "2"; // 表示登录失败
    String MESSAGE_COMM_MES = "3"; // 普通信息包
    String MESSAGE_GET_ONLINE_FRIEND = "4"; // 请求返回在线用户列表
    String MESSAGE_RET_ONLINE_FRIEND = "5"; // 返回在线用户列表
    String MESSAGE_CLIENT_EXIT = "6"; // 客户端请求退出
    String MESSAGE_TO_ALL_MES = "7"; // 群发消息
    String MESSAGE_FILE_MES = "8"; // 发送文件
    String MESSAGE_OFFLINE_MES = "9"; // 离线类型
    String MESSAGE_OFFLINE_ALL_USER_MES = "10"; // 下线所有用户
    String MESSAGE_CLIENT_EXIT_FORCE  = "11"; // 强制退出客户端
}
