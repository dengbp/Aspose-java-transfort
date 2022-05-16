package com.yrnet.appweb.business.custom.controller;


import com.yrnet.appweb.business.bill.dto.VipInfoResDto;
import com.yrnet.appweb.business.custom.dto.UserEmailDto;
import com.yrnet.appweb.business.custom.service.IVipInfoService;
import com.yrnet.appweb.common.annotation.ControllerEndpoint;
import com.yrnet.appweb.common.annotation.Log;
import com.yrnet.appweb.common.entity.ViewWebResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * @author dengbp
 */
@RestController
@RequestMapping("/custom/vip-info")
@Slf4j
@Validated
public class VipInfoController {


    @Resource
    private IVipInfoService vipInfoService;

    @PostMapping("/setEmail")
    @ResponseBody
    @ControllerEndpoint(operation = "会员用户邮件地址设置", exceptionMessage = "会员用户邮件地址设置失败")
    @Log("会员用户邮件地址设置接口")
    public ViewWebResponse setEmail(@RequestBody @Valid UserEmailDto dto){
        if (vipInfoService.setEmail(dto.getUserOpenId(),dto.getEmail())){
            return new ViewWebResponse().success().message("设置成功");
        }else {
            return new ViewWebResponse().fail().message("设置失败，请先升级会员");
        }
    }

    @PostMapping("/queryEmail")
    @ResponseBody
    @ControllerEndpoint(operation = "会员用户邮件地址查询", exceptionMessage = "会员用户邮件地址查询失败")
    @Log("会员用户邮件地址查询接口")
    public ViewWebResponse queryEmail(@RequestBody  UserEmailDto dto){
        String email = "";
        log.info("queryEmail userOpenId param:{}",dto.getUserOpenId());
        if (StringUtils.isBlank(dto.getUserOpenId())){
            return new ViewWebResponse().success().data(email);
        }
        VipInfoResDto res =  vipInfoService.getVipInf(dto.getUserOpenId());
        if (res != null){
            log.info("用户email:{}",res.getEmail());
            return new ViewWebResponse().success().data(res.getEmail());
        }
        return new ViewWebResponse().success().data(email);

    }

}
