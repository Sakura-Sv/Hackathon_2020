package com.testdb.demo.controller;

import com.alibaba.fastjson.JSONObject;
import com.testdb.demo.service.StarService;
import com.testdb.demo.utils.AjaxResponseBody;
import com.testdb.demo.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/star")
@AjaxResponseBody
public class StarController {

    @Autowired
    StarService ss;

//    @GetMapping
//    public Result<Void> star(Principal principal,
//                             @RequestBody JSONObject jsonObject){
//        ss.star(principal.getName(),
//                jsonObject.getString("username"),
//                jsonObject.getLongValue("motherId"));
//        return Result.success();
//    }

    @GetMapping("/count")
    public Result<Long> star(@RequestParam long letterId ){
        return Result.success(ss.countStar(letterId));
    }

}
