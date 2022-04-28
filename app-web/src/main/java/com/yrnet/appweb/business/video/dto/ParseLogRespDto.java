package com.yrnet.appweb.business.video.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author dengbp
 * @ClassName PaymentLogReqDto
 * @Description TODO
 * @date 2/5/21 4:20 PM
 */
@Data
public class ParseLogRespDto implements Serializable {

    /** 视频id*/
    private Long videoId;
    /** 视频标题*/
    private String title;

    /**
     * 去印时间
     */
    private Long createTime;

    /**
     * 0:成功，1：失败
     */
    private Integer state;

    /** 视频url */
    private String url;


    /** 视频封面url */
    private String img;

    /** 是否允许下载 0允许 1禁止(无可用下载次数) 2可下载需减1 */
    private Integer allow;
}
