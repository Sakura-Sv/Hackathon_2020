package com.testdb.demo.entity.mood;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BaseMood{
    private String username;
    private String nickname;
    private String moodType;
    private String description;
    private String avatarUrl;
}

