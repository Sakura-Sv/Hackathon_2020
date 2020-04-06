package com.testdb.demo.entity.letter;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class Comment {

    @TableId(type = IdType.AUTO)
    private long id;
    private String username;
    private String commentText;
    private LocalDateTime commentTime;
    private long letterId;
    @TableField(exist = false)
    private String avatarUrl;

}
