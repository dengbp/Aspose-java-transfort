package com.yrnet.viewweb.business.bill.controller;


import com.yrnet.viewweb.business.bill.entity.UserAccount;
import com.yrnet.viewweb.business.bill.service.IUserAccountService;
import com.yrnet.viewweb.common.annotation.ControllerEndpoint;
import com.yrnet.viewweb.common.annotation.Log;
import com.yrnet.viewweb.common.entity.ViewWebResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.constraints.NotBlank;

/**
 * @author dengbp
 */
@RestController
@RequestMapping("/bill/user-account")
public class UserAccountController {

    @Resource
    private IUserAccountService userAccountService;


    @GetMapping("info")
    @ResponseBody
    @ControllerEndpoint(operation = "支付接口", exceptionMessage = "支付失败")
    @Log("帐号信息查询")
    public ViewWebResponse info(@NotBlank(message = "{required}")  String userId) {
        UserAccount userAccount = userAccountService.getByUserId(userId);
        return new ViewWebResponse().success().data(userAccount==null?new UserAccount():userAccount);
    }

}
