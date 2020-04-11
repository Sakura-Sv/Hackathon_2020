package com.testdb.demo.service.letter;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.testdb.demo.entity.letter.Letter;
import com.testdb.demo.entity.user.BaseUser;
import com.testdb.demo.service.RedisService;
import com.testdb.demo.service.message.MessageService;
import com.testdb.demo.service.user.ScoreService;
import com.testdb.demo.service.user.UserService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    ScoreService scoreService;

    public static final Integer STAR_TYPE = 1;

    /**
     * 点赞
     * @param token 用户信息
     * @param targetUsername 点赞目标用户名
     * @param aid 点赞文章id
     * @return
     */
    @SneakyThrows
    public Boolean star(Authentication token, String targetUsername, long aid){
        BaseUser user = UserService.t2b(token);
        if( ! redisService.sHasKey(aid + ".star", user.getUsername())) {
            redisService.sSet(aid + ".star", user.getUsername());

            Letter targetLetter = letterService.getOne(new QueryWrapper<Letter>()
                    .select("preview", "letter_type").eq("id", aid));

            scoreService.addScore(targetUsername, ScoreService.STAR_SCORE);

            messageService.sendMessage(targetUsername,
                    user.getUsername(),
                    user.getAvatar(),
                    LocalDateTime.now(),
                    getStarTips(user.getNickname(), targetLetter.getLetterType()),
                    getStarContent(user.getNickname(), targetLetter.getLetterType()),
                    targetLetter.getPreview(),
                    Integer.parseInt(targetLetter.getLetterType()),
                    STAR_TYPE,
                    aid, aid);
            return true;
        }
        return false;
    }

    public String getStarTips(String nickname, String letterType) throws Exception {
        switch(letterType){
            case("1"): return "用户" + nickname + "和你击了掌";
            case("2") : return "用户" + nickname + "拥抱了你";
        }
        throw new Exception("Wrong Letter Type");
    }

    /**
     * 获取点赞数
     * @param letterId 信id
     * @return
     */
    public Long countStar(long letterId){
        return redisService.sGetSetSize(letterId + ".star");
    }

    public String getStarContent(String nickname, String letterType) throws Exception{
        switch(letterType) {
            case("1") : return "用户" + nickname + "和你击了掌";
            case("2") : return "用户" + nickname + "拥抱了你一下";
        }
        throw new Exception("Wrong Parameters");
    }

}
