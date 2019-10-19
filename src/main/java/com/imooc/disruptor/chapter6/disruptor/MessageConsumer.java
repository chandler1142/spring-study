package com.imooc.disruptor.chapter6.disruptor;

import com.imooc.disruptor.chapter6.common.entity.TranslatorDataWrapper;
import com.lmax.disruptor.WorkHandler;

public abstract class MessageConsumer implements WorkHandler<TranslatorDataWrapper> {

    protected String consumerId;

    public MessageConsumer(String consumerId) {
        this.consumerId = consumerId;
    }

    public String getConsumerId() {
        return consumerId;
    }

    public void setConsumerId(String consumerId) {
        this.consumerId = consumerId;
    }

}
