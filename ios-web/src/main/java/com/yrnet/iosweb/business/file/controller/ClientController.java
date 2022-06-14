package com.yrnet.iosweb.business.file.controller;

import com.yrnet.iosweb.business.file.service.TransferApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author dengbp
 * @ClassName ClientController
 * @Description TODO
 * @date 4/9/22 10:51 PM
 */
@RestController
@RequestMapping("/custom/sample")
public class ClientController {

    @Autowired
    private TransferApi transferApi;

    @GetMapping("test")
    @ResponseBody
    public String test() {
        return transferApi.hello();
    }
}

