package com.yrnet.viewweb.business.file.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

import com.yrnet.viewweb.business.file.dto.ConvertLogResponse;
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
@TableName("file_convert_log")
public class ConvertLog implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ID", type = IdType.AUTO)
    private Long id;

    private Long batchId;
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
     * 源文件路径(含文件名),多个逗号分开
     */
    private String filePath;

    /**
     * 源文件大小，单位kB
     */
    private Long fileSize;


    /**
     * 新文件名称，带后缀
     */
    private String newFileName;

    /**
     * 新文件大小
     */
    private Long newFileSize;



    /**
     * 新文件路径(含文件名),多个逗号分开
     */
    private String newFilePath;

    /**
     * 处理时间,格式:yyyymmddHHMMSS
     */
    private Long createTime;

    /**
     * 转换状态 转换状态 0成功 1处理中 2失败
     */
    private Integer state;

    /**
     * word转pdf:1 pdf转word:2 excel转pdf:3 pdf转excel:4
     * ppt转pdf:5 pdf转ppt:6 jpg转pdf:7 pdf转jpg:8 png转pdf:9
     * pdf转png:10 docx转pdf:11 pdf转docx:12 odt转pdf:13 doc转pdf:14
     * */
    private Integer toType;

    /**
     * 是否允许下载 0允许 1禁止
     */
    private Integer allow;


    public ConvertLogResponse transformToRes(){
        return ConvertLogResponse.builder().state(state)
                .fileSize(fileSize)
                .fileName(fileName)
                .createTime(createTime)
                .fileId(id)
                .allow(allow)
                .build();
    }


}
