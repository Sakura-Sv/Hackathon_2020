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
    // 0 点赞 1 评论信 2 评论评论 3 对回信的感谢
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
