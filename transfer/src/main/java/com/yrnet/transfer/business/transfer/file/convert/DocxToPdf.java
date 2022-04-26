package com.yrnet.transfer.business.transfer.file.convert;

import com.aspose.pdf.Document;
import com.aspose.pdf.PdfSaveOptions;
import com.yrnet.transfer.business.transfer.file.Out;
import lombok.extern.slf4j.Slf4j;

import java.io.File;

/**
 * @author dengbp
 * @ClassName DocxToPdf
 * @Description TODO
 * @date 4/26/22 9:25 AM
 */
@Slf4j
public class DocxToPdf {

    public static long wordToPdf(String inFile, String outFile) {
        if (!com.yrnet.transfer.business.transfer.file.License.getWordLicense()) {
            return 0;
        }
        try {
            long old = System.currentTimeMillis();
            Document doc = new Document(inFile);
            PdfSaveOptions options = new PdfSaveOptions();
            doc.save(outFile, options);
            long fileSize = new File(outFile).length();
            long now = System.currentTimeMillis();
            log.info("target file size:{}",fileSize);
            Out.print(inFile, outFile, now, old);
            return fileSize;
        } catch (Exception e) {
            log.error(inFile + "转换失败，请重试",e);
            return 0;
        }
    }
}
