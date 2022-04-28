package com.yrnet.appweb.business.login.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yrnet.appweb.business.login.entity.UserRelation;
import org.apache.ibatis.annotations.Param;

/**
 * @author baiyang
 * @version 1.0
 * @date 2020/11/29 2:40 下午
 */
public interface UserRelationMapper extends BaseMapper<UserRelation> {
    UserRelation selectByWxOpenId(@Param("id") String wxOpenId, @Param("type") Integer type);
}
