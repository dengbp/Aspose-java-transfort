package com.yrnet.sso.server.service.impl;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yrnet.sso.server.core.model.ApiMenuRel;
import com.yrnet.sso.server.dao.ApiMenuRelMapper;
import com.yrnet.sso.server.service.ApiMenuRelService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author liuQu
 * @date 2021/8/13 14:40
 */
@Service
public class ApiMenuRelServiceImpl extends ServiceImpl<ApiMenuRelMapper, ApiMenuRel> implements ApiMenuRelService {

    private ApiMenuRelMapper apiMenuRelMapper;

    public ApiMenuRelServiceImpl(ApiMenuRelMapper apiMenuRelMapper){
        this.apiMenuRelMapper = apiMenuRelMapper;
    }
    @Override
    public boolean isAllowVisit(List<String> menuIdList, String url) {
        List<Map<String,String>> list = this.apiMenuRelMapper.queryApiInfo(menuIdList,url);
        return CollectionUtils.isNotEmpty(list);
    }
}
