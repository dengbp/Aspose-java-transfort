package com.yrnet.transfer.business.transfer.file.convert;

import com.aspose.words.Document;
import com.aspose.words.ImportFormatMode;
import com.aspose.words.SaveFormat;
import com.yrnet.transfer.business.transfer.file.Out;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileOutputStream;

/**
 * Description todo
 * @Author dengbp
 * @Date 6:47 PM 3/11/22
 **/
@Slf4j
public class WordToPdf {


    public static long wordToPdf(String inFile, String outFile) {
        if (!com.yrnet.transfer.business.transfer.file.License.getWordLicense()) {
            return 0;
        }
        try {

            long old = System.currentTimeMillis();
            File file = new File(outFile);
            FileOutputStream os = new FileOutputStream(file);
            Document doc = new Document(inFile);
            Document tmp = new Document();
            tmp.removeAllChildren();
            tmp.appendDocument(doc, ImportFormatMode.USE_DESTINATION_STYLES);
            System.out.println("开始解析word文档" + inFile);
            doc.save(os, SaveFormat.PDF);
            long now = System.currentTimeMillis();
            log.info("target file size:{}",file.length());
            os.close();
            Out.print(inFile, outFile, now, old);
            return file.length();
        } catch (Exception e) {
            log.error(inFile + "转换失败，请重试",e);
            return 0;
        }
    }
}
