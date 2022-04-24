package com.yrnet.transfer.business.transfer.service;

import com.yrent.common.constant.ConvertType;
import com.yrent.common.constant.FileSuffixConstant;
import com.yrnet.transfer.business.transfer.dto.TransferRequest;
import com.yrnet.transfer.business.transfer.dto.ConvertResponse;
import com.yrnet.transfer.business.transfer.file.convert.*;
import com.yrnet.transfer.common.exception.TransferException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author dengbp
 * @ClassName FormatConvertService
 * @Description TODO
 * @date 4/17/22 12:49 AM
 */
@Service
@Slf4j
public class FormatConvertService {

    private static  Integer FAIL = 2;
    private static Integer SUCCESS = 0;

    public ConvertResponse convert(TransferRequest transferReq)throws TransferException{
        ConvertResponse response = ConvertResponse.builder().fileId(transferReq.getFileId()).fileSize(0L).state(FAIL).build();
        ConvertType type = ConvertType.getByCode((char)transferReq.getToType().intValue());
        String fileName = transferReq.getFileName().substring(0,transferReq.getFileName().lastIndexOf("."));
        List<String> inFiles = Arrays.asList(transferReq.getFilePath().split(","));
        //目标文件(包含路径，不含文件后缀)
        final List<String> outFiles = new ArrayList<>();
        inFiles.forEach(inFile->outFiles.add(inFile.substring(0,inFile.lastIndexOf("."))));
        long fileSize;
        String outFile;
        switch (type){
            case word_to_pdf:
                outFile = outFiles.get(0).concat(FileSuffixConstant.PDF);
                fileSize = WordToPdf.wordToPdf(transferReq.getFilePath(),outFile);
                successResponse(response,fileName.concat(FileSuffixConstant.PDF),outFile,fileSize);
                break;
            case pdf_to_word:
                outFile = outFiles.get(0).concat(FileSuffixConstant.DOC);
                fileSize = PdfToWord.pdfToDoc(transferReq.getFilePath(),outFile);
                successResponse(response,fileName.concat(FileSuffixConstant.PDF),outFile,fileSize);
                break;
            case pdf_to_docx:
                outFile = outFiles.get(0).concat(FileSuffixConstant.DOCX);
                fileSize = PdfToWord.pdfToDOCX(transferReq.getFilePath(),outFile);
                successResponse(response,fileName.concat(FileSuffixConstant.PDF),outFile,fileSize);
                break;
            case excel_to_pdf:
                outFile = outFiles.get(0).concat(FileSuffixConstant.PDF);
                fileSize = ExcelToPdf.excelToPdf(transferReq.getFilePath(),outFile);
                successResponse(response,fileName.concat(FileSuffixConstant.PDF),outFile,fileSize);
                break;
            case pdf_to_excel:
                outFile = outFiles.get(0).concat(FileSuffixConstant.EXCEL);
                fileSize =  PdfToExcel.pdfToExcel(transferReq.getFilePath(),outFile);
                successResponse(response,fileName.concat(FileSuffixConstant.EXCEL),outFile,fileSize);
                break;
            case ppt_to_pdf:
                outFile = outFiles.get(0).concat(FileSuffixConstant.PDF);
                fileSize =  PptToPdf.pptToPdf(transferReq.getFilePath(),outFile);
                successResponse(response,fileName.concat(FileSuffixConstant.PDF),outFile,fileSize);
                break;
            case pdf_to_ppt:
                outFile = outFiles.get(0).concat(FileSuffixConstant.PPT);
                fileSize =  PdfToPpt.pdfToPpt(transferReq.getFilePath(),outFile);
                successResponse(response,fileName.concat(FileSuffixConstant.PPT),outFile,fileSize);
                break;
            case jpg_to_pdf:
                outFile = outFiles.get(0).concat(FileSuffixConstant.PDF);
                fileSize =  JpgToPdf.jpgToPdf(inFiles,outFile);
                successResponse(response,fileName.concat(FileSuffixConstant.PDF),outFile,fileSize);
                break;
            case pdf_to_jpg:
                List<String> outJpg = new ArrayList<>();
                fileSize =  PdfToJpg.pdfToJpg(transferReq.getFilePath(),outJpg);
                String outJpgPaths = outJpg.stream().collect(Collectors.joining(","));
                successResponse(response,fileName.concat(FileSuffixConstant.JPG),outJpgPaths,fileSize);
                break;
            case png_to_pdf:
                outFile = outFiles.get(0).concat(FileSuffixConstant.PDF);
                fileSize =  PngToPdf.pngToPdf(inFiles,outFile);
                successResponse(response,fileName.concat(FileSuffixConstant.PDF),outFile,fileSize);
                break;
            case pdf_to_png:
                List<String> outPng = new ArrayList<>();
                fileSize =  PdfToPng.pdfToPng(transferReq.getFilePath(),outPng);
                String outPngPaths = outPng.stream().collect(Collectors.joining(","));
                successResponse(response,fileName.concat(FileSuffixConstant.PNG),outPngPaths,fileSize);
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
       return response;
    }

    private void successResponse(ConvertResponse response, String fileName, String path, long fileSize){
        response.setState(SUCCESS);
        response.setFileName(fileName);
        response.setFilePath(path);
        response.setFileSize(fileSize);
    }
}
