package com.yrnet.viewweb.business.video.dto;

import com.yrnet.viewweb.common.entity.Token;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 *
 *
 * @author dengbp
 */
@Data
public class ShareReqDto extends Token {

    @NotNull
    private String userId;

    @NotNull
    private String shareTime;

}
