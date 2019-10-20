package com.imooc.disruptor.test;

import com.imooc.disruptor.chapter6.NettyServerApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Hello world!
 */
@SpringBootApplication
public class App {

    public static void main(String[] args) {
        SpringApplication.run(NettyServerApplication.class, args);
    }
}
