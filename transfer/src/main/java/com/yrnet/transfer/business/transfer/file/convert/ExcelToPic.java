package com.yrnet.transfer.business.transfer.file.convert;

import com.aspose.cells.*;
import com.yrnet.transfer.business.transfer.file.Out;

import java.io.File;

/**
 * Description todo
 * @Author dengbp
 * @Date 6:47 PM 3/11/22
 **/
public class ExcelToPic {

    public static long excelToPdf(String inFile, String outFile) {
        if (!com.yrnet.transfer.business.transfer.file.License.getExcelLicense()) {
            return 0;
        }
        try {
            long old = System.currentTimeMillis();
            Workbook wb = new Workbook(inFile);
            Worksheet sheet = wb.getWorksheets().get(0);
            ImageOrPrintOptions imgOptions = new ImageOrPrintOptions();
            imgOptions.setImageFormat(ImageFormat.getPng());
            imgOptions.setCellAutoFit(true);
            imgOptions.setOnePagePerSheet(true);
            SheetRender render = new SheetRender(sheet, imgOptions);
            render.toImage(0, outFile);
            long now = System.currentTimeMillis();
            Out.print(inFile, outFile, now, old);
            return new File(outFile).length();
        }catch (Exception e) {
            e.printStackTrace();
            return 0;
        }

    }

}
