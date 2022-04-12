package com.yrnet.viewweb.business.bill.controller;

import com.yrnet.viewweb.business.bill.service.ICommonProblemService;
import com.yrnet.viewweb.common.annotation.ControllerEndpoint;
import com.yrnet.viewweb.common.annotation.Log;
import com.yrnet.viewweb.common.entity.ViewWebResponse;
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
@RequestMapping("/bill/common_problem")
@Slf4j
public class CommonProblemController {

    @Resource
    private ICommonProblemService iCommonProblemService;

    @PostMapping("/list")
    @ResponseBody
    @ControllerEndpoint(operation = "常见问题数据接口", exceptionMessage = "常见问题数据查询失败")
    @Log("常见问题数据接口")
    public ViewWebResponse list(){
        return new ViewWebResponse().success().data(iCommonProblemService.list());
    }

}
