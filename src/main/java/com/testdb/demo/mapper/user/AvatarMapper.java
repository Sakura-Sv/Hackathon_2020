package com.testdb.demo.mapper.user;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.testdb.demo.entity.user.Avatar;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface AvatarMapper extends BaseMapper<Avatar> {

}
