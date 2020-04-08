package com.testdb.demo.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.testdb.demo.entity.QiniuCallbackMessage;
import com.testdb.demo.entity.user.User;
import com.testdb.demo.service.UserService;
import com.testdb.demo.utils.QiniuUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping(value={("/api/test")})
@PreAuthorize("@RbacAuthorityService.test(authentication)")
@Slf4j
public class TestController {

    /**
     * 用于测试
     */

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private UserService us;

    @GetMapping
    public String test(){
        return QiniuUtil.getRandomKeyToken();
    }


    @GetMapping("/redis")
    public void testRedis() {
        stringRedisTemplate.opsForValue().set("lz", "hjh", 3, TimeUnit.MINUTES);
        System.out.println(stringRedisTemplate.opsForValue().get("lz"));
    }

    @GetMapping("/url")
    public String getUrl(Principal principal) {
        System.out.println(principal);
        String fileName = "test1.jpg";
        String domainOfBucket = "http://devtools.qiniu.com";
        String finalUrl = String.format("%s/%s", domainOfBucket, fileName);
        return finalUrl;
    }

    @SneakyThrows
    @PostMapping("/callback")
    public void callback(HttpServletRequest request) {
        byte[] callbackBody = new byte[2048];
        request.getInputStream().read(callbackBody);
        QiniuCallbackMessage message = JSON.parseObject(callbackBody, QiniuCallbackMessage.class);
        String username = message.getUsername();
        if(QiniuUtil.validateCallback(request, callbackBody)){
            String newUrl = "http://q81okm9pv.bkt.clouddn.com/avatar/"+username;
            us.update(new UpdateWrapper<User>().eq("username", username)
                    .set("avatar", newUrl));
        }
    }
}
