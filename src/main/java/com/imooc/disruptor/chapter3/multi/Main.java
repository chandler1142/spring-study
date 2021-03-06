package com.imooc.disruptor.chapter3.multi;

import com.lmax.disruptor.*;
import com.lmax.disruptor.dsl.ProducerType;

import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {

    public static void main(String[] args) throws InterruptedException {

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
        ExecutorService pool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        orderWorkerPool.start(pool);

        final CountDownLatch latch = new CountDownLatch(1);

        //7. 设置100个生产者
        for (int i = 0; i < 100; i++) {
            final Producer producer = new Producer(ringBuffer);
            new Thread(new Runnable() {

                @Override
                public void run() {
                    try {
                        latch.await();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    for (int j = 0; j < 100; j++) {
                        producer.sendData(UUID.randomUUID().toString());
                    }
                }

            }).start();

        }

        Thread.sleep(2000);
        System.out.println("----------------线程创建完毕, 开始生产数据---------------");
        latch.countDown();

        Thread.sleep(5000);

        System.out.println("消费者处理的任务总数： " + consumers[0].getCount());
        pool.shutdown();

    }

}
