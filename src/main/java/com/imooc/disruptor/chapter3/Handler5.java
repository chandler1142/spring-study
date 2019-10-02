package com.imooc.disruptor.chapter3;

import com.lmax.disruptor.EventHandler;

public class Handler5 implements EventHandler<Trade> {

    @Override
    public void onEvent(Trade event, long sequence, boolean endOfBatch) throws Exception {
        System.out.println("handler5: GET PRICE : " + event.getPrice());
    }

}