package com.yrnet.pcweb.business.bill.controller;


import com.yrnet.pcweb.business.bill.entity.UserAccount;
import com.yrnet.pcweb.business.bill.service.IUserAccountService;
import com.yrnet.pcweb.business.custom.service.IVipInfoService;
import com.yrnet.pcweb.common.annotation.ControllerEndpoint;
import com.yrnet.pcweb.common.annotation.Log;
import com.yrnet.pcweb.common.entity.ViewWebResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.NotBlank;
import java.util.Map;

/**
 * @author dengbp
 */
@RestController
@RequestMapping("/bill/user-account")
public class UserAccountController {

    @Resource
    private IUserAccountService userAccountService;

    @Resource
    private IVipInfoService vipInfoService;


    @GetMapping("info")
    @ResponseBody
    @ControllerEndpoint(operation = "去水印支付接口", exceptionMessage = "支付失败")
    @Log("去水印帐号信息查询")
    public ViewWebResponse info(@NotBlank(message = "{required}")  String userId) {
        UserAccount userAccount = userAccountService.getByUserId(userId);
        return new ViewWebResponse().success().data(userAccount==null?new UserAccount():userAccount);
    }

    /**
     * Description 文档转换
     * @param map
     * @return com.yrnet.viewweb.common.entity.ViewWebResponse
     * @Author dengbp
     * @Date 1:38 PM 4/27/22
     **/
    @PostMapping("state")
    @ResponseBody
    @ControllerEndpoint(operation = "帐号会员状态查询接口", exceptionMessage = "帐号会员状态查询失败")
    @Log("帐号会员状态查询")
    public ViewWebResponse state(@RequestBody Map map) {
        String openId = (String)map.get("openId");
        if (StringUtils.isBlank(openId)){
            return new ViewWebResponse().fail().message("openId is null");
        }
        return new ViewWebResponse().success().data(vipInfoService.getVipInf(openId));
    }

}
