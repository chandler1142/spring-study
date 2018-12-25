package com.chandler.servlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class MyServlet extends HttpServlet {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        handleLogic(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        handleLogic(req, resp);
    }

    @Override
    public void init() throws ServletException {
        logger.info(" this is init method");
    }

    private void handleLogic(HttpServletRequest req, HttpServletResponse resp) {
        logger.info("handle my logic");
        ServletContext sc = getServletContext();
        RequestDispatcher rd = null;
        rd = sc.getRequestDispatcher("/index.jsp");
        try {
            rd.forward(req, resp);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
