package com.testdb.demo.service.user;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.testdb.demo.entity.user.BaseUser;
import com.testdb.demo.entity.user.Score;
import com.testdb.demo.mapper.user.ScoreMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class ScoreService extends ServiceImpl<ScoreMapper, Score> {

    @Autowired
    private ScoreMapper scoreMapper;

    public static final long BASE_SCORE = 1;

    public Integer getScore(Authentication token){
        BaseUser user = UserService.t2b(token);
        Score score = this.getOne(new QueryWrapper<Score>().select("score").eq("username", user.getUsername()));
        return calculateLevel(score.getScore());
    }

    public Integer calculateLevel(long score) {
        if (score > 1200) {
            return 3;
        } else if (score > 400) {
            return 2;
        }
        return 1;
    }

}

