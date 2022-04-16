package com.yrnet.transfer.business.transfer.controller;

import com.yrnet.transfer.annotion.Log;
import com.yrnet.transfer.business.transfer.dto.TransferReq;
import com.yrnet.transfer.business.transfer.file.file2Pdf.ExcelToPdf;
import com.yrnet.transfer.business.transfer.file.file2pic.ExcelToPic;
import com.yrnet.transfer.common.http.TransferResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;

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


    @Log("文档格式转换")
    @PostMapping("exe")
    @ResponseBody
    public TransferResponse enforce(@RequestBody @Valid TransferReq transferReq){


        String targetPath = "/Users/dengbp/Downloads/doc-sys/transfer/pdf/";
        /*String path1 = "/Users/dengbp/Downloads/doc-sys/transfer/模板-QCC2-mini异常处理规范1.1.doc";
        String fileName1 = "模板-QCC2-mini异常处理规范1.1.pdf";
        docToPdf(path1, targetPath+fileName1);
        System.out.println(111111);

        String path2 = "/Users/dengbp/Downloads/doc-sys/transfer/模板-LL-SP-DIS30216.doc";
        String fileName2 = "模板-LL-SP-DIS30216.pdf";
        docToPdf(path2, targetPath+fileName2);
        System.out.println(222222);*/


        String path3 = "/Users/dengbp/Downloads/doc-sys/transfer/模板-ENG4-.xlsx";
        String fileName3 = "模板-ENG4.pdf";
        ExcelToPdf.excelToPdf(path3, targetPath+fileName3);
        System.out.println(333333);

        String fileName31 = "模板-ENG4.png";
        ExcelToPic.excelToPdf(path3,targetPath+fileName31);

       /* String  path4 = "/Users/dengbp/Downloads/doc-sys/transfer/模板-DIS3-1230.xlsx";
        String fileName4 = "模板-DIS3-1230.pdf";
        excelToPdf(path4, targetPath+fileName4);
        System.out.println(444444);


        String  path5 = "/Users/dengbp/Downloads/doc-sys/transfer/模板-ENG2点胶目检作业指导书.pptx";
        String fileName5 = "模板-ENG2点胶目检作业指导书.pdf";
        pptToPdf(path5, targetPath+fileName5);
        System.out.println(555555);


        String  path6 = "/Users/dengbp/Downloads/doc-sys/transfer/模板-ENG3-SMT照明维修作业指导书-1.5.pptx";
        String fileName6 = "模板-ENG3-SMT照明维修作业指导书-1.5.pdf";
        pptToPdf(path6, targetPath+fileName6);
        System.out.println(666666);*/

        return new TransferResponse().success().message("success");
    }
}
