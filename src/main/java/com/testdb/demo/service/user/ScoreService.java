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
    ScoreMapper scoreMapper;

    public static final long BASE_SCORE = 1;

//    public Score getTree(Authentication token){
//        BaseUser user = UserService.t2b(token);
//
//    }

}

