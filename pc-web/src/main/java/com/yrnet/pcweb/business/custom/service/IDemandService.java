package com.yrnet.pcweb.business.custom.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yrnet.pcweb.business.custom.dto.CustomDemandReqDto;
import com.yrnet.pcweb.business.custom.entity.Demand;
import com.yrnet.pcweb.common.exception.DocumentException;

/**
 * @author dengbp
 */
public interface IDemandService extends IService<Demand> {

    void addDemand(CustomDemandReqDto reqDto)throws DocumentException;

}
