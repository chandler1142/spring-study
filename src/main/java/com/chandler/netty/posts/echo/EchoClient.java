package com.chandler.netty.posts.echo;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;

public class EchoClient {
    private final String host;
    private final int port;

    public EchoClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void start()
            throws Exception {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            // 创建Bootstrap
            Bootstrap b = new Bootstrap();
            // 指定使用 NioEventLoopGroup 去处理客户端事件
            b.group(group)
                    // 指定channel类型为NIO
                    .channel(NioSocketChannel.class)
                    // 指定要连接的远程地址
                    .remoteAddress(new InetSocketAddress(host, port))
                    // 将 EchoClientHandler 添加到 pipeline中
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new EchoClientHandler());
                        }
                    });
            // 连接远程地址，一直等待直到连接完成
            ChannelFuture f = b.connect().sync();
            // 在channel关闭前一直处于block状态
            f.channel().closeFuture().sync();
        } finally {
            // 关闭线程池，释放所有资源
            group.shutdownGracefully().sync();
        }
    }

    public static void main(String[] args)
            throws Exception {
//        if (args.length != 2) {
//            System.err.println("Usage: " + EchoClient.class.getSimpleName() +
//                    " <host> <port>"
//            );
//            return;
//        }

        final String host = "localhost";
        final int port = 8090;
        new EchoClient(host, port).start();
    }
}
