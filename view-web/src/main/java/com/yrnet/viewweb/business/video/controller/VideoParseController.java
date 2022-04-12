package com.yrnet.viewweb.business.video.controller;


import com.yrnet.viewweb.business.video.dto.ParseLogReqDto;
import com.yrnet.viewweb.business.video.dto.VideoDownloadReqDto;
import com.yrnet.viewweb.business.video.dto.VideoParseReqDto;
import com.yrnet.viewweb.business.video.service.IParseLogService;
import com.yrnet.viewweb.common.annotation.ControllerEndpoint;
import com.yrnet.viewweb.common.annotation.Log;
import com.yrnet.viewweb.common.entity.QueryRequestPage;
import com.yrnet.viewweb.common.entity.ViewWebResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * @author dengbp
 */
@RestController
@RequestMapping("/video")
@Slf4j
public class VideoParseController {

    @Resource
    private IParseLogService parseLogService;

    @PostMapping("/parse")
    @ResponseBody
    @ControllerEndpoint(operation = "去印接口", exceptionMessage = "去印失败")
    @Log("去印接口")
    public ViewWebResponse parse(@RequestBody @Valid VideoParseReqDto reqDto){
        return new ViewWebResponse().success().data(parseLogService.parseVideo(reqDto));
    }

    @GetMapping("/handle/ranking")
    @ResponseBody
    @ControllerEndpoint(operation = "处理排行接口", exceptionMessage = "处理排行查询失败")
    @Log("处理排行接口")
    public ViewWebResponse ranking(){
        return new ViewWebResponse().success().data(parseLogService.ranking());
    }

    @PostMapping("/download")
    @ResponseBody
    @ControllerEndpoint(operation = "下载记录接口", exceptionMessage = "下载记录失败")
    @Log("下载记录接口")
    public ViewWebResponse download(@RequestBody @Valid VideoDownloadReqDto reqDto){
        parseLogService.updateDownloadRecord(reqDto);
        return new ViewWebResponse().success();
    }

    @PostMapping("/log")
    @ResponseBody
    @ControllerEndpoint(operation = "去印记录查询", exceptionMessage = "去印记录查询失败")
    @Log("去印记录查询接口")
    public ViewWebResponse log(@RequestBody @Valid ParseLogReqDto dto){
        return new ViewWebResponse().success().data(parseLogService.queryLog(dto));
    }

    @PostMapping("/chosen/list")
    @ResponseBody
    @ControllerEndpoint(operation = "精选查询", exceptionMessage = "精选查询查询失败")
    @Log("精选查询接口")
    public ViewWebResponse chosen(@RequestBody @Valid QueryRequestPage requestPage){
        return new ViewWebResponse().success().data(parseLogService.onlineList(requestPage));
    }



    @GetMapping("/test/ranking")
    public ViewWebResponse refresh(){
        parseLogService.refreshImg();
        return new ViewWebResponse().success();
    }

}
