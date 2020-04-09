package com.testdb.demo.entity.message;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Message {

    // 行为发起者
    private String username;
    // 行为发起者的头像
    private String avatarUrl;
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime createTime;
    /**
     * tips xxx点赞了你
     * content 233333333我也觉得
     * preview 卢本伟牛逼！！！！！！！！
     */
    private String tips;
    private String content;
    private String preview;
    /**
     *      letterType  1/2/3 表示幸运信/解忧信/回信
     *      contentType 1/2/3 表示点赞/评论/楼中楼
     */
    private Integer letterType;
    private Integer contentType;
    private long pid;
    private long targetId;

}
