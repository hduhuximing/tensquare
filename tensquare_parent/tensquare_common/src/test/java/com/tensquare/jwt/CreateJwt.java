package com.tensquare.jwt;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

/**
 * @author: 肖德子裕
 * @date: 2018/12/02 20:15
 * @description: 测试创建认证令牌
 */
public class CreateJwt {
    public static void main(String[] args) {
        JwtBuilder builder = Jwts.builder()
                .setId("666")
                .setSubject("xdzy")
                .setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS256, "itcast")
                .setExpiration(new Date(new Date().getTime()+60000))
                .claim("role","password");
        System.out.println(builder.compact());
    }
}
