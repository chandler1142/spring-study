package com.imooc.disruptor.chapter6.client;

import com.imooc.disruptor.chapter6.common.entity.TranslatorData;
import com.imooc.disruptor.chapter6.common.entity.TranslatorDataWrapper;
import com.imooc.disruptor.chapter6.disruptor.MessageConsumer;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.ssl.ReferenceCountedOpenSslContext;
import io.netty.util.ReferenceCountUtil;

public class MessageConsumerImpl4Client extends MessageConsumer {

    public MessageConsumerImpl4Client(String consumerId) {
        super(consumerId);
    }

    @Override
    public void onEvent(TranslatorDataWrapper event) throws Exception {
        TranslatorData response = event.getTranslatorData();
        ChannelHandlerContext ctx = event.getCtx();
        try {
            System.err.println("Client端: id= " + response.getId()
                    + ", name= " + response.getName()
                    + ", message= " + response.getMessage());
        } finally {
            ReferenceCountUtil.release(response);
        }
    }

}
