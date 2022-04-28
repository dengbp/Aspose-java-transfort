package com.yrnet.sso.server.core.util;

import com.yrnet.sso.server.config.SpringContextUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.concurrent.TimeUnit;



public class JedisUtil {
    private static Logger logger = LoggerFactory.getLogger(JedisUtil.class);


    private static RedisTemplate<String,Object> redisTemplate;

    private static RedisTemplate<String,Object> getRedisTemplate(){
        if(redisTemplate == null){
            redisTemplate = (RedisTemplate<String,Object>)SpringContextUtils.getBean("redisTemplate");
        }
        return redisTemplate;
    }




    /**
     * Set Object
     *
     * @param key
     * @param obj
     * @param seconds 存活时间,单位/秒
     */
    public static void setValue(String key, Object obj, int seconds) {
        RedisTemplate<String,Object> client = getRedisTemplate();
        try {
            client.opsForValue().set(key, obj,seconds,TimeUnit.SECONDS);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    /**
     * Get Object
     *
     * @param key
     * @return
     */
    public static Object getValue(String key) {
        Object obj = null;
        RedisTemplate<String,Object> client = getRedisTemplate();
        try {
            obj = client.opsForValue().get(key);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return obj;
    }

    /**
     * Delete key
     *
     * @param key
     * @return Integer reply, specifically:
     * an integer greater than 0 if one or more keys were removed
     * 0 if none of the specified key existed
     */
    public static boolean delKey(String key) {
        boolean result = false;
        RedisTemplate<String,Object> client = getRedisTemplate();
        try {
            result = client.delete(key);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return result;
    }
}
