package com.imooc.disruptor.chapter6.client;

import com.imooc.disruptor.chapter6.common.entity.TranslatorData;
import com.imooc.disruptor.chapter6.disruptor.MessageProducer;
import com.imooc.disruptor.chapter6.disruptor.RingbufferPoolFactory;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;

public class ClientHandler extends ChannelInboundHandlerAdapter {

    public void channelRead(ChannelHandlerContext ctx, Object msg) {
//        try {
//            TranslatorData response = (TranslatorData) msg;
//            System.err.println("Client端: id= " + response.getId()
//                    + ", name= " + response.getName()
//                    + ", message= " + response.getMessage());
//        } finally {
//            //一定要注意 用完了缓存 要进行释放
//            ReferenceCountUtil.release(msg);
//        }

        TranslatorData response = (TranslatorData) msg;
        String producerId = "sessionId:002";
        MessageProducer messageProducer = RingbufferPoolFactory.getInstance().getMessageProducer(producerId);
        messageProducer.onData(response, ctx);
    }

}
