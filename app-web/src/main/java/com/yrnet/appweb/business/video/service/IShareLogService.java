package com.yrnet.appweb.business.video.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yrnet.appweb.business.video.dto.ShareReqDto;
import com.yrnet.appweb.business.video.entity.ShareLog;
import com.yrnet.appweb.common.exception.DocumentException;

/**
 * @author dengbp
 */
public interface IShareLogService extends IService<ShareLog> {


    void add(ShareReqDto dto)throws DocumentException;

}
