package com.testdb.demo.service.letter;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.testdb.demo.entity.letter.BaseLetter;
import com.testdb.demo.mapper.letter.BaseLetterMapper;
import org.springframework.stereotype.Service;

@Service
public class BaseLetterService extends ServiceImpl<BaseLetterMapper, BaseLetter> {


    public Page<BaseLetter> getBaseLetterList(String username, int index){
        return this.page(new Page<>(index,5),new QueryWrapper<BaseLetter>()
                .select("id", "letter_type", "preview", "create_time")
                .eq("author", username)
                .ne("letter_type", "3")
                .orderByDesc("create_time"));
    }

}
