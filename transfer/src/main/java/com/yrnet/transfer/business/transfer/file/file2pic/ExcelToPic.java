package com.yrnet.transfer.business.transfer.file.file2pic;

import com.aspose.cells.*;
import com.yrnet.transfer.business.transfer.file.Out;

/**
 * Description todo
 * @Author dengbp
 * @Date 6:47 PM 3/11/22
 **/
public class ExcelToPic {

    public static void excelToPdf(String inPath, String outPath) {
        if (!com.yrnet.transfer.business.transfer.file.License.getExcelLicense()) {
            return;
        }
        try {
            long old = System.currentTimeMillis();


            Workbook wb = new Workbook(inPath);
            Worksheet sheet = wb.getWorksheets().get(0);
            ImageOrPrintOptions imgOptions = new ImageOrPrintOptions();
            imgOptions.setImageFormat(ImageFormat.getPng());
            imgOptions.setCellAutoFit(true);
            imgOptions.setOnePagePerSheet(true);
            SheetRender render = new SheetRender(sheet, imgOptions);

            render.toImage(0, outPath);

            long now = System.currentTimeMillis();
            Out.print(inPath, outPath, now, old);
        }catch (Exception e) {
            e.printStackTrace();
        }

    }

}
