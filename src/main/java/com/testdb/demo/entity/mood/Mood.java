package com.testdb.demo.entity.mood;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class Mood {

    @TableId(type = IdType.AUTO)
    private int id;
    private String moodType;
    private String preview;
    private String description;
    private LocalDate moodDate;
    private String dayOfWeek;
    private String username;
    private Boolean isPublic;

}
