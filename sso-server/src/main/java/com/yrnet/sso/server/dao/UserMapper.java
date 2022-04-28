package com.yrnet.sso.server.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yrnet.sso.server.core.model.UserInfo;

import java.util.List;

/**
 * @author liuQu
 * @date 2021/5/10 14:53
 */
public interface UserMapper extends BaseMapper<UserInfo> {

    List<String> queryRoleIdByUserId(String userId);
}
