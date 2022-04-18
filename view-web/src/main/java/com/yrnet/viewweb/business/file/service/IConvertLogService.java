package com.yrnet.viewweb.business.file.service;

import com.yrnet.viewweb.business.file.bo.FileConvertBo;
import com.yrnet.viewweb.business.file.dto.ConvertLogRequest;
import com.yrnet.viewweb.business.file.dto.ConvertLogResponse;
import com.yrnet.viewweb.business.file.entity.ConvertLog;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yrnet.viewweb.common.exception.DocumentException;

import java.util.List;

/**
 * @author dengbp
 */
public interface IConvertLogService extends IService<ConvertLog> {


    /**
     * Description 转换记录查询
     * @param dto
     * @return java.util.List<com.yrnet.viewweb.business.video.dto.ParseLogRespDto>
     * @throws DocumentException
     * @Author dengbp
     * @Date 7:05 PM 2/5/21
     **/
    List<ConvertLogResponse> queryLog(ConvertLogRequest dto)throws DocumentException;


    ConvertLogResponse handle(FileConvertBo bo)throws DocumentException;


}
