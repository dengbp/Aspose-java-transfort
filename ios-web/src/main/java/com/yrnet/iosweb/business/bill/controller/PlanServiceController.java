package com.yrnet.iosweb.business.bill.controller;


import com.yrnet.iosweb.business.bill.dto.PlansReqDto;
import com.yrnet.iosweb.business.bill.service.IPlanServiceService;
import com.yrnet.iosweb.common.annotation.ControllerEndpoint;
import com.yrnet.iosweb.common.annotation.Log;
import com.yrnet.iosweb.common.entity.ViewWebResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author dengbp
 */
@RestController
@RequestMapping("/bill/plan-service")
@Slf4j
public class PlanServiceController {

    @Resource
    private IPlanServiceService planServiceService;

    @PostMapping("/list")
    @ResponseBody
    @ControllerEndpoint(operation = "套餐接口", exceptionMessage = "套餐查询失败")
    @Log("套餐接口")
    public ViewWebResponse list(@RequestBody PlansReqDto reqDto){
        log.info("token value[{}]",reqDto.getToken());
        return new ViewWebResponse().success().data(planServiceService.list());
    }

}
