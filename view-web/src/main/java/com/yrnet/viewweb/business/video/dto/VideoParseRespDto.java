package com.yrnet.viewweb.business.video.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author dengbp
 * @ClassName VideoParseRespDto
 * @Description TODO
 * @date 2/4/21 1:02 AM
 */
@Data
public class VideoParseRespDto implements Serializable {

    private String title;
    private String url;
    private String img;
    private Long videoId;
    /** 是否允许下载 0允许 1禁止*/
    private Integer allow;
    private String downloadUrl;
    /** 处理结果编码 */
    private String code;
}
