package com.yrnet.transfer.business.transfer.file.convert;

import com.aspose.pdf.Document;
import com.aspose.pdf.ExcelSaveOptions;
import com.aspose.pdf.SaveFormat;
import com.yrnet.transfer.business.transfer.file.Out;

import java.io.File;
import java.io.FileOutputStream;

/**
 * Description todo
 * @Author dengbp
 * @Date 6:47 PM 3/11/22
 **/
public class PdfToExcel {

    public static long pdfToExcel(String inFile, String outFile) {
        if (!com.yrnet.transfer.business.transfer.file.License.getExcelLicense()) {
            return 0;
        }
        try {
            long old = System.currentTimeMillis();
            // Load source PDF file
            Document doc = new Document(inFile);
            // Set Excel options这个option还不知怎么用
            ExcelSaveOptions options = new ExcelSaveOptions();
            // Set output format
            // Set minimizing option
            options.setMinimizeTheNumberOfWorksheets(true);
            // Convert PDF to Excel XLSX
            doc.save(outFile, SaveFormat.Excel);
            long now = System.currentTimeMillis();
            Out.print(inFile, outFile, now, old);
            return new File(outFile).length();
        }catch (Exception e) {
            e.printStackTrace();
            return 0;
        }

    }

}
