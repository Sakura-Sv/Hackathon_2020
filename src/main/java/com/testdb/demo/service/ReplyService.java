package com.testdb.demo.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.testdb.demo.entity.letter.Comment;
import com.testdb.demo.entity.letter.Letter;
import com.testdb.demo.entity.letter.Reply;
import com.testdb.demo.entity.user.BaseUser;
import com.testdb.demo.entity.user.User;
import com.testdb.demo.mapper.ReplyMapper;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ReplyService extends ServiceImpl<ReplyMapper, Reply> {

    @Autowired
    ReplyMapper replyMapper;

    @Autowired
    CommentService commentService;

    @Autowired
    UserService userService;

    public void reply(Authentication token, Reply reply){
        BaseUser user = UserService.t2b(token);
        reply.setCommenterName(user.getUsername());
        reply.setNickname(user.getNickname());
        reply.setCommentTime(LocalDateTime.now());
        reply.setTargetNickname(userService
                .getOne(new QueryWrapper<User>()
                        .select("nickname")
                        .eq("username", reply.getTargetUsername()))
                .getNickname());
        this.save(reply);
    }

}
