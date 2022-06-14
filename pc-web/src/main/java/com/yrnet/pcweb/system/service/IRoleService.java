package com.yrnet.pcweb.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yrnet.pcweb.common.entity.QueryRequestPage;
import com.yrnet.pcweb.system.entity.Role;

import java.util.List;

/**
 * @author dengbp
 */
public interface IRoleService extends IService<Role> {

    IPage<Role> findRoles(Role role, QueryRequestPage request);

    List<Role> findUserRole(String userName);

    Role findByName(String roleName);

    void createRole(Role role);

    void deleteRoles(String[] roleIds) throws Exception;

    void updateRole(Role role) throws Exception;
}
