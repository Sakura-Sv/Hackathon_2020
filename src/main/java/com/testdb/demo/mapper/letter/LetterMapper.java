package com.testdb.demo.mapper.letter;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.testdb.demo.entity.letter.Letter;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface LetterMapper extends BaseMapper<Letter> {

    Letter getById(Long id);

    Letter getRandomLetter(Integer index, String letterType);

}
