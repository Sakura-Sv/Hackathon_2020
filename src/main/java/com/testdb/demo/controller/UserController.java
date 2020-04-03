package com.testdb.demo.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.testdb.demo.entity.BaseUser;
import com.testdb.demo.entity.User;
import com.testdb.demo.service.UserService;
import com.testdb.demo.utils.*;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;

@RestController
@RequestMapping(value="/api/user")
@AjaxResponseBody
public class UserController {

    /**
     * 用户基本信息相关操作
     */

    @Autowired
    private UserService us;

    @PostMapping("/signup")
    @SneakyThrows
    public Result<String> signUp(@Validated @RequestBody User user,
                                 BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return Result.failure(ResultStatus.WRONG_PARAMETERS.setMessage( "账号必须为可用的邮箱！"));
        }
        if(us.getOne(new QueryWrapper<User>().eq("username", user.getUsername()))!=null){
            return Result.failure(ResultStatus.WRONG_PARAMETERS.setMessage("该邮箱已被注册！"));
        }
        us.signUp(user);
        return Result.success("Success!");
    }

    @PostMapping("/signin")
    public User signIn(@RequestBody User user) {
        return user;
    }

    @GetMapping("/confirm")
    public Result<Void> confirmUser(@RequestParam String confirmCode){
        int status = us.confirmUser(confirmCode);
        if(status == 2){
            return Result.failure(ResultStatus.WRONG_PARAMETERS.setMessage("该验证码已过期！"));
        }
        else if(status == 1){
            return Result.failure(ResultStatus.WRONG_PARAMETERS.setMessage("该账号已被激活！"));
        }
        return Result.success();
    }

    @GetMapping(value = "/info")
    @SneakyThrows
    public Result<BaseUser> getUserInfo(Principal principal){
        BaseUser user = us.getBaseInfo(principal.getName());
        return Result.success(user);
    }

    @PostMapping("/info")
    @SneakyThrows
    public Result<Void> setUserInfo(Principal principal,
                                    @RequestBody JSONObject jsonParam){
        us.updateUserInfo(principal.getName(), jsonParam);
        return Result.success();
    }

    @GetMapping("/avatar")
    public Result<String> getAvatar(Principal principal){
        return Result.success(us.getOne(new QueryWrapper<User>()
                .select("avatar").eq("username", principal.getName()))
                .getAvatar());
    }

    @PostMapping("/avatar")
    public Result<Void> getAvatarToken(Principal principal){
        us.uploadAvatar(principal.getName());
        return Result.success();
    }

}



