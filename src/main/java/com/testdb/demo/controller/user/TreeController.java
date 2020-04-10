package com.testdb.demo.controller.user;

import com.testdb.demo.entity.user.Score;
import com.testdb.demo.service.user.ScoreService;
import com.testdb.demo.utils.response.AjaxResponseBody;
import com.testdb.demo.utils.response.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/tree")
@AjaxResponseBody
public class TreeController {

    @Autowired
    ScoreService scoreService;

    @GetMapping
    public Result<Integer> getScore(Authentication token){
        return Result.success(scoreService.getScore(token));
    }

}
