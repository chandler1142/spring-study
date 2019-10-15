package com.imooc.disruptor.chapter6.disruptor;

import com.imooc.disruptor.chapter6.common.entity.TranslatorData;
import com.imooc.disruptor.chapter6.common.entity.TranslatorDataWrapper;
import com.lmax.disruptor.RingBuffer;
import io.netty.channel.ChannelHandlerContext;

public class MessageProducer {

    private String producerId;

    private RingBuffer<TranslatorDataWrapper> ringBuffer;

    public MessageProducer(String producerId, RingBuffer<TranslatorDataWrapper> ringBuffer) {
        this.producerId = producerId;
        this.ringBuffer = ringBuffer;
    }


    public void onData(TranslatorData data, ChannelHandlerContext ctx) {
        long sequence = ringBuffer.next();
        try {
            TranslatorDataWrapper translatorDataWrapper = ringBuffer.get(sequence);
            translatorDataWrapper.setCtx(ctx);
            translatorDataWrapper.setTranslatorData(data);
        } finally {
            ringBuffer.publish(sequence);
        }
    }

}
