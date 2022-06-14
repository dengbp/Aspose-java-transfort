package com.yrnet.pcweb.business.file.bo;

import com.yrnet.pcweb.business.file.entity.ConvertLog;
import com.yrnet.pcweb.common.utils.DateUtil;
import lombok.Builder;
import lombok.Data;

import java.io.File;
import java.util.List;

/**
 * @author dengbp
 * @ClassName FileConvertBo
 * @Description TODO
 * @date 4/18/22 2:01 PM
 */
@Builder
@Data
public class FileConvertBo {

    private File file;
    private List<File> files;
    private int toType;
    private String openId;

    public ConvertLog builderEntity(){
        ConvertLog log = new ConvertLog();
        log.setFileName(file.getName());
        log.setFileSize(file.length());
        log.setUserOpenId(openId);
        String path = file.getAbsolutePath();
        log.setFileSuffix(path.substring(path.lastIndexOf(".")));
        log.setToType(toType);
        log.setCreateTime(Long.parseLong(DateUtil.current_yyyyMMddHHmmss()));
        return log;
    }
}
