package com.testdb.demo.entity.letter;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class Letter extends BaseLetter {

    @TableId(type = IdType.AUTO)
    private long id;
    private String letterType;
    private String author;
    private LocalDate createTime;
    private String preview;
    private String content;
    private String annexUrl;
    private long starCount;
    private String targetUsername;
    private long commentCount;

}
