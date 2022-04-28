package com.yrnet.appweb.business.video.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author dengbp
 * @ClassName LicenseQeuryDto
 * @Description 发布动态
 * @date 2019-11-28 10:27
 */
@Data
public class MomentDto implements Serializable {


    /**
     * 多媒体访问url（文件上传成功后返回的id）
     */
    private String url;

    /**
     * 配文、我的秀言
     */
    private String showWord;

    /**
     * 对应缩略图、视频第一帧
     **/
    private String previewUrl;

}
