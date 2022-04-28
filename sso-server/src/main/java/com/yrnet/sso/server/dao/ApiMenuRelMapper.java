package com.yrnet.sso.server.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yrnet.sso.server.core.model.ApiMenuRel;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author liuQu
 * @date 2021/8/13 14:38
 */
public interface ApiMenuRelMapper extends BaseMapper<ApiMenuRel> {

    List<Map<String,String>> queryApiInfo(@Param("menuIdList") List<String> menuIdList,@Param("url") String url);
}
