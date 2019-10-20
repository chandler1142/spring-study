package com.chandler.jvm;


import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;
import java.util.ArrayList;
import java.util.List;

/**
 * 需求：写一段程序，让其运行时的表现为触发5次ygc，然后3次fgc，然后3次ygc，然后1次fgc，请给出代码以及启动参数。
 * VM设置：-Xms50m -Xmx50m -Xmn10m -XX:+UseParallelGC -XX:+PrintGCDetails -XX:+PrintGCTimeStamps
 * */
public class GCTest2 {

    public static void main(String[] args) {
        getJvmInfo();
        List<Data> list = new ArrayList<>();
        for(int i=0;i<20;i++) {
            Data data = new Data();
            list.add(data);
            getJvmInfo();
        }
        System.out.println(123);
    }

    /**
     * 通过代码打印程序的堆、内存信息
     */
    public static void getJvmInfo() {
        System.out.println("-----------------------JVM-Info-start----------------");

        MemoryMXBean memoryMXBean = ManagementFactory.getMemoryMXBean();
        MemoryUsage mu = memoryMXBean.getHeapMemoryUsage();

        System.out.println("heapInfo:" + mu);
        System.out.println("初始化堆:" + mu.getInit() / 1024 / 1024 + "Mb");
        System.out.println("最大堆值:" + mu.getMax() / 1024 / 1024 + "Mb");
        System.out.println("已用堆值:" + mu.getUsed() / 1024 / 1024 + "Mb");

        MemoryUsage none = memoryMXBean.getNonHeapMemoryUsage();
        System.out.println("non-heap Info(非堆内存):" + none);


        List<String> args = ManagementFactory.getRuntimeMXBean().getInputArguments();
        System.out.println("运行时VM参数:" + args);

        System.out.println("运行时总内存" + Runtime.getRuntime().totalMemory() / 1024 / 1024);
        System.out.println("运行时空闲内存" + Runtime.getRuntime().freeMemory() / 1024 / 1024);
        System.out.println("运行时最大内存" + Runtime.getRuntime().maxMemory() / 1024 / 1024);

        System.out.println("-----------------------JVM-Info-end----------------");
        System.out.println("--");
        System.out.println("--");
        System.out.println("--");
    }

    static class Data {

        private byte[] data;

        public Data() {
            data = new byte[1024*1024];
        }

    }

}
