package com.shu;


/**
 * @description:
 * @author: shu
 * @createDate: 2024/1/5 10:45
 * @version: 1.0
 */
public class NettyServerApplication {
    // 端口号
    private static final int PORT = 8082;
    // 服务器地址
    private static final String HOST = "127.0.0.1";

    // 创建服务端
    public static void main(String[] args) {
        // 创建客户端
        NettyServer client = new NettyServer(HOST, PORT);
        // 启动客户端连接服务器
        client.start();
    }

}
