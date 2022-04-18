package com.yrnet.viewweb.business.file.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author dengbp
 * @ClassName TransferReq
 * @Description TODO
 * @date 4/17/22 12:20 AM
 */
@Builder
@Data
public class TransferResponse {

    /** 文件id,用于方便更新 */
    private Long fileId;

    /** 文件名带后缀 */
    private String fileName;

    private String filePath;

    /**
     * 新文件大小，单位kb
     */
    private Long fileSize;

    /** 转换状态 0成功 1失败 */
    private Integer state;

}
