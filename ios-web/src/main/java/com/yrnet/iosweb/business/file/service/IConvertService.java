package com.yrnet.iosweb.business.file.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yrnet.iosweb.business.file.bo.FileConvertBo;
import com.yrnet.iosweb.business.file.dto.ConvertLogRequest;
import com.yrnet.iosweb.business.file.dto.ConvertLogResponse;
import com.yrnet.iosweb.business.file.entity.ConvertLog;
import com.yrnet.iosweb.common.exception.DocumentException;

import java.util.List;

/**
 * @author dengbp
 */
public interface IConvertService extends IService<ConvertLog> {


    /**
     * Description 转换记录查询
     * @param dto
     * @return java.util.List<com.yrnet.iosweb.business.video.dto.ParseLogRespDto>
     * @throws DocumentException
     * @Author dengbp
     * @Date 7:05 PM 2/5/21
     **/
    List<ConvertLogResponse> queryLog(ConvertLogRequest dto)throws DocumentException;

    /**
     * Description 文档转换
     * @param bo
     * @return com.yrnet.iosweb.business.file.dto.ConvertLogResponse
     * @throws DocumentException
     * @Author dengbp
     * @Date 2:56 PM 4/28/22
     **/
    ConvertLogResponse transfer(FileConvertBo bo)throws DocumentException;

    /**
     * Description 校验是否免费试用已用完
     * @param openId
     * @return boolean true:可用；false:不可用
     * @throws DocumentException
     * @Author dengbp
     * @Date 1:27 PM 4/29/22
     **/

    boolean canFreeTrial(String openId)throws DocumentException;




}
