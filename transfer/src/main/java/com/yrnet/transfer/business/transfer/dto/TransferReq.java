package com.yrnet.transfer.business.transfer.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author dengbp
 * @ClassName TransferReq
 * @Description TODO
 * @date 4/17/22 12:20 AM
 */
@Data
public class TransferReq {
    private Integer fileId;
    @NotNull
    private String filePath;
    /**
     * Description word转pdf:1
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
