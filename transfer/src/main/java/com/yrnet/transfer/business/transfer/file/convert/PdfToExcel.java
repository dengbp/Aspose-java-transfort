package com.yrnet.transfer.business.transfer.file.convert;

import com.aspose.pdf.Document;
import com.aspose.pdf.ExcelSaveOptions;
import com.yrnet.transfer.business.transfer.file.Out;

import java.io.File;

/**
 * Description todo
 * @Author dengbp
 * @Date 6:47 PM 3/11/22
 **/
public class PdfToExcel {

    public static long pdfToExcel(String inFile, String outFile) {
        if (!com.yrnet.transfer.business.transfer.file.License.getPdfLicense()) {
            return 0;
        }
        try {

            long old = System.currentTimeMillis();
            // Load source PDF file
            Document doc = new Document(inFile);
// Set Excel options
            ExcelSaveOptions options = new ExcelSaveOptions();
// Set output format
            options.setFormat(ExcelSaveOptions.ExcelFormat.XLSX);
// Convert PDF to XLSX
            doc.save(outFile, options);



            long now = System.currentTimeMillis();
            Out.print(inFile, outFile, now, old);
            return new File(outFile).length();
        }catch (Exception e) {
            e.printStackTrace();
            return 0;
        }

    }

}
