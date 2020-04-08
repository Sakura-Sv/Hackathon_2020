package com.testdb.demo.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.testdb.demo.entity.letter.Letter;
import com.testdb.demo.entity.user.BaseUser;
import com.testdb.demo.mapper.LetterMapper;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDate;


@Service
public class LetterService extends ServiceImpl<LetterMapper, Letter> {

    @Autowired
    LetterMapper letterMapper;

    @SneakyThrows
    public Letter getLetter(String id){
        return this.getById(id);
    }

    @SneakyThrows
    public Boolean checkInvalidLetterId(long letterId){
        return this.getOne(new QueryWrapper<Letter>().select("id").eq("id", letterId)) == null;
    }

    @SneakyThrows
    public void postLetter(Authentication token, Letter letter){
        BaseUser user = UserService.t2b(token);
        letter.setAuthor(user.getUsername());
        letter.setNickname(user.getNickname());

        int length = letter.getContent().length();
        if(length > 40) {
            letter.setPreview(letter.getContent().substring(0, 40) + "...");
        }
        else {
            letter.setPreview(letter.getContent());
        }

        letter.setCreateTime(LocalDate.now());
        this.save(letter);
    }

}
