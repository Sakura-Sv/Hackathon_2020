package com.testdb.demo.controller;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.testdb.demo.entity.QiniuCallbackMessage;
import com.testdb.demo.entity.User;
import com.testdb.demo.service.UserService;
import com.testdb.demo.utils.AjaxResponseBody;
import com.testdb.demo.utils.QiniuUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("/api/callback")
@AjaxResponseBody
@Slf4j
public class CallbackController {

    @Autowired
    UserService us;

    @PostMapping("/avatar")
    @SneakyThrows
    public void updateUserAvater(HttpServletRequest request){
        Map<String, Object> results = QiniuUtil.validateCallback(request, "");
        if((Boolean)results.get("valid")){
            QiniuCallbackMessage message = (QiniuCallbackMessage) results.get("message");
            String newUrl = "http://q81okm9pv.bkt.clouddn.com/avatar/"+message.getUsername();
            us.update(new UpdateWrapper<User>().eq("username", message.getUsername())
                    .set("avatar", newUrl));
        }
    }

}
