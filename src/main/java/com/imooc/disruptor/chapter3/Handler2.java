package com.imooc.disruptor.chapter3;


import com.lmax.disruptor.EventHandler;

import java.util.UUID;

public class Handler2 implements EventHandler<Trade> {

    @Override
    public void onEvent(Trade event, long sequence, boolean endOfBatch) throws Exception {
        System.out.println("handler2: SET id");
        event.setId(UUID.randomUUID().toString());
        Thread.sleep(2000);
    }

}
