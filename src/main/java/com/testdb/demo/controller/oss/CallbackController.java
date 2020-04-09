package com.testdb.demo.controller.oss;

import com.testdb.demo.service.user.AvatarService;
import com.testdb.demo.utils.response.AjaxResponseBody;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/callback")
@AjaxResponseBody
@Slf4j
public class CallbackController {

    @Autowired
    AvatarService as;

    @PostMapping("/avatar")
    @SneakyThrows
    public void updateUserAvatar(HttpServletRequest request) {
        as.uploadAvatarCallback(request);
    }

}
