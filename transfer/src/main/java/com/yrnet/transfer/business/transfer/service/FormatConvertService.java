package com.yrnet.transfer.business.transfer.service;

import com.yrent.common.constant.ConvertType;
import com.yrent.common.constant.FileSuffixConstant;
import com.yrnet.transfer.business.transfer.dto.TransferRequest;
import com.yrnet.transfer.business.transfer.dto.ConvertResponse;
import com.yrnet.transfer.business.transfer.file.convert.ExcelToPdf;
import com.yrnet.transfer.business.transfer.file.convert.PdfToWord;
import com.yrnet.transfer.business.transfer.file.convert.WordToPdf;
import com.yrnet.transfer.common.exception.TransferException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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
        String fullPath = transferReq.getFilePath().substring(0,transferReq.getFilePath().lastIndexOf(transferReq.getFileName())).concat(fileName);
        long fileSize;
        switch (type){
            case word_to_pdf:
                fullPath = fullPath.concat(FileSuffixConstant.PDF);
                fileSize = WordToPdf.wordToPdf(transferReq.getFilePath(),fullPath);
                successResponse(response,fileName.concat(FileSuffixConstant.PDF),fullPath,fileSize);
                break;
            case pdf_to_word:
                fullPath = fullPath.concat(FileSuffixConstant.DOC);
                fileSize = PdfToWord.pdfToDoc(transferReq.getFilePath(),fullPath.concat(FileSuffixConstant.DOC));
                successResponse(response,fileName.concat(FileSuffixConstant.PDF),fullPath,fileSize);
                break;
            case pdf_to_docx:
                fullPath = fullPath.concat(FileSuffixConstant.DOCX);
                fileSize = PdfToWord.pdfToDOCX(transferReq.getFilePath(),fullPath.concat(FileSuffixConstant.DOCX));
                successResponse(response,fileName.concat(FileSuffixConstant.PDF),fullPath,fileSize);
                break;
            case excel_to_pdf:
                fullPath = fullPath.concat(FileSuffixConstant.PDF);
                fileSize = ExcelToPdf.excelToPdf(transferReq.getFilePath(),fullPath.concat(FileSuffixConstant.PDF));
                successResponse(response,fileName.concat(FileSuffixConstant.PDF),fullPath,fileSize);
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
       return response;
    }

    private void successResponse(ConvertResponse response, String fileName, String path, long fileSize){
        response.setState(SUCCESS);
        response.setFileName(fileName);
        response.setFilePath(path);
        response.setFileSize(fileSize);
    }
}
