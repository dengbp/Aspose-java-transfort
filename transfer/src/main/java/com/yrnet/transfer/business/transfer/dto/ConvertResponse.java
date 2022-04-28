package com.yrnet.transfer.business.transfer.dto;

import lombok.Builder;
import lombok.Data;

/**
 * @author dengbp
 * @ClassName TransferReq
 * @Description TODO
 * @date 4/17/22 12:20 AM
 */
@Builder
@Data
public class ConvertResponse {

    /** 文件id,用于方便更新 */
    private Long fileId;

    /** 转换后新文件名带后缀 */
    private String fileName;
    /** 转换后新文件路径含文件名 */
    private String filePath;

    /**
     * 转换后新文件大小，单位kb
     */
    private Long fileSize;
    /** 转换状态 转换状态 0成功 1处理中 2失败 */
    private Integer state;
}
