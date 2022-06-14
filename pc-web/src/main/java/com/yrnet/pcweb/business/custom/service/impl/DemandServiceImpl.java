package com.yrnet.pcweb.business.custom.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yrnet.pcweb.business.custom.dto.CustomDemandReqDto;
import com.yrnet.pcweb.business.custom.entity.Demand;
import com.yrnet.pcweb.business.custom.mapper.DemandMapper;
import com.yrnet.pcweb.business.custom.service.IDemandService;
import com.yrnet.pcweb.common.exception.DocumentException;
import org.springframework.stereotype.Service;

/**
 * @author dengbp
 */
@Service
public class DemandServiceImpl extends ServiceImpl<DemandMapper, Demand> implements IDemandService {

    @Override
    public void addDemand(CustomDemandReqDto reqDto) throws DocumentException {
        Demand demand = new Demand();
        demand.setDemand(reqDto.getDemand());
        demand.setPhone(reqDto.getPhone());
        demand.setUserId(reqDto.getUserId());
        save(demand);
    }
}
