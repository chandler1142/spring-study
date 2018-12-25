package com.chandler.context;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class MyDataContextListener  implements ServletContextListener {

    private ServletContext context = null;

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        this.context = servletContextEvent.getServletContext();
        context.setAttribute("myData", "this is myData");
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        this.context = null;
    }

}
