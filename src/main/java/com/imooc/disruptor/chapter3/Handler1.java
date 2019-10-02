package com.imooc.disruptor.chapter3;


import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.WorkHandler;

public class Handler1 implements EventHandler<Trade>, WorkHandler<Trade> {

    //EventHandler
    @Override
    public void onEvent(Trade event, long sequence, boolean endOfBatch) throws Exception {
        this.onEvent(event);
    }

    //WorkHandler
    @Override
    public void onEvent(Trade event) throws Exception {
        System.out.println("handler1: SET NAME");
        event.setName("H1");
        Thread.sleep(1000);
    }

}
