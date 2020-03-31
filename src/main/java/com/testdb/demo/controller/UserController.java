package com.testdb.demo.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.testdb.demo.entity.User;
import com.testdb.demo.service.UserService;
import com.testdb.demo.utils.AjaxResponseBody;
import com.testdb.demo.utils.Result;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
@RestController
@RequestMapping(value="/users")
@CrossOrigin(methods={RequestMethod.GET,RequestMethod.POST})
@AjaxResponseBody
public class UserController {

    /**
     * 用户基本信息相关操作
     */

    @Autowired
    private UserService us;

    @GetMapping("/name")
    @SneakyThrows
    public Result<User> getUserByName(Principal principal){
        User user = us.getOne(new QueryWrapper<User>().eq("username",principal.getName()));
        System.out.println(principal);
        user.setPassword(null);
        return Result.success(user);
    }

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
    public Result<Void> confirmUser(@RequestParam String confirmCode){
        us.confirmUser(confirmCode);
        return Result.success();
    }

    @GetMapping("/delete/{name}")
    @SneakyThrows
    public Result<Void> deleteByName(@PathVariable("name") String name){
        us.remove(new QueryWrapper<User>().eq("username", name));
        return Result.success();
    }

}



