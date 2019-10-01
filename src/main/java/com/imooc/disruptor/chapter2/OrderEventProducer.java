package com.imooc.disruptor.chapter2;

import com.lmax.disruptor.RingBuffer;

import java.nio.ByteBuffer;

public class OrderEventProducer {

    private RingBuffer<OrderEvent> ringBuffer;

    public OrderEventProducer(RingBuffer<OrderEvent> ringBuffer) {
        this.ringBuffer = ringBuffer;
    }

    public void sendData(ByteBuffer byteBuffer) {
        //1. 在生产者发送消息的时候，首先需要从我们的ringbuffer里面获取一个可用的序号
        long sequence = ringBuffer.next();
        try {
            //2. 根据这个序号,找到具体的"OrderEvent"元素
            //此时获取的OrderEvent对象是一个没有被填充的对象
            OrderEvent orderEvent = ringBuffer.get(sequence);
            //3.赋值
            orderEvent.setValue(byteBuffer.getLong(0));
        } finally {
            //4.提交操作
            ringBuffer.publish(sequence);
        }
    }

}
