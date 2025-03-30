//package com.github.backend.utils;
//
//import io.jsonwebtoken.*;
//import io.jsonwebtoken.security.Keys;
//import java.security.Key;
//import java.util.Date;
//
//public class Jwt {
//    private static final Key SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256); // 自动生成安全密钥
//    private static final long EXPIRATION_TIME = 86400000; // 24小时（毫秒）
//
//    // 生成 Token
//    public static String generateToken(String username) {
//        return Jwts.builder()
//                .setSubject(username)
//                .setIssuedAt(new Date())
//                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
//                .signWith(SECRET_KEY)
//                .compact();
//    }
//
//    // 验证 Token
//    public static boolean validateToken(String token) {
//        try {
//            Jwts.parserBuilder()
//                    .setSigningKey(SECRET_KEY)
//                    .build()
//                    .parseClaimsJws(token);
//            return true;
//        } catch (Exception e) {
//            return false;
//        }
//    }
//
//    // 从 Token 中提取用户名
//    public static String getUsernameFromToken(String token) {
//        return Jwts.parserBuilder()
//                .setSigningKey(SECRET_KEY)
//                .build()
//                .parseClaimsJws(token)
//                .getBody()
//                .getSubject();
//    }
//}
