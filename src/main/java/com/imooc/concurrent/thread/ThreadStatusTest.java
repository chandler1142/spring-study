package com.imooc.concurrent.thread;

public class ThreadStatusTest {

    static class RunnableThread extends Thread {
        public void run() {
            while (true) {
                if (Thread.currentThread().isInterrupted()) {
                    System.out.println("exit RunnableThread");
                    break;
                }
            }
        }
    }

    static class BlockedThread extends Thread {

        public synchronized static void doSomething() {
            System.out.println(Thread.currentThread().getName() + " entered...");
            while (true) {
                if (Thread.currentThread().isInterrupted()) {
                    System.out.println(Thread.currentThread().getName()  + " exit BlockedThread");
                    break;
                }
            }
        }

        @Override
        public void run() {
            doSomething();
        }
    }

    static class WaitThread extends Thread{

        @Override
        public void run(){
            synchronized (this){
                try {
                    wait();
                } catch (InterruptedException e) {
                    System.out.println("i am waiting but facing interruptexception now");
                }
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread thread1 = new Thread();
        System.out.println(thread1.getState());
        thread1.interrupt();
        System.out.println(thread1.isInterrupted());

        Thread thread2 = new Thread();
        thread2.start();
        thread2.join();
        System.out.println(thread2.getState());
        thread2.interrupt();
        System.out.println(thread2.isInterrupted());

        //对于处于new和terminated状态的线程对于中断是屏蔽的，也就是说中断操作对这两种状态下的线程是无效的

        System.out.println("=============================");
        RunnableThread runnableThread = new RunnableThread();
        runnableThread.start();
        System.out.println(runnableThread.getState());
        runnableThread.interrupt();
        System.out.println(runnableThread.isInterrupted());

        //Java将这种权力交给了我们的程序，Java给我们提供了一个中断标志位，我们的程序可以通过if判断中断标志位是否被设置来中断我们的程序而不是系统强制的中断
        //线程一旦发现自己的中断标志为被设置了，立马跳出死循环。这样的设计好处就在于给了我们程序更大的灵活性。

        System.out.println("=============================");

        Thread thread4 = new BlockedThread();
        thread4.setName("thread4");
        thread4.start();

        Thread thread5 = new BlockedThread();
        thread5.setName("thread5");
        thread5.start();

        Thread.sleep(1000);
        System.out.println(thread4.getState());
        System.out.println(thread5.getState());

        System.out.println(thread5.isInterrupted());
        System.out.println(thread5.getState());

        thread4.interrupt();
        //从输出结果看来，thread2处于BLOCKED状态，执行中断操作之后，该线程仍然处于BLOCKED状态，但是中断标志位却已被修改。这种状态下的线程和处于RUNNABLE状态下的线程是类似的，给了我们程序更大的灵活性去判断和处理中断。

        thread5.interrupt();

        System.out.println("=============================");


        Thread thread = new WaitThread();
        thread.start();

        Thread.sleep(500);
        System.out.println(thread.getState());
        thread.interrupt();
        Thread.sleep(1000);
        System.out.println(thread.isInterrupted());
    }
}
