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

    /**
     * 发送通知
     * @param targetUsername  目标用户名
     * @param username  发送用户名
     * @param avatarUrl 发送用户头像路径
     * @param createTime 行为产生时间
     * @param tips 回复的提示信息
     * @param content 回复的内容
     * @param preview 被回复的内容预览
     * @param letterType 被回复的信的类型  1/2/3  幸运信 解忧信 回信
     * @param contentType 被回复的评论类型 1/2/3  点赞   评论  楼中楼
     * @param pid 信息父级id   仅可以为文章id/评论id
     * @param targetId 目标信息id  可以为文章id/评论id/回复id
     */
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

    /**
     * 获取个人通知列表
     * @param username 用户名
     * @param index 页码
     * @return
     */
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
     * @param messagePage 通知列表
     * @param username 用户名
     * @param index 第几页通知
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

    /**
     * 获取页码数
     * @param messageNum 通知数
     * @return
     */
    public long getPageNum(long messageNum){
        if(messageNum % SIZE == 0){
            return messageNum / SIZE;
        }
        else{
            return messageNum / SIZE + 1;
        }
    }

}
