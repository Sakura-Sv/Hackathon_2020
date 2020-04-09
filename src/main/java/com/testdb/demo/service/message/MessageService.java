package com.testdb.demo.service.message;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.testdb.demo.entity.user.Message;
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

    public void sendMessage(String username,
                            String avatarUrl,
                            String targetName,
                            LocalDateTime createTime,
                            String content,
                            int level, long motherId){
        redisService.lSet(username,
                new Message(targetName, avatarUrl, createTime, content, level, motherId));
    }

    public List<Message> getMessageList(String username){
        List<Object> list = redisService.lGet(username, 0, -1);
        if(list == null) {
            return null;
        }
        List<Message> newList = new ArrayList<>();
        for(Object obj: list){
            newList.add((Message)obj);
            System.out.println(newList);
        }
        Collections.reverse(newList);
        return newList;
    }

//    public void star(String username, String targetName, long motherId){
//        if( ! redisService.sHasKey(motherId + ".star", username)) {
//            redisService.sSet(motherId + ".star", username);
//            sendMessage(username, targetName, LocalDateTime.now(), 0, motherId);
//        }
//    }

    public Long countStar(long motherId){
        return redisService.sGetSetSize(motherId+".star");
    }

}
