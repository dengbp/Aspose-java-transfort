package com.yrnet.pcweb.business.file.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.yrnet.pcweb.business.file.entity.UploadFileLog;
import com.yrnet.pcweb.business.file.mapper.UploadFileLogMapper;
import com.yrnet.pcweb.business.file.service.IUploadFileLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yrnet.pcweb.common.exception.DocumentException;
import com.yrnet.pcweb.common.properties.ViewWebProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * @author dengbp
 */
@Service
@Slf4j
public class UploadFileLogServiceImpl extends ServiceImpl<UploadFileLogMapper, UploadFileLog> implements IUploadFileLogService {

    @Resource UploadFileLogMapper uploadFileLogMapper;

    @Autowired
    private ViewWebProperties viewWebProperties;

    @Override
    public Long addLog(MultipartFile file,String fileName, String openId) throws DocumentException {
        UploadFileLog fileLog = UploadFileLog.builderEntity(file,openId);
        try {
            fileLog.setFileName(fileName);
            fileLog.setFilePath(storage(file,fileName));
        }catch (Exception e) {
            log.error(e.getMessage(),e);
            throw new DocumentException(e.getMessage());
        }
        uploadFileLogMapper.insert(fileLog);
        return new Long(fileLog.getId());
    }

    @Override
    public List<UploadFileLog> queryByIds(List<Long> ids) throws DocumentException {
        log.info("ids size:{}",ids.size());
        return list(new LambdaQueryWrapper<UploadFileLog>().in(UploadFileLog::getId,ids));
    }

    private String storage(MultipartFile file,String fileName) throws IOException {
        String path = viewWebProperties.getFile_path()+ "/" + fileName;
        File dest = new File(path);
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdirs();
        }
        file.transferTo(dest);
        return path;
    }
}
