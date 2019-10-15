package com.imooc.disruptor.chapter6.common.entity;

import io.netty.channel.ChannelHandlerContext;

public class TranslatorDataWrapper {

    private ChannelHandlerContext ctx;

    private TranslatorData translatorData;

    public ChannelHandlerContext getCtx() {
        return ctx;
    }

    public void setCtx(ChannelHandlerContext ctx) {
        this.ctx = ctx;
    }

    public TranslatorData getTranslatorData() {
        return translatorData;
    }

    public void setTranslatorData(TranslatorData translatorData) {
        this.translatorData = translatorData;
    }

}
