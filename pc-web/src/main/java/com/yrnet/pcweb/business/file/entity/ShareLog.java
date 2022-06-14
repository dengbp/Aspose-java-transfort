package com.yrnet.pcweb.business.file.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 *
 *
 * @author dengbp
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class ShareLog implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    /**
     * 用户id
     */
    private String userId;

    /**
     * 分享后，消费下载次数
     */
    private Integer useTimes;

    /**
     * 更新时间,格式:yyyymmddHHMMSS
     */
    private Long updateTime;

    /**
     * 分享受时间,格式:yyyymmddHHMMSS
     */
    private Long createTime;


}
