package com.chandler.pattern;

import java.util.concurrent.LinkedBlockingQueue;

public class ProducerAndConsumerTest {

    public static void main(String[] args) {
        LinkedBlockingQueue<Object> list = new LinkedBlockingQueue<>(5);

    }

    static class Producer implements Runnable {

        private LinkedBlockingQueue<Object> list;

        public Producer(LinkedBlockingQueue<Object> list) {
            this.list = list;
        }

        @Override
        public void run() {
            while (true) {
                try {
                    Object obj = new Object();
                    list.put(obj);
                    System.out.println("Producer produce one element and put into container, container size: " + list.size());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    static class Consumer implements Runnable {

        private LinkedBlockingQueue<Object> list;

        public Consumer(LinkedBlockingQueue<Object> list) {
            this.list = list;
        }

        @Override
        public void run() {

        }
    }

}
