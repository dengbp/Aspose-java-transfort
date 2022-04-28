package com.yrnet.sso.server.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yrnet.sso.server.core.model.UserInfo;

import java.util.List;

public interface UserService extends IService<UserInfo> {

    UserInfo findUser(String username, String password);

    void saveLoginLog(String userId,String ip);

    List<String> queryRoleIdByUserId(String userId);

}
