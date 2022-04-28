package com.yrnet.sso.server.core.login;


import com.alibaba.fastjson.JSON;
import com.yrnet.sso.server.config.Conf;
import com.yrnet.sso.server.core.store.SsoLoginStore;
import com.yrnet.sso.server.core.store.SsoSessionIdHelper;
import com.yrnet.sso.server.core.model.UserInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;

/**
 * @author xuxueli 2018-11-15 15:54:40
 */
public class SsoTokenLoginHelper {

    private final static Logger logger = LoggerFactory.getLogger(SsoTokenLoginHelper.class);

    /**
     * client login
     * @param xxlUser
     */
    public static void login( UserInfo xxlUser) {
        SsoLoginStore.put(xxlUser);
    }

    /**
     * client logout
     *
     * @param token
     */
    public static void logout(String token) {
        String storeKey = SsoSessionIdHelper.parseStoreKey(token);
        if (storeKey == null) {
            return;
        }
        SsoLoginStore.remove(storeKey);
    }
    /**
     * client logout
     *
     * @param request
     */
    public static void logout(HttpServletRequest request) {
        String headerSessionId = request.getHeader(Conf.SSO_TOKEN);
        logout(headerSessionId);
    }


    /**
     * login check
     *
     * @param token
     * @return
     */
    public static UserInfo loginCheck(String token){
        String storeKey = SsoSessionIdHelper.parseStoreKey(token);
        if (storeKey == null) {
            return null;
        }

        UserInfo xxlUser = SsoLoginStore.get(storeKey);
        logger.info("获取redis缓存，key={}",storeKey);
        if (xxlUser != null) {
            logger.info("获取redis缓存成功,key={},val={}",storeKey, JSON.toJSONString(xxlUser));
            String version = SsoSessionIdHelper.parseVersion(token);
            if (xxlUser.getVersion().equals(version)) {
                logger.info("token校验通过");
                // 有效期时间超过一半自动刷新有效时间
                if ((System.currentTimeMillis() - xxlUser.getExpireFreshTime()) > (xxlUser.getExpireMinute()*60*1000/2)) {
                    xxlUser.setExpireFreshTime(System.currentTimeMillis());
                    SsoLoginStore.put(xxlUser);
                }
                return xxlUser;
            }
        }
        logger.info("token校验不通过");
        return null;
    }


    /**
     * login check
     * @param request
     * @return
     */
    public static UserInfo loginCheck(HttpServletRequest request){
        String headerSessionId = request.getHeader(Conf.SSO_TOKEN);
        return loginCheck(headerSessionId);
    }
}
