package com.yrnet.appweb.business.video.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author dengbp
 * @ClassName CoordinateRequest
 * @Description TODO
 * @date 2020-11-11 17:50
 */
@Data
public class MultipartInfoRequestDto implements Serializable {

    /** 发布视频id */
    @NotNull
    private Integer mulId;

    /** 多媒体类型 0:视频；1：图片 */
    private String type;
    /**  0:公开；1：不公开 */
    @NotNull
    private String isPublic;
    /** 发布文本 */
    private String showWord;
}
