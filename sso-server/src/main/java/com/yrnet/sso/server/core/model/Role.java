package com.yrnet.sso.server.core.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.List;

/**
 * @author liuQu
 * @date 2021/5/17 18:04
 */
@TableName(value = "cmdb_role")
@Data
public class Role {

    @TableId(type = IdType.AUTO)
    private String roleId;
    private String roleName;
    private String roleDes;
    @TableField(exist = false)
    private int userNum;
    /**菜单ID，多个英文逗号分隔*/
    @TableField(exist = false)
    private List<String> menuIds;
    @TableField(exist = false)
    private List<UserInfo> userInfoList;
    @TableField(exist = false)
    /**用户ID，多个英文逗号分隔*/
    private List<String> userIds;
    @TableField(exist = false)
    private String pageLength;
    @TableField(exist = false)
    private String pageNo;

}
