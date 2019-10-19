package com.imooc.disruptor.chapter6.server;

import com.imooc.disruptor.chapter6.common.entity.TranslatorData;
import com.imooc.disruptor.chapter6.disruptor.MessageProducer;
import com.imooc.disruptor.chapter6.disruptor.RingbufferPoolFactory;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;

public class ServerHandler extends ChannelInboundHandlerAdapter {

    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        try {
            /**
            TranslatorData request = (TranslatorData) msg;
            System.err.println("Server端: id= " + request.getId()
                    + ", name= " + request.getName()
                    + ", message= " + request.getMessage());
            //数据库持久化操作 IO读写 ---》 交给一个线程池，去异步调用执行
            TranslatorData response = new TranslatorData();
            response.setId("resp: " + request.getId());
            response.setMessage("resp: " + request.getMessage());
            response.setName("resp: " + request.getName());
            ctx.writeAndFlush(response);
             **/
            TranslatorData request = (TranslatorData) msg;

            //自己的应用服务应该有一个ID生成规则
            String producerId = "sessionId:001";
            MessageProducer messageProducer = RingbufferPoolFactory.getInstance().getMessageProducer(producerId);

            messageProducer.onData(request, ctx);

        } finally {
            //一定要注意 用完了缓存 要进行释放
            ReferenceCountUtil.release(msg);
        }
    }

}
