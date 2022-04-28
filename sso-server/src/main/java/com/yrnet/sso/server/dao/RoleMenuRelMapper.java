package com.yrnet.sso.server.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yrnet.sso.server.core.model.RoleMenuRel;

import java.util.List;

/**
 * @author liuQu
 * @date 2021/8/13 14:31
 */
public interface RoleMenuRelMapper extends BaseMapper<RoleMenuRel> {

    List<String> queryMenuIdList(List<String> roleIdList);
}
