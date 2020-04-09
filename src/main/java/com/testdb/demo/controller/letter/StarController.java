package com.testdb.demo.controller.letter;

import com.alibaba.fastjson.JSONObject;
import com.testdb.demo.service.letter.StarService;
import com.testdb.demo.utils.response.AjaxResponseBody;
import com.testdb.demo.utils.response.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/star")
@AjaxResponseBody
public class StarController {

    @Autowired
    StarService ss;

    @GetMapping
    public Result<Void> star(Authentication token,
                             @RequestBody JSONObject jsonObject){
        ss.star(token,
                jsonObject.getString("targetUsername"),
                jsonObject.getLongValue("aid"));
        return Result.success();
    }

    @GetMapping("/count")
    public Result<Long> star(@RequestParam long letterId ){
        return Result.success(ss.countStar(letterId));
    }

}
