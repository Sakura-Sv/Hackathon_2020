package com.testdb.demo.controller;

import com.testdb.demo.service.RedisService;
import com.testdb.demo.utils.AjaxResponseBody;
import com.testdb.demo.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/api/message")
@AjaxResponseBody
public class MessageController {

    @Autowired
    RedisService redisService;

    @GetMapping
    public Result<Void> getMessageList(Principal principal){
        System.out.println(redisService.lGet(principal.getName(), 0, -1));
        return Result.success();
    }

}
