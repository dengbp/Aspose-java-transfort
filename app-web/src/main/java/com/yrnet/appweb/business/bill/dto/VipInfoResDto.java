package com.yrnet.appweb.business.bill.dto;

import lombok.Builder;
import lombok.Data;

/**
 * @author dengbp
 * @ClassName VipInfoResDto
 * @Description TODO
 * @date 4/27/22 1:34 PM
 */
@Builder
@Data
public class VipInfoResDto {

    /** 0 普通用户；1 员会 */
    private Integer state;

    /** 到期时间(yyyyMMdd)，显示要转成天数 */
    private String expireDate;
}
