package com.yrnet.transfer.business.transfer.file.convert;

import com.aspose.words.Document;
import com.aspose.words.DocumentBuilder;
import com.yrnet.transfer.business.transfer.file.Out;

import java.io.File;
import java.util.List;

/**
 * @author dengbp
 * @ClassName JpgToPdf
 * @Description TODO
 * @date 4/22/22 10:21 AM
 */
public class JpgToPdf {

    public static long jpgToPdf(List<String> inFile, String outFile) {
        if (!com.yrnet.transfer.business.transfer.file.License.getWordLicense()) {
            return 0;
        }
        try {
            long old = System.currentTimeMillis();
            Document doc = new Document();
            DocumentBuilder builder = new DocumentBuilder(doc);
            for (String file : inFile)
            {
                builder.insertImage(file);
                // 插入一个段落分隔符以避免重叠图像。
                builder.writeln();
            }
            doc.save(outFile);
            long now = System.currentTimeMillis();
            Out.print(inFile.get(0), outFile, now, old);
            return new File(outFile).length();
        }catch (Exception e) {
            e.printStackTrace();
            return 0;
        }

    }
}
