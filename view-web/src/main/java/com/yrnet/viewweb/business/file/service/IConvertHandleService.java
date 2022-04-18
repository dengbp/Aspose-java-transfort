package com.yrnet.viewweb.business.file.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yrnet.viewweb.business.file.bo.FileConvertBo;
import com.yrnet.viewweb.business.file.dto.*;
import com.yrnet.viewweb.business.file.entity.ParseLog;
import com.yrnet.viewweb.common.exception.DocumentException;

import java.util.List;

/**
 * @author dengbp
 */
public interface IConvertHandleService extends IService<ParseLog> {


    /**
     * Description 转换记录查询
     * @param dto
     * @return java.util.List<com.yrnet.viewweb.business.video.dto.ParseLogRespDto>
     * @throws DocumentException
     * @Author dengbp
     * @Date 7:05 PM 2/5/21
     **/
    List<ConvertLogResponse> queryLog(ConvertLogRequest dto)throws DocumentException;

    /**
     * Description 文件格式转换
     * @param bo
     * @return ConvertLogResponse
     * @throws DocumentException
     * @Author dengbp
     * @Date 2:03 PM 4/18/22
     **/

    ConvertLogResponse convert(FileConvertBo bo)throws DocumentException;





}
