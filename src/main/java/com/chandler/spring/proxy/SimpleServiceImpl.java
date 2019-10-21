package com.chandler.spring.proxy;

import java.util.Date;

public class SimpleServiceImpl implements SimpleService {

    @Override
    public void consume() {
        System.out.println("consume...");
        new Date();
    }

}
