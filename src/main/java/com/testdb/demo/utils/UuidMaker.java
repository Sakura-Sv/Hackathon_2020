package com.testdb.demo.utils;

import lombok.SneakyThrows;
import org.apache.commons.codec.binary.Hex;

import java.security.MessageDigest;
import java.util.Random;
import java.util.UUID;

public class UuidMaker {

    /**
     * 获取UUID4
     * @return
     */
    @SneakyThrows
    public static String getUuid(){
        MessageDigest salt = MessageDigest.getInstance("SHA-256");
        salt.update(UUID.randomUUID().toString().getBytes("UTF-8"));
        return Hex.encodeHexString(salt.digest());
    }

    /**
     * 获取6位数随机验证码
     * @return
     */
    public static String randomCode() {
        StringBuilder str = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 6; i++) {
            str.append(random.nextInt(10));
        }
        return str.toString();
    }

}
