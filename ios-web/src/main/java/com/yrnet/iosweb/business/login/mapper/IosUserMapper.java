package com.yrnet.iosweb.business.login.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yrnet.iosweb.business.login.entity.IosUser;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

/**
 * @author baiyang
 * @version 1.0
 * @date 2020/11/29 2:30 下午
 */
@Repository
public interface IosUserMapper extends BaseMapper<IosUser> {
   IosUser queryByOpenId(String openId);
}
