package com.yrnet.viewweb.system.service.impl;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yrnet.viewweb.common.entity.ViewWebConstant;
import com.yrnet.viewweb.monitor.service.IRedisService;
import com.yrnet.viewweb.system.entity.Menu;
import com.yrnet.viewweb.system.entity.Role;
import com.yrnet.viewweb.system.entity.User;
import com.yrnet.viewweb.system.entity.UserConfig;
import com.yrnet.viewweb.system.mapper.UserMapper;
import com.yrnet.viewweb.system.service.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("cacheService")
public class CacheServiceImpl implements ICacheService {

    @Autowired
    private IRedisService redisService;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private ObjectMapper mapper;

    @Override
    public void testConnect() throws Exception {
        this.redisService.exists("test");
    }

    @Override
    public User getUser(String username) throws Exception {
        String userString = this.redisService.get(ViewWebConstant.USER_CACHE_PREFIX + username);
        if (StringUtils.isBlank(userString)) {
            throw new Exception();
        }
        else {
            return this.mapper.readValue(userString, User.class);
        }
    }

    @Override
    public List<Role> getRoles(String username) throws Exception {
        String roleListString = this.redisService.get(ViewWebConstant.USER_ROLE_CACHE_PREFIX + username);
        if (StringUtils.isBlank(roleListString)) {
            throw new Exception();
        } else {
            JavaType type = mapper.getTypeFactory().constructParametricType(List.class, Role.class);
            return this.mapper.readValue(roleListString, type);
        }
    }

    @Override
    public List<Menu> getPermissions(String username) throws Exception {
        String permissionListString = this.redisService.get(ViewWebConstant.USER_PERMISSION_CACHE_PREFIX + username);
        if (StringUtils.isBlank(permissionListString)) {
            throw new Exception();
        } else {
            JavaType type = mapper.getTypeFactory().constructParametricType(List.class, Menu.class);
            return this.mapper.readValue(permissionListString, type);
        }
    }

    @Override
    public UserConfig getUserConfig(String userId) throws Exception {
        String userConfigString = this.redisService.get(ViewWebConstant.USER_CONFIG_CACHE_PREFIX + userId);
        if (StringUtils.isBlank(userConfigString)) {
            throw new Exception();
        }
        else {
            return this.mapper.readValue(userConfigString, UserConfig.class);
        }
    }

    @Override
    public void saveUser(User user) throws Exception {
        String username = user.getUsername();
        this.deleteUser(username);
        redisService.set(ViewWebConstant.USER_CACHE_PREFIX + username, mapper.writeValueAsString(user));
    }

    @Override
    public void updateUser(String username) throws Exception {
        User user = userMapper.findDetail(username);
        String cacheUser = redisService.get(ViewWebConstant.USER_CACHE_PREFIX + username);
        if ( cacheUser !=null ){
            User tmp = this.mapper.readValue(cacheUser, User.class);
            user.setTableName(tmp.getTableName());
            user.setProjectId(tmp.getProjectId());
        }
        this.deleteUser(username);
        redisService.set(ViewWebConstant.USER_CACHE_PREFIX + username, mapper.writeValueAsString(user));
    }

    @Override
    public void saveRoles(String username,IRoleService roleService) throws Exception {
        List<Role> roleList = roleService.findUserRole(username);
        if (!roleList.isEmpty()) {
            this.deleteRoles(username);
            redisService.set(ViewWebConstant.USER_ROLE_CACHE_PREFIX + username, mapper.writeValueAsString(roleList));
        }

    }

    @Override
    public void savePermissions(String username,IMenuService menuService) throws Exception {
        List<Menu> permissionList = menuService.findUserPermissions(username);
        if (!permissionList.isEmpty()) {
            this.deletePermissions(username);
            redisService.set(ViewWebConstant.USER_PERMISSION_CACHE_PREFIX + username, mapper.writeValueAsString(permissionList));
        }
    }

    @Override
    public void saveUserConfigs(String userId,IUserConfigService userConfigService) throws Exception {
        UserConfig userConfig = userConfigService.findByUserId(userId);
        if (userConfig != null) {
            this.deleteUserConfigs(userId);
            redisService.set(ViewWebConstant.USER_CONFIG_CACHE_PREFIX + userId, mapper.writeValueAsString(userConfig));
        }
    }

    @Override
    public void deleteUser(String username) throws Exception {
        username = username.toLowerCase();
        redisService.del(ViewWebConstant.USER_CACHE_PREFIX + username);
    }

    @Override
    public void deleteRoles(String username) throws Exception {
        username = username.toLowerCase();
        redisService.del(ViewWebConstant.USER_ROLE_CACHE_PREFIX + username);
    }

    @Override
    public void deletePermissions(String username) throws Exception {
        username = username.toLowerCase();
        redisService.del(ViewWebConstant.USER_PERMISSION_CACHE_PREFIX + username);
    }

    @Override
    public void deleteUserConfigs(String userId) throws Exception {
        redisService.del(ViewWebConstant.USER_CONFIG_CACHE_PREFIX + userId);
    }
}
