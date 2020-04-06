package com.testdb.demo.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.testdb.demo.entity.letter.Comment;
import com.testdb.demo.mapper.CommentMapper;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService extends ServiceImpl<CommentMapper, Comment> {

    @Autowired
    CommentMapper commentMapper;

    @SneakyThrows
    public Page<Comment> getCommentList( int index, long letterId){
        Page<Comment> page = new Page<>(index, 20);
        return page.setRecords(this.baseMapper.getCommentList(letterId, page));
    }
}
