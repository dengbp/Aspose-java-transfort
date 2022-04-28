package com.yrnet.sso.server.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yrnet.sso.server.core.model.LoginLog;
import org.apache.ibatis.annotations.Param;

/**
 * @author liuQu
 * @date 2021/5/21 10:38
 */
public interface LoginLogMapper extends BaseMapper<LoginLog> {

    void saveLoginLog(@Param("userId") String userId,@Param("ip") String ip);
}
