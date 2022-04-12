package com.yrnet.transfer.business.transfer.file.file2Pdf;

import com.aspose.slides.Presentation;
import com.yrnet.transfer.business.transfer.file.Out;

import java.io.File;
import java.io.FileOutputStream;



/**
 * Description todo
 * @Author dengbp
 * @Date 6:47 PM 3/11/22
 **/
public class PptToPdf {

    public static void pptToPdf(String inPath, String outPath) {
        if (!com.yrnet.transfer.business.transfer.file.License.getPptLicense()) {
            return;
        }
        try {
            long old = System.currentTimeMillis();
            File pdfFile = new File(outPath);
            FileOutputStream os = new FileOutputStream(pdfFile);
            Presentation pres = new Presentation(inPath);
            pres.save(os, com.aspose.slides.SaveFormat.Pdf);
            os.close();
            long now = System.currentTimeMillis();
            Out.print(inPath, outPath, now, old);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
