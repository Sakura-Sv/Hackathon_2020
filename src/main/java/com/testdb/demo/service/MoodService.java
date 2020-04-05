package com.testdb.demo.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.testdb.demo.entity.Mood;
import com.testdb.demo.mapper.MoodMapper;
import com.testdb.demo.utils.WeekUtil;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.*;


@Service
public class MoodService extends ServiceImpl<MoodMapper, Mood> {

    @Autowired
    MoodMapper moodMapper;

    @SneakyThrows
    public int addMood(Mood mood, String username){

        // 2 心情描述过长 1 今天已经填写过了 0 成功
        if(moodMapper.selectOne(new QueryWrapper<Mood>()
                .eq("user_id", username)
                .eq("mood_date", LocalDate.now())
                .select("id")) != null){
            return 1;
        }
        if(mood.getDescription().length() > 80) {
            return 2;
        }
        LocalDate now = LocalDate.now();
        mood.setUserId(username);
        mood.setMoodDate(now);
        mood.setDayOfWeek(now.getDayOfWeek().getDisplayName(TextStyle.SHORT, Locale.ENGLISH));
        moodMapper.insert(mood);
        return 0;
    }

    @SneakyThrows
    public String getCurrentDay(){
        return LocalDate.now()
                .getDayOfWeek()
                .getDisplayName(TextStyle.SHORT, Locale.ENGLISH);
    }


    @SneakyThrows
    public Map<String, Integer> getMoodList(String username){
        LocalDate beginTime = LocalDate.now().minusDays(6);
        List<Map<String, String>> oldList = moodMapper.getWeekMoodList(username, beginTime);
        Map<String, Integer> newList = createWeekMoodList();
        for(Map<String, String> day: oldList){
            newList.put(day.get("day_of_week"), Integer.parseInt(day.get("mood_type")));
        }
        return newList;
    }

    @SneakyThrows
    public Map<String, Integer> createWeekMoodList(){
        Map<String, Integer> list = new LinkedHashMap<>();
        String currentDay = getCurrentDay();
        list.put(currentDay, 0);
        for(int i=0;i<6;++i){
            String lastDay = WeekUtil.last(currentDay);
            list.put(lastDay, 0);
            currentDay = lastDay;
        }
        return list;
    }

}
