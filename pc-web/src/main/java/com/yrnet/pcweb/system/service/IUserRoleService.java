package com.yrnet.pcweb.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yrnet.pcweb.system.entity.UserRole;

import java.util.List;

/**
 * @author dengbp
 */
public interface IUserRoleService extends IService<UserRole> {

    void deleteUserRolesByRoleId(String[] roleIds);

    void deleteUserRolesByUserId(String[] userIds);

    List<String> findUserIdsByRoleId(String[] roleIds);
}
