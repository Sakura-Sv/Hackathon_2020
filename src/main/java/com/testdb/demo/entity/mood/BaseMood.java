package com.testdb.demo.entity.mood;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BaseMood{

    /**
     * 用于随即返回两个人的心情包装    接口/api/mood/others
     */

    private String username;
    private String nickname;
    private String moodType;
    private String description;
    private String avatarUrl;
}

