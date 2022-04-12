package com.yrnet.viewweb.common.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author liuQu
 * @date 2020/9/22 17:16
 */
public class JWTSCDPUtil {

    private static Logger logger = LoggerFactory.getLogger(JWTUtil.class);

    /**
     * token秘钥，自定义秘钥，请勿随便修改
     */
    public static final String SECRET = "JKKLJOoasdlfj";

    /**
     * JSON Web Token(JWT)由三部分组成构成: header, payload, signature,它们之间用圆点(.)连接
     */
    public static String createToken() throws Exception {
        Date iatDate = new Date();
        // 过期时间
        Calendar nowTime = Calendar.getInstance();
        nowTime.add(Calendar.SECOND, 60);
        Date expiresDate = nowTime.getTime();
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        /**
         * header组成部门
         * 1.签名算法名称
         * 2.token类型
         */
        Map<String, Object> headerMap = new HashMap<String, Object>();
//        headerMap.put("alg", SignatureAlgorithm.HS256.getValue());
//        headerMap.put("typ", "JWT");

        /**
         * payload部分，用来存放实际需要传递的数据
         * withClaim 声明自定义字段
         * JWT官方字段
         * String ISSUER = "iss";  签发人
         * String SUBJECT = "sub";  主题
         * String EXPIRES_AT = "exp";  过期时间
         * String NOT_BEFORE = "nbf";  生效时间
         * String ISSUED_AT = "iat";  签发时间
         * String JWT_ID = "jti";   编号
         * String AUDIENCE = "aud";  受众
         */
        byte[] apiKeySecretBytes = SECRET.getBytes();

//        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

        JwtBuilder build = Jwts.builder()
//                .setHeader(headerMap)// header
                .setExpiration(expiresDate)//过期时间
                .signWith(signatureAlgorithm, apiKeySecretBytes);
        return build.compact();
    }

    public static Claims parseJWT(String jsonWebToken) throws Exception{
        return Jwts.parser().setSigningKey(SECRET.getBytes())
                        .parseClaimsJws(jsonWebToken).getBody();

    }


    public static void main(String[] args) {
        try{
            String token = createToken();
            System.out.println(token);
//            String   token = "eyJhbGciOiJIUzI1NiJ9.eyJleHAiOjE2MDA4NDUzMDB9.EpWdDxpDLatFBEj4dxGfoVrUc-RY_MRMt3ebo-W_dr0";
//            Claims claims = parseJWT(token);
//            System.out.println(claims.toString());
//            for (Map.Entry<String,Object> m : obj.entrySet()){
//                System.out.println("key="+m.getKey()+",val="+m.getValue());
//            }
        }catch (Exception e){
            e.printStackTrace();
        }


    }
}
