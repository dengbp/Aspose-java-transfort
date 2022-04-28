package com.yrnet.sso.server.controller;

import com.yrnet.sso.server.config.Conf;
import com.yrnet.sso.server.core.login.SsoWebLoginHelper;
import com.yrnet.sso.server.core.store.SsoLoginStore;
import com.yrnet.sso.server.core.store.SsoSessionIdHelper;
import com.yrnet.sso.server.core.model.UserInfo;
import com.yrnet.sso.server.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

/**
 * sso server (for web)
 *
 * @author xuxueli 2017-08-01 21:39:47
 */
@Controller
public class WebController {


    private UserService userService;
    @Autowired
    public WebController(UserService userService){
        this.userService = userService;
    }

    @RequestMapping("/")
    public String index(Model model, HttpServletRequest request, HttpServletResponse response){

        // login check
        UserInfo xxlUser = SsoWebLoginHelper.loginCheck(request, response);

        if (xxlUser == null) {
            String redirectUrl = request.getParameter(Conf.REDIRECT_URL);
            if(StringUtils.isBlank(redirectUrl)){
                model.addAttribute("exceptionMsg", Conf.REDIRECT_URL+"不能为空");
                return "/common/common.exception";
            }
            model.addAttribute(Conf.REDIRECT_URL, redirectUrl);
            return "redirect:/login?"+Conf.REDIRECT_URL + "=" +redirectUrl;
        } else {
            model.addAttribute("xxlUser", xxlUser);
            return "index";
        }
    }

    /**
     * Login page
     *
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(Conf.SSO_LOGIN)
    public String login(Model model, HttpServletRequest request, HttpServletResponse response) {

        // login check
        UserInfo xxlUser = SsoWebLoginHelper.loginCheck(request, response);
        String redirectUrl = request.getParameter(Conf.REDIRECT_URL);
        if (xxlUser != null) {

            // success redirect

            if (redirectUrl!=null && redirectUrl.trim().length()>0) {

                String sessionId = SsoWebLoginHelper.getSessionIdByCookie(request);
                String redirectUrlFinal = null;
                if(redirectUrl.startsWith("http")){
                    redirectUrlFinal = redirectUrl + "?" + Conf.SSO_TOKEN + "=" + sessionId;
                }else{
                    redirectUrlFinal = "http://"+redirectUrl + "?" + Conf.SSO_TOKEN + "=" + sessionId;
                }
                return "redirect:" + redirectUrlFinal;
            } else {
                return "redirect:/";
            }
        }
        if(StringUtils.isBlank(redirectUrl)){
            model.addAttribute("exceptionMsg", "重定向URL不能为空");
            return "/common/common.exception";
        }
        model.addAttribute("errorMsg", request.getParameter("errorMsg"));
        model.addAttribute(Conf.REDIRECT_URL, redirectUrl);
        return "login";
    }

    /**
     * Login
     * @param request
     * @param redirectAttributes
     * @param username
     * @param password
     * @return
     */
    @RequestMapping("/doLogin")
    public String doLogin(HttpServletRequest request,
                        HttpServletResponse response,
                        RedirectAttributes redirectAttributes,
                        String username,
                        String password,
                        String ifRemember) {

        boolean ifRem = (ifRemember!=null&&"on".equals(ifRemember))?true:false;

        // valid login
        UserInfo userInfo = userService.findUser(username, password);
        if (userInfo == null) {
            redirectAttributes.addAttribute("errorMsg", "账号密码不匹配");

            redirectAttributes.addAttribute(Conf.REDIRECT_URL, request.getParameter(Conf.REDIRECT_URL));
            return "redirect:/login";
        }

        // 1、make xxl-sso user
        userInfo.setVersion(UUID.randomUUID().toString().replaceAll("-", ""));
        userInfo.setExpireMinute(SsoLoginStore.getRedisExpireMinute());
        userInfo.setExpireFreshTime(System.currentTimeMillis());


        // 2、make session id
        String sessionId = SsoSessionIdHelper.makeToken(userInfo);

        // 3、login, store storeKey + cookie sessionId
        SsoWebLoginHelper.login(response, sessionId, userInfo, ifRem);

        // 4、return, redirect sessionId
        String redirectUrl = request.getParameter(Conf.REDIRECT_URL);
        if (redirectUrl!=null && redirectUrl.trim().length()>0) {
            String redirectUrlFinal = null;
            if(redirectUrl.startsWith("http")){
                redirectUrlFinal = redirectUrl + "?" + Conf.SSO_TOKEN + "=" + sessionId;
            }else{
                redirectUrlFinal = "http://"+redirectUrl + "?" + Conf.SSO_TOKEN + "=" + sessionId;
            }
            return "redirect:" + redirectUrlFinal;
        } else {
            return "redirect:/";
        }

    }

    /**
     * Logout
     *
     * @param request
     * @param redirectAttributes
     * @return
     */
    @RequestMapping(Conf.SSO_LOGOUT)
    public String logout(HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
        // logout
        SsoWebLoginHelper.logout(request, response);
        redirectAttributes.addAttribute(Conf.REDIRECT_URL, request.getParameter(Conf.REDIRECT_URL));
        return "redirect:/login";
    }
}
