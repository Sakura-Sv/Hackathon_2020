package com.testdb.demo.service.letter;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.testdb.demo.entity.letter.Letter;
import com.testdb.demo.entity.user.BaseUser;
import com.testdb.demo.mapper.letter.LetterMapper;
import com.testdb.demo.service.RedisService;
import com.testdb.demo.service.user.ScoreService;
import com.testdb.demo.service.user.UserService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


@Service
@CacheConfig(cacheNames = "LetterService")
public class LetterService extends ServiceImpl<LetterMapper, Letter> {

    @Autowired
    LetterMapper letterMapper;

    @Autowired
    UserService userService;

    @Autowired
    RedisService redisService;

    @Autowired
    LetterServiceExternal letterServiceExternal;

    @Autowired
    ScoreService scoreService;

    /**
     * 获取一封信的信息
     * @param id
     * @return
     */
    @SneakyThrows
    public Letter getLetter(Long id){
        return letterMapper.getById(id);
    }

    /**
     * 检查信的有效性
     * @param letterId
     * @return
     */
    @SneakyThrows
    @Cacheable(key = "#root.method.name+#letterId", unless = "#letterId==null")
    public Boolean checkInvalidLetterId(long letterId){
        return this.getOne(new QueryWrapper<Letter>().select("id").eq("id", letterId)) == null;
    }

    /**
     * 随机获取一封信
     * @param token 用户信息
     * @param letterType 信的类型
     * @return
     */
    @SneakyThrows
    public Letter getRandomLetter(Authentication token, String letterType){
        BaseUser user = UserService.t2b(token);
        Letter letter = null;
        Random random = new Random();
        int letterNum = this.count();

        List<Long> ids = new ArrayList<>();
        ids.addAll(letterServiceExternal.getSameLetterTypeList(letterType));
        ids.addAll(letterServiceExternal.getMyLetterList(user.getUsername()));

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

    /**
     * 发送一封信
     * @param token
     * @param letter
     */
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
