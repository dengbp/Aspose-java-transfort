package com.yrnet.iosweb.common.authentication;

import com.yrnet.iosweb.common.entity.ViewWebConstant;
import com.yrnet.iosweb.common.utils.HttpContextUtil;
import com.yrnet.iosweb.common.utils.IPUtil;
import com.yrnet.iosweb.common.utils.JWTUtil;
import com.yrnet.iosweb.common.utils.LicenseUtil;
import com.yrnet.iosweb.monitor.service.IRedisService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

import javax.servlet.http.HttpServletRequest;
import java.util.HashSet;
import java.util.Set;

/**
 * 自定义实现 ShiroRealm，包含认证和授权两大模块
 *
 * @author dengbp
 */
@Slf4j
public class ShiroRealm extends AuthorizingRealm {

    @Lazy
    @Autowired
    private IRedisService redisService;
   /* @Lazy
    @Autowired
    private UserManager userManager;*/

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JwtToken;
    }

    /**`
     * 授权模块，获取用户角色和权限
     *
     * @param token token
     * @return AuthorizationInfo 权限信息
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection token) {
        String username = JWTUtil.getUsername(token.toString());

        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();

        // 获取用户角色集
        Set<String> roleSet = new HashSet<>(0);//userManager.getUserRoles(username);
        simpleAuthorizationInfo.setRoles(roleSet);

        // 获取用户权限集
        Set<String> permissionSet = new HashSet<>(0);//userManager.getUserPermissions(username);
        simpleAuthorizationInfo.setStringPermissions(permissionSet);
        return simpleAuthorizationInfo;
    }

    /**
     * 用户认证
     *
     * @param authenticationToken 身份认证 token
     * @return AuthenticationInfo 身份认证信息
     * @throws AuthenticationException 认证相关异常
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        // 这里的 token是从 JWTFilter 的 executeLogin 方法传递过来的，已经经过了解密
        String token = (String) authenticationToken.getCredentials();

        // 从 redis里获取这个 token
        HttpServletRequest request = HttpContextUtil.getHttpServletRequest();
        String ip = IPUtil.getIpAddr(request);

        String encryptToken = LicenseUtil.encryptToken(token);
        String encryptTokenInRedis = null;
        try {
            encryptTokenInRedis = redisService.get(ViewWebConstant.TOKEN_CACHE_PREFIX + encryptToken);
        } catch (Exception ignore) {
        }
        // 如果找不到，说明已经失效
        if (StringUtils.isBlank(encryptTokenInRedis)){
            log.warn("encryptTokenInRedis is empty...");
            throw new AuthenticationException("token已经过期");
        }

        String username = JWTUtil.getUsername(token);

        if (StringUtils.isBlank(username)) {
            throw new AuthenticationException("token校验不通过");
        }
        //暂时屏蔽，启用shiro时要去掉注释
//        User user = userManager.getUser(username);
//        if (user == null) {
//            throw new AuthenticationException("用户名或密码错误");
//        }
//        if (!JWTUtil.verify(token, username, user.getPassword())) {
//            throw new AuthenticationException("token校验不通过");
//        }
        return new SimpleAuthenticationInfo(token, token, "license3_shiro_realm");
    }
}
