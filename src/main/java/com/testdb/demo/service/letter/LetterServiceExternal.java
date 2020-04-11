package com.testdb.demo.service.letter;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.testdb.demo.entity.letter.Letter;
import com.testdb.demo.mapper.letter.LetterMapper;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@CacheConfig(cacheNames = "LetterServiceExternal")
public class LetterServiceExternal {

    /**
     * 该类用于解耦LetterService服务，避免内部调用导致无法缓存
     * 为缓存提供切面
     */

    @Autowired
    LetterMapper letterMapper;

    @SneakyThrows
    @Cacheable(key = "#root.method.name+#username", unless = "#username==null")
    public List<Long> getMyLetterList(String username){
        List<Letter> letters = letterMapper.selectList(new QueryWrapper<Letter>()
                .select("id")
                .eq("author", username));
        List<Long> results = new ArrayList<>();
        for(Letter letter: letters){
            results.add(letter.getId());
        }
        return results;
    }

    @SneakyThrows
    @Cacheable(key = "#root.method.name+#letterType", unless = "#letterType==null")
    public List<Long> getSameLetterTypeList(String letterType){
        List<Letter> letters = letterMapper.selectList(new QueryWrapper<Letter>()
                .select("id")
                .ne("letter_type", letterType));
        List<Long> results = new ArrayList<>();
        for(Letter letter: letters){
            results.add(letter.getId());
        }
        return results;
    }
}
