package com.chandler.spring;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringBeanLifecycleApplication {

    public static void main(String[] args) throws InterruptedException {
        // 为面试而准备的Bean生命周期加载过程
        ApplicationContext context = new ClassPathXmlApplicationContext("Bean-Lifecycle.xml");
        Book book = (Book)context.getBean("book");
        System.out.println("Book name = " + book.getBookName());
        ((ClassPathXmlApplicationContext) context).destroy();

        // 完整的加载过程，当然了解的越多越好
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("SubBean-Lifecycle.xml");
        SubBook subBookClass = (SubBook) applicationContext.getBean("bookClass");
        System.out.println("BookSystemName = " + subBookClass.getBookSystem());
        ((ClassPathXmlApplicationContext) applicationContext).registerShutdownHook();
    }

}
