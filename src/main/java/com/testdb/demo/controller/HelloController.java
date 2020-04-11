package com.testdb.demo.controller;

import com.testdb.demo.service.user.UserService;
import com.testdb.demo.utils.response.Result;
import com.testdb.demo.utils.response.ResultStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller("/")
public class HelloController {

    @Autowired
    UserService us;

    @RequestMapping("/")
    public String hello(){
        return "index";
    }

    /**
     * 用户账号激活接口
     * @param confirmCode
     * @param model
     * @return
     */
    @GetMapping("/confirm")
    public String confirmUser(@RequestParam String confirmCode, Model model) {
        int status = us.confirmUser(confirmCode);
        switch(status){
            case 2: model.addAttribute("message","抱歉，您的激活码已过期");
            case 1: model.addAttribute("message","该账号已被激活哦");
            default: model.addAttribute("message","恭喜您，激活成功！");
        }
        return "confirm";
    }

}
