package com.yrnet.transfer.business.transfer.file.file2Pdf;

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
public class DocToPdf {


    public static void docToPdf(String inPath, String outPath) {
        if (!com.yrnet.transfer.business.transfer.file.License.getWordLicense()) {
            return;
        }
        try {

            long old = System.currentTimeMillis();
            File file = new File(outPath);
            FileOutputStream os = new FileOutputStream(file);
            Document doc = new Document(inPath);
            Document tmp = new Document();
            tmp.removeAllChildren();
            tmp.appendDocument(doc, ImportFormatMode.USE_DESTINATION_STYLES);
            System.out.println("开始解析word文档" + inPath);
            doc.save(os, SaveFormat.PDF);
            long now = System.currentTimeMillis();
            os.close();
            Out.print(inPath, outPath, now, old);
        } catch (Exception e) {
            System.out.println(inPath + "转换失败，请重试");
            e.printStackTrace();
        }
    }
}
