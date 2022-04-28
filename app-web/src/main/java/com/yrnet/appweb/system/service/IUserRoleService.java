package com.yrnet.appweb.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yrnet.appweb.system.entity.UserRole;

import java.util.List;

/**
 * @author dengbp
 */
public interface IUserRoleService extends IService<UserRole> {

    void deleteUserRolesByRoleId(String[] roleIds);

    void deleteUserRolesByUserId(String[] userIds);

    List<String> findUserIdsByRoleId(String[] roleIds);
}
