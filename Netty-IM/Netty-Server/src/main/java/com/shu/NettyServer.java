package com.shu;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

/**
 * @description:
 * @author: shu
 * @createDate: 2024/1/5 10:45
 * @version: 1.0
 */
public class NettyServer {
    // 端口
    private int port;
    // 服务器地址
    private String host;
    // Worker线程组
    private NioEventLoopGroup workerGroup = new NioEventLoopGroup();
    // Boss线程组
    private NioEventLoopGroup bossGroup = new NioEventLoopGroup();

    public NettyServer(String host, int port) {
        this.port = port;
        this.host = host;
    }

    /**
     * 启动服务端
     *
     */
    public void start(){
        try {
            // 创建服务端
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            // 设置线程组
            serverBootstrap.group(bossGroup,workerGroup)
                    // 设置NIO通讯模式
                    .channel(NioServerSocketChannel.class)
                    // 设置缓冲区大小
                    .option(ChannelOption.SO_BACKLOG,1024)
                    // 设置数据处理器
                    .childHandler(new io.netty.channel.ChannelInitializer<io.netty.channel.socket.SocketChannel>() {
                        @Override
                        protected void initChannel(io.netty.channel.socket.SocketChannel socketChannel) throws Exception {
                            //1.拆包器
                            socketChannel.pipeline().addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE,5,4));
                            //2.自定义解码器
                            socketChannel.pipeline().addLast(new MyDecoder());
                            //3.业务Handler
                            socketChannel.pipeline().addLast(new MyServerChatHandler());
                            //4.自定义编码器
                            socketChannel.pipeline().addLast(new MyEncoder());

                        }
                    });
            // 启动服务端
            io.netty.channel.ChannelFuture channelFuture = serverBootstrap.bind(host, port).sync();
            System.out.println("服务端启动成功，端口：" + port);
            // 监听关闭的channel，设置为同步方式
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
