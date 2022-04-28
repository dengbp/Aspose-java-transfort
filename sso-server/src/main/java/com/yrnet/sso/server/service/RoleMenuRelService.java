package com.yrnet.sso.server.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yrnet.sso.server.core.model.RoleMenuRel;

import java.util.List;

/**
 * @author liuQu
 * @date 2021/8/13 14:28
 */
public interface RoleMenuRelService extends IService<RoleMenuRel> {

    List<String> queryMenuIdList(List<String> roleIdList);
}
