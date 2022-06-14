package com.yrnet.pcweb.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yrnet.pcweb.system.entity.RoleMenu;

import java.util.List;

/**
 * @author dengbp
 */
public interface IRoleMenuService extends IService<RoleMenu> {

    void deleteRoleMenusByRoleId(String[] roleIds);

    void deleteRoleMenusByMenuId(String[] menuIds);

    List<RoleMenu> getRoleMenusByRoleId(String roleId);
}
