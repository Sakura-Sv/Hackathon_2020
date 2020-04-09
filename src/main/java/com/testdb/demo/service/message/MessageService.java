package com.testdb.demo.service.message;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.testdb.demo.entity.message.Message;
import com.testdb.demo.mapper.message.MessageMapper;
import com.testdb.demo.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class MessageService extends ServiceImpl<MessageMapper, Message> {

    @Autowired
    RedisService redisService;

    public void sendMessage(String targetUsername,
                            String username,
                            String avatarUrl,
                            LocalDateTime createTime,
                            String content,
                            String level, long pid,
                            long targetId){
        if(redisService.lGetListSize(targetUsername) > 300){
            redisService.lTrim(targetUsername,0 ,200);
        }
        redisService.lLPush(targetUsername,
                new Message(username, avatarUrl, createTime, content, level, pid, targetId));
    }

    public List<Message> getMessageList(String username){
        List<Object> list = redisService.lGet(username, 0, -1);
        if(list == null) {
            return null;
        }
        List<Message> newList = new ArrayList<>();
        for(Object obj: list){
            newList.add((Message)obj);
        }
        return newList;
    }

    public Long countStar(long motherId){
        return redisService.sGetSetSize(motherId+".star");
    }

}
