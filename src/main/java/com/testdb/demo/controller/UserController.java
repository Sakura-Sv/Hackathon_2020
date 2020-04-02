package com.testdb.demo.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.testdb.demo.entity.User;
import com.testdb.demo.service.UserService;
import com.testdb.demo.utils.AjaxResponseBody;
import com.testdb.demo.utils.DateTimeUtil;
import com.testdb.demo.utils.Result;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;

@RestController
@RequestMapping(value="/users")
@AjaxResponseBody
public class UserController {

    /**
     * 用户基本信息相关操作
     */

    @Autowired
    private UserService us;

    @PostMapping("/signup")
    @SneakyThrows
    public Result<Void> signUp(@Validated @RequestBody User user){
        us.signUp(user);
        return Result.success();
    }

    @PostMapping("/signin")
    public User signIn(@RequestBody User user) {
        return user;
    }

    @GetMapping("/confirm")
    public Result<String> confirmUser(@RequestParam String confirmCode){
        int status = us.confirmUser(confirmCode);
        if(status == 2){
            return Result.failure("Your confirm code is expried!");
        }
        else if(status == 1){
            return Result.failure("Your account is already available!");
        }
        return Result.success("Success");
    }

    @GetMapping("/info")
    @SneakyThrows
    public Result<User> getUserInfo(Principal principal){
        User user = us.getOne(new QueryWrapper<User>().eq("username",principal.getName()));
        user.setPassword(null);
        return Result.success(user);
    }

    @PostMapping("/info")
    @SneakyThrows
    public Result<Void> setUserInfo(Principal principal,
                                    @RequestBody JSONObject jsonParam){
        String sex = jsonParam.getString("sex");
        Date birthday = jsonParam.getDate("birthday");
        String description = jsonParam.getString("description");

        User user = us.getOne(new QueryWrapper<User>().eq("username",principal.getName()));

        if(sex != null) {
            user.setSex(sex);
        }
        if(birthday != null) {
            user.setBirthday(DateTimeUtil.toLocalDateViaInstant(birthday));
        }
        if(description != null) {
            user.setDescription(description);
        }

        us.updateUserInfo(user);
        return Result.success();
    }

}



