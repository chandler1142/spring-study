package com.imooc.concurrent;

import java.io.ObjectInputStream;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;

public class QueueTest {

    public static void main(String[] args) {
        LinkedBlockingQueue<Object> queue = new LinkedBlockingQueue<>(20);

        try {
            for(int i=0;i<100;i++) {
                Object object = new Object();
                queue.add(object);
                System.out.println("add element to queue " + (i+1));
            }
        } catch (Exception e) {
            System.out.println("catch exception:" + e.getMessage());
        }

        System.out.println(1231);


    }

}
