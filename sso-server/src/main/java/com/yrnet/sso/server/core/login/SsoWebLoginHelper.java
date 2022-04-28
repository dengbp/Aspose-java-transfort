package com.yrnet.sso.server.core.login;


import com.yrnet.sso.server.config.Conf;
import com.yrnet.sso.server.core.store.SsoLoginStore;
import com.yrnet.sso.server.core.store.SsoSessionIdHelper;
import com.yrnet.sso.server.core.model.UserInfo;
import com.yrnet.sso.server.core.util.CookieUtil;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author xuxueli 2018-04-03
 */
public class SsoWebLoginHelper {

    /**
     * client login
     *
     * @param response
     * @param sessionId
     * @param ifRemember    true: cookie not expire, false: expire when browser close （server cookie）
     * @param xxlUser
     */
    public static void login(HttpServletResponse response,
                             String sessionId,
                             UserInfo xxlUser,
                             boolean ifRemember) {

        SsoLoginStore.put(xxlUser);
        CookieUtil.set(response, Conf.SSO_TOKEN, sessionId, ifRemember);
    }

    /**
     * client logout
     *
     * @param request
     * @param response
     */
    public static void logout(HttpServletRequest request,
                              HttpServletResponse response) {

        String cookieSessionId = CookieUtil.getValue(request, Conf.SSO_TOKEN);
        if (cookieSessionId==null) {
            return;
        }

        String storeKey = SsoSessionIdHelper.parseStoreKey(cookieSessionId);
        if (storeKey != null) {
            SsoLoginStore.remove(storeKey);
        }

        CookieUtil.remove(request, response, Conf.SSO_TOKEN);
    }



    /**
     * login check
     *
     * @param request
     * @param response
     * @return
     */
    public static UserInfo loginCheck(HttpServletRequest request, HttpServletResponse response){

        String cookieSessionId = CookieUtil.getValue(request, Conf.SSO_TOKEN);
        if(StringUtils.isBlank(cookieSessionId)){
            return null;
        }

        // cookie user
        UserInfo xxlUser = SsoTokenLoginHelper.loginCheck(cookieSessionId);
        if (xxlUser != null) {
            return xxlUser;
        }

        // redirect user

        // remove old cookie
        SsoWebLoginHelper.removeSessionIdByCookie(request, response);

        // set new cookie
        String paramSessionId = request.getParameter(Conf.SSO_TOKEN);
        xxlUser = SsoTokenLoginHelper.loginCheck(paramSessionId);
        if (xxlUser != null) {
            CookieUtil.set(response, Conf.SSO_TOKEN, paramSessionId, false);    // expire when browser close （client cookie）
            return xxlUser;
        }

        return null;
    }


    /**
     * client logout, cookie only
     *
     * @param request
     * @param response
     */
    public static void removeSessionIdByCookie(HttpServletRequest request, HttpServletResponse response) {
        CookieUtil.remove(request, response, Conf.SSO_TOKEN);
    }

    /**
     * get sessionid by cookie
     *
     * @param request
     * @return
     */
    public static String getSessionIdByCookie(HttpServletRequest request) {
        String cookieSessionId = CookieUtil.getValue(request, Conf.SSO_TOKEN);
        return cookieSessionId;
    }


}
