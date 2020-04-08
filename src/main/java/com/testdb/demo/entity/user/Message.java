package com.testdb.demo.entity.user;

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

    private String username;
    private String avatarUrl;
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime createTime;
    private String content;
    /**
     *  level由两位数组成表示    第一位1/2 表示幸运信/解忧信
     *                        第二位0/1/2/3 表示点赞/评论/楼中楼
     */
    private int level;
    private long motherId;

    public Message(String username, String avatarUrl, LocalDateTime createTime, String content, int level, long motherId){
        this.username = username;
        this.avatarUrl = avatarUrl;
        this.createTime = createTime;
        this.content = content;
        this.level = level;
        this.motherId = motherId;
    }

}
