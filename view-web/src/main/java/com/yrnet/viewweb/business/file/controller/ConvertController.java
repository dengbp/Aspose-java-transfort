package com.yrnet.viewweb.business.file.controller;

import com.yrent.common.constant.ConvertType;
import com.yrent.common.constant.FileSuffixConstant;
import com.yrent.common.utils.SnowFlakeUtil;
import com.yrnet.viewweb.business.file.bo.FileConvertBo;
import com.yrnet.viewweb.business.file.dto.ConvertLogRequest;
import com.yrnet.viewweb.business.file.service.IConvertLogService;
import com.yrnet.viewweb.common.annotation.ControllerEndpoint;
import com.yrnet.viewweb.common.annotation.Log;
import com.yrnet.viewweb.common.entity.ViewWebResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.Map;

/**
 * @author dengbp
 * @ClassName PrivateSpaceController
 * @Description 私人空间控制器
 * @date 4/12/21 9:48 PM
 */
@RestController
@RequestMapping("/manager")
@Slf4j
@Validated
public class ConvertController {

    @Resource
    private IConvertLogService convertLogService;

    /**
     * Description 转换文档上传
     * @param file 文件(多文件主要用于图片转换)
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
    public ViewWebResponse upload(@RequestParam("file") MultipartFile[] file, @RequestParam(value="toType") int toType, @RequestParam(value="openId") String openId,@RequestParam(value="num") Integer num) {
        ViewWebResponse response = new ViewWebResponse().success();
        if (file == null) {
            return response.fail().message("上传失败，文件为空");
        }
        if (StringUtils.isBlank(openId)){
            return response.fail().message("openId为空");
        }
        if (num ==  null){
            return response.fail().message("num为空");
        }
        for (MultipartFile f : file){
            validator(f,toType,response);
            if (!response.isNormal()){
                return response;
            }
        }
        return response.message("请求处理成功").data(convertLogService.handle(
                FileConvertBo.builder()
                        .batchId(SnowFlakeUtil.creater.nextId())
                        .files(file)
                        .openId(openId)
                        .toType(toType)
                        .build())
        );
    }

    private void validator(MultipartFile file,int toType,ViewWebResponse response){
        response.fail().message("文件类型与转换目标不对应");
        String fileName = file.getOriginalFilename();
        String suffix = fileName.substring(fileName.lastIndexOf("."));
        ConvertType type = ConvertType.getByCode((char)toType);
        switch (type){
            case word_to_pdf:
                if (StringUtils.equalsIgnoreCase(suffix, FileSuffixConstant.DOC)){
                    response.success();
                }
                if (StringUtils.equalsIgnoreCase(suffix,FileSuffixConstant.DOCX)){
                    response.success();
                }
                break;
            case pdf_to_word:
                if (StringUtils.equalsIgnoreCase(suffix,FileSuffixConstant.PDF)){
                    response.success();
                }
                break;
            case pdf_to_docx:
                if (StringUtils.equalsIgnoreCase(suffix,FileSuffixConstant.PDF)){
                    response.success();
                }
                break;
            case excel_to_pdf:
                if (StringUtils.equalsIgnoreCase(suffix,FileSuffixConstant.EXCEL)){
                    response.success();
                }
                break;
            case pdf_to_excel:
                if (StringUtils.equalsIgnoreCase(suffix,FileSuffixConstant.PDF)){
                    response.success();
                }
                break;
            case ppt_to_pdf:
                if (StringUtils.equalsIgnoreCase(suffix,FileSuffixConstant.PDF)){
                    response.success();
                }
                break;
            case pdf_to_ppt:
                if (StringUtils.equalsIgnoreCase(suffix,FileSuffixConstant.PDF)){
                    response.success();
                }
                break;
            case jpg_to_pdf:
                if (StringUtils.equalsIgnoreCase(suffix,FileSuffixConstant.JPG)){
                    response.success();
                }
                break;
            case pdf_to_jpg:
                if (StringUtils.equalsIgnoreCase(suffix,FileSuffixConstant.PDF)){
                    response.success();
                }
                break;
            case png_to_pdf:
                if (StringUtils.equalsIgnoreCase(suffix,FileSuffixConstant.PNG)){
                    response.success();
                }
                break;
            case pdf_to_png:
                if (StringUtils.equalsIgnoreCase(suffix,FileSuffixConstant.PDF)){
                    response.success();
                }
                break;
            case docx_to_pdf:
                if (StringUtils.equalsIgnoreCase(suffix,FileSuffixConstant.DOCX)){
                    response.success();
                }
                break;
            case odt_to_pdf:
                if (StringUtils.equalsIgnoreCase(suffix,FileSuffixConstant.ODT)){
                    response.success();
                }
                break;
            case doc_to_pdf:
                if (StringUtils.equalsIgnoreCase(suffix,FileSuffixConstant.DOC)){
                    response.success();
                }
                break;
            default:
                response.message("格式不匹配！");
                log.warn("no type match!");
                break;
        }
    }

    @PostMapping("/doc/log")
    @ResponseBody
    @ControllerEndpoint(operation = "转换记录查询", exceptionMessage = "转换记录查询失败")
    @Log("转换记录查询接口")
    public ViewWebResponse log(@RequestBody @Valid ConvertLogRequest request){
        return new ViewWebResponse().success().data(convertLogService.queryLog(request));
    }


    @PostMapping("/doc/delete")
    @ResponseBody
    @ControllerEndpoint(operation = "转换记录删除接口", exceptionMessage = "转换记录删除失败")
    @Log("转换记录删除接口")
    public ViewWebResponse delete(@RequestBody Map map){
        Long id = (Long) map.get("docId");
        if (id == null){
            return new ViewWebResponse().fail().message("docId为空");
        }
        return new ViewWebResponse().success().data(convertLogService.removeById(id)).message("删除成功");
    }
}
