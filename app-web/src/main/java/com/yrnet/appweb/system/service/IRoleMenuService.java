package com.yrnet.appweb.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yrnet.appweb.system.entity.RoleMenu;

import java.util.List;

/**
 * @author dengbp
 */
public interface IRoleMenuService extends IService<RoleMenu> {

    void deleteRoleMenusByRoleId(String[] roleIds);

    void deleteRoleMenusByMenuId(String[] menuIds);

    List<RoleMenu> getRoleMenusByRoleId(String roleId);
}
