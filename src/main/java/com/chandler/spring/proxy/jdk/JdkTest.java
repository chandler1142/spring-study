package com.chandler.spring.proxy.jdk;

import com.chandler.spring.proxy.ProxyService;
import com.chandler.spring.proxy.SimpleService;
import com.chandler.spring.proxy.SimpleServiceImpl;

public class JdkTest {

    public static void main(String[] args) {
        int count = 10;
        long jdkStart = System.currentTimeMillis();
        for (int j = 0; j < count; j++) {
            SimpleService service = new SimpleServiceImpl();
            SimpleService proxy = (SimpleService) ProxyService.jdkProxyObject(service);
            proxy.consume();
        }
        long jdkEnd = System.currentTimeMillis();

        System.out.println("==================================================");
        System.out.println("java.version:" + System.getProperty("java.version"));
        System.out.println("new count:" + count);
        System.out.println("jdk new proxy spend time(ms):" + (jdkEnd - jdkStart));
    }

}
