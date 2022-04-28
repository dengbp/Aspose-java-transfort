package com.yrnet.appweb.business.login.service;

import com.yrnet.appweb.business.login.entity.WxUser;

/**
 * @author baiyang
 * @version 1.0
 * @date 2020/11/29 2:28 下午
 */
public interface WxUserService {

    WxUser queryByOpenId(String openId);

    void insert(WxUser wxUser);

    void updateByWxUserId(WxUser wxUserFromData);
}
