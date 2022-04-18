package com.yrnet.viewweb.business.file.controller;

import com.yrnet.viewweb.business.file.bo.FileConvertBo;
import com.yrnet.viewweb.business.file.dto.ConvertLogRequest;
import com.yrnet.viewweb.business.file.service.IConvertHandleService;
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
    private IConvertHandleService convertHandleService;

    /**
     * Description 转换文档上传
     * @param file 文件
     * @param toType 转换成目标文档类型：1转成图片；2转成word;3转成pdf;...
     * @param openId 用户id
     * @return com.yr.net.app.base.dto.RestResult
     * @Author dengbp
     * @Date 23:59 2020-11-24
     **/
    @RequestMapping("/doc/upload")
    @ResponseBody
    @ControllerEndpoint(operation = "用户多媒体上传", exceptionMessage = "用户多媒体上传失败")
    @Log("用户转换文档上传")
    public ViewWebResponse upload(@RequestParam("file") MultipartFile file, @RequestParam(value="toType") int toType, @RequestParam(value="openId") String openId) {
        if (file == null) {
            return new ViewWebResponse().fail().message("上传失败，文件为空");
        }
        return new ViewWebResponse().success().data(convertHandleService.convert(FileConvertBo.builder().build()));
    }


    @PostMapping("/doc/log")
    @ResponseBody
    @ControllerEndpoint(operation = "转换记录查询", exceptionMessage = "转换记录查询失败")
    @Log("转换记录查询接口")
    public ViewWebResponse log(@RequestBody @Valid ConvertLogRequest request){
        return new ViewWebResponse().success().data(convertHandleService.queryLog(request));
    }
}
