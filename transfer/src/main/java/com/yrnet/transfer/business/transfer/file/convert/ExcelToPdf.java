package com.yrnet.transfer.business.transfer.file.convert;

import com.aspose.cells.PdfSaveOptions;
import com.aspose.cells.SaveFormat;
import com.aspose.cells.Workbook;
import com.yrnet.transfer.business.transfer.file.Out;

import java.io.File;
import java.io.FileOutputStream;

/**
 * Description todo
 * @Author dengbp
 * @Date 6:47 PM 3/11/22
 **/
public class ExcelToPdf {

    public static void excelToPdf(String inFile, String outFile) {
        if (!com.yrnet.transfer.business.transfer.file.License.getExcelLicense()) {
            return;
        }
        try {
            long old = System.currentTimeMillis();
            File pdfFile = new File(outFile);
            Workbook wb = new Workbook(inFile);
            PdfSaveOptions pdfSaveOptions = new PdfSaveOptions();
            pdfSaveOptions.setOnePagePerSheet(true);
            FileOutputStream fileOS = new FileOutputStream(pdfFile);
            wb.save(fileOS, SaveFormat.PDF);
            fileOS.close();
            long now = System.currentTimeMillis();
            Out.print(inFile, outFile, now, old);
        }catch (Exception e) {
            e.printStackTrace();
        }

    }

}
