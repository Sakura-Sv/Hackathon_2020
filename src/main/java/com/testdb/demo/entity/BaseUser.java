package com.testdb.demo.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Data
@NoArgsConstructor
public class BaseUser {

    /**
     * 工具人类，用于安全的描述用户
     */

    private String username;
    private LocalDate birthday;
    private String sex;
    private String description;
    private String nickname;
    private String avatar;
    private String address;
}
