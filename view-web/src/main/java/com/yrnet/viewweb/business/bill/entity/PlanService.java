package com.yrnet.viewweb.business.bill.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 *
 *
 * @author dengbp
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class PlanService implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 套餐名称
     */
    private String planName;

    /**
     * 套餐费用，单位分
     */
    private BigDecimal amount;

    /**
     * 有效时长，单位月
     */
    private Integer effectiveTime;

    /**
     * 创建时间
     */
    private Long createTime;

    /** 0非默认，1默认 */
    private Integer checked;


}
