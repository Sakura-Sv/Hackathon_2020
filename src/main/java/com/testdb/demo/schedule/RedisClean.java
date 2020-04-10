package com.testdb.demo.schedule;

import com.testdb.demo.service.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.ConvertingCursor;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Set;

@Component
@Slf4j
public class RedisClean {

    @Autowired
    RedisService redisService;

    @Autowired
    RedisTemplate redisTemplate;

    @Scheduled(cron="0 0 4 * * ?")
    public void cleanMoodList(){
        /**
         * 每天四点整开始定时清理个人MoodList的缓存
         */
        log.info("Begin to clean redis mood list cache");
        String regexp = "HearWind:MoodServicegetMoodList*";
        Set<Object> results = redisService.scan(regexp);
        for(Object result: results){
            redisService.del(result.toString());
        }
    }


}