package com.chandler.controller;

import com.chandler.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

public class UserController extends AbstractController {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    protected ModelAndView handleRequestInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
        List<User> userList = new ArrayList<>();

        User userA = new User();
        userA.setAge(13);
        userA.setUsername("Xiao A");
        userList.add(userA);

        User userB = new User();
        userB.setAge(18);
        userB.setUsername("Xiao B");
        userList.add(userB);

        String myDataValue = (String) getServletContext().getAttribute("myData");
        logger.info("myDataValue: {} ", myDataValue);
        return new ModelAndView("userlist", "users", userList);
    }
}