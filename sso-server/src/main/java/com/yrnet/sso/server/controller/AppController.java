package com.yrnet.sso.server.controller;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.yrnet.sso.server.core.login.SsoTokenLoginHelper;
import com.yrnet.sso.server.core.result.ReturnT;
import com.yrnet.sso.server.core.store.SsoLoginStore;
import com.yrnet.sso.server.core.store.SsoSessionIdHelper;
import com.yrnet.sso.server.core.model.UserInfo;
import com.yrnet.sso.server.core.util.PublicUtil;
import com.yrnet.sso.server.service.ApiMenuRelService;
import com.yrnet.sso.server.service.RoleMenuRelService;
import com.yrnet.sso.server.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/sso")
public class AppController {

    private static Logger logger = LoggerFactory.getLogger(AppController.class);
    private UserService userService;
    private RoleMenuRelService roleMenuRelService;
    private ApiMenuRelService apiMenuRelService;
    @Autowired
    public AppController(UserService userService,RoleMenuRelService roleMenuRelService,ApiMenuRelService apiMenuRelService){
        this.userService = userService;
        this.roleMenuRelService = roleMenuRelService;
        this.apiMenuRelService = apiMenuRelService;
    }


    /**管理员角色ID*/
    public static final String ADMIN_ROLE_ID = "1";


    /**
     * Login
     *
     * @param username  账号
     * @param password  密码
     * @return          token
     */
    @RequestMapping(value = "/login",method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public ReturnT<String> login(String username, String password, HttpServletRequest request) {

//        logger.info("登录账号:{}，密码:{}",username,password);
        if (username==null || username.trim().length()==0) {
            return new ReturnT<>(ReturnT.FAIL_CODE, "账号不能为空");
        }
        if (password==null || password.trim().length()==0) {
            return new ReturnT<>(ReturnT.FAIL_CODE, "密码不能为空");
        }
        // valid login
        UserInfo userInfo = userService.findUser(username, password);
        if (userInfo==null) {
            return new ReturnT<>(ReturnT.FAIL_CODE,"账号密码不匹配");
        }
        if(userInfo.getStatus()!=0){
            return new ReturnT<>(ReturnT.FAIL_CODE,"无效账号");
        }

        // 1、make xxl-sso user
        userInfo.setVersion(UUID.randomUUID().toString().replaceAll("-", ""));
        userInfo.setExpireMinute(SsoLoginStore.getRedisExpireMinute());
        userInfo.setExpireFreshTime(System.currentTimeMillis());

        // 2、generate token
        String token = SsoSessionIdHelper.makeToken(userInfo);

        // 3、login, store storeKey
        SsoTokenLoginHelper.login(userInfo);

        // 4、save login log
        userService.saveLoginLog(userInfo.getUserId(), PublicUtil.getUserIp(request));
        request.getSession().setAttribute("token",token);
        // 5、return sessionId
        return new ReturnT<>(token);
    }


    /**
     * Logout
     *
     * @param token     登录token
     */
    @RequestMapping(value = "/logout",method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public ReturnT<String> logout(String token) {
        // logout, remove storeKey
        SsoTokenLoginHelper.logout(token);
        return ReturnT.SUCCESS;
    }

    /**
     * loginCheck
     * @param token     登录token
     */
    @RequestMapping(value = "/loginCheck",method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public ReturnT<UserInfo> loginCheck(String token) {
        logger.info("开始检验token是否有效，token={}",token);
        UserInfo xxlUser = SsoTokenLoginHelper.loginCheck(token);
        if (xxlUser == null) {
            return new ReturnT<>(ReturnT.FAIL_CODE, "sso not login.");
        }
        return new ReturnT<>(xxlUser);
    }

    @RequestMapping(value = "/deleteToken",method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public ReturnT<Boolean> deleteToken(String userId){
        SsoLoginStore.remove(userId);
        return new ReturnT<>(true);
    }

    @ResponseBody
    @RequestMapping(value = "/auth",method = {RequestMethod.GET,RequestMethod.POST})
    public ReturnT<?> interfaceAuthentication(String userId,String url){
        logger.info("开始校验是否有接口权限，参数userId={},url={}",userId,url);
        //根据用户ID查询角色ID
        List<String> roleIdList = this.userService.queryRoleIdByUserId(userId);
        if(CollectionUtils.isEmpty(roleIdList)) return new ReturnT<>(false);
        //判断是否有超级管理员角色
        if(roleIdList.contains(ADMIN_ROLE_ID)) return new ReturnT<>(true);
        //根据角色ID查询已授权菜单
        List<String> menuIdList = this.roleMenuRelService.queryMenuIdList(roleIdList);
        if(CollectionUtils.isEmpty(menuIdList)) return new ReturnT<>(false);
        //判断调用接口是否有权限访问
        boolean isAllow = this.apiMenuRelService.isAllowVisit(menuIdList,url);
        logger.info("结束 校验是否有接口权限，结果为：{}",isAllow);
        return new ReturnT<>(isAllow);
    }
}
