package com.testdb.demo.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class QiniuCallbackMessage {

    /**
     * 用于构造七牛云回调信息对象
     */

    private String key;
    private String hash;
    private String bucket;
    private String username;
    private String callbackUrl;

}
