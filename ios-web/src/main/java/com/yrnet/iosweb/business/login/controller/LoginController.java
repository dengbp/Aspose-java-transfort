package com.yrnet.iosweb.business.login.controller;

import com.yrnet.iosweb.business.login.entity.IosUser;
import com.yrnet.iosweb.business.login.entity.LoginDto;
import com.yrnet.iosweb.business.login.entity.LoginVo;
import com.yrnet.iosweb.business.login.service.IosUserService;
import com.yrnet.iosweb.common.entity.ViewWebResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

/**
 * @author dengbp
 * @version 1.0
 * @date 2022/11/29 2:21 下午
 */
@RestController
@RequestMapping("/user")
@Slf4j
@Validated
public class LoginController {


    @Resource
    private final IosUserService iosUserService;

    public LoginController(IosUserService iosUserService) {
        this.iosUserService = iosUserService;
    }


    @PostMapping("/login")
    public ViewWebResponse login(@RequestBody @Valid LoginDto loginDto) {
        IosUser result = new IosUser();
        boolean state = iosUserService.verify(loginDto.getEncryptedData(),result);
        if (state){
            return new ViewWebResponse().success().data(result).message("success");
        }
        return new ViewWebResponse().fail().message("login verify fail");
    }


}
