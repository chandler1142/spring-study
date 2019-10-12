package com.imooc.disruptor.chapter6.client;

public class NettyClientRunner {

    public static void main(String[] args) {
        NettyClient client = new NettyClient();
        client.sendData();
    }

}
