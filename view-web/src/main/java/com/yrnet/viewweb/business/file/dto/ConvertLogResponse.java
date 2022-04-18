package com.yrnet.viewweb.business.file.dto;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * @author dengbp
 * @ClassName PaymentLogReqDto
 * @Description TODO
 * @date 2/5/21 4:20 PM
 */
@Builder
@Data
public class ConvertLogResponse implements Serializable {

    /** 文件id*/
    private Long fileId;
    /** 新文件名带后缀*/
    private String fileName;

    /**
     *  转换时间
     */
    private Long createTime;

    /**
     * 0:成功，1：失败
     */
    private Integer state;

    /** url */
    private String url;


    /** 转换用户名 */
    private String useName;

    /** 是否允许下载 0允许 1禁止(无可用下载次数) 2可下载需减1 */
    private Integer allow;

    /** 新文件大小，单位kB */
    private Long fileSize;
}
