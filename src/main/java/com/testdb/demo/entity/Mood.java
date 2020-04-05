package com.testdb.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import java.time.LocalDate;

@Data
@NoArgsConstructor
public class Mood {

    @TableId(type = IdType.AUTO)
    private String id;
    private int moodType;
    private String description;
    private LocalDate moodDate;
    private String dayOfWeek;
    private String userId;

}
