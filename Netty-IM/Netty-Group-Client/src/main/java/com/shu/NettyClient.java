package com.shu;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

/**
 * @description:
 * @author: shu
 * @createDate: 2024/1/5 10:10
 * @version: 1.0
 */
public class NettyClient {

    /**
     * 端口
     */
    private int port;

    /**
     * 服务器地址
     */
    private String host;


    private NioEventLoopGroup group=new NioEventLoopGroup();

    public NettyClient(String host, int port) {
        this.port = port;
        this.host = host;
    }

    /**
     * 启动客户端连接服务器
     */
    public void start() {

        try {
            // 创建客户端启动助手
            Bootstrap bootstrap = new Bootstrap();
            // 设置线程组
            bootstrap.group(group)
                    // 设置NIO通讯模式
                    .channel(NioSocketChannel.class)
                    // 设置缓冲区大小
                    .option(ChannelOption.SO_BACKLOG, 1024)
                    // 设置数据处理器
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            //1.拆包器
                            socketChannel.pipeline().addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE,5,4));
                            //2.自定义解码器
                            socketChannel.pipeline().addLast(new MyDecoder());
                            //3.自定义业务
                            socketChannel.pipeline().addLast(new MyClientChatHandler());
                            //4.自定义编码器
                            socketChannel.pipeline().addLast(new MyEncoder());

                        }
                    });
            // 连接服务器
            ChannelFuture future = bootstrap.connect(host, port).sync();
            System.out.println("客户端启动成功...");
            // 等待连接关闭
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            // 优雅关闭线程组
            group.shutdownGracefully();
        }
    }


    /**
     * 停止客户端
     */
    public void stop() {
        group.shutdownGracefully();
    }
}
