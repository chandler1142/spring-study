package com.imooc.disruptor.chapter3;

import com.lmax.disruptor.EventHandler;

public class Handler3 implements EventHandler<Trade> {

    @Override
    public void onEvent(Trade event, long sequence, boolean endOfBatch) throws Exception {
        System.out.println("handler3: "
                + "Name: " + event.getName()
                + " ID: " + event.getId()
                + " price: " + event.getPrice()
        +" INSTANCE: " + event.toString());
    }

}
