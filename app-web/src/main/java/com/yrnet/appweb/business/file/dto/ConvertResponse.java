package com.yrnet.appweb.business.file.dto;

import lombok.Data;

/**
 * @author dengbp
 * @ClassName TransferReq
 * @Description TODO
 * @date 4/17/22 12:20 AM
 */
@Data
public class ConvertResponse {

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
