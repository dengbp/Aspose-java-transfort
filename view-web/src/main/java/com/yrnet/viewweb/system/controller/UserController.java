package com.yrnet.viewweb.system.controller;

import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.wuwenze.poi.ExcelKit;
import com.yrnet.viewweb.common.annotation.Log;
import com.yrnet.viewweb.common.controller.BaseController;
import com.yrnet.viewweb.common.entity.QueryRequestPage;
import com.yrnet.viewweb.common.exception.YinXXException;
import com.yrnet.viewweb.common.utils.MD5Util;
import com.yrnet.viewweb.system.entity.Role;
import com.yrnet.viewweb.system.entity.User;
import com.yrnet.viewweb.system.entity.UserConfig;
import com.yrnet.viewweb.system.service.IRoleService;
import com.yrnet.viewweb.system.service.IUserConfigService;
import com.yrnet.viewweb.system.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author dengbp
 */
@Slf4j
@Validated
@RestController
@RequestMapping("user")
public class UserController extends BaseController {

    private String message;

    @Autowired
    private IUserService userService;
    @Autowired
    private IUserConfigService userConfigService;
    @Autowired
    private IRoleService roleService;

    @GetMapping("check/{username}")
    public boolean checkUserName(@NotBlank(message = "{required}") @PathVariable String username) {
        return this.userService.findByName(username) == null;
    }

    @GetMapping("/{username}")
    public User detail(@NotBlank(message = "{required}") @PathVariable String username) {
        User user=this.userService.findByName(username);
        //修复用户修改自己的个人信息第二次提示roleId不能为空
        List<Role> roles=roleService.findUserRole(username);
        List<Long> roleIds=roles.stream().map(role ->role.getRoleId()).collect(Collectors.toList());
        String roleIdStr= StringUtils.join(roleIds.toArray(new Long[roleIds.size()]),",");
        user.setRoleId(roleIdStr);
        return user;
    }

    @GetMapping
    @RequiresPermissions("user:view")
    public Map<String, Object> userList(QueryRequestPage queryRequest, User user) {
        return getDataTable(userService.findUserDetail(user, queryRequest));
    }

    @Log("新增用户")
    @PostMapping
    @RequiresPermissions("user:add")
    public void addUser(@Valid User user) throws YinXXException {
        try {
            this.userService.createUser(user);
        } catch (Exception e) {
            message = "新增用户失败";
            log.error(message, e);
            throw new YinXXException(message);
        }
    }

    @Log("修改用户")
    @PutMapping
    @RequiresPermissions("user:update")
    public void updateUser(@Valid User user) throws YinXXException {
        try {
            this.userService.updateUser(user);
        } catch (Exception e) {
            message = "修改用户失败";
            log.error(message, e);
            throw new YinXXException(message);
        }
    }

    @Log("删除用户")
    @DeleteMapping("/{userIds}")
    @RequiresPermissions("user:delete")
    public void deleteUsers(@NotBlank(message = "{required}") @PathVariable String userIds) throws YinXXException {
        try {
            String[] ids = userIds.split(StringPool.COMMA);
            this.userService.deleteUsers(ids);
        } catch (Exception e) {
            message = "删除用户失败";
            log.error(message, e);
            throw new YinXXException(message);
        }
    }

    @PutMapping("profile")
    public void updateProfile(@Valid User user) throws YinXXException {
        try {
            this.userService.updateProfile(user);
        } catch (Exception e) {
            message = "修改个人信息失败";
            log.error(message, e);
            throw new YinXXException(message);
        }
    }

    @PutMapping("avatar")
    public void updateAvatar(
            @NotBlank(message = "{required}") String username,
            @NotBlank(message = "{required}") String avatar) throws YinXXException {
        try {
            this.userService.updateAvatar(username, avatar);
        } catch (Exception e) {
            message = "修改头像失败";
            log.error(message, e);
            throw new YinXXException(message);
        }
    }

    @PutMapping("userconfig")
    public void updateUserConfig(@Valid UserConfig userConfig) throws YinXXException {
        try {
            this.userConfigService.update(userConfig);
        } catch (Exception e) {
            message = "修改个性化配置失败";
            log.error(message, e);
            throw new YinXXException(message);
        }
    }

    @GetMapping("password/check")
    public boolean checkPassword(
            @NotBlank(message = "{required}") String username,
            @NotBlank(message = "{required}") String password) {
        String encryptPassword = MD5Util.encrypt(username, password);
        User user = userService.findByName(username);
        if (user != null) {
            return StringUtils.equals(user.getPassword(), encryptPassword);
        }
        else {
            return false;
        }
    }

    @PutMapping("password")
    public void updatePassword(
            @NotBlank(message = "{required}") String username,
            @NotBlank(message = "{required}") String password) throws YinXXException {
        try {
            userService.updatePassword(username, password);
        } catch (Exception e) {
            message = "修改密码失败";
            log.error(message, e);
            throw new YinXXException(message);
        }
    }

    @PutMapping("password/reset")
    @RequiresPermissions("user:reset")
    public void resetPassword(@NotBlank(message = "{required}") String usernames) throws YinXXException {
        try {
            String[] usernameArr = usernames.split(StringPool.COMMA);
            this.userService.resetPassword(usernameArr);
        } catch (Exception e) {
            message = "重置用户密码失败";
            log.error(message, e);
            throw new YinXXException(message);
        }
    }

    @PostMapping("excel")
    @RequiresPermissions("user:export")
    public void export(QueryRequestPage queryRequest, User user, HttpServletResponse response) throws YinXXException {
        try {
            List<User> users = this.userService.findUserDetail(user, queryRequest).getRecords();
            ExcelKit.$Export(User.class, response).downXlsx(users, false);
        } catch (Exception e) {
            message = "导出Excel失败";
            log.error(message, e);
            throw new YinXXException(message);
        }
    }
}
