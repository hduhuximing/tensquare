package com.tensquare.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import java.text.SimpleDateFormat;

/**
 * @author: 肖德子裕
 * @date: 2018/12/02 20:25
 * @description: 测试解析令牌
 */
public class ParseJwtTest {
    public static void main(String[] args) {
        Claims claims = Jwts.parser().setSigningKey("itcast")
                .parseClaimsJws("eyJhbGciOiJIUzI1NiJ9" +
                        ".eyJqdGkiOiI2NjYiLCJzdWIiOiJ4ZHp5IiwiaWF0IjoxNTQzODIxNTk0LCJleHAiOjE1NDM4MjE2NTQsInJvbGUiOiJwYXNzd29yZCJ9" +
                        ".QHDkSVH5Bygie3uHy_saLRtnjWv5RQfARgJRhYPXPJc")
                .getBody();
        System.out.println("用户ID:"+claims.getId());
        System.out.println("用户姓名:"+claims.getSubject());
        System.out.println("登录时间:"+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").
                format(claims.getIssuedAt()));
        System.out.println("过期时间:"+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").
                format(claims.getExpiration()));
        System.out.println("用户角色:"+claims.get("role"));
    }
}
