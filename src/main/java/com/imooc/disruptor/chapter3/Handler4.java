package com.imooc.disruptor.chapter3;

import com.lmax.disruptor.EventHandler;

public class Handler4 implements EventHandler<Trade> {

    @Override
    public void onEvent(Trade event, long sequence, boolean endOfBatch) throws Exception {
        System.out.println("handler4: SET PRICE");
        event.setPrice(17.0);
    }

}
