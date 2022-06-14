package com.yrnet.pcweb.business.file.dto;

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
public class ConvertLogRequest extends Token {

    @NotNull
    private String openId;

    /** 要转换的文件id,多个逗号分开 */
    @NotNull
    private String fileId;

    /**
     * 转换成目标文档类型：word转pdf:1
     * pdf转word:2
     * excel转pdf:3
     * pdf转excel:4
     * ppt转pdf:5
     * pdf转ppt:6
     * jpg转pdf:7
     * pdf转jpg:8
     * png转pdf:9
     * pdf转png:10
     * docx转pdf:11
     * pdf转docx:12
     * odt转pdf:13
     * doc转pdf:14
     **/
    @NotNull
    private Integer toType;


}
