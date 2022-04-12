package com.yrnet.viewweb.common.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.yrnet.viewweb.common.authentication.JwtToken;
import com.yrnet.viewweb.common.entity.ViewWebConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * All rights Reserved, Designed By SEGI
 * <pre>
 * Copyright:  Copyright(C) 2018
 * Company:    SEGI.
 * @Author:     dengbp
 * @Date: 2018/7/27
 * </pre>
 * <p>
 *    jwt工具
 * </p>
 */
public class JWTUtil {
    static Logger log = LoggerFactory.getLogger(JWTUtil.class);


    /**
     * 校验token是否正确
     * @param token 密钥
     * @param secret 用户的密码
     * @return 是否正确
     */
    public static boolean verify(String token, String username, String secret) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withClaim("userName", username)
                    .build();
            DecodedJWT jwt = verifier.verify(token);
            return true;
        } catch (Exception exception) {
            exception.printStackTrace();
            log.error(exception.getMessage());
            return false;
        }
    }
    /**
     * 生成签名,5min后过期
     * @param username 用户名
     * @param secret 用户的密码
     * @return 加密的token（Hash-based Message Authentication Code：哈希消息认证码）
     */
    public static String sign(String username, String secret,long expireTime) {
        try {
            Date date = new Date(System.currentTimeMillis()+expireTime);
            Algorithm algorithm = Algorithm.HMAC256(secret);
            // 附带username信息
            return JWT.create()
                    .withClaim("userName", username)
                    .withExpiresAt(date)
                    .sign(algorithm);
        } catch (UnsupportedEncodingException e) {
            return null;
        }
    }

    /**
     * 获得token中的信息无需secret解密也能获得
     * @return token中包含的用户名
     */
    public static String getUsername(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim("userName").asString();
        } catch (JWTDecodeException e) {
            return null;
        }
    }

    /**
     * 加密
     * @param userNmae 用户名
     * @param userPassWord 用户密码
     * @param expireTime 过期时间
     * @param publicKey 加密公匙
     * @return
     */
    public static JwtToken getToken(final String userNmae, final String userPassWord, final Long expireTime, final String publicKey) {
        String token;
        try {
            //先RSA加密
            token = RsaEncryptUtil.encrypt(userPassWord, publicKey);

            //二次JWT加密
            Date date = new Date(System.currentTimeMillis() + expireTime);
            token = JWT.create()
                    .withIssuer("authToken")
                    .withClaim("userName", userNmae)
                    .withClaim("userPassWord", token)
                    .withExpiresAt(date)
                    .sign(Algorithm.HMAC256("SEGI-License"));


        } catch (Exception e) {
            e.printStackTrace();
            log.error("getToken=====>>", e.getMessage());
            return null;
        }
        return new JwtToken(token);
    }

    /**
     * 解密token
     * @param token 前端传入加密后的字符串
     * @param privateKey 解密私匙
     * @return
     */
    public static Map<String, String> deToken(final String token, final String privateKey) {
        Map<String, String> jwtMap = new HashMap<>(2);
        try {
            //先JWT解密
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256("myCarAlgoritHm"))
                    .withIssuer("authToken")
                    .build();
            DecodedJWT jwt = verifier.verify(token);

            //二次进行RSA解密
            if(null != jwt) {
                String userName = jwt.getClaim("userName").asString();
                String userPassWord = jwt.getClaim("userPassWord").asString();

                userPassWord = RsaEncryptUtil.decrypt(userPassWord, privateKey);

                jwtMap.put("userName", userName);
                jwtMap.put("passWord", userPassWord);
            }
        } catch (Exception e) {
            log.error("getToken=====>>", e.getMessage());
            return jwtMap;
        }
        return jwtMap;
    }

    /**
     * token 加密
     *
     * @param token token
     * @return 加密后的 token
     */
    public static String encryptToken(String token) {
        try {
            EncryptUtil encryptUtil = new EncryptUtil(ViewWebConstant.TOKEN_CACHE_PREFIX);
            return encryptUtil.encrypt(token);
        } catch (Exception e) {
            log.info("token加密失败：", e);
            return null;
        }
    }

    /**
     * token 解密
     *
     * @param encryptToken 加密后的 token
     * @return 解密后的 token
     */
    public static String decryptToken(String encryptToken) {
        try {
            EncryptUtil encryptUtil = new EncryptUtil(ViewWebConstant.TOKEN_CACHE_PREFIX);
            return encryptUtil.decrypt(encryptToken);
        } catch (Exception e) {
            log.info("token解密失败：", e);
            return null;
        }
    }
}
