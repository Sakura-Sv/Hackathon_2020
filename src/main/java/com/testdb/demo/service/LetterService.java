package com.testdb.demo.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.testdb.demo.entity.letter.Comment;
import com.testdb.demo.entity.letter.Letter;
import com.testdb.demo.mapper.LetterMapper;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;


@Service
public class LetterService extends ServiceImpl<LetterMapper, Letter> {

    @Autowired
    LetterMapper letterMapper;

    @SneakyThrows
    public Letter getLetter(String id){
        return this.getById(id);
    }

    @SneakyThrows
    public void postLetter(String username, Letter letter){
        letter.setAuthor(username);
        letter.setPreview(letter.getContent().substring(0,40)+"...");
        letter.setCreateTime(LocalDate.now());
        this.save(letter);
    }

}
