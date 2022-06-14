package com.yrnet.pcweb.business.bill.controller;

import com.yrnet.pcweb.business.bill.service.ITSwitchService;
import com.yrnet.pcweb.common.annotation.ControllerEndpoint;
import com.yrnet.pcweb.common.annotation.Log;
import com.yrnet.pcweb.common.entity.ViewWebResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author dengbp
 */
@RestController
@RequestMapping("/bill/tswitch")
@Slf4j
public class TSwitchController {

    @Resource
    private ITSwitchService iTSwitchService;

    @PostMapping("/list")
    @ResponseBody
    @ControllerEndpoint(operation = "动态数据接口", exceptionMessage = "动态数据查询失败")
    @Log("动态数据接口")
    public ViewWebResponse list(){
        return new ViewWebResponse().success().data(iTSwitchService.list());
    }

}
