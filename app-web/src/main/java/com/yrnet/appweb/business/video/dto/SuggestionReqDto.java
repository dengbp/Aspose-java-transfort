package com.yrnet.appweb.business.video.dto;

import com.yrnet.appweb.common.entity.Token;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author dengbp
 * @ClassName SuggestionReqDto
 * @Description TODO
 * @date 2/5/21 11:24 PM
 */

@Data
public class SuggestionReqDto extends Token {

    @NotNull
    private String userId;

    /**
     * 建议描述
     */
    private String suggestionDesc;

    /**
     * 建议类型
     */
    private String suggestionType;
}
