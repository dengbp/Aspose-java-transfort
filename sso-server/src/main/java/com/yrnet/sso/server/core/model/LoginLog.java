package com.yrnet.sso.server.core.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author liuQu
 * @date 2021/5/21 10:37
 */
@TableName(value = "cmdb_login_log")
@Data
public class LoginLog {

    @TableId(type = IdType.AUTO)
    private String id;
    private String userId;
    private String clientIp;
    private String loginTime;
}
