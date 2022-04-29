package com.yrnet.appweb.business.file.service;

import com.yrnet.appweb.business.file.entity.UploadFileLog;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yrnet.appweb.common.exception.DocumentException;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author dengbp
 */
public interface IUploadFileLogService extends IService<UploadFileLog> {

    Long addLog(MultipartFile file,String fileName, String openId)throws DocumentException;

    List<UploadFileLog> queryByIds(List<Long> ids)throws DocumentException;

}
