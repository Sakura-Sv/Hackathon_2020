package com.testdb.demo.entity.message;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class Message {

    // 行为发起者
    private String username;
    // 行为发起者的头像
    private String avatarUrl;
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime createTime;
    //行为信息
    private String content;
    /**
     *  level由两位数组成表示    第一位1/2/3 表示幸运信/解忧信/回信
     *                        第二位1/2/3 表示点赞/评论/楼中楼
     */
    private String level;
    private long pid;
    private long targetId;

    public Message(String username,
                   String avatarUrl,
                   LocalDateTime createTime,
                   String content,
                   String level,
                   long pid,
                   long targetId){
        this.username = username;
        this.avatarUrl = avatarUrl;
        this.createTime = createTime;
        this.content = content;
        this.level = level;
        this.pid = pid;
        this.targetId = targetId;
    }

}
