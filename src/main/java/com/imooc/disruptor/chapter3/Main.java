package com.imooc.disruptor.chapter3;

import com.lmax.disruptor.BusySpinWaitStrategy;
import com.lmax.disruptor.EventFactory;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

public class Main {


    public static void main(String[] args) throws InterruptedException {

        long start = System.currentTimeMillis();

        //构建一个线程池用于提交任务
        ExecutorService es1 = Executors.newFixedThreadPool(4);
        // Handler个数和线程数息息相关
        ExecutorService es2 = Executors.newFixedThreadPool(5);

        //1. 构建Disruptor
        Disruptor disruptor = null;
//        Disruptor<Trade> disruptor = new Disruptor(
//                new EventFactory<Trade>() {
//                    @Override
//                    public Trade newInstance() {
//                        return new Trade();
//                    }
//                },
//                1024 * 1024,
//                new ThreadFactory() {
//                    @Override
//                    public Thread newThread(Runnable r) {
//                        return new Thread(r);
//                    }
//                },
//                ProducerType.SINGLE,
//                new BusySpinWaitStrategy()
//        );

        //2. 把消费者设置Disruptor中handleEventsWith

        //2.1 串行操作
//        EventHandlerGroup<Trade> eventHandlerGroup = disruptor
//                .handleEventsWith(new Handler1())
//                .handleEventsWith(new Handler2())
//                .handleEventsWith(new Handler3());
//
        //2.2 并行操作
//        disruptor.handleEventsWith(new Handler1());
//        disruptor.handleEventsWith(new Handler2());
//        disruptor.handleEventsWith(new Handler3());

        //2.3 菱形操作
//        EventHandlerGroup<Trade> eventHandlerGroup = disruptor.handleEventsWith(new Handler1(), new Handler2());
//        eventHandlerGroup.then(new Handler3());

        //2.4 multi edge operation
//        Handler1 h1 = new Handler1();
//        Handler2 h2 = new Handler2();
//        Handler3 h3 = new Handler3();
//        Handler4 h4 = new Handler4();
//        Handler5 h5 = new Handler5();
//
//        disruptor.handleEventsWith(h1, h4);
//        disruptor.after(h1).handleEventsWith(h2);
//        disruptor.after(h4).handleEventsWith(h5);
//        disruptor.after(h2, h5).handleEventsWith(h3);

        //3. 启动Disruptor
//        RingBuffer<Trade> ringBuffer = disruptor.start();

        CountDownLatch latch = new CountDownLatch(1);

//        es1.submit(new TradePublisher(latch, disruptor));

        latch.await(); //主函数阻塞，进行向下

        es1.shutdown();
        es2.shutdown();
        disruptor.shutdown();

        System.out.println("总耗时： " + (System.currentTimeMillis() - start));

    }
}
