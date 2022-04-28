package com.yrnet.appweb.business.bill.entity;

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
public class PaymentLog implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    /**
     * 用户id
     */
    private String userId;

    /**
     * 支付商品id
     */
    private Integer producerId;

    /**
     * 支付商品名称
     */
    private String producerName;

    /**
     * 金额
     */
    private BigDecimal amount;

    /**
     * 0待支付，1失败，2成功
     */
    private Integer status;

    /**
     * 支付备注
     */
    private String remark;

    /**
     * 创建时间
     */
    private Long createTime;


}
