package com.yrnet.sso.server.core.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.io.Serializable;
import java.util.Map;

/**
 * xxl sso user
 *
 * @author xuxueli 2018-04-02 19:59:49
 */
@TableName(value = "cmdb_user")
@Data
public class UserInfo implements Serializable {
    private static final long serialVersionUID = 42L;

    @TableId(type = IdType.AUTO)
    private String userId;
    private String userCode;
    private String userName;
    private String email;
    @JsonIgnore
    private String pwd;
    private int status;
    private String phone;
    @TableField(exist = false)
    private String version;
    @TableField(exist = false)
    private int expireMinute;
    @TableField(exist = false)
    private long expireFreshTime;



}
