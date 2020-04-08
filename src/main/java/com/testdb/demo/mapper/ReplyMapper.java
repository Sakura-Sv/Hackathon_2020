package com.testdb.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.testdb.demo.entity.letter.Reply;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface ReplyMapper extends BaseMapper<Reply> {
}
