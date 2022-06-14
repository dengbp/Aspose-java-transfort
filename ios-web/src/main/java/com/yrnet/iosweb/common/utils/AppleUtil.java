package com.yrnet.iosweb.common.utils;

/**
 * @author dengbp
 * @ClassName AppleUtil
 * @Description TODO
 * @date 5/26/22 11:03 AM
 */

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.RSAPublicKeySpec;
import java.util.Map;


@Slf4j
public class AppleUtil {


    private static final String KEY_ID = "";

    private static final String TEAM_ID = "";

    private static final String AUTH_KEY = "";

    /**
     * 解密个人信息
     *
     * @param identityToken APP获取的identityToken
     * @return              解密参数：失败返回null
     */
    public static boolean verify(String identityToken,Map<String, Object> result) {
        try {
            String [] identityTokens = identityToken.split("\\.");


            Map<String, Object> data1 = JSONObject.parseObject(new String(Base64.decodeBase64(identityTokens[0]), "UTF-8"));
            Map<String, Object> data2 = JSONObject.parseObject(new String(Base64.decodeBase64(identityTokens[1]), "UTF-8"));
            String kid = (String) data1.get("kid");
            String aud = (String) data2.get("aud");
            String sub = (String) data2.get("sub");
            if (verify(identityToken,kid, aud, sub)) {
                result.putAll(data2);
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 验证
     *
     * @param identityToken APP获取的identityToken
     * @param aud           您在您的Apple Developer帐户中的client_id
     * @param sub           用户的唯一标识符对应APP获取到的：user
     * @return				true/false
     */
    public  static boolean verify(String identityToken,String kid, String aud, String sub)  {
        PublicKey publicKey = getPublicKey(kid);
        JwtParser jwtParser = Jwts.parser().setSigningKey(publicKey);
        jwtParser.requireIssuer("https://appleid.apple.com");
        jwtParser.requireAudience(aud);
        jwtParser.requireSubject(sub);
        try {
            Jws<Claims> claim = jwtParser.parseClaimsJws(identityToken);
            if (claim != null && claim.getBody().containsKey("auth_time")) {
                return true;
            }
            return false;
        } catch (ExpiredJwtException e) {
            log.error(e.getMessage(),e);
            return false;
        } catch (Exception e) {
            log.error(e.getMessage(),e);
            return false;
        }
    }

    /**
     * 根据kid生成公钥
     *
     * @return 构造好的公钥
     */
    public  static PublicKey getPublicKey(String kid) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            JSONObject data = restTemplate.getForObject("https://appleid.apple.com/auth/keys",JSONObject.class);
            JSONArray keysJsonArray = data.getJSONArray("keys");
            String n = "";
            String e = "";
            for (int i = 0; i < keysJsonArray.size(); i++) {
                JSONObject jsonObject = keysJsonArray.getJSONObject(i);
                if (StringUtils.equals(jsonObject.getString("kid"), kid)) {
                    n = jsonObject.getString("n");
                    e = jsonObject.getString("e");
                }
            }
            final BigInteger modulus = new BigInteger(1, Base64.decodeBase64(n));
            final BigInteger publicExponent = new BigInteger(1, Base64.decodeBase64(e));

            final RSAPublicKeySpec spec = new RSAPublicKeySpec(modulus, publicExponent);
            final KeyFactory kf = KeyFactory.getInstance("RSA");
            return kf.generatePublic(spec);
        } catch (final Exception e) {
            e.printStackTrace();
        }
        return null;
    }



    /**
     * 对前端传来的JWT字符串identityToken的第二部分进行解码
     * 主要获取其中的aud和sub，aud大概对应ios前端的包名，sub大概对应当前用户的授权的openID
     * @param identityToken
     * @return  {"aud":"com.xkj.****","sub":"000***.8da764d3f9e34d2183e8da08a1057***.0***","c_hash":"UsKAuEoI-****","email_verified":"true","auth_time":1574673481,"iss":"https://appleid.apple.com","exp":1574674081,"iat":1574673481,"email":"****@qq.com"}
     */
    public static JSONObject parserIdentityToken(String identityToken){
        String[] arr = identityToken.split("\\.");
        String decode = new String (Base64.decodeBase64(arr[1]));
        String substring = decode.substring(0, decode.indexOf("}")+1);
        JSONObject jsonObject = JSON.parseObject(substring);
        return  jsonObject;
    }

}
