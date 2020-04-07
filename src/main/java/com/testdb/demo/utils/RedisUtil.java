//package com.testdb.demo.utils;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.data.redis.core.StringRedisTemplate;
//
//public class RedisUtil {
//
//    @Autowired
//    private static RedisTemplate redisTemplate;
//
//    @Autowired
//    private static StringRedisTemplate stringRedisTemplate;
//
//    public static void hput(String key, String field, Object object){
//        redisTemplate.opsForHash().put(key, field, object);
//    }
//
//    public static void shput(String key, String field, String value) {
//        stringRedisTemplate.opsForHash().put(key, field, value);
//    }
//
//    public static Object hget(String key, String field){
//        return redisTemplate.opsForHash().get(key, field);
//    }
//
//    public static String shget(String key, String field) {
//        return (String)stringRedisTemplate.opsForHash().get(key, field);
//    }
//
//    public static void hdel(String key, String field){
//        redisTemplate.opsForHash().delete(key, field);
//    }
//
//    public static void shdel(String key, String field) {
//        stringRedisTemplate.opsForHash().delete(key, field);
//    }
//
//}
