package com.yrnet.transfer.business.transfer.file.convert;

import com.aspose.pdf.Document;
import com.aspose.pdf.PptxSaveOptions;
import com.yrnet.transfer.business.transfer.file.Out;

import java.io.File;

/**
 * @author dengbp
 * @ClassName PdfToPpt
 * @Description TODO
 * @date 4/22/22 9:58 AM
 */
public class PdfToPpt {

    public static long pdfToPpt(String inFile, String outFile) {
        if (!com.yrnet.transfer.business.transfer.file.License.getPdfLicense()) {
            return 0;
        }
        long old = System.currentTimeMillis();
        // Load PDF document
        Document pdfDocument = new Document(inFile);
        // Set PPTX save options
        PptxSaveOptions pptxOptions = new PptxSaveOptions();
        pptxOptions.setExtractOcrSublayerOnly(true);
        // Save PDF as PPTX
        pdfDocument.save(outFile, pptxOptions);
        long now = System.currentTimeMillis();
        Out.print(inFile, outFile, now, old);
        return new File(outFile).length();
    }
}
