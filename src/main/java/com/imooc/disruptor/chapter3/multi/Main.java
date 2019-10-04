package com.imooc.disruptor.chapter3.multi;

import com.lmax.disruptor.*;
import com.lmax.disruptor.dsl.ProducerType;

import java.util.concurrent.Executors;

public class Main {

    public static void main(String[] args) {

        //1.创建RingBuffer
        RingBuffer<Order> ringBuffer = RingBuffer.create(
                ProducerType.MULTI,
                new EventFactory<Order>() {
                    @Override
                    public Order newInstance() {
                        return new Order();
                    }
                },
                1024 * 1024,
                new YieldingWaitStrategy()
        );

        //2.创建屏障
        SequenceBarrier sequenceBarrier = ringBuffer.newBarrier();

        //3.创建多个消费者
        Consumer[] consumers = new Consumer[10];
        for (int i = 0; i < consumers.length; i++) {
            consumers[i] = new Consumer("C" + i);
        }

        //4. 创建多消费者工作池
        WorkerPool<Order> orderWorkerPool = new WorkerPool<Order>(
                ringBuffer,
                sequenceBarrier,
                new ExceptionHandler<Order>() {
                    @Override
                    public void handleEventException(Throwable ex, long sequence, Order event) {

                    }

                    @Override
                    public void handleOnStartException(Throwable ex) {

                    }

                    @Override
                    public void handleOnShutdownException(Throwable ex) {

                    }
                },
                consumers
        );

        //5. 设置多个消费者的sequence，用于单独统计消费进度, 并且设置到ringbuffer中
        ringBuffer.addGatingSequences(orderWorkerPool.getWorkerSequences());

        //6.启动workpool
        orderWorkerPool.start(Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors()));

    }

}
