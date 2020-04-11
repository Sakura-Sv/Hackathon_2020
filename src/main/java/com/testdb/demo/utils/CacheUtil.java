package com.testdb.demo.utils;

import com.testdb.demo.service.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.Set;

@Slf4j
@Component
public class CacheUtil {

    @Autowired
    RedisService redisService;

    /**
     * 清理评论缓存
     */
    @Async
    public void cleanMoodList(String aid){
        log.info("Begin to clean redis mood list cache");
        String regexp = "HearWind:CommentServicegetCommentList"+aid+"end*";
        Set<Object> results = redisService.scan(regexp);
        for(Object result: results){
            redisService.del(result.toString());
        }
    }

}
