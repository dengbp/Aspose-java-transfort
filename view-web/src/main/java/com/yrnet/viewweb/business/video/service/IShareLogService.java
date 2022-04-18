package com.yrnet.viewweb.business.video.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yrnet.viewweb.business.video.dto.ShareReqDto;
import com.yrnet.viewweb.business.video.entity.ShareLog;
import com.yrnet.viewweb.common.exception.DocumentException;

/**
 * @author dengbp
 */
public interface IShareLogService extends IService<ShareLog> {


    void add(ShareReqDto dto)throws DocumentException;

}
