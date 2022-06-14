package com.yrnet.pcweb.business.file.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 分享可下载认证
 *
 * @author dengbp
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class ShareDownloadAuth implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户id
     */
    private String userId;

    /**
     * 当前下载次数
     */
    private Integer useTimes;

    /**
     * 分享时间,格式:yyyymmdd
     */
    private Long createTime;


}
