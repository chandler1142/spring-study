package com.imooc.disruptor.chapter3;

import com.lmax.disruptor.BusySpinWaitStrategy;
import com.lmax.disruptor.EventFactory;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.EventHandlerGroup;
import com.lmax.disruptor.dsl.ProducerType;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {


    public static void main(String[] args) throws InterruptedException {

        long start = System.currentTimeMillis();

        //构建一个线程池用于提交任务
        ExecutorService es1 = Executors.newFixedThreadPool(4);
        ExecutorService es2 = Executors.newFixedThreadPool(4);

        //1. 构建Disruptor
        Disruptor<Trade> disruptor = new Disruptor<Trade>(
                new EventFactory<Trade>() {
                    @Override
                    public Trade newInstance() {
                        return new Trade();
                    }
                },
                1024 * 1024,
                es2,
                ProducerType.SINGLE,
                new BusySpinWaitStrategy()
        );

        //2. 把消费者设置Disruptor中handleEventsWith

        //2.1 串行操作
//        EventHandlerGroup<Trade> eventHandlerGroup = disruptor
//                .handleEventsWith(new Handler1())
//                .handleEventsWith(new Handler2())
//                .handleEventsWith(new Handler3());
//
        //2.2 并行操作
        disruptor.handleEventsWith(new Handler1());
        disruptor.handleEventsWith(new Handler2());
        disruptor.handleEventsWith(new Handler3());

        //3. 启动Disruptor
        RingBuffer<Trade> ringBuffer = disruptor.start();

        CountDownLatch latch = new CountDownLatch(1);

        es1.submit(new TradePublisher(latch, disruptor));

        latch.await(); //主函数阻塞，进行向下

        es1.shutdown();
        es2.shutdown();
        disruptor.shutdown();

        System.out.println("总耗时： " + (System.currentTimeMillis() - start));

    }
}
