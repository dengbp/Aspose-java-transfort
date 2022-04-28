package com.yrnet.sso.server.core.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author liuQu
 * @date 2021/5/18 16:33
 */
@TableName(value = "cmdb_role_menu_rel")
@Data
public class RoleMenuRel {

    @TableId(type = IdType.AUTO)
    private String relId;
    private String roleId;
    private String menuId;
}
