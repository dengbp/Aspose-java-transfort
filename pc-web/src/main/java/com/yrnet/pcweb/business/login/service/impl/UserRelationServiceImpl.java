package com.yrnet.pcweb.business.login.service.impl;

import com.yrnet.pcweb.business.login.entity.UserRelation;
import com.yrnet.pcweb.business.login.mapper.UserRelationMapper;
import com.yrnet.pcweb.business.login.service.UserRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author baiyang
 * @version 1.0
 * @date 2020/11/29 2:39 下午
 */
@Service
public class UserRelationServiceImpl implements UserRelationService {
    @Autowired
    private UserRelationMapper userRelationMapper;

    @Override
    public void insert(UserRelation userRelation) {
        userRelationMapper.insert(userRelation);
    }

    @Override
    public UserRelation selectByWxOpenId(String id,Integer type) {
        return userRelationMapper.selectByWxOpenId(id,type);
    }


}
