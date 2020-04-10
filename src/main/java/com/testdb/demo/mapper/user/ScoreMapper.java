package com.testdb.demo.mapper.user;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.testdb.demo.entity.user.Score;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface ScoreMapper extends BaseMapper<Score> {

    @Update("UPDATE score SET score = score + #{score} WHERE username = #{username}")
    void addScore(String username, int score);
}
