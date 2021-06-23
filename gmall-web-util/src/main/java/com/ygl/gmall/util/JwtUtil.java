// Copyright (C) 2021 Focus Media Holding Ltd. All Rights Reserved.

package com.ygl.gmall.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Map;

/**
 * TODO
 *
 * @author ygl
 * @version V1.0
 * @since 2021-06-22 17:03
 **/
public class JwtUtil {

    //加密
    /**
    *
    * TODO
     * 参数1：服务器密钥
     * 参数2：用户信息
     * 参数3：盐值
    * @return null
    * @author ygl
    * @date 2021/6/22 17:11
    **/
    public static String encode(String key, Map<String, Object> param, String salt) {

        if (salt != null) {
            key += salt;
        }
        JwtBuilder jwtBuilder = Jwts.builder().signWith(SignatureAlgorithm.HS256, key);

        jwtBuilder = jwtBuilder.setClaims(param);

        String token = jwtBuilder.compact();
        return token;

    }


    //解密
    public static Map<String, Object> decode(String token, String key, String salt) {

        Claims claims = null;
        if (salt != null) {
            key += salt;
        }
        try {
            claims = Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody();
        } catch (JwtException e) {
            return null;
        }
        return claims;
    }

}
