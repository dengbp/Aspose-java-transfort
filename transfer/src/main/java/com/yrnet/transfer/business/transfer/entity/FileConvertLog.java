package com.yrnet.transfer.business.transfer.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 *
 *
 * @author dengbp
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class FileConvertLog implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ID", type = IdType.AUTO)
    private Long id;

    /**
     * 用户id
     */
    private String userOpenId;

    /**
     * 源文件名称，带后缀
     */
    private String fileName;

    /**
     * 源文件后续
     */
    private String fileSuffix;

    /**
     * 源文件路径
     */
    private String filePath;

    /**
     * 文件大小，单位kB
     */
    private Long fileSize;

    /**
     * 新文件路径
     */
    private String newFilePath;

    /**
     * 处理时间,格式:yyyymmddHHMMSS
     */
    private Long createTime;

    /**
     * 转换状态 -1：转换中 0:成功，1：失败
     */
    private Integer state;

    /**
     * 是否允许下载 0允许 1禁止
     */
    private Integer allow;

    /**
     * word转pdf:1 pdf转word:2 excel转pdf:3 pdf转excel:4 ppt转pdf:5 pdf转ppt:6 jpg转pdf:7 pdf转jpg:8 png转pdf:9 pdf转png:10 docx转pdf:11 pdf转docx:12 odt转pdf:13 doc转pdf:14
     */
    private Integer toType;

    /**
     * 新文件名
     */
    private String newFileName;

    /**
     * 新文件大小
     */
    private Long newFileSize;


}
