package com.testdb.demo.controller;

import com.testdb.demo.entity.Mood;
import com.testdb.demo.service.MoodService;
import com.testdb.demo.utils.AjaxResponseBody;
import com.testdb.demo.utils.Result;
import com.testdb.demo.utils.ResultStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDate;

@RestController
@RequestMapping(value="/api/mood")
@AjaxResponseBody
public class MoodController {

    @Autowired
    MoodService ms;

//    @GetMapping
//    public Result<Void> getMoodList(){
//
//    }

    @PostMapping
    public Result<Void> addMood(Principal principal,
                                @RequestBody Mood mood){
        int status = ms.addMood(mood, principal.getName());
        if(status == 1){
            return Result.failure(ResultStatus.FAILURE.setMessage("今天已经记录过心情啦！"));
        }else if(status == 2){
            return Result.failure(ResultStatus.WRONG_PARAMETERS.setMessage("你的一句话太长啦！"));
        }
        return Result.success();
    }



}
