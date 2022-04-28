package com.yrnet.sso.server.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yrnet.sso.server.core.model.RoleMenuRel;
import com.yrnet.sso.server.dao.RoleMenuRelMapper;
import com.yrnet.sso.server.service.RoleMenuRelService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author liuQu
 * @date 2021/8/13 14:30
 */
@Service
public class RoleMenuRelServiceImpl extends ServiceImpl<RoleMenuRelMapper, RoleMenuRel> implements RoleMenuRelService {


    private RoleMenuRelMapper roleMenuRelMapper;

    public RoleMenuRelServiceImpl(RoleMenuRelMapper roleMenuRelMapper){
        this.roleMenuRelMapper = roleMenuRelMapper;
    }
    @Override
    public List<String> queryMenuIdList(List<String> roleIdList) {
        return roleMenuRelMapper.queryMenuIdList(roleIdList);
    }
}
