package com.yrnet.sso.server.core.model;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author liuQu
 * @date 2021/8/6 10:38
 */
@TableName(value = "cmdb_api_menu_rel")
@Data
public class ApiMenuRel {

    private String relId;
    private String menuId;
    private String apiId;
}
