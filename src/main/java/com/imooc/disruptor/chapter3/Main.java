package com.imooc.disruptor.chapter3;

import com.lmax.disruptor.BusySpinWaitStrategy;
import com.lmax.disruptor.EventFactory;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {


    public static void main(String[] args) throws InterruptedException {

        long start = System.currentTimeMillis();

        //构建一个线程池用于提交任务
        ExecutorService es = Executors.newFixedThreadPool(4);

        //1. 构建Disruptor
        Disruptor<Trade> disruptor = new Disruptor<Trade>(
                new EventFactory<Trade>() {
                    @Override
                    public Trade newInstance() {
                        return new Trade();
                    }
                },
                1024 * 1024,
                Executors.newFixedThreadPool(64),
                ProducerType.SINGLE,
                new BusySpinWaitStrategy()
        );

        //2. 把消费者设置Disruptor中handleEventsWith

        //3. 启动Disruptor
        RingBuffer<Trade> ringBuffer = disruptor.start();

        CountDownLatch latch = new CountDownLatch(1);

        es.submit(new TradePublisher(latch, disruptor));

        latch.await(); //主函数阻塞，进行向下

        es.shutdown();
        disruptor.shutdown();

        System.out.println("总耗时： " + (System.currentTimeMillis() - start));

    }
}
