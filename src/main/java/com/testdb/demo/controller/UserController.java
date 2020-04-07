package com.testdb.demo.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.testdb.demo.entity.user.Avatar;
import com.testdb.demo.entity.user.BaseUser;
import com.testdb.demo.entity.user.User;
import com.testdb.demo.service.AvatarService;
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

    @Autowired
    private AvatarService as;

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
        switch(status){
            case 2: return Result.failure(ResultStatus.WRONG_PARAMETERS.setMessage("该验证码已过期！"));
            case 1: return Result.failure(ResultStatus.WRONG_PARAMETERS.setMessage("该账号已被激活！"));
            default: return Result.success();
        }
    }

    @GetMapping(value = "/info")
    @SneakyThrows
    public Result<BaseUser> getUserInfo(Principal principal,
                                        @RequestParam(value="username", required = false) String username){
        BaseUser user;
        if(username == null) {
            user = us.getBaseInfo(principal.getName());
        }
        else{
            user = us.getBaseInfo(username);
            if(user == null){
                return Result.failure(ResultStatus.WRONG_PARAMETERS.setMessage("不存在该用户！"));
            }
        }
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
        return Result.success(as.getOne(new QueryWrapper<Avatar>()
                .select("url").eq("username", principal.getName()))
                .getUrl());
    }

    @PostMapping("/avatar")
    public Result<String> getAvatarToken(Principal principal){
        String token = as.uploadAvatar(principal.getName());
        return Result.success(token);
    }

    @GetMapping("/forget")
    public Result<Void> getConfirmCode(@RequestParam String username){
        us.getConfirmCode(username);
        return Result.success();
    }

    @PostMapping("/forget")
    public Result<Void> findPassword(@RequestBody JSONObject jsonParam){
        int wrongConfirmCode = us.findPassword(jsonParam.getString("username"),
                jsonParam.getString("newPassword"),
                jsonParam.getString("confirmCode"));
        switch(wrongConfirmCode){
            case 3: return Result.failure(ResultStatus.FAILURE.setMessage("请先发起找回密码请求！"));
            case 2: return Result.failure(ResultStatus.FAILURE.setMessage("验证码已过期！"));
            case 1: return Result.failure(ResultStatus.WRONG_PARAMETERS.setMessage("验证码错误！"));
            default: return Result.success();
        }
    }

    @PostMapping("/password")
    public Result<Void> changPassword(Principal principal,
                                      @RequestBody JSONObject jsonParam){
        Boolean wrongPassword = us.changePassword(principal.getName(),
                jsonParam.getString("oldPassword"),
                jsonParam.getString("newPassword"));
        if(wrongPassword){
            return Result.failure(ResultStatus.WRONG_PARAMETERS.setMessage("密码错误！"));
        }
        return Result.success();
    }

}



