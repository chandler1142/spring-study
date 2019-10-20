package com.imooc.disruptor.test.rest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class RestService {

    @RequestMapping("/listStudent")
    public String listStudent() {

        return "listStudent";
    }

}
