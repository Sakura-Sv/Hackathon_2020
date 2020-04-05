package com.testdb.demo.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.testdb.demo.entity.Mood;
import com.testdb.demo.mapper.MoodMapper;
import com.testdb.demo.utils.Result;
import com.testdb.demo.utils.ResultStatus;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Locale;

@Service
public class MoodService extends ServiceImpl<MoodMapper, Mood> {

    @Autowired
    MoodMapper moodMapper;

    @SneakyThrows
    public int addMood(Mood mood, String username){

        // 2 心情描述过长 1 今天已经填写过了 0 成功
//        if(moodMapper.selectOne(new QueryWrapper<Mood>()
//                .eq("user_id", username)
//                .eq("mood_date", LocalDate.now())
//                .select("id")) != null){
//            return 1;
//        }
        if(mood.getDescription().length() > 80) {
            return 2;
        }
        LocalDate now = LocalDate.now();
        mood.setUserId(username);
        mood.setMoodDate(now);
        mood.setDayOfWeek(now.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.ENGLISH));
        moodMapper.insert(mood);
        return 0;
    }
}
