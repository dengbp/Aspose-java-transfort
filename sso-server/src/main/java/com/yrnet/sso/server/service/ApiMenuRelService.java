package com.yrnet.sso.server.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yrnet.sso.server.core.model.ApiMenuRel;

import java.util.List;

/**
 * @author liuQu
 * @date 2021/8/13 14:39
 */
public interface ApiMenuRelService extends IService<ApiMenuRel> {

    boolean isAllowVisit(List<String> menuIdList,String url);
}
