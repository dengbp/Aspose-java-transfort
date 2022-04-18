package com.yrnet.viewweb.business.file.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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
@TableName("video_parse_log")
public class ParseLog implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 用户id
     */
    private String userId;

    /**
     * 用户头像
     */
    private String userAvatarUrl;

    /**
     * 去印url
     */
    private String parseUrl;

    /**
     * 处理结果
     */
    private String parseResult;

    /**
     * 新的,可用的url
     */
    private String activeUrl;

    /** 视频封面url */
    private String img;

    /**
     * 下载次数
     */
    private Integer downloadCount;

    /**
     * 处理时间,格式:yyyymmddHHMMSS
     */
    private Long createTime;

    /** 是否允许下载 0允许 1禁止 */
    private Integer allow;


}
