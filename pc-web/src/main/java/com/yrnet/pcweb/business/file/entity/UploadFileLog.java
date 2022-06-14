package com.yrnet.pcweb.business.file.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

import com.yrnet.pcweb.common.utils.DateUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 *
 * @author dengbp
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("upload_file_log")
public class UploadFileLog implements Serializable {

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
     * 上传时间,格式:yyyymmddHHMMSS
     */
    private Long createTime;


    public static UploadFileLog builderEntity(MultipartFile file,String openId){
        UploadFileLog log = new UploadFileLog();
        log.setFileName(file.getOriginalFilename());
        log.setFileSize(file.getSize());
        log.setUserOpenId(openId);
        log.setFileSuffix(file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".")));
        log.setCreateTime(Long.parseLong(DateUtil.current_yyyyMMddHHmmss()));
        return log;
    }

}
