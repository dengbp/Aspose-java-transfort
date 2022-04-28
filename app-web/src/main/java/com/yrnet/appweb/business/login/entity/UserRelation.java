package com.yrnet.appweb.business.login.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

/**
 * @author baiyang
 * @version 1.0
 * @date 2020/11/29 2:26 下午
 */
@Data
@TableName("user_relation")
public class UserRelation {

    @TableId
    @JsonSerialize(using = ToStringSerializer.class)
    private Long userId;
    private Integer type;
    private String wxOpenId;
    private Long pcId;
}
