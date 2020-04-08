package com.testdb.demo.entity.letter;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class Reply  {

    @TableId(type = IdType.AUTO)
    private Long id;
    private String commenterName;
    private String nickname;
    private String content;
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime commentTime;
    private String targetUsername;
    private String targetNickname;
    private Long commentId;
    private Long pid;
    @TableField(exist = false)
    private String avatarUrl;
    @TableField(exist = false)
    private String targetAvatarUrl;

}
