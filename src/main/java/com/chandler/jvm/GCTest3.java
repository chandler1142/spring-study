package com.chandler.jvm;


/**
 *
 * -verbose:gc -XX:+UseSerialGC  -Xms20M -Xmx20M -Xmn10M -XX:+PrintGCDetails -XX:SurvivorRatio=8
 */
public class GCTest3 {

    private static final int _1MB = 1024 * 1024;

    public static void main(String[] args) {
        byte[] a1, a2, a3, a4;
        showMemory();
        a1 = new byte[2 * _1MB];
        a2 = new byte[2 * _1MB];
        showMemory();
        a3 = new byte[2 * _1MB];
        showMemory();
        a4 = new byte[3 * _1MB];
        showMemory();


        System.out.println("exit");

    }

    private static void showMemory() {
        long total;
        long free;
        total = Runtime.getRuntime().totalMemory();
        free = Runtime.getRuntime().freeMemory();
        System.out.println("total:" + (total / 1024 / 1024) + "M \tfree:" + (free / 1024 / 1024) + "M \tused:"
                + ((total / 1024 / 1024) - (free / 1024 / 1024)) + "M");
    }

}
