package org.simple.controller;

import org.simple.annotation.Autowired;
import org.simple.annotation.Controller;
import org.simple.annotation.RequestMapping;
import org.simple.bean.Data;
import org.simple.service.TestService;

@Controller
public class TestController {

    @Autowired
    private TestService service;

    @RequestMapping("/test")
    public Data test(){
        String test = service.test();

        return new Data(test);
    }
}
