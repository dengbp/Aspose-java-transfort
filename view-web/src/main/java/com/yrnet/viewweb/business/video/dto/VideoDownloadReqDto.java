package com.yrnet.viewweb.business.video.dto;

import com.yrnet.viewweb.common.entity.Token;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author dengbp
 * @ClassName VideoDownloadReqDto
 * @Description TODO
 * @date 2/4/21 11:54 AM
 */
@Data
public class VideoDownloadReqDto extends Token {

    @NotNull
    private String userId;
    @NotNull
    private Integer videoId;
}
