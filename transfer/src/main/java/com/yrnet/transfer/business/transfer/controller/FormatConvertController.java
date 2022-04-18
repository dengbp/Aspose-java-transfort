package com.yrnet.transfer.business.transfer.controller;

import com.yrnet.transfer.annotion.Log;
import com.yrnet.transfer.business.transfer.dto.TransferRequest;
import com.yrnet.transfer.business.transfer.service.FormatConvertService;
import com.yrnet.transfer.common.http.TransferResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * @author dengbp
 * @ClassName FormatConverController
 * @Description TODO
 * @date 4/17/22 12:02 AM
 */

@RestController
@RequestMapping("/format-convert")
@Slf4j
@Validated
public class FormatConvertController {

    @Resource
    private FormatConvertService formatConvertService;


    @Log("文档格式转换")
    @PostMapping("exe")
    @ResponseBody
    public TransferResponse enforce(@RequestBody @Valid TransferRequest transferReq){
        return new TransferResponse().success().data(formatConvertService.convert(transferReq)).message("success");
    }
}
