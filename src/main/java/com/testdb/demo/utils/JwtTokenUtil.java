package com.testdb.demo.utils;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Date;

@Component
@Slf4j
public class JwtTokenUtil {

    /**
     * token工具类
     */

    private static InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("lz-jwt.jks"); // 寻找证书文件
    private static PrivateKey privateKey = null;
    private static PublicKey publicKey = null;

    static { // 将证书文件里边的私钥公钥拿出来
        try {
            KeyStore keyStore = KeyStore.getInstance("JKS"); // java key store 固定常量
            keyStore.load(inputStream, "123456".toCharArray());
            privateKey = (PrivateKey) keyStore.getKey("lz-jwt", "123456".toCharArray()); // jwt 为 命令生成整数文件时的别名
            publicKey = keyStore.getCertificate("lz-jwt").getPublicKey();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String generateToken(String subject, int expirationSeconds, String salt) {
        return Jwts.builder()
                .setClaims(null)
                .setSubject(subject)
                .setExpiration(new Date(System.currentTimeMillis() + expirationSeconds * 1000))
//                .signWith(SignatureAlgorithm.HS512, salt) // 不使用公钥私钥就这样
                .signWith(privateKey, SignatureAlgorithm.RS256)
                .compact();
    }

    public static String parseToken(String token) {
        String subject = null;
        try {
             Claims claims = Jwts.parserBuilder()
//                    .setSigningKey(salt) // 不使用公钥私钥就这样
                    .setSigningKey(publicKey)
                    .build()
                    .parseClaimsJws(token).getBody();
            subject = claims.getSubject();
        } catch (SignatureException e) {
            log.info("Invalid JWT signature.");
            log.trace("Invalid JWT signature trace: {}", e);
        } catch (MalformedJwtException e) {
            log.info("Invalid JWT token.");
            log.trace("Invalid JWT token trace: {}", e);
        } catch (ExpiredJwtException e) {
            log.info("Expired JWT token.");
            log.trace("Expired JWT token trace: {}", e);
        } catch (UnsupportedJwtException e) {
            log.info("Unsupported JWT token.");
            log.trace("Unsupported JWT token trace: {}", e);
        } catch (IllegalArgumentException e) {
            log.info("JWT token compact of handler are invalid.");
            log.trace("JWT token compact of handler are invalid trace: {}", e);
        }
        return subject;
    }

}