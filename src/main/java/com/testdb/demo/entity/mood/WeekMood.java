package com.testdb.demo.entity.mood;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class WeekMood{

    /**
     * 用于一周心情包装
     */

    private String moodType;
    private String preview;

    public WeekMood(String moodType, String preview){
        this.moodType = moodType;
        this.preview = preview;
    }

}


