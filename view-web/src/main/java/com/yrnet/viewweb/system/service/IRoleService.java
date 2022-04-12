package com.yrnet.viewweb.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yrnet.viewweb.common.entity.QueryRequestPage;
import com.yrnet.viewweb.system.entity.Role;

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
