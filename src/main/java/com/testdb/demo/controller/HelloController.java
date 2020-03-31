package com.testdb.demo.controller;

import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import com.testdb.demo.utils.QiniuUtil;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.security.Key;
import java.sql.Time;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController("/test")
@PreAuthorize("@RbacAuthorityService.test(authentication)")
//@CrossOrigin(origins={"http://localhost:4200"},methods = {RequestMethod.GET})
public class HelloController {

    /**
     * 用于测试
     */

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

//    @ResponseBody
//    @GetMapping("/user")
//    public List<Map<String, Object>> map(){
//        List<Map<String, Object>> list = jdbcTemplate.queryForList("SELECT name, fullname FROM user");
//        return list;
//    }

    @GetMapping("/redis")
    public void testRedis(){
        stringRedisTemplate.opsForValue().set("lz", "hjh",3, TimeUnit.MINUTES);
        System.out.println(stringRedisTemplate.opsForValue().get("lz"));
    }

    @GetMapping("/url")
    public String getUrl(){
        String fileName = "test1.jpg";
        String domainOfBucket = "http://devtools.qiniu.com";
        String finalUrl = String.format("%s/%s", domainOfBucket, fileName);
        return finalUrl;
    }


    @ResponseBody
    @GetMapping("/token")
    public String test(){
        return QiniuUtil.getToken();
    }

}
