package com.testdb.demo.service.message;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.testdb.demo.entity.message.Message;
import com.testdb.demo.entity.message.MessagePage;
import com.testdb.demo.mapper.message.MessageMapper;
import com.testdb.demo.service.RedisService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@CacheConfig(cacheNames = "MessageService")
public class MessageService extends ServiceImpl<MessageMapper, Message> {

    @Autowired
    private RedisService redisService;

    // 单页信息数
    private static final int SIZE = 20;

    @CacheEvict(key = "'getMessageList'+#username")
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
    @Cacheable(key = "#root.method.name+#username", unless = "#username==null")
    public MessagePage getMessageList(String username, int index)  {

        if(index<0){
            throw new Exception("Wrong Index");
        }

        MessagePage messagePage = new MessagePage();
        List<Object> list = getOldList(messagePage, username, index);

        if(list == null) {
            return null;
        }

        List<Message> newList = new ArrayList<>();
        for(Object obj: list){
            newList.add((Message)obj);
        }

        messagePage.setMessages(newList);
        return messagePage;
    }

    /**
     * 工具方法  用于获取信息列表   还需要将Object转化为Message
     * @param messagePage
     * @param username
     * @param index
     * @return
     * @throws Exception
     */
    public List<Object> getOldList(MessagePage messagePage, String username, int index) throws Exception {

        List<Object> list;
        long messageNum = redisService.lGetListSize(username);

        messagePage.setSize(SIZE);
        messagePage.setPages(getPageNum(messageNum));

        if(messageNum <= SIZE){
            list = redisService.lGet(username, 0, -1);
            messagePage.setCurrentPage(1);
        }
        else if(messageNum < index*SIZE){
            long begin = messageNum/SIZE*SIZE;
            list = redisService.lGet(username, begin, -1);
            messagePage.setCurrentPage(messagePage.getPages());
        }
        else{
            list = redisService.lGet(username, (index -1) * SIZE, index*SIZE);
            messagePage.setCurrentPage(index);
        }
        return list;
    }

    public long getPageNum(long messageNum){
        if(messageNum % SIZE == 0){
            return messageNum / SIZE;
        }
        else{
            return messageNum / SIZE + 1;
        }
    }

}
