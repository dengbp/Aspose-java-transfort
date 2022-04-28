package com.yrnet.appweb.business.custom.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yrnet.appweb.business.custom.dto.CustomDemandReqDto;
import com.yrnet.appweb.business.custom.entity.Demand;
import com.yrnet.appweb.common.exception.DocumentException;

/**
 * @author dengbp
 */
public interface IDemandService extends IService<Demand> {

    void addDemand(CustomDemandReqDto reqDto)throws DocumentException;

}
