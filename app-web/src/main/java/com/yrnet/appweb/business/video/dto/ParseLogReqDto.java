package com.yrnet.appweb.business.video.dto;

import com.yrnet.appweb.common.entity.Token;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author dengbp
 * @ClassName PaymentLogReqDto
 * @Description TODO
 * @date 2/5/21 4:20 PM
 */
@Data
public class ParseLogReqDto extends Token {

    @NotNull
    private String userId;
}
