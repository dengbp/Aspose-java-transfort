package com.yrnet.pcweb.business.bill.dto;

import com.yrnet.pcweb.common.entity.Token;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author dengbp
 * @ClassName PaymentLogReqDto
 * @Description TODO
 * @date 2/5/21 4:20 PM
 */
@Data
public class PaymentLogReqDto extends Token {

    @NotNull
    private String userId;
}
