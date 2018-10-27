package com.zwdbj.server.mobileapi.shiro;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.apache.shiro.SecurityUtils;

import java.io.UnsupportedEncodingException;
import java.util.Date;

public class JWTUtil {
    // 过期时间30天
    public static final long EXPIRE_TIME = 30*24*3600;
    public static final String JWTTokenSecrect = "fdfdfddferereuyuidkvdkfhfnri,./klkdhdjjueieokmAASSISKSK";


    public static boolean verify(String token, String id) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(JWTTokenSecrect);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withClaim("id", id)
                    .build();
            DecodedJWT jwt = verifier.verify(token);
            return true;
        } catch (Exception exception) {
            return false;
        }
    }

    /**
     * 获得token中的信息无需secret解密也能获得
     * @return token中包含的用户名
     */
    public static String getId(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim("id").asString();
        } catch (JWTDecodeException e) {
            return null;
        }
    }
    public static long getCurrentId() {
        try {
            org.apache.shiro.subject.Subject subject = SecurityUtils.getSubject();
            if (subject.isAuthenticated()) {
                String token = (String) subject.getPrincipal();
                String id = getId(token);
                return Long.parseLong(id);
            }
            return 0;
        } catch (Exception ex) {
            return 0;
        }
    }

    public static String sign(String id) {
        try {
            Date date = new Date(System.currentTimeMillis()+EXPIRE_TIME*1000);
            Algorithm algorithm = Algorithm.HMAC256(JWTTokenSecrect);
            // 附带username信息
            return JWT.create()
                    .withClaim("id", id)
                    .withExpiresAt(date)
                    .sign(algorithm);
        } catch (UnsupportedEncodingException e) {
            return null;
        }
    }
}
