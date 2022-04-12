package com.yrnet.viewweb.system.manager;

import com.yrnet.viewweb.common.router.RouterMeta;
import com.yrnet.viewweb.common.router.VueRouter;
import com.yrnet.viewweb.common.utils.LicenseUtil;
import com.yrnet.viewweb.common.utils.SpringContextUtil;
import com.yrnet.viewweb.common.utils.TreeUtil;
import com.yrnet.viewweb.system.entity.Menu;
import com.yrnet.viewweb.system.entity.Role;
import com.yrnet.viewweb.system.entity.User;
import com.yrnet.viewweb.system.entity.UserConfig;
import com.yrnet.viewweb.system.service.*;
import com.yrnet.viewweb.system.service.impl.MenuServiceImpl;
import com.yrnet.viewweb.system.service.impl.RoleServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 封装一些和 User相关的业务操作
 */
@Service
public class UserManager {

    @Autowired
    private ICacheService cacheService;
    @Autowired
    private IUserConfigService userConfigService;

    /**
     * 通过用户名获取用户基本信息
     *
     * @param username 用户名
     * @return 用户基本信息
     */
    public User getUser(String username) {
        return LicenseUtil.selectCacheByTemplate(
                () -> this.cacheService.getUser(username),
                () -> ((IUserService)SpringContextUtil.getBean("userService")).findByName(username));
    }

    /**
     * 通过用户名获取用户角色集合
     *
     * @param username 用户名
     * @return 角色集合
     */
    public Set<String> getUserRoles(String username) {
        List<Role> roleList = LicenseUtil.selectCacheByTemplate(
                () -> this.cacheService.getRoles(username),
                () -> ((IRoleService)SpringContextUtil.getBean("roleService")).findUserRole(username));
        return roleList.stream().map(Role::getRoleName).collect(Collectors.toSet());
    }

    /**
     * 通过用户名获取用户权限集合
     *
     * @param username 用户名
     * @return 权限集合
     */
    public Set<String> getUserPermissions(String username) {
        List<Menu> permissionList = LicenseUtil.selectCacheByTemplate(
                () -> this.cacheService.getPermissions(username),
                () -> ((IMenuService)SpringContextUtil.getBean("menuService")).findUserPermissions(username));
        return permissionList.stream().map(Menu::getPerms).collect(Collectors.toSet());
    }

    /**
     * 通过用户名构建 Vue路由
     *
     * @param username 用户名
     * @return 路由集合
     */
    public ArrayList<VueRouter<Menu>> getUserRouters(String username) {
        List<VueRouter<Menu>> routes = new ArrayList<>();
        List<Menu> menus = ((IMenuService)SpringContextUtil.getBean("menuService")).findUserMenus(username);
        menus.forEach(menu -> {
            VueRouter<Menu> route = new VueRouter<>();
            route.setId(menu.getMenuId().toString());
            route.setParentId(menu.getParentId().toString());
            route.setIcon(menu.getIcon());
            route.setPath(menu.getPath());
            route.setComponent(menu.getComponent());
            route.setName(menu.getMenuName());
            route.setMeta(new RouterMeta(true, null));
            routes.add(route);
        });
        return TreeUtil.buildVueRouter(routes);
    }

    /**
     * 通过用户 ID获取前端系统个性化配置
     *
     * @param userId 用户 ID
     * @return 前端系统个性化配置
     */
    public UserConfig getUserConfig(String userId) {
        return LicenseUtil.selectCacheByTemplate(
                () -> this.cacheService.getUserConfig(userId),
                () -> this.userConfigService.findByUserId(userId));
    }

    /**
     * 将用户相关信息添加到 Redis缓存中
     *
     * @param user user
     */
    public void loadUserRedisCache(User user) throws Exception {
        // 缓存用户
        cacheService.updateUser(user.getUsername());
        // 缓存用户角色
        cacheService.saveRoles(user.getUsername(),(SpringContextUtil.getBean(RoleServiceImpl.class)));
        // 缓存用户权限
        cacheService.savePermissions(user.getUsername(),(SpringContextUtil.getBean(MenuServiceImpl.class)));
        // 缓存用户个性化配置
        cacheService.saveUserConfigs(String.valueOf(user.getUserId()),userConfigService);
    }

    /**
     * 将用户角色和权限添加到 Redis缓存中
     *
     * @param userIds userIds
     */
    public void loadUserPermissionRoleRedisCache(List<String> userIds) throws Exception {
        for (String userId : userIds) {
            User user = ((IUserService)SpringContextUtil.getBean("userService")).getById(userId);
            // 缓存用户角色
            cacheService.saveRoles(user.getUsername(),((IRoleService)SpringContextUtil.getBean("roleService")));
            // 缓存用户权限
            cacheService.savePermissions(user.getUsername(),((IMenuService)SpringContextUtil.getBean("menuService")));
        }
    }

    /**
     * 通过用户 id集合批量删除用户 Redis缓存
     *
     * @param userIds userIds
     */
    public void deleteUserRedisCache(String... userIds) throws Exception {
        for (String userId : userIds) {
            User user = ((IUserService)SpringContextUtil.getBean("userService")).getById(userId);
            if (user != null) {
                cacheService.deleteUser(user.getUsername());
                cacheService.deleteRoles(user.getUsername());
                cacheService.deletePermissions(user.getUsername());
            }
            cacheService.deleteUserConfigs(userId);
        }
    }

}
