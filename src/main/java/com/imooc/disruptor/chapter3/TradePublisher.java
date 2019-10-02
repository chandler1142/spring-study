package com.imooc.disruptor.chapter3;

import com.lmax.disruptor.EventTranslator;
import com.lmax.disruptor.dsl.Disruptor;

import java.util.Random;
import java.util.concurrent.CountDownLatch;

public class TradePublisher implements Runnable {

    private Disruptor<Trade> disruptor;
    private CountDownLatch latch;

    private static int PUBLISH_COUNT = 10;

    public TradePublisher(CountDownLatch latch, Disruptor<Trade> disruptor) {
        this.latch = latch;
        this.disruptor = disruptor;
    }

    @Override
    public void run() {
        TradeEventTranslator eventTranslator = new TradeEventTranslator();
        //新的提交任务的方式
        for (int i = 0; i < PUBLISH_COUNT; i++) {
            disruptor.publishEvent(eventTranslator);
        }
        latch.countDown();
    }

}

class TradeEventTranslator implements EventTranslator<Trade> {

    private Random random = new Random();

    @Override
    public void translateTo(Trade event, long sequence) {
        this.generateEvent(event);
    }

    private void generateEvent(Trade event) {
        event.setPrice(random.nextDouble() * 9999);
    }

}
