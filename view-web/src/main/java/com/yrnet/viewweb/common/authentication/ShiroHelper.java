package com.yrnet.viewweb.common.authentication;

import com.yrnet.viewweb.common.annotation.Helper;
import org.apache.shiro.authz.AuthorizationInfo;

/**
 * @author dengbp
 */
@Helper
public class ShiroHelper extends ShiroRealm {

    /**
     * 获取当前用户的角色和权限集合
     *
     * @return AuthorizationInfo
     */
    public AuthorizationInfo getCurrentuserAuthorizationInfo() {
        return super.doGetAuthorizationInfo(null);
    }
}
