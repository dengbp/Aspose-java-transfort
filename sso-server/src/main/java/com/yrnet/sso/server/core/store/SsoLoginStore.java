package com.yrnet.sso.server.core.store;


import com.alibaba.fastjson.JSON;
import com.yrnet.sso.server.config.Conf;
import com.yrnet.sso.server.core.model.UserInfo;
import com.yrnet.sso.server.core.util.JedisUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * local login store
 *
 * @author xuxueli 2018-04-02 20:03:11
 */
public class SsoLoginStore {

    private static Logger logger = LoggerFactory.getLogger(SsoLoginStore.class);

    private static int redisExpireMinute = 120;    // 1440 minute, 24 hour
    public static void setRedisExpireMinute(int redisExpireMinute) {
        if (redisExpireMinute < 30) {
            redisExpireMinute = 30;
        }
        SsoLoginStore.redisExpireMinute = redisExpireMinute;
    }
    public static int getRedisExpireMinute() {
        return redisExpireMinute;
    }

    /**
     * get
     *
     * @param storeKey
     * @return
     */
    public static UserInfo get(String storeKey) {

        String redisKey = redisKey(storeKey);
        Object objectValue = JedisUtil.getValue(redisKey);
        if (objectValue != null) {
            UserInfo xxlUser = (UserInfo) objectValue;
            return xxlUser;
        }
        return null;
    }

    /**
     * remove
     *
     * @param storeKey
     */
    public static void remove(String storeKey) {
        String redisKey = redisKey(storeKey);
        JedisUtil.delKey(redisKey);
    }

    /**
     * put
     *
     * @param xxlUser
     */
    public static void put(UserInfo xxlUser) {
        String redisKey = redisKey(xxlUser.getUserId());
        JedisUtil.setValue(redisKey, xxlUser, redisExpireMinute * 60);
        logger.info("设置redis缓存成功,key={},val={}",redisKey, JSON.toJSONString(xxlUser));
    }

    private static String redisKey(String userId){
        return Conf.SSO_TOKEN.concat("#").concat(userId);
    }

}
