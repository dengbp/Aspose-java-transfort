package com.yrnet.viewweb.business.custom.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yrnet.viewweb.business.custom.dto.CustomDemandReqDto;
import com.yrnet.viewweb.business.custom.entity.Demand;
import com.yrnet.viewweb.business.custom.mapper.DemandMapper;
import com.yrnet.viewweb.business.custom.service.IDemandService;
import com.yrnet.viewweb.common.exception.YinXXException;
import org.springframework.stereotype.Service;

/**
 * @author dengbp
 */
@Service
public class DemandServiceImpl extends ServiceImpl<DemandMapper, Demand> implements IDemandService {

    @Override
    public void addDemand(CustomDemandReqDto reqDto) throws YinXXException {
        Demand demand = new Demand();
        demand.setDemand(reqDto.getDemand());
        demand.setPhone(reqDto.getPhone());
        demand.setUserId(reqDto.getUserId());
        save(demand);
    }
}
