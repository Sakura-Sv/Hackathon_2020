package com.testdb.demo.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.testdb.demo.entity.User;
import com.testdb.demo.mapper.UserMapper;
import com.testdb.demo.utils.UuidMaker;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class UserService extends ServiceImpl<UserMapper, User> {

    @Autowired
    UserMapper userMapper;

    @Autowired
    EmailServiceImpl emailServiceImpl;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Transactional
    @SneakyThrows
    public boolean signUp(User user) {
        // 加密密码
        String password = passwordEncoder.encode(user.getPassword());
        user.setPassword(password);
        user.setConfirmCode(UuidMaker.getUuid());
        // 设置不可用
        user.setEnable(false);
        // 插入
        userMapper.insert(user);
        sendConfirmMessage(user);
        Integer userId = userMapper.selectIdByUsername(user.getUsername());
        return userMapper.createUser(userId.toString(),"2");
    }

    @SneakyThrows
    public void sendConfirmMessage(User user) {
        String to = "1204736871@qq.com";
        String subject = "为" + user.getUsername() + "激活账户";
        String context = "<a href=\"http://127.0.0.1:8080/users/confirm/?confirmCode=" + user.getConfirmCode() +
                "\">激活请点击:" + user.getConfirmCode() + "</a>";
        emailServiceImpl.sendHtmlMail(to, subject, context);
    }

    @Transactional
    @SneakyThrows
    public Boolean confirmUser(String confirmCode){
        User user = userMapper.selectOne(new QueryWrapper<User>().eq("confirm_code", confirmCode));
        if(!user.getEnable()){
            user.setEnable(true);
            userMapper.update(user, new QueryWrapper<User>().eq("username", user.getUsername()));
        }
        return true;
    }

}
