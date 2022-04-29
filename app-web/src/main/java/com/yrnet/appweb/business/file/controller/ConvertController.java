package com.yrnet.appweb.business.file.controller;

import com.alibaba.fastjson.JSONObject;
import com.yrent.common.constant.ConvertType;
import com.yrent.common.constant.FileSuffixConstant;
import com.yrnet.appweb.business.file.bo.FileConvertBo;
import com.yrnet.appweb.business.file.dto.ConvertLogRequest;
import com.yrnet.appweb.business.file.entity.UploadFileLog;
import com.yrnet.appweb.business.file.service.IConvertService;
import com.yrnet.appweb.business.file.service.IUploadFileLogService;
import com.yrnet.appweb.common.annotation.ControllerEndpoint;
import com.yrnet.appweb.common.annotation.Log;
import com.yrnet.appweb.common.entity.ViewWebResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
    private IConvertService convertService;
    @Autowired
    private IUploadFileLogService uploadFileLogService;



    /**
     * Description 转换文档
     * @param request 仅支持单个文件
     * @return ViewWebResponse
     * @Author dengbp
     * @Date 23:59 2020-11-24
     **/
    @RequestMapping("/doc/transfer")
    @ResponseBody
    @ControllerEndpoint(operation = "转换文档", exceptionMessage = "转换文档失败")
    @Log("转换文档")
    public ViewWebResponse transfer(@RequestBody @Valid  ConvertLogRequest request) {
        ViewWebResponse response = new ViewWebResponse().success();
        log.info("ids:{}",request.getFileId());
        List<UploadFileLog> fileLogs = uploadFileLogService.queryByIds(Stream.of(request.getFileId().split(",")).collect(Collectors.toList()).stream().map(Long::parseLong).collect(Collectors.toList()));
        List<File> files = new ArrayList<>();
        for (UploadFileLog f : fileLogs){
            File file = new File(f.getFilePath());
            validator(file,request.getToType(),response);
            if (!response.isNormal()){
                return response;
            }
            files.add(file);
        }
        if(convertService.isVipUser(request.getOpenId())){
            if(convertService.vipIsExpired(request.getOpenId())){
                return response.fail().message("会员已过期,请充值");
            }
        }else if (!convertService.canFreeTrial(request.getOpenId())){
            return response.fail().message("试用次数已用完,请升级会员");
        }
        return response.message("请求处理成功").data(convertService.transfer(
                FileConvertBo.builder()
                        .files(files)
                        .file(files.get(0))
                        .openId(request.getOpenId())
                        .toType(request.getToType())
                        .build())
        );
    }





    /**
     * Description 文档上传
     * @param file 仅支持单个文件
     * @param openId 用户id
     * @param fileName 带后缀的文件名
     * @return ViewWebResponse
     * @Author dengbp
     * @Date 23:59 2020-11-24
     **/
    @RequestMapping("/doc/upload")
    @ResponseBody
    @ControllerEndpoint(operation = "用户文档上传", exceptionMessage = "文档上传失败")
    @Log("用户文档上传")
    public ViewWebResponse upload(@RequestParam("file") MultipartFile file, @RequestParam(value="openId") String openId, @RequestParam(value="fileName") String fileName) {
        ViewWebResponse response = new ViewWebResponse().success();
        if (file == null) {
            log.warn("上传失败，文件为空");
            return response.fail().message("上传失败，文件为空");
        }
        if (StringUtils.isBlank(openId)){
            log.warn("上传失败，openId为空");
            return response.fail().message("上传失败，openId为空");
        }
        if (StringUtils.isBlank(fileName)){
            log.warn("上传失败，fileName为空");
            return response.fail().message("上传失败，fileName为空");
        }
        return new ViewWebResponse().success().data(new JSONObject(){{this.put("fileId", uploadFileLogService.addLog(file,fileName,openId));}});
    }



    private void validator(File file, int toType, ViewWebResponse response){
        response.fail().message("文件类型与转换目标不对应");
        String fileName = file.getName();
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
    public ViewWebResponse log(@RequestBody  ConvertLogRequest request){
        if (StringUtils.isBlank(request.getOpenId())){
            return new ViewWebResponse().fail().message("openId为空");
        }
        return new ViewWebResponse().success().data(convertService.queryLog(request));
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
        return new ViewWebResponse().success().data(convertService.removeById(id)).message("删除成功");
    }
}
