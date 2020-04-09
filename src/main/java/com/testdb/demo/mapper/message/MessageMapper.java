package com.testdb.demo.mapper.message;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.testdb.demo.entity.message.Message;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MessageMapper extends BaseMapper<Message> {
}
