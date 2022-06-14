package com.yrnet.pcweb.business.custom.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author dengbp
 * @ClassName CustomDemandReqDto
 * @Description TODO
 * @date 6/28/21 1:33 PM
 */
@Data
public class CustomDemandReqDto {

    /**
     * 客户需求
     */
    @NotNull
    private String demand;

    /**
     * 客户电话
     */
    @NotNull
    private String phone;

    /**
     * 客户id,openId
     */
    @NotNull
    private String userId;
}
