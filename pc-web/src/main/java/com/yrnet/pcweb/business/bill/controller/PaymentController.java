package com.yrnet.pcweb.business.bill.controller;

import com.yrnet.pcweb.business.bill.dto.PayMentReqDto;
import com.yrnet.pcweb.business.bill.dto.PaymentLogReqDto;
import com.yrnet.pcweb.business.bill.service.IPaymentLogService;
import com.yrnet.pcweb.common.annotation.ControllerEndpoint;
import com.yrnet.pcweb.common.annotation.Log;
import com.yrnet.pcweb.common.entity.ViewWebResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Map;

/**
 * Description 文档支付服务
 * @Author dengbp
 * @Date 4:41 PM 5/6/22
 **/
@RestController
@RequestMapping("/payment")
@Slf4j
@Validated
public class PaymentController {
    @Resource
    private IPaymentLogService iPaymentLogService;

    @PostMapping("/pay")
    @ResponseBody
    @ControllerEndpoint(operation = "支付接口", exceptionMessage = "支付失败")
    @Log("支付接口")
    public ViewWebResponse pay(@RequestBody PayMentReqDto reqDto) {
        try {
            Map<String, Object> restMap = iPaymentLogService.wxPayment(reqDto);
            if (restMap != null) {
                return new ViewWebResponse().success().data(restMap);
            } else {
                return new ViewWebResponse().fail();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ViewWebResponse().fail();
    }

    /**
     * 支付异步通知
     */
    @PostMapping(value = "/wxNotify")
    @Log("支付回调接口")
    public String wxNotify(HttpServletRequest request, HttpServletResponse response) throws Exception {
        log.info("支付回调=>begin");
        String result = iPaymentLogService.wxNotify(request, response);
        return result;
    }


    @PostMapping("/log")
    @ResponseBody
    @ControllerEndpoint(operation = "充值记录查询", exceptionMessage = "充值记录查询失败")
    @Log("充值记录查询接口")
    public ViewWebResponse log(@RequestBody @Valid PaymentLogReqDto dto){
        log.info("用户id[{}]查询充值记录",dto.getUserId());
        return new ViewWebResponse().success().data(iPaymentLogService.queryPayLog(dto));
    }
}
