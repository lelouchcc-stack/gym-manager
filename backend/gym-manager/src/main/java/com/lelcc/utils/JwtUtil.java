package com.lelcc.utils;

import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {
    @Value("${jwt.secret:gymmanagersjwtseretkey12345678901}")
    private String secret;
    @Value("${jwt.expiration:86400000}")
    private Long expire;

    public String createToken(Long user_id){
        Map<String,Object> map = new HashMap<>();
        map.put("user_id",user_id);
        map.put("exp",new Date(System.currentTimeMillis() + expire));

        return JWTUtil.createToken(map,secret.getBytes());
    }
    public boolean validateToken(String token){
        try {
            return JWTUtil.verify(token, secret.getBytes());
        }catch (Exception e){
            return false;
        }

    }

    public Long getUserId(String token){
        JWT jwt = JWTUtil.parseToken(token);
        return Long.valueOf(jwt.getPayloads().get("user_id").toString());
    }
}
