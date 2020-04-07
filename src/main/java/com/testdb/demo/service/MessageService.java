package com.testdb.demo.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.testdb.demo.entity.user.Message;
import com.testdb.demo.mapper.MessageMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class MessageService extends ServiceImpl<MessageMapper, Message> {

    @Autowired
    RedisService redisService;

    public void sendMessage(String username, String targetName, LocalDateTime createTime, int level, long motherId){
        redisService.lSet(username,
                new Message(targetName, createTime, level, motherId));
    }

    public void star(String username, String targetName, long motherId){
        if( ! redisService.sHasKey(motherId+ ".star", username)) {
            redisService.sSet(motherId + ".star", username);
            sendMessage(username, targetName, LocalDateTime.now(), 0, motherId);
        }
    }

    public Long countStar(long motherId){
        return (Long) redisService.sGetSetSize(motherId+".star");
    }

}
