package com.testdb.demo.service.message;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.testdb.demo.entity.message.Message;
import com.testdb.demo.mapper.message.MessageMapper;
import com.testdb.demo.service.RedisService;
import lombok.SneakyThrows;
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
                            String tips,
                            String content,
                            String preview,
                            Integer letterType,
                            Integer contentType,
                            long pid,
                            long targetId){
        if(redisService.lGetListSize(targetUsername) > 300){
            redisService.lTrim(targetUsername,0 ,200);
        }
        redisService.lLPush(targetUsername,
                new Message(username, avatarUrl,
                        createTime,
                        tips, content, preview,
                        letterType, contentType,
                        pid, targetId));
    }

    @SneakyThrows
    public List<Message> getMessageList(String username, int index)  {

        if(index<0){
            throw new Exception("Wrong Index");
        }

        List<Object> list = getOldList(username, index);

        if(list == null) {
            return null;
        }

        List<Message> newList = new ArrayList<>();
        for(Object obj: list){
            newList.add((Message)obj);
        }

        return newList;
    }

    public List<Object> getOldList(String username, int index) throws Exception {
        List<Object> list;
        long messageNum = redisService.lGetListSize(username);
        if(messageNum <= 20){
            list = redisService.lGet(username, 0, -1);
        }
        else if(messageNum <= index*20){
            long begin = messageNum/20*20;
            list = redisService.lGet(username, begin, -1);
        }
        else{
            list = redisService.lGet(username, (index -1) * 20, index*20);
        }
        return list;
    }

}
