package com.testdb.demo.controller.letter;

import com.testdb.demo.service.letter.StarService;
import com.testdb.demo.utils.response.AjaxResponseBody;
import com.testdb.demo.utils.response.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
