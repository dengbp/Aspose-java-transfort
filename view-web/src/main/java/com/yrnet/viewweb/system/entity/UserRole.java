package com.yrnet.viewweb.system.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.wuwenze.poi.annotation.Excel;
import lombok.Data;

import java.io.Serializable;

/**
 * @author dengbp
 */
@Data
@TableName("t_user_role")
@Excel("用户角色表")
public class UserRole implements Serializable {

    private static final long serialVersionUID = 2354394771912648574L;
    /**
     * 用户ID
     */
    @TableField("USER_ID")
    private Long userId;

    /**
     * 角色ID
     */
    @TableField("ROLE_ID")
    private Long roleId;


}
