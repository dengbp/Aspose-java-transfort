package com.yrnet.transfer.business.transfer.file.convert;

import com.aspose.pdf.Document;
import com.aspose.pdf.devices.JpegDevice;
import com.aspose.pdf.devices.Resolution;
import com.yrnet.transfer.business.transfer.file.Out;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

/**
 * @author dengbp
 * @ClassName JpgToPdf
 * @Description TODO
 * @date 4/22/22 10:21 AM
 */
@Slf4j
public class PdfToPng {
    public static long pdfToPng(String inFile, List<String> outFile) throws Exception {
        long size = 0;
        if (!com.yrnet.transfer.business.transfer.file.License.getPdfLicense()) {
            return size;
        }
        try {
            long old = System.currentTimeMillis();
            Document pdfDocument = new Document(inFile);
            //图片宽度：800
            //图片高度：100
            // 分辨率 960
            //Quality [0-100] 最大100
            //例： new JpegDevice(800, 1000, resolution, 90);
            Resolution resolution = new Resolution(960);
            JpegDevice jpegDevice = new JpegDevice(resolution);
            for (int index=1;index<=pdfDocument.getPages().size();index++) {
                // 输出路径
                String path = inFile.substring(0,inFile.lastIndexOf(".")) + "_"+index+".png";
                File file = new File(path);
                size += file.length();
                FileOutputStream fileOs = new FileOutputStream(file);
                jpegDevice.process(pdfDocument.getPages().get_Item(index), fileOs);
                outFile.add(path);
                fileOs.close();
                long now = System.currentTimeMillis();
                Out.print(inFile, path, now, old);
            }
            return size;
        }catch (Exception e){
            log.error(e.getMessage(),e);
            throw new Exception(e.getMessage());
        }
    }
}
