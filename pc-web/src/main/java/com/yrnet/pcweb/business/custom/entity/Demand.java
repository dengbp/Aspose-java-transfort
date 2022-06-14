package com.yrnet.pcweb.business.custom.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 *
 *
 * @author dengbp
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("custom_demand")
public class Demand implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 客户需求
     */
    private String demand;

    /**
     * 客户电话
     */
    private String phone;

    /**
     * 客户id,openId
     */
    private String userId;


}
