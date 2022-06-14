package com.yrnet.iosweb.business.custom.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yrnet.iosweb.business.custom.dto.CustomDemandReqDto;
import com.yrnet.iosweb.business.custom.entity.Demand;
import com.yrnet.iosweb.common.exception.DocumentException;

/**
 * @author dengbp
 */
public interface IDemandService extends IService<Demand> {

    void addDemand(CustomDemandReqDto reqDto)throws DocumentException;

}
