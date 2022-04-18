package com.yrnet.transfer.business.transfer.service;

import com.yrnet.transfer.business.transfer.dto.TransferRequest;
import com.yrnet.transfer.business.transfer.dto.TransferResponse;
import com.yrnet.transfer.business.transfer.file.convert.ExcelToPdf;
import com.yrnet.transfer.business.transfer.file.convert.ExcelToPic;
import com.yrnet.transfer.business.transfer.file.convert.PdfToWord;
import com.yrnet.transfer.business.transfer.file.convert.WordToPdf;
import com.yrnet.transfer.common.exception.TransferException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import com.yrnet.transfer.business.transfer.constant.ConvertType;

/**
 * @author dengbp
 * @ClassName FormatConvertService
 * @Description TODO
 * @date 4/17/22 12:49 AM
 */
@Service
@Slf4j
public class FormatConvertService {

    public TransferResponse convert(TransferRequest transferReq)throws TransferException{
        ConvertType type = ConvertType.getByCode((char)transferReq.getToType().intValue());
        String outPath = "";
        String fileName = "";
        String fullPath = outPath.concat(fileName);
        switch (type){
            case word_to_pdf:
                WordToPdf.wordToPdf(transferReq.getFilePath(),fullPath);
                break;
            case pdf_to_word:
                PdfToWord.pdfToDoc(transferReq.getFilePath(),fullPath);
                break;
            case pdf_to_docx:
                PdfToWord.pdfToDOCX(transferReq.getFilePath(),fullPath);
                break;
            case excel_to_pdf:
                ExcelToPdf.excelToPdf(transferReq.getFilePath(),fullPath);
                break;
            case pdf_to_excel:

                break;
            case ppt_to_pdf:

                break;
            case pdf_to_ppt:

                break;
            case jpg_to_pdf:

                break;
            case pdf_to_jpg:

                break;
            case png_to_pdf:

                break;
            case pdf_to_png:

                break;
            case docx_to_pdf:

                break;
            case odt_to_pdf:

                break;
            case doc_to_pdf:

                break;
            default:
                log.warn("no type match!");
                break;
        }


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

       return TransferResponse.builder().build();
    }
}
