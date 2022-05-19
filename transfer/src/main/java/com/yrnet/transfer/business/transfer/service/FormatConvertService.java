package com.yrnet.transfer.business.transfer.service;

import com.yrent.common.constant.ConvertType;
import com.yrent.common.constant.FileSuffixConstant;
import com.yrnet.transfer.business.transfer.dto.TransferRequest;
import com.yrnet.transfer.business.transfer.dto.ConvertResponse;
import com.yrnet.transfer.business.transfer.entity.FileConvertLog;
import com.yrnet.transfer.business.transfer.file.convert.*;
import com.yrnet.transfer.common.exception.TransferException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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

    private static  Integer FAIL = 1;
    private static Integer PROCESSING = -1;
    private static Integer SUCCESS = 0;
    @Resource
    private IFileConvertLogService fileConvertLogService;

    public ConvertResponse convert(TransferRequest transferReq)throws TransferException{
        ConvertResponse response = ConvertResponse.builder().fileId(transferReq.getFileId()).fileSize(0L).state(PROCESSING).build();
        ConvertType type = ConvertType.getByCode((char)transferReq.getToType().intValue());
        String fileName = transferReq.getFileName().substring(0,transferReq.getFileName().lastIndexOf("."));
        List<String> inFiles = Arrays.asList(transferReq.getFilePath().split(","));
        long fileSize = 0;
        try{
            //目标文件(包含路径，不含文件后缀)
            final List<String> outFiles = new ArrayList<>();
            inFiles.forEach(inFile->outFiles.add(inFile.substring(0,inFile.lastIndexOf("."))));
            String outFile;
            switch (type){
                case word_to_pdf:
                    outFile = outFiles.get(0).concat(FileSuffixConstant.PDF);
                    setResponse(response,fileName.concat(FileSuffixConstant.PDF),outFile);
                    fileSize = WordToPdf.wordToPdf(transferReq.getFilePath(),outFile);
                    response.setState(SUCCESS);
                    break;
                case pdf_to_word:
                    outFile = outFiles.get(0).concat(FileSuffixConstant.DOC);
                    setResponse(response,fileName.concat(FileSuffixConstant.DOC),outFile);
                    fileSize = PdfToWord.pdfToDoc(transferReq.getFilePath(),outFile);
                    response.setState(SUCCESS);
                    break;
                case pdf_to_docx:
                    outFile = outFiles.get(0).concat(FileSuffixConstant.DOCX);
                    setResponse(response,fileName.concat(FileSuffixConstant.DOCX),outFile);
                    fileSize = PdfToWord.pdfToDOCX(transferReq.getFilePath(),outFile);
                    response.setState(SUCCESS);
                    break;
                case excel_to_pdf:
                    outFile = outFiles.get(0).concat(FileSuffixConstant.PDF);
                    setResponse(response,fileName.concat(FileSuffixConstant.PDF),outFile);
                    fileSize = ExcelToPdf.excelToPdf(transferReq.getFilePath(),outFile);
                    response.setState(SUCCESS);
                    break;
                case pdf_to_excel:
                    outFile = outFiles.get(0).concat(FileSuffixConstant.EXCEL);
                    setResponse(response,fileName.concat(FileSuffixConstant.EXCEL),outFile);
                    fileSize =  PdfToExcel.pdfToExcel(transferReq.getFilePath(),outFile);
                    response.setState(SUCCESS);
                    break;
                case ppt_to_pdf:
                    outFile = outFiles.get(0).concat(FileSuffixConstant.PDF);
                    setResponse(response,fileName.concat(FileSuffixConstant.PDF),outFile);
                    fileSize =  PptToPdf.pptToPdf(transferReq.getFilePath(),outFile);
                    response.setState(SUCCESS);
                    break;
                case pdf_to_ppt:
                    outFile = outFiles.get(0).concat(FileSuffixConstant.PPT);
                    setResponse(response,fileName.concat(FileSuffixConstant.PPT),outFile);
                    fileSize =  PdfToPpt.pdfToPpt(transferReq.getFilePath(),outFile);
                    response.setState(SUCCESS);
                    break;
                case pic_to_pdf:
                    outFile = outFiles.get(0).concat(FileSuffixConstant.PDF);
                    setResponse(response,fileName.concat(FileSuffixConstant.PDF),outFile);
                    fileSize =  JpgToPdf.jpgToPdf(inFiles,outFile);
                    response.setState(SUCCESS);
                    break;
                case pdf_to_jpg:
                    List<String> outJpg = new ArrayList<>();
                    fileSize =  PdfToJpg.pdfToJpg(transferReq.getFilePath(),outJpg);
                    String outJpgPaths = outJpg.stream().collect(Collectors.joining(","));
                    setResponse(response,fileName.concat(FileSuffixConstant.JPG),outJpgPaths);
                    response.setState(SUCCESS);
                    break;
                case pdf_to_png:
                    List<String> outPng = new ArrayList<>();
                    fileSize =  PdfToPng.pdfToPng(transferReq.getFilePath(),outPng);
                    String outPngPaths = outPng.stream().collect(Collectors.joining(","));
                    setResponse(response,fileName.concat(FileSuffixConstant.PNG),outPngPaths);
                    response.setState(SUCCESS);
                    break;
                case docx_to_pdf:
                    outFile = outFiles.get(0).concat(FileSuffixConstant.PDF);
                    setResponse(response,fileName.concat(FileSuffixConstant.PDF),outFile);
                    fileSize =  WordToPdf.wordToPdf(transferReq.getFilePath(),outFile);
                    response.setState(SUCCESS);
                    break;
                case odt_to_pdf:
                    outFile = outFiles.get(0).concat(FileSuffixConstant.PDF);
                    setResponse(response,fileName.concat(FileSuffixConstant.PDF),outFile);
                    fileSize =  OdtToPdf.odtToPdf(transferReq.getFilePath(),outFile);
                    response.setState(SUCCESS);
                    break;
                case doc_to_pdf:
                    outFile = outFiles.get(0).concat(FileSuffixConstant.PDF);
                    setResponse(response,fileName.concat(FileSuffixConstant.PDF),outFile);
                    fileSize = WordToPdf.wordToPdf(transferReq.getFilePath(),outFile);
                    response.setState(SUCCESS);
                    break;
                default:
                    log.warn("no type match!");
                    break;
            }
        }catch (Exception e){
            log.error(e.getMessage(),e);
            response.setState(FAIL);
        }finally {
            response.setFileSize(fileSize);
            updateLog(response);
            return response;
        }
    }

    private void updateLog(ConvertResponse tr){
        FileConvertLog clog = new FileConvertLog();
            if (StringUtils.isBlank(tr.getFilePath())){
                clog.setState(FAIL);
            }
            clog.setNewFilePath(tr.getFilePath());
            clog.setNewFileName(tr.getFileName());
            clog.setNewFileSize(tr.getFileSize()==null?0:tr.getFileSize());
            clog.setId(tr.getFileId());
            clog.setState(tr.getState());
            fileConvertLogService.updateById(clog);
    }

    private void setResponse(ConvertResponse response, String fileName, String path){
        response.setFileName(fileName);
        response.setFilePath(path);
    }
}
