package com.imooc.disruptor.chapter6.client;

import com.imooc.disruptor.chapter6.disruptor.MessageConsumer;
import com.imooc.disruptor.chapter6.disruptor.MessageConsumerImpl4Server;
import com.imooc.disruptor.chapter6.disruptor.RingbufferPoolFactory;
import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.dsl.ProducerType;

public class NettyClientRunner {

    public static void main(String[] args) {

        MessageConsumer[] consumers = new MessageConsumer[4];
        for (int i = 0; i < consumers.length; i++) {
            MessageConsumer messageConsumer = new MessageConsumerImpl4Client("code:clientId:00" + i);
            consumers[i] = messageConsumer;
        }
        RingbufferPoolFactory.getInstance().intiAndStart(
                ProducerType.MULTI,
                1024 * 1024,
                new BlockingWaitStrategy(),
                consumers
        );

        NettyClient client = new NettyClient();
        client.sendData();
    }

}
