package com.smc.smccloud.core.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.smc.smccloud.core.model.constants.Constants;
import com.smc.smccloud.core.model.dto.JwtEntity;
import io.jsonwebtoken.*;
import net.sf.json.util.JSONUtils;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Date;

/**
 * @Author lyc
 * @Date 2022/1/24 13:37
 * @Descripton TODO  jwt 工具类
 */
@Component
public class JwtUtil {


    /**
     * 校验是不是jwt签名 验证格式
     * @param token
     * @return
     */
    public static boolean isSigned(String token){
        return  Jwts.parser()
                .setSigningKey(Constants.JWT_TOKEN_KEY)
                .isSigned(token);
    }

    /**
     * 校验签名是否正确(token格式不对或者token失效 都返回false)
     * 可以用这个检验token是否失效  token格式是否正确
     * @param token
     * @return
     */
    public static boolean verifyToken(String token){
        try {
            Jwts.parser()
                .setSigningKey(Constants.JWT_TOKEN_KEY.getBytes(StandardCharsets.UTF_8)) //
                .parseClaimsJws(token);
            return true;
        }catch (JwtException e){
            System.out.println(e.getMessage());
            return false;
        }
    }

    /**
     * 获取payload 部分内容（即要传的信息）
     * 使用方法：如获取userId：getClaim(token).get("userId");
     * @param token
     * @return
     */
    public static Claims getClaim(String token) {
        Claims claims = null;
        try {
            claims = Jwts.parser()
                    .setSigningKey(Constants.JWT_TOKEN_KEY .getBytes(StandardCharsets.UTF_8))
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            return null;
        }
        return claims;
    }

    /**
     * 获取头部信息map
     * 使用方法 : getHeader(token).get("alg");
     * @param token
     * @return
     */
    public static JwsHeader getHeader(String token) {
        JwsHeader header = null;
        try {
            header = Jwts.parser()
                    .setSigningKey(Constants.JWT_TOKEN_KEY)
                    .parseClaimsJws(token)
                    .getHeader();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return header;
    }

    /**
     * 获取jwt发布时间
     */
    public static Date getIssuedAt(String token) {
        return getClaim(token).getIssuedAt();
    }

    /**
     * 获取jwt失效时间
     */
    public static Date getExpiration(String token) {
        try {
            return getClaim(token).getExpiration();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 验证token是否失效
     *
     * @param token
     * @return        true:过期   false:没过期
     */
    public static boolean isExpired(String token) {
        try {
            final Date expiration = getExpiration(token);
            if (expiration == null ) {
                return true;
            } else {
                return expiration.before(new Date());
            }
        } catch (ExpiredJwtException expiredJwtException) {
            expiredJwtException.getClaims();
            return true;
        }
    }

    public static void main(String[] args) {
        String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOlsiYXBwIl0sInNjb3BlIjpbInNtYyJdLCJleHAiOjE2ODExOTM2MzksImF1dGhvcml0aWVzIjpbIuW8gOWPkSJdLCJqdGkiOiIyYzIzZmU3Yi1hY2Q4LTQ1N2YtYTU4Mi1jMTNkYjUwMjBhYjEiLCJjbGllbnRfaWQiOiJDMTgwOTciLCJ1c2VybmFtZSI6IkMxODA5NyJ9.k3on4c8CJ47UPT882zmJEdZVPoFIyQN2vSikFePb1uc";
        boolean b = verifyToken(token);
        System.out.println("b = " + b);
    }
    
}
