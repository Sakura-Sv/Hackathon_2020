package com.testdb.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.testdb.demo.entity.Mood;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface MoodMapper extends BaseMapper<Mood> {

    @Select("SELECT day_of_week, mood_type FROM mood " +
            "WHERE user_id = #{username} AND mood_date >= #{beginTime}")
    List<Map<String, String>> getWeekMoodList(String username, LocalDate beginTime);
}
