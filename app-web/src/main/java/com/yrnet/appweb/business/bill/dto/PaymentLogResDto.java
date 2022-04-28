package com.yrnet.appweb.business.bill.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author dengbp
 * @ClassName PaymentLogReqDto
 * @Description TODO
 * @date 2/5/21 4:20 PM
 */
@Data
public class PaymentLogResDto implements Serializable {

    /**
     * 支付商品名称(对应高保真的：会员充值)
     */
    private String producerName;

    /**
     * 支付时间
     */
    private Long createTime;

    /**
     * 有效时长，单位月
     */
    private Integer effectiveTime;


    /**
     * 金额(单位分，前端乘以100后展示)
     */
    private Integer amount;
}
