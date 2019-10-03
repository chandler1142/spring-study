package com.chandler.test;

public class Test {

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        Integer sum = 0;
        for(int i=0;i<50000000;i++) {
            sum = add(sum, i);
        }
        long end = System.currentTimeMillis();
        System.out.println("Cost: " + (end - start));
        System.out.println(sum);
    }

    public static synchronized Integer add(Integer sum, Integer i) {
        sum = sum + i;
        return sum;
    }

}
