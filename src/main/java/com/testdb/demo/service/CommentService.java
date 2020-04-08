package com.testdb.demo.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.testdb.demo.entity.letter.Comment;
import com.testdb.demo.entity.letter.Letter;
import com.testdb.demo.entity.user.BaseUser;
import com.testdb.demo.entity.user.Message;
import com.testdb.demo.entity.user.User;
import com.testdb.demo.mapper.CommentMapper;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class CommentService extends ServiceImpl<CommentMapper, Comment>{

    @Autowired
    private MessageService messageService;

//    @SneakyThrows
//    public Boolean checkInvalidCommentId(long commentId){
//        return this.getOne(new QueryWrapper<ReplyComment>().select("id").eq("id", commentId)) == null;
//    }

//    @SneakyThrows
//    public Page<Comment> getCommentList( int index, long motherId, int level){
//        Page<Comment> page = new Page<>(index, 20);
//        return page.setRecords(this.baseMapper.getCommentList(motherId, level, page));
//    }

    @SneakyThrows
    public void postComment(Authentication token, Comment comment){
        BaseUser user = UserService.t2b(token);
        LocalDateTime postTime = LocalDateTime.now();
        comment.setNickname(user.getNickname());
        comment.setCommentTime(postTime);
        comment.setCommenterName(user.getUsername());
        this.save(comment);
//        messageService.sendMessage(user.getUsername(),
//                user.getAvatar(),
//                comment.getCommenterName(),
//                comment.getCommentTime(),
//                comment.getCommentText(),
//                comment.getLevel(),
//                comment.getMotherId());
    }

}
