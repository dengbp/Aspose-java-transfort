package com.yrnet.viewweb.business.video.controller;

import com.alibaba.fastjson.JSONObject;
import com.yrnet.viewweb.business.video.dto.MultipartInfoRequestDto;
import com.yrnet.viewweb.business.video.dto.ParseLogReqDto;
import com.yrnet.viewweb.business.video.service.IParseLogService;
import com.yrnet.viewweb.common.annotation.ControllerEndpoint;
import com.yrnet.viewweb.common.annotation.Log;
import com.yrnet.viewweb.common.entity.ViewWebResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * @author dengbp
 * @ClassName PrivateSpaceController
 * @Description 私人空间控制器
 * @date 4/12/21 9:48 PM
 */
@RestController
@RequestMapping("/manager")
@Slf4j
public class PrivateSpaceController {

    @Resource
    private IParseLogService parseLogService;


    @PostMapping("/log")
    @ResponseBody
    @ControllerEndpoint(operation = "去印记录查询", exceptionMessage = "去印记录查询失败")
    @Log("去印记录查询接口")
    public ViewWebResponse log(@RequestBody @Valid ParseLogReqDto dto){
        return new ViewWebResponse().success().data(parseLogService.queryLog(dto));
    }

    /**
     * Description 多媒体上传后发布
     * @return com.yr.net.app.base.dto.RestResult
     * @Author dengbp
     * @Date 23:59 2020-11-24
     **/

    @RequestMapping(value="/release", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    @ControllerEndpoint(operation = "用户多媒体信息", exceptionMessage = "用户多媒体信息失败")
    @Log("动态发布信息")
    public ViewWebResponse release(@RequestBody @Valid MultipartInfoRequestDto requestDto){
        if(requestDto.getMulId()==null){
            return new ViewWebResponse().fail().message("动态id[mulIds]不能为空");
        }
        parseLogService.release(requestDto);
        return new ViewWebResponse().success();
    }
    @RequestMapping(value="/video/delete", method = {RequestMethod.POST})
    @ResponseBody
    @ControllerEndpoint(operation = "用户视频删除", exceptionMessage = "用户视频删除失败")
    @Log("用户视频删除接口")
    public ViewWebResponse delete(@RequestBody JSONObject jsonParam){
        Long videoId = jsonParam.getLong("videoId");
        if(videoId==null){
            return new ViewWebResponse().fail().message("视频id[videoId]不能为空");
        }
        parseLogService.getBaseMapper().deleteById(videoId);
        return new ViewWebResponse().success();
    }
}
