package com.yrnet.viewweb.business.custom.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yrnet.viewweb.business.custom.dto.CustomDemandReqDto;
import com.yrnet.viewweb.business.custom.entity.Demand;
import com.yrnet.viewweb.common.exception.YinXXException;

/**
 * @author dengbp
 */
public interface IDemandService extends IService<Demand> {

    void addDemand(CustomDemandReqDto reqDto)throws YinXXException;

}
