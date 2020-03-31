package com.testdb.demo.utils;

import lombok.Data;

@Data
public class AuthenticationBean {

    /**
     * 专用于JWT认证
     */

    String username;
    String password;
}
