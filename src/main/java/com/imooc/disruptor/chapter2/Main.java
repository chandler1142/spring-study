package com.imooc.disruptor.chapter2;

import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.YieldingWaitStrategy;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;

import java.nio.ByteBuffer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {


    public static void main(String[] args) {
        //实例化Disruptor对象

        ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        int ringBufferSize = 4;

        /**
         * 1. 消息工厂对象
         * 2. ringBufferSize
         * 3. 线程池
         * 4. ProducerType
         * 5. BlockingWaitStrategy
         */
        Disruptor<OrderEvent> disruptor = new Disruptor<OrderEvent>(
                new OrderEventFactory(),
                ringBufferSize,
                executor,
                ProducerType.SINGLE,
                new BlockingWaitStrategy()
        );

        //2. 添加消费者监听
        disruptor.handleEventsWith(new OrderEventHandler());

        //3. 直接启动disruptor
        disruptor.start();


        //4. 创建生产者
        //获取实际存储的容器：RingBuffer
        RingBuffer<OrderEvent> ringBuffer = disruptor.getRingBuffer();

        OrderEventProducer producer = new OrderEventProducer(ringBuffer);
        ByteBuffer byteBuffer = ByteBuffer.allocate(8);

        for (long i = 0; i < 5; i++) {
            byteBuffer.putLong(0, i);
            producer.sendData(byteBuffer);
        }

        disruptor.shutdown();
        executor.shutdown();

    }

}
