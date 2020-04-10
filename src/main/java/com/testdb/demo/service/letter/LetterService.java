package com.testdb.demo.service.letter;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.testdb.demo.entity.letter.Letter;
import com.testdb.demo.entity.user.BaseUser;
import com.testdb.demo.entity.user.User;
import com.testdb.demo.mapper.letter.LetterMapper;
import com.testdb.demo.service.message.MessageService;
import com.testdb.demo.service.user.UserService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Random;


@Service
public class LetterService extends ServiceImpl<LetterMapper, Letter> {

    @Autowired
    LetterMapper letterMapper;

    @Autowired
    UserService userService;

    @SneakyThrows
    public Letter getLetter(Long id){
        return letterMapper.getById(id);
    }

    @SneakyThrows
    public Boolean checkInvalidLetterId(long letterId){
        return this.getOne(new QueryWrapper<Letter>().select("id").eq("id", letterId)) == null;
    }

    @SneakyThrows
    public Letter getRandomLetter(Authentication token, String letterType){
        Letter letter = null;
        Random random = new Random();
        int letterNum = this.count();
        while(letter == null){
            int index = random.nextInt(letterNum);
            letter = letterMapper.getRandomLetter(index, letterType);
        }
        if(letter.getContent().length() > 80){
            letter.setContent(letter.getContent().substring(0,78)+"...");
        }
        letter.setLetterType(letterType);
        return letter;
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
        if(letter.getLetterType().equals("3")) {
            letter.setTargetUsername(this.getOne(new QueryWrapper<Letter>()
                    .select("author")
                    .eq("id", letter.getTargetLetterId()))
                    .getAuthor());
        }
        if(letter.getAnnexUrl()!=null){
            letter.setAnnexUrl("http://q81okm9pv.bkt.clouddn.com/"+letter.getAnnexUrl());
        }
        letter.setCreateTime(LocalDateTime.now());
        this.save(letter);

    }

}
