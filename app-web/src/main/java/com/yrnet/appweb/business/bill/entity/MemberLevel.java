package com.yrnet.appweb.business.bill.entity;

import com.baomidou.mybatisplus.annotation.TableField;
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
public class MemberLevel implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;

    /**
     * 用户id
     */
    private String userId;

    /**
     * 套餐id
     */
    private Long plansId;

    /**
     * 等级编码
     */
    private String levelCode;

    /**
     * 生效开始时间,格式:yyyymmddHHMMSS
     */
        @TableField("issue_Date")
    private Long issueDate;

    /**
     * 过期时间,格式:yyyymmddHHMMSS
     */
        @TableField("expiry_Date")
    private Long expiryDate;

    /**
     * 更新时间,格式:yyyymmddHHMMSS
     */
    private Long updateTime;

    /**
     * 创建时间,格式:yyyymmddHHMMSS式:yyyymmddHHMMSS
     */
    private Long createTime;


}
