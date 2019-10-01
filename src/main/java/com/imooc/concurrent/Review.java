package com.imooc.concurrent;

import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.LockSupport;

public class Review {


    public static void main(String[] args) throws InterruptedException {
        //Object 锁
//        final Object lock = new Object();

        Thread threadA = new Thread(new Runnable() {
            @Override
            public void run() {
                int sum = 0;
                for (int i = 0; i < 10; i++) {
                    sum += i;
                }
//                synchronized (lock) {
//                    try {
//                        lock.wait();
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
                LockSupport.park();
                System.out.println("sum = " + sum);
            }
        });

        threadA.start();

        Thread.sleep(1000);


        System.out.println(123123);

//        synchronized (lock) {
//            lock.notify();
//        }
        LockSupport.unpark(threadA);

    }




}
