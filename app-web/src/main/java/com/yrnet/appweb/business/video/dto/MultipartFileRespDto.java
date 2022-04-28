package com.yrnet.appweb.business.video.dto;

import lombok.Data;

/**
 * @author dengbp
 * @ClassName MultipartFileRespDto
 * @Description TODO
 * @date 1/16/21 8:08 PM
 */
@Data
public class MultipartFileRespDto {
    /** 转换后的文件id  */
    private Long docId;
    /** 转换后的文件下载url  */
    private String docUrl;
}
