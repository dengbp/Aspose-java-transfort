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

    public static long pdfToExcel(String inFile, String outFile) throws Exception {
        if (!com.yrnet.transfer.business.transfer.file.License.getPdfLicense()) {
            return 0;
        }
        try {
            long old = System.currentTimeMillis();
            Document doc = new Document(inFile);
            ExcelSaveOptions options = new ExcelSaveOptions();
            options.setFormat(ExcelSaveOptions.ExcelFormat.XLSX);
            doc.save(outFile, options);
            Out.print(inFile, outFile, System.currentTimeMillis(), old);
            return new File(outFile).length();
        }catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e.getMessage());
        }
    }

}
