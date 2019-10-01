package com.imooc.disruptor.chapter2;

import java.io.Serializable;

public class OrderEvent implements Serializable {

    private long value; //订单价格

    public long getValue() {
        return value;
    }

    public void setValue(long value) {
        this.value = value;
    }

}
