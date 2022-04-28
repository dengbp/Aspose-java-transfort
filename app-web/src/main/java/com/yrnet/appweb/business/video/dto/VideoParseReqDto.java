package com.yrnet.appweb.business.video.dto;

import com.yrnet.appweb.common.entity.Token;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 *
 *
 * @author dengbp
 */
@Data
public class VideoParseReqDto extends Token {

    @NotNull
    private String userId;
    @NotNull
    private String parseUrl;

}
