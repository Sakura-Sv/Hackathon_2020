package com.testdb.demo.entity.mood;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class WeekMood{

    private String moodType;
    private String preview;

    public WeekMood(String moodType, String preview){
        this.moodType = moodType;
        this.preview = preview;
    }

}


