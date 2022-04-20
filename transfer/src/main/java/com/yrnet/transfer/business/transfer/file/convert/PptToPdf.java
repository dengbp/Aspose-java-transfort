package com.yrnet.transfer.business.transfer.file.convert;

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

    public static long pptToPdf(String inFile, String outFile) {
        if (!com.yrnet.transfer.business.transfer.file.License.getPptLicense()) {
            return 0;
        }
        try {
            long old = System.currentTimeMillis();
            File pdfFile = new File(outFile);
            FileOutputStream os = new FileOutputStream(pdfFile);
            Presentation pres = new Presentation(inFile);
            pres.save(os, com.aspose.slides.SaveFormat.Pdf);
            os.close();
            long now = System.currentTimeMillis();
            Out.print(inFile, outFile, now, old);
            return pdfFile.length();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
}
