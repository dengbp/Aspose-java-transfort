package com.yrnet.pcweb.system.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.wuwenze.poi.annotation.Excel;
import lombok.Data;

import java.io.Serializable;

/**
 * @author dengbp
 */
@Data
@TableName("t_role_menu")
@Excel("角色菜单关系表")
public class RoleMenu implements Serializable {

    private static final long serialVersionUID = -5200596408874170216L;
    /**
     * 角色ID
     */
    @TableField("ROLE_ID")
    private Long roleId;

    /**
     * 菜单/按钮ID
     */
    @TableField("MENU_ID")
    private Long menuId;


}
