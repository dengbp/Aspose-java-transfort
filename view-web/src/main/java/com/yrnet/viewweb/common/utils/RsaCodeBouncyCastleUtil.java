package com.yrnet.viewweb.common.utils;

import org.apache.commons.codec.binary.Base64;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import java.io.ByteArrayOutputStream;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Pattern;

/**
 * @author dengbp
 * @ClassName RSAUtil
 * @Description 非对称加解密
 * @date 2019-11-06 11:00
 */
public final class RsaCodeBouncyCastleUtil {
    private static Logger logger = LoggerFactory.getLogger(Security.class);
    /**
     * 非对称加密密钥算法
     */
    protected static final String KEY_ALGORITHM_RSA = "RSA";

    protected static final String SIGNATURE_ALGORITHM = "MD5withRSA";
    /**
     * 密钥(静态、成员变量串不能混淆)
     */
    private static final String RSA_PUBLIC_KEY = "SEGI-PublicKey-1.0";
    private static final String RSA_PRIVATE_KEY = "SEGIPrivateKey20191107";
    private static final String RSA_PUBLIC_VALUE = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC7qpHynjfKixtUwJ2VtMAJGBgElVitlaSA9/zMJaSDYq03BhdxAF2iJCasKGhQfm2pZ34Q39bODrfZb93ra/Zfh8kMrT1PEIpCuEOeycSlBpOYAGujcHvaIjhEcteLtpdxGSRqX4Ui1wJLM0ff1alHvB5LNbVboSJuAG+V7369AQIDAQAB";
    private static final String RSA_PRIVATE_VALUE = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBALuqkfKeN8qLG1TAnZW0wAkYGASVWK2VpID3/MwlpINirTcGF3EAXaIkJqwoaFB+balnfhDf1s4Ot9lv3etr9l+HyQytPU8QikK4Q57JxKUGk5gAa6Nwe9oiOERy14u2l3EZJGpfhSLXAkszR9/VqUe8Hks1tVuhIm4Ab5Xvfr0BAgMBAAECgYAAgcBtKFI8QM3tF6wVSGywWZlPAdxAc8QC+Sh8oKlmFXEkUebkTjzygCh+lIlblkvThk0H3KLm4ZFLjArMZPlAXZwlPgxOQ7chLIVq3Ye+8F8EAlyPsFTa9pyjgqppQs16gvrDENrA69b+4HY8BGJHCKsypqlYB2hY988hy/2gsQJBAPBDQ3cRppat9wgKQsQs3BNRXHWaSFg7AElyYo5s89ctL2GdgrVm9tNZ1XUxFMvQaaQiB1DN7zlq3qvIhaTZ9l0CQQDH9V3nRq+tW7uPjK/8B+t+K/zN+FV27ImfftXhoxst5L1wQtymCj9nkyzDtqSKJOswPOMxkRduZ3f2fyLqkm71AkEAomRUTT5Y4q9isMR+rCnF9iif3b0R+NCoOPxfMPTjOyUHSWs01XdqjR/C7xrle3y6an21hCAs+RoKtwEpk2re4QJBALVUHibIJDG6WTlRJJoR+I0/iI6j36Tr1dWsaHqotdIFd4EhMQTlkB/2CAcnTsjzt8/Tt1fQsoXtEI2ZN2mvBy0CQBoIWv4GK5q8tU0y2jBEwqNIPONsxURnclQIVBb+sfb++IBIAtQXGuskdc6GZnKU7Dqwkx5OXFj9eTy398kuaQU=";

    private static final int MAX_ENCRYPT_BLOCK = 117;
    private static final int MAX_DECRYPT_BLOCK = 128;

    /**
     * RSA密钥长度
     * 默认1024位，
     * 密钥长度必须是64的倍数，
     * 范围在512至65536位之间。
     */
    private static final int KEY_SIZE = 1024;
    private static final String PROVIDER = BouncyCastleProvider.PROVIDER_NAME;


