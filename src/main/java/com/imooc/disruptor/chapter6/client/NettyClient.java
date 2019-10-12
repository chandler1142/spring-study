package com.imooc.disruptor.chapter6.client;

import com.imooc.disruptor.chapter6.common.codec.MarshallingCodeCFactory;
import com.imooc.disruptor.chapter6.common.entity.TranslatorData;
import com.sun.java.accessibility.util.Translator;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

public class NettyClient {

    public static final String HOST = "127.0.0.1";
    public static final Integer PORT = 8765;

    private Channel channel;
    private ChannelFuture cf;

    public NettyClient() {
        this.connect(HOST, PORT);
    }

    //1. 创建工作线程组
    EventLoopGroup workGroup = new NioEventLoopGroup();

    private void connect(String host, Integer port) {
        //2. 辅助类
        Bootstrap bootstrap = new Bootstrap();
        try {
            bootstrap.group(workGroup)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.RCVBUF_ALLOCATOR, AdaptiveRecvByteBufAllocator.DEFAULT)
                    .option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast(MarshallingCodeCFactory.buildMarshallingDecoder());
                            socketChannel.pipeline().addLast(MarshallingCodeCFactory.buildMarshallingEncoder());
                            socketChannel.pipeline().addLast(new ClientHandler());
                        }
                    });
            this.cf = bootstrap.connect(host, port).sync();
            System.out.println("Client connected...");

            this.channel = cf.channel();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendData() {
        for(int i=0;i<10;i++) {
            TranslatorData request = new TranslatorData();
            request.setId(""+i);
            request.setName("请求消息名称 " + i);
            request.setMessage("请求消息内容 " + i);
            this.channel.writeAndFlush(request);
        }
    }

    public void close() throws InterruptedException {
        this.cf.channel().closeFuture().sync();
        workGroup.shutdownGracefully();
        System.out.println("Client shutdown...");
    }


}
