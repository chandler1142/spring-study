package com.imooc.disruptor.chapter6.disruptor;

import com.imooc.disruptor.chapter6.common.entity.TranslatorData;
import com.imooc.disruptor.chapter6.common.entity.TranslatorDataWrapper;

public class MessageConsumerImpl4Server extends MessageConsumer {

    public MessageConsumerImpl4Server(String consumerId) {
        super(consumerId);
    }

    @Override
    public void onEvent(TranslatorDataWrapper event) throws Exception {
        TranslatorData request = event.getTranslatorData();
        System.err.println("Server端: id= " + request.getId()
                + ", name= " + request.getName()
                + ", message= " + request.getMessage());

        TranslatorData response = new TranslatorData();
        response.setId("resp: " + request.getId());
        response.setMessage("resp: " + request.getMessage());
        response.setName("resp: " + request.getName());
        event.getCtx().writeAndFlush(response);
    }

}
