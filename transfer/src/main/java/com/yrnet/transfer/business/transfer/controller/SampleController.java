package com.yrnet.transfer.business.transfer.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author dengbp
 * @ClassName SampleController
 * @Description TODO
 * @date 4/9/22 10:44 PM
 */

@RestController
@RequestMapping("/sample")
public class SampleController {

    @GetMapping("hello")
    @ResponseBody
    public String hello() {
        return "Hello world!";
    }
}
