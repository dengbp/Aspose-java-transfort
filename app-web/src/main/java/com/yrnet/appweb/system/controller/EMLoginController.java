package com.yrnet.appweb.system.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wf.captcha.base.Captcha;
import com.yrnet.appweb.common.annotation.Limit;
import com.yrnet.appweb.common.authentication.JwtToken;
import com.yrnet.appweb.common.controller.BaseController;
import com.yrnet.appweb.common.entity.LicenseResponse;
import com.yrnet.appweb.common.entity.ViewWebConstant;
import com.yrnet.appweb.common.exception.DocumentException;
import com.yrnet.appweb.common.properties.ViewWebProperties;
import com.yrnet.appweb.common.utils.*;
import com.yrnet.appweb.monitor.entity.ActiveUser;
import com.yrnet.appweb.monitor.entity.LoginLog;
import com.yrnet.appweb.monitor.mapper.LoginLogMapper;
import com.yrnet.appweb.monitor.service.ILoginLogService;
import com.yrnet.appweb.monitor.service.IRedisService;
import com.yrnet.appweb.system.entity.User;
import com.yrnet.appweb.system.entity.UserConfig;
import com.yrnet.appweb.system.manager.UserManager;
import com.yrnet.appweb.system.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.*;

/**
 * @author dengbp
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/em")
public class EMLoginController extends BaseController {

    @Autowired
    private IUserService userService;
    @Autowired
    private UserManager userManager;
    @Autowired
    private ILoginLogService loginLogService;
    @Autowired
    private IRedisService redisService;
    @Autowired
    private ObjectMapper mapper;
    @Autowired
    private LoginLogMapper loginLogMapper;
    @Autowired
    private ViewWebProperties yinXXProperties;


    @PostMapping("login")
    @Limit(key = "login", period = 60, count = 20, name = "登录接口", prefix = "limit")
    public LicenseResponse login(
            @NotBlank(message = "{required}") String username,
            @NotBlank(message = "{required}") String password,
            @NotBlank(message = "{required}") String tableName,
            @NotBlank(message = "{required}") String projectId,
            @NotBlank(message = "{required}") String imageCode,
            // @NotBlank(message = "{required}") String verifyCode,
            boolean rememberMe, HttpServletRequest request) throws Exception {

        String key = redisService.get("RANDOMVALIDATECODEKEY");
        if (key == null) {
            return new LicenseResponse().success().code(HttpStatus.valueOf(500)).message("验证码过期");
        }
        if (!key.equalsIgnoreCase(imageCode)) {
            return new LicenseResponse().success().code(HttpStatus.valueOf(500)).message("验证码错误");
        }
        username = StringUtils.lowerCase(username);
        password = MD5Util.encrypt(username, password);

        final String errorMessage = "用户名或密码错误";
        User user = this.userManager.getUser(username);

        if (user == null) {
            throw new DocumentException(errorMessage);
        }
        if (!StringUtils.equals(user.getPassword(), password)) {
            throw new DocumentException(errorMessage);
        }
        if (User.STATUS_LOCK.equals(user.getStatus())) {
            throw new DocumentException("账号已被锁定,请联系管理员！");
        }
        log.info("验证用户是否已在别处登录...");
        Set<ActiveUser> activeUsers = this.getActiveUser(null);
        @NotBlank(message = "{required}") String finalUsername = username;
        activeUsers.forEach(activeUser -> {
            if(finalUsername.equals(String.valueOf(activeUser.getUsername()))) {
                log.warn("此用户正在使用({}),将要被剔除...",activeUser.getUsername());
                try {
                    this.kickout(activeUser.getId());
                } catch (Exception e) {
                    log.error("剔除用户失败");
                    e.printStackTrace();
                }
            }
        });
        // 保存登录记录
        LoginLog loginLog = new LoginLog();
        loginLog.setUsername(username);
        this.loginLogService.saveLoginLog(loginLog);

        String token = JWTUtil.encryptToken(JWTUtil.sign(username, password, yinXXProperties.getShiro().getJwtTimeOut()));
        LocalDateTime expireTime = LocalDateTime.now().plusSeconds(yinXXProperties.getShiro().getJwtTimeOut());
        String expireTimeStr = DateUtil.formatFullTime(expireTime);
        JwtToken jwtToken = new JwtToken(token, expireTimeStr);
        user.setTableName(tableName);
        user.setProjectId(Long.parseLong(projectId));
        String userId = this.saveTokenToRedis(user, jwtToken, request);
        user.setId(userId);
        // 更新用户登录时间
        this.userService.updateLoginTime(user);
        Map<String, Object> userInfo = this.generateUserInfo(jwtToken, user);
        return new LicenseResponse().message("认证成功").data(userInfo);
    }

    @PostMapping("regist")
    public void regist(
            @NotBlank(message = "{required}") String username,
            @NotBlank(message = "{required}") String password) throws Exception {
        this.userService.regist(username, password);
    }

    @GetMapping("index/{username}")
    public LicenseResponse index(@NotBlank(message = "{required}") @PathVariable String username) {
        Map<String, Object> data = new HashMap<>();
        // 获取系统访问记录
        Long totalVisitCount = loginLogMapper.findTotalVisitCount();
        data.put("totalVisitCount", totalVisitCount);
        Long todayVisitCount = loginLogMapper.findTodayVisitCount();
        data.put("todayVisitCount", todayVisitCount);
        Long todayIp = loginLogMapper.findTodayIp();
        data.put("todayIp", todayIp);
        // 获取近期系统访问记录
        List<Map<String, Object>> lastSevenVisitCount = loginLogMapper.findLastSevenDaysVisitCount(null);
        data.put("lastSevenVisitCount", lastSevenVisitCount);
        User param = new User();
        param.setUsername(username);
        List<Map<String, Object>> lastSevenUserVisitCount = loginLogMapper.findLastSevenDaysVisitCount(param);
        data.put("lastSevenUserVisitCount", lastSevenUserVisitCount);
        return new LicenseResponse().data(data);
    }

    @GetMapping("images/captcha")
    public void captcha(HttpServletRequest request, HttpServletResponse response) throws Exception {
        CaptchaUtil.outPng(110, 34, 4, Captcha.TYPE_ONLY_NUMBER, request, response);
    }

    @GetMapping("logout/{id}")
    public void logout(@NotBlank(message = "{required}") @PathVariable String id) throws Exception {
        this.kickout(id);
    }


    private Set<ActiveUser> getActiveUser(String username)throws Exception {
        String now = DateUtil.formatFullTime(LocalDateTime.now());
        Set<String> userOnlineStringSet = redisService.zrangeByScore(ViewWebConstant.ACTIVE_USERS_ZSET_PREFIX, now, "+inf");
        Set<ActiveUser> activeUsers = new HashSet<>();
        for (String userOnlineString : userOnlineStringSet) {
            ActiveUser activeUser = mapper.readValue(userOnlineString, ActiveUser.class);
            activeUser.setToken(null);
            if (StringUtils.isNotBlank(username)) {
                if (StringUtils.equalsIgnoreCase(username, activeUser.getUsername())) {
                    activeUsers.add(activeUser);
                }
            } else {
                activeUsers.add(activeUser);
            }
        }
        return activeUsers;
    }

    @RequiresPermissions("user:online")
    @GetMapping("online")
    public LicenseResponse userOnline(String username) throws Exception {
        Set<ActiveUser> activeUsers = this.getActiveUser(username);
        return new LicenseResponse().data(activeUsers);
    }

    /**
     * 生成前端需要的用户信息，包括：
     * 1. token
     * 2. Vue Router
     * 3. 用户角色
     * 4. 用户权限
     * 5. 前端系统个性化配置信息
     *
     * @param token token
     * @param user  用户信息
     * @return UserInfo
     */
    private Map<String, Object> generateUserInfo(JwtToken token, User user) {
        String username = user.getUsername();
        Map<String, Object> userInfo = new HashMap<>();
        userInfo.put("token", token.getToken());
        userInfo.put("exipreTime", token.getExipreAt());

        Set<String> roles = this.userManager.getUserRoles(username);
        userInfo.put("roles", roles);

        Set<String> permissions = this.userManager.getUserPermissions(username);
        userInfo.put("permissions", permissions);

        UserConfig userConfig = this.userManager.getUserConfig(String.valueOf(user.getUserId()));
        userInfo.put("config", userConfig);

        user.setPassword("it's a secret");
        userInfo.put("user", user);
        return userInfo;
    }

    @DeleteMapping("kickout/{id}")
    @RequiresPermissions("user:kickout")
    public void kickout(@NotBlank(message = "{required}") @PathVariable String id) throws Exception {
        String now = DateUtil.formatFullTime(LocalDateTime.now());
        Set<String> userOnlineStringSet = redisService.zrangeByScore(ViewWebConstant.ACTIVE_USERS_ZSET_PREFIX, now, "+inf");
        ActiveUser kickoutUser = null;
        String kickoutUserString = "";
        for (String userOnlineString : userOnlineStringSet) {
            ActiveUser activeUser = mapper.readValue(userOnlineString, ActiveUser.class);
            if (StringUtils.equals(activeUser.getId(), id)) {
                kickoutUser = activeUser;
                kickoutUserString = userOnlineString;
            }
        }
        if (kickoutUser != null && StringUtils.isNotBlank(kickoutUserString)) {
            // 删除 zset中的记录
            redisService.zrem(ViewWebConstant.ACTIVE_USERS_ZSET_PREFIX, kickoutUserString);
            // 删除对应的 token缓存
            redisService.del(ViewWebConstant.TOKEN_CACHE_PREFIX + kickoutUser.getToken());
        }
    }

    private String saveTokenToRedis(User user, JwtToken token, HttpServletRequest request) throws Exception {
        String ip = IPUtil.getIpAddr(request);

        // 构建在线用户
        ActiveUser activeUser = new ActiveUser();
        activeUser.setUsername(user.getUsername());
        activeUser.setIp(ip);
        activeUser.setToken(token.getToken());
        activeUser.setLoginAddress(AddressUtil.getCityInfo(ip));

        // zset 存储登录用户，score 为过期时间戳
        this.redisService.zadd(ViewWebConstant.ACTIVE_USERS_ZSET_PREFIX, Double.valueOf(token.getExipreAt()), mapper.writeValueAsString(activeUser));
        // redis 中存储这个加密 token，key = 前缀 + 加密 token + .ip
        this.redisService.set(ViewWebConstant.TOKEN_CACHE_PREFIX + token.getToken(), token.getToken(), yinXXProperties.getShiro().getJwtTimeOut() * 1000);

        return activeUser.getId();
    }
}
