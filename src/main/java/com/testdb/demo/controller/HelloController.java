package com.testdb.demo.controller;

import com.testdb.demo.utils.response.Result;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller("/")
public class HelloController {

    @RequestMapping("/")
    public String hello(){
        return "index";
    }

}
