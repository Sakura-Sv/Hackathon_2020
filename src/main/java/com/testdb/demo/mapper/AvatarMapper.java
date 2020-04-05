package com.testdb.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.testdb.demo.entity.Avatar;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface AvatarMapper extends BaseMapper<Avatar> {

}