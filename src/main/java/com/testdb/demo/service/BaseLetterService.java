package com.testdb.demo.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.testdb.demo.entity.letter.BaseLetter;
import com.testdb.demo.mapper.BaseLetterMapper;
import org.springframework.stereotype.Service;

@Service
public class BaseLetterService extends ServiceImpl<BaseLetterMapper, BaseLetter> {

    public Page<BaseLetter> getBaseLetterList(String username, int index){
        return this.page(new Page<>(index,5),new QueryWrapper<BaseLetter>()
                .select("id", "preview", "create_time")
                .eq("author", username)
                .orderByDesc("create_time"));

    }
}