    static{
        Security.addProvider(new BouncyCastleProvider());
        logger.info("add BouncyCastleProvider");
    }

    /**
     * 用私钥对信息生成数字签名
     *
     * @param data       加密数据
     * @param privateKey 私钥
     * @return
     * @throws Exception
     */
    public static String sign(byte[] data, String privateKey) throws Exception {
        /* 解密由base64编码的私钥 **/
        byte[] keyBytes = decryptBASE64(privateKey);
        /* 构造PKCS8EncodedKeySpec对象 **/
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        /*  KEY_ALGORITHM 指定的加密算法 **/
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM_RSA,PROVIDER);
        /* 取私钥匙对象 **/
        PrivateKey priKey = keyFactory.generatePrivate(pkcs8KeySpec);
        /* 用私钥对信息生成数字签名 **/
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM,PROVIDER);
        signature.initSign(priKey);
        signature.update(data);
        return encryptBASE64(signature.sign());
    }

    /**
     * 校验数字签名
     *
     * @param data      加密数据
     * @param publicKey 公钥
     * @param sign      数字签名
     * @return 校验成功返回true 失败返回false
     * @throws Exception
     */
    public static boolean verify(byte[] data, String publicKey, String sign)
            throws Exception {
        /* 解密由base64编码的公钥 **/
        byte[] keyBytes = decryptBASE64(publicKey);
        /* 构造X509EncodedKeySpec对象 **/
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        /* KEY_ALGORITHM 指定的加密算法 **/
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM_RSA,PROVIDER);
        /* 取公钥匙对象 **/
        PublicKey pubKey = keyFactory.generatePublic(keySpec);
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM,PROVIDER);
        signature.initVerify(pubKey);
        signature.update(data);
        /* 验证签名是否正常 **/
        return signature.verify(decryptBASE64(sign));
    }
    /**
     * 私钥解密
     *
     * @param data
     *            待解密数据
     * @param key
     *            私钥
     * @return byte[] 解密数据
     * @throws Exception
     */
    public static byte[] decryptByPrivateKey(String data, String key)
            throws Exception {
        byte[] dataBytes = decryptBASE64(data);
        byte[] keyBytes = decryptBASE64(key);
        /* 取得私钥 **/
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);

        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM_RSA,PROVIDER);

        /* 生成私钥 **/
        PrivateKey privateKey = keyFactory.generatePrivate(pkcs8KeySpec);

        // 对数据解密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm(),PROVIDER);

        cipher.init(Cipher.DECRYPT_MODE, privateKey);

        int inputLen = dataBytes.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段解密
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
                cache = cipher.doFinal(dataBytes, offSet, MAX_DECRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(dataBytes, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_DECRYPT_BLOCK;
        }
        byte[] decryptedData = out.toByteArray();
        out.close();
        return decryptedData;
    }

    /**
     * Description 使用固定私钥解密
     * @param data
     * @return byte[]
     * @Author dengbp
     * @Date 14:54 2020-01-02
     **/
    public static byte[] decryptByPrivateKey(String data)
            throws Exception {
        byte[] dataBytes = decryptBASE64(data);
        byte[] keyBytes = decryptBASE64(RSA_PRIVATE_VALUE);
        /* 取得私钥 **/
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);

        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM_RSA,PROVIDER);

        /* 生成私钥 **/
        PrivateKey privateKey = keyFactory.generatePrivate(pkcs8KeySpec);

        // 对数据解密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm(),PROVIDER);

        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        logger.info("cipher provider: {}" , cipher.getProvider().getClass().getName());
        int inputLen = dataBytes.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段解密
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
                cache = cipher.doFinal(dataBytes, offSet, MAX_DECRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(dataBytes, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_DECRYPT_BLOCK;
        }
        byte[] decryptedData = out.toByteArray();
        out.close();
        return decryptedData;
    }

   /**
    * Description 公钥解密
    * @param data	待定解密数据
    * @param key 密钥
    * @return byte[]
    * @Author dengbp
    * @Date 18:27 2019-11-06
    **/
    public static byte[] decryptByPublicKey(String data, String key)
            throws Exception {

        byte[] dataBytes = decryptBASE64(data);
        byte[] keyBytes = decryptBASE64(key);
        /*取得公钥 **/
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);

        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM_RSA,PROVIDER);

        /* 生成公钥 **/
        PublicKey publicKey = keyFactory.generatePublic(x509KeySpec);

        /* 对数据解密 **/
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm(),PROVIDER);
        logger.info("cipher provider: {}" , cipher.getProvider().getClass().getName());
        cipher.init(Cipher.DECRYPT_MODE, publicKey);
        int inputLen = dataBytes.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        /** 对数据分段解密 */
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
                cache = cipher.doFinal(dataBytes, offSet, MAX_DECRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(dataBytes, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_DECRYPT_BLOCK;
        }
        byte[] decryptedData = out.toByteArray();
        out.close();
        return decryptedData;
    }


    /**
     * Description 使用固定公钥加密
     * @param data
     * @return byte[]
     * @Author dengbp
     * @Date 14:48 2020-01-02
     **/
    public static byte[] decryptByPublicKey(String data)
            throws Exception {

        byte[] dataBytes = decryptBASE64(data);
        byte[] keyBytes = decryptBASE64(RSA_PUBLIC_VALUE);
        /*取得公钥 **/
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);

        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM_RSA,PROVIDER);

        /* 生成公钥 **/
        PublicKey publicKey = keyFactory.generatePublic(x509KeySpec);

        /* 对数据解密 **/
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm(),PROVIDER);
        logger.info("cipher provider: {}" , cipher.getProvider().getClass().getName());
        cipher.init(Cipher.DECRYPT_MODE, publicKey);
        int inputLen = dataBytes.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        /** 对数据分段解密 */
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
                cache = cipher.doFinal(dataBytes, offSet, MAX_DECRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(dataBytes, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_DECRYPT_BLOCK;
        }
        byte[] decryptedData = out.toByteArray();
        out.close();
        return decryptedData;
    }

    /**
     * Description 公钥加密
     * @param data
 * @param key
     * @return string 加密后数据
     * @Author dengbp
     * @Date 17:47 2019-11-06
     **/
    public static String encryptByPublicKey(String data, String key)
            throws Exception {

        byte[] keyBytes = decryptBASE64(key);
        byte[] dataBytes = data.getBytes();
        /* 取得公钥 **/
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);

        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM_RSA,PROVIDER);

        PublicKey publicKey = keyFactory.generatePublic(x509KeySpec);

        /* 对数据加密 **/
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm(),PROVIDER);

        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        logger.info("cipher provider: {}" , cipher.getProvider().getClass().getName());
        int blockSize = cipher.getBlockSize();
        if(blockSize>0){
            int outputSize = cipher.getOutputSize(dataBytes.length);
            int leavedSize = dataBytes.length % blockSize;
            int blocksSize = leavedSize != 0 ? dataBytes.length / blockSize + 1
                    : dataBytes.length / blockSize;
            byte[] raw = new byte[outputSize * blocksSize];
            int i = 0,remainSize=0;
            while ((remainSize = dataBytes.length - i * blockSize) > 0) {
                int inputLen = remainSize > blockSize?blockSize:remainSize;
                cipher.doFinal(dataBytes, i * blockSize, inputLen, raw, i * outputSize);
                i++;
            }
            return RsaCodeBouncyCastleUtil.encryptBASE64(raw);
        }
        return RsaCodeBouncyCastleUtil.encryptBASE64(cipher.doFinal(dataBytes));
    }

    /**
     * Description 使用固定公钥加密
     * @param data
     * @return java.lang.String
     * @Author dengbp
     * @Date 14:51 2020-01-02
     **/
    public static String encryptByPublicKey(String data)
            throws Exception {

        byte[] dataBytes = data.getBytes();
        byte[] keyBytes = decryptBASE64(RSA_PUBLIC_VALUE);
        /* 取得公钥 **/
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);

        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM_RSA,PROVIDER);

        PublicKey publicKey = keyFactory.generatePublic(x509KeySpec);

        /* 对数据加密 **/
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm(),PROVIDER);

        cipher.init(Cipher.ENCRYPT_MODE, publicKey);

        int blockSize = cipher.getBlockSize();
        if(blockSize>0){
            int outputSize = cipher.getOutputSize(dataBytes.length);
            int leavedSize = dataBytes.length % blockSize;
            int blocksSize = leavedSize != 0 ? dataBytes.length / blockSize + 1
                    : dataBytes.length / blockSize;
            byte[] raw = new byte[outputSize * blocksSize];
            int i = 0,remainSize=0;
            while ((remainSize = dataBytes.length - i * blockSize) > 0) {
                int inputLen = remainSize > blockSize?blockSize:remainSize;
                cipher.doFinal(dataBytes, i * blockSize, inputLen, raw, i * outputSize);
                i++;
            }
            return RsaCodeBouncyCastleUtil.encryptBASE64(raw);
        }
        return RsaCodeBouncyCastleUtil.encryptBASE64(cipher.doFinal(dataBytes));
    }

    /**
     * Description 私钥加密
     * @param data 待加密数据
     * @param key 私钥
     * @return string 加密后数据
     * @Author dengbp
     * @Date 17:32 2019-11-06
     **/
    public static String encryptByPrivateKey(String data, String key)
            throws Exception {

        byte[] dataBytes = data.getBytes();
        byte[] keyBytes = decryptBASE64(key);
        /* 取得私钥 **/
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);

        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM_RSA,PROVIDER);

        /* 生成私钥 **/
        PrivateKey privateKey = keyFactory.generatePrivate(pkcs8KeySpec);

        /* 对数据加密 **/
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm(),PROVIDER);
        logger.info("cipher provider: {}" , cipher.getProvider().getClass().getName());
        cipher.init(Cipher.ENCRYPT_MODE, privateKey);
        int inputLen = dataBytes.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        /**对数据分段加密 */
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
                cache = cipher.doFinal(dataBytes, offSet, MAX_ENCRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(dataBytes, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_ENCRYPT_BLOCK;
        }
        byte[] encryptedData = out.toByteArray();
        out.close();
        return encryptBASE64(encryptedData);
    }


    /**
     * Description 使用固定私钥加密
     * @param data
     * @return java.lang.String
     * @Author dengbp
     * @Date 14:46 2020-01-02
     **/
    public static String encryptByPrivateKey(String data)
            throws Exception {

        byte[] dataBytes = data.getBytes();
        byte[] keyBytes = decryptBASE64(RSA_PRIVATE_VALUE);
        /* 取得私钥 **/
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);

        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM_RSA,PROVIDER);

        /* 生成私钥 **/
        PrivateKey privateKey = keyFactory.generatePrivate(pkcs8KeySpec);

        /* 对数据加密 **/
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm(),PROVIDER);

        cipher.init(Cipher.ENCRYPT_MODE, privateKey);
        int inputLen = dataBytes.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        /**对数据分段加密 */
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
                cache = cipher.doFinal(dataBytes, offSet, MAX_ENCRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(dataBytes, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_ENCRYPT_BLOCK;
        }
        byte[] encryptedData = out.toByteArray();
        out.close();
        return encryptBASE64(encryptedData);
    }

    /**
     * 取得私钥
     *
     * @param keyMap
     *            密钥Map
     * @return String 私钥
     * @throws Exception
     */
    public static String getPrivateKey(Map<String, Key> keyMap)
            throws Exception {
        return encryptBASE64(keyMap.get(RSA_PRIVATE_KEY).getEncoded());
    }


    /**
     * 取得公钥
     *
     * @param keyMap
     *            密钥Map
     * @return String 公钥
     * @throws Exception
     */
    public static String getPublicKey(Map<String, Key> keyMap)
            throws Exception {
        return encryptBASE64(keyMap.get(RSA_PUBLIC_KEY).getEncoded());
    }

    /**
     * Description todo
     * @param key	key
     * @return byte[] byte
     * @Author dengbp
     * @Date 17:23 2019-11-06
     **/
    public static byte[] decryptBASE64(String key) {
        return Base64.decodeBase64(key);
    }

    /**
     * Description todo
     * @param bytes
     * @return String Base64串
     * @Author dengbp
     * @Date 17:24 2019-11-06
     **/
    public static String encryptBASE64(byte[] bytes) {
        return Base64.encodeBase64String(bytes);
    }


    /**
     * Description 初始化密钥
     * @param seed
     * @return java.util.Map<java.lang.String,java.security.Key>
     * @Author dengbp
     * @Date 11:06 2019-11-06
     **/
    public static Map<String,Key> initKey(byte[] seed)throws Exception{
        /* 实例化密钥对生成器 **/
        KeyPairGenerator keyPairGen = KeyPairGenerator
                .getInstance(KEY_ALGORITHM_RSA,PROVIDER);

        /* 初始化密钥对生成器 **/
        keyPairGen.initialize(KEY_SIZE,	new SecureRandom(seed) );

        /* 生成密钥对 **/
        KeyPair keyPair = keyPairGen.generateKeyPair();

        /* 公钥 **/
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();

        /* 私钥 **/
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();

        /* 封装密钥 **/
        Map<String, Key> keyMap = new HashMap<String, Key>(2);

        /*FileOutputStream fos = new FileOutputStream(new File("/Users/dengbp/Documents/work/segi/code/SourceCode/segi-license-manager/src/main/resources/myFile"));
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            outputStream.write(publicKey.getEncoded());
            outputStream.writeTo(fos);
        } catch (IOException e) {
            e.printStackTrace();
        }

        byte[] aa = new byte[publicKey.getEncoded().length] ;
        FileInputStream fis = new FileInputStream(new File("/Users/dengbp/Documents/work/segi/code/SourceCode/segi-license-manager/src/main/resources/myFile"));
        fis.read(aa);

        logger.info(">>>>>:{}",encryptBASE64(aa));*/

        keyMap.put(RSA_PUBLIC_KEY, publicKey);
        keyMap.put(RSA_PRIVATE_KEY, privateKey);
        return keyMap;
    }

    /**
     * 初始化密钥
     * @param seed 种子
     * @return Map 密钥Map
     * @throws Exception
     */
    public static Map<String,Key> initKey(String seed)throws Exception{
        return initKey(seed.getBytes());
    }

    /**
     * 初始化密钥
     *
     * @return Map 密钥Map
     * @throws Exception
     */
    public static Map<String, Key> initKey() throws Exception {
        return initKey(UUID.randomUUID().toString().getBytes());
    }

    public static boolean isBase64(String code) {
        String base64Pattern = "^([A-Za-z0-9+/]{4})*([A-Za-z0-9+/]{4}|[A-Za-z0-9+/]{3}=|[A-Za-z0-9+/]{2}==)$";
        return Pattern.matches(base64Pattern, code);
    }

   /* public static PublicKey getPublicRSAKey(String key) throws Exception {
        X509EncodedKeySpec x509 = new X509EncodedKeySpec(Base64.decode(key));
        KeyFactory kf = KeyFactory.getInstance(KEY_ALGORITHM_RSA);
        return kf.generatePublic(x509);
    }

    public static PrivateKey getPrivateRSAKey(String key) throws Exception {
        PKCS8EncodedKeySpec pkgs8 = new PKCS8EncodedKeySpec(Base64.decode(key));
        KeyFactory kf = KeyFactory.getInstance(KEY_ALGORITHM_RSA);
        return kf.generatePrivate(pkgs8);
    }*/
}
