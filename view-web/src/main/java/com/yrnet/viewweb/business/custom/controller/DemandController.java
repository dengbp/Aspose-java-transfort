package com.yrnet.viewweb.business.custom.controller;


import com.yrnet.viewweb.business.custom.dto.CustomDemandReqDto;
import com.yrnet.viewweb.business.custom.service.IDemandService;
import com.yrnet.viewweb.common.annotation.ControllerEndpoint;
import com.yrnet.viewweb.common.annotation.Log;
import com.yrnet.viewweb.common.entity.ViewWebResponse;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * @author dengbp
 */
@RestController
@RequestMapping("/custom/demand")
public class DemandController {

    @Resource
    private IDemandService demandService;

    @PostMapping("/add")
    @ResponseBody
    @ControllerEndpoint(operation = "去印个性需求接口", exceptionMessage = "去印个性需求增加失败")
    @Log("去印个性需求接口")
    public ViewWebResponse add(@RequestBody @Valid CustomDemandReqDto reqDto){
        demandService.addDemand(reqDto);
        return new ViewWebResponse().success().message("需求提交成功,稍后客服联系您");
    }

}
