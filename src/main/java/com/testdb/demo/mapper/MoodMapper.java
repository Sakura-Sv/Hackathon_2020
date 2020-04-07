package com.testdb.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.testdb.demo.entity.mood.BaseMood;
import com.testdb.demo.entity.mood.Mood;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Mapper
@Repository
public interface MoodMapper extends BaseMapper<Mood> {

    List<BaseMood> getOthersMoodList(String username, LocalDate currentDay, String userMoodType);

}
