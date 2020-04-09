package com.testdb.demo.controller;

import com.testdb.demo.utils.response.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/hello")
public class HelloController {

    @GetMapping("/")
    public Result<String> greet(){
        return Result.success("Hello!");
    }

}
