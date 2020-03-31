package com.testdb.demo.utils;

import lombok.SneakyThrows;
import org.apache.commons.codec.binary.Hex;

import java.security.MessageDigest;
import java.util.UUID;

public class UuidMaker {

    @SneakyThrows
    public static String getUuid(){
        MessageDigest salt = MessageDigest.getInstance("SHA-256");
        salt.update(UUID.randomUUID().toString().getBytes("UTF-8"));
        return Hex.encodeHexString(salt.digest());
    }

}
