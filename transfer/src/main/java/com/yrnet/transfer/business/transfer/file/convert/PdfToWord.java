package com.yrnet.transfer.business.transfer.file.convert;


import com.aspose.pdf.DocSaveOptions;
import com.aspose.pdf.Document;
import com.yrnet.transfer.business.transfer.file.Out;
import lombok.extern.slf4j.Slf4j;

import java.io.File;


/**
 * @author dengbp
 * @ClassName PdfToWord
 * @Description TODO
 * @date 4/18/22 10:06 AM
 */
@Slf4j
public class PdfToWord {

    public static long pdfToDoc(String inFile, String outFile) {
        if (!com.yrnet.transfer.business.transfer.file.License.getPdfLicense()) {
            return 0;
        }
        log.info("开始转换...");
        long old = System.currentTimeMillis();
        Document pdfDocument = new Document(inFile);
        DocSaveOptions saveOptions = new DocSaveOptions();
        saveOptions.setFormat(DocSaveOptions.DocFormat.Doc);
        pdfDocument.save(outFile, saveOptions);
        long now = System.currentTimeMillis();
        Out.print(inFile, outFile, now, old);
        log.info("转换结束...");
        return new File(outFile).length();
    }

    public static long pdfToDOCX(String inFile, String outFile) {
        if (!com.yrnet.transfer.business.transfer.file.License.getPdfLicense()) {
            return 0 ;
        }
        log.info("开始转换...");
        long old = System.currentTimeMillis();
        Document doc = new Document(inFile);
        DocSaveOptions saveOptions = new DocSaveOptions();
        saveOptions.setFormat(DocSaveOptions.DocFormat.DocX);
        doc.save(outFile, saveOptions);
        long now = System.currentTimeMillis();
        Out.print(inFile, outFile, now, old);
        log.info("转换结束...");
        return new File(outFile).length();
    }
}
