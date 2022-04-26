package com.yrnet.transfer.business.transfer.file.convert;

import com.yrnet.transfer.business.transfer.file.Out;
import com.aspose.words.*;
import lombok.extern.slf4j.Slf4j;

import java.io.File;

/**
 * @author dengbp
 * @ClassName OdtToPdf
 * @Description TODO
 * @date 4/26/22 9:31 AM
 */
@Slf4j
public class OdtToPdf {

    public static long odtToPdf(String inFile, String outFile) {
        if (!com.yrnet.transfer.business.transfer.file.License.getWordLicense()) {
            return 0;
        }
        try {
            long old = System.currentTimeMillis();
            Document doc = new Document(inFile);
            doc.save(outFile);
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
