package com.yrnet.iosweb.business.file.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
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
public class ConvertFreeTrialRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ID", type = IdType.AUTO)
    private Long id;

    /**
     * 用户id
     */
    private String userOpenId;

    /**
     * 源文件路径
     */
    private String filePath;

    /**
     * 新文件路径
     */
    private String newFilePath;

    /**
     * 处理时间,格式:yyyymmddHHMMSS
     */
    private Long createTime;

    /**
     * word转pdf:1 pdf转word:2 excel转pdf:3 pdf转excel:4 ppt转pdf:5 pdf转ppt:6 jpg转pdf:7 pdf转jpg:8 png转pdf:9 pdf转png:10 docx转pdf:11 pdf转docx:12 odt转pdf:13 doc转pdf:14
     */
    private Integer toType;


    public static ConvertFreeTrialRecord builder(){
        return new ConvertFreeTrialRecord();
    }


}
