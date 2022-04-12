package com.yrnet.viewweb.business.video.controller;


import com.yrnet.viewweb.business.video.dto.ShareReqDto;
import com.yrnet.viewweb.business.video.service.IShareLogService;
import com.yrnet.viewweb.common.annotation.ControllerEndpoint;
import com.yrnet.viewweb.common.annotation.Log;
import com.yrnet.viewweb.common.entity.ViewWebResponse;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * @author dengbp
 */
@RestController
@RequestMapping("/video/share-log")
public class ShareLogController {

    @Resource
    private IShareLogService shareLogService;

    @PostMapping("/record")
    @ResponseBody
    @ControllerEndpoint(operation = "分享记录接口", exceptionMessage = "分享记录失败")
    @Log("分享记录接口")
    public ViewWebResponse record(@RequestBody @Valid ShareReqDto reqDto){
        shareLogService.add(reqDto);
        return new ViewWebResponse().success();
    }

}
