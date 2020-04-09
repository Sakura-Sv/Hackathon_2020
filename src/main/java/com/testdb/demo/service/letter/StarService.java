package com.testdb.demo.service.letter;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.testdb.demo.entity.letter.Letter;
import com.testdb.demo.entity.user.BaseUser;
import com.testdb.demo.service.RedisService;
import com.testdb.demo.service.message.MessageService;
import com.testdb.demo.service.user.UserService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class StarService {

    @Autowired
    private MessageService messageService;

    @Autowired
    private RedisService redisService;

    @Autowired
    private LetterService letterService;

    @SneakyThrows
    public Boolean star(Authentication token, String targetUsername, long aid){
        BaseUser user = UserService.t2b(token);
        if( ! redisService.sHasKey(aid + ".star", user.getUsername())) {
            redisService.sSet(aid + ".star", user.getUsername());

            Letter targetLetter = letterService.getOne(new QueryWrapper<Letter>()
                    .select("letter_type").eq("id", aid));

            messageService.sendMessage(targetUsername,
                    user.getUsername(),
                    user.getAvatar(),
                    LocalDateTime.now(),
                    getStarContent(user.getNickname(), targetLetter.getLetterType()),
                    getStarLevel(targetLetter.getLetterType()),
                    aid, aid);
            return true;
        }
        return false;
    }

    public Long countStar(long letterId){
        return messageService.countStar(letterId);
    }

    public String getStarContent(String nickname, String letterType) throws Exception{
        switch(letterType) {
            case("1") : return "用户" + nickname + "和你击了掌";
            case("2") : return "用户" + nickname + "拥抱了你一下";
        }
        throw new Exception("Wrong Parameters");
    }

    public String getStarLevel(String letterType) throws Exception {
        switch(letterType) {
            // 前缀1指幸运信
            // 前缀2指解忧信
            case("1") : return "11";
            case("2") : return "21";
        }
        throw new Exception("错误的Letter Type");
    }
}
