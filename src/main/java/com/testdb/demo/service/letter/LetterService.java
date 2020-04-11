package com.testdb.demo.service.letter;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.testdb.demo.entity.letter.Letter;
import com.testdb.demo.entity.user.BaseUser;
import com.testdb.demo.entity.user.User;
import com.testdb.demo.mapper.letter.LetterMapper;
import com.testdb.demo.service.RedisService;
import com.testdb.demo.service.message.MessageService;
import com.testdb.demo.service.user.ScoreService;
import com.testdb.demo.service.user.UserService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


@Service
public class LetterService extends ServiceImpl<LetterMapper, Letter> {

    @Autowired
    LetterMapper letterMapper;

    @Autowired
    UserService userService;

    @Autowired
    RedisService redisService;

    @Autowired
    ScoreService scoreService;

    @SneakyThrows
    public Letter getLetter(Long id){
        return letterMapper.getById(id);
    }

    @SneakyThrows
    public Boolean checkInvalidLetterId(long letterId){
        return this.getOne(new QueryWrapper<Letter>().select("id").eq("id", letterId)) == null;
    }

    @SneakyThrows
    public List<Long> getSameLetterTypeList(String letterType){
        List<Letter> letters = letterMapper.selectList(new QueryWrapper<Letter>()
                .select("id")
                .ne("letter_type", letterType));
        List<Long> results = new ArrayList<>();
        for(Letter letter: letters){
            results.add(letter.getId());
        }
        return results;
    }

    @SneakyThrows
    public List<Long> getMyLetterList(String username){
        List<Letter> letters = letterMapper.selectList(new QueryWrapper<Letter>()
                .select("id")
                .eq("author", username));
        List<Long> results = new ArrayList<>();
        for(Letter letter: letters){
            results.add(letter.getId());
        }
        return results;
    }

    @SneakyThrows
    public Letter getRandomLetter(Authentication token, String letterType){
        BaseUser user = UserService.t2b(token);
        Letter letter = null;
        Random random = new Random();
        int letterNum = this.count();

        List<Long> ids = new ArrayList<>();
        ids.addAll(getSameLetterTypeList(letterType));
        ids.addAll(getMyLetterList(user.getUsername()));

        while(letter == null){
            Long index = (long)(random.nextInt(letterNum)+1);
            if(ids.contains(index)){
                continue;
            }
            if(redisService.hGet("RandomLetter", user.getUsername())==index){
                continue;
            }
            letter = letterMapper.getRandomLetter(index, letterType);
        }
        if(letter.getContent().length() > 80){
            letter.setContent(letter.getContent().substring(0,78)+"...");
        }
        letter.setLetterType(letterType);
        redisService.hSet("RandomLetter", user.getUsername(), letter.getId());
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
        scoreService.addScore(user.getUsername(), ScoreService.POST_LETTER_SCORE);

    }

}
