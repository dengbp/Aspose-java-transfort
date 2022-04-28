package com.yrnet.sso.server.core.store;


import com.yrnet.sso.server.core.model.UserInfo;

/**
 * make client sessionId
 *
 *      client: cookie = [userid#version]
 *      server: redis
 *                  key = [userid]
 *                  value = user (user.version, valid this)
 *
 * //   group         The same group shares the login status, Different groups will not interact
 *
 * @author xuxueli 2018-11-15 15:45:08
 */

public class SsoSessionIdHelper {


    /**
     * make client token
     *
     * @param userInfo
     * @return
     */
    public static String makeToken(UserInfo userInfo){
        String token = userInfo.getUserId().concat("_").concat(userInfo.getVersion());
        return token;
    }

    /**
     * parse storeKey from sessionId
     *
     * @param token
     * @return
     */
    public static String parseStoreKey(String token) {
        if (token!=null && token.indexOf("_")>-1) {
            String[] sessionIdArr = token.split("_");
            if (sessionIdArr.length==2
                    && sessionIdArr[0]!=null
                    && sessionIdArr[0].trim().length()>0) {
                String userId = sessionIdArr[0].trim();
                return userId;
            }
        }
        return null;
    }

    /**
     * parse version from sessionId
     *
     * @param sessionId
     * @return
     */
    public static String parseVersion(String sessionId) {
        if (sessionId!=null && sessionId.indexOf("_")>-1) {
            String[] sessionIdArr = sessionId.split("_");
            if (sessionIdArr.length==2
                    && sessionIdArr[1]!=null
                    && sessionIdArr[1].trim().length()>0) {
                String version = sessionIdArr[1].trim();
                return version;
            }
        }
        return null;
    }
}
