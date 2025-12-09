package com.example.electronicgamingplatform.util;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class JwtUtils {
    // 密钥（实际项目中应放在配置文件）
//    private static final String SECRET_KEY = "electronicgaming";
    private static final SecretKey SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    // 过期时间：2小时
    private static final long EXPIRATION_TIME = 7200000;
    // 模拟存储已注销的令牌
    private static final ConcurrentHashMap<String, Long> BLACKLIST = new ConcurrentHashMap<>();

    // 生成令牌
    public static String generateToken(String username) {
        Date now = new Date();
        Date expiration = new Date(now.getTime() + EXPIRATION_TIME);

        Map<String, Object> claims = new HashMap<>();
        claims.put("username", username);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expiration)
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    // 验证令牌
    public static boolean validateToken(String token) {
        try {
            // 检查是否在黑名单
            if (BLACKLIST.containsKey(token)) {
                return false;
            }

            Jwts.parserBuilder()
                    .setSigningKey(SECRET_KEY)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // 从令牌中获取用户名
    public static String getUsernameFromToken(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.get("username", String.class);
    }

    // 注销令牌（加入黑名单）
    public static void invalidateToken(String token) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(SECRET_KEY)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            // 存储令牌和过期时间
            BLACKLIST.put(token, claims.getExpiration().getTime());
        } catch (Exception e) {
            // 无效令牌直接忽略
        }
    }
}