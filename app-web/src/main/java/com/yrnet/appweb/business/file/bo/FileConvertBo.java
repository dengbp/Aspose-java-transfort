package com.yrnet.appweb.business.file.bo;

import com.yrnet.appweb.business.file.entity.ConvertLog;
import com.yrnet.appweb.common.utils.DateUtil;
import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author dengbp
 * @ClassName FileConvertBo
 * @Description TODO
 * @date 4/18/22 2:01 PM
 */
@Builder
@Data
public class FileConvertBo {

    private MultipartFile file;
    private MultipartFile[] files;
    private Long batchId;
    private int toType;
    private String openId;

    public ConvertLog builderEntity(){
        ConvertLog log = new ConvertLog();
        log.setBatchId(batchId);
        log.setFileName(file.getOriginalFilename());
        log.setFileSize(file.getSize());
        log.setUserOpenId(openId);
        log.setFileSuffix(file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".")));
        log.setToType(toType);
        log.setCreateTime(Long.parseLong(DateUtil.current_yyyyMMddHHmmss()));
        return log;
    }
}
