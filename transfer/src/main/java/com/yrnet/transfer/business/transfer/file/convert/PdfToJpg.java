package com.yrnet.transfer.business.transfer.file.convert;

import com.aspose.pdf.Document;
import com.aspose.pdf.SaveFormat;
import com.yrnet.transfer.business.transfer.file.Out;

import java.io.File;
import com.aspose.pdf.Document;
import com.aspose.pdf.devices.JpegDevice;
import com.aspose.pdf.devices.Resolution;
import lombok.extern.slf4j.Slf4j;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
/**
 * @author dengbp
 * @ClassName JpgToPdf
 * @Description TODO
 * @date 4/22/22 10:21 AM
 */
@Slf4j
public class PdfToJpg {

    public static long pdfToJpg(String inFile, List<String> outFile) {
        long size = 0;
        if (!com.yrnet.transfer.business.transfer.file.License.getPptLicense()) {
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
                String path = inFile.substring(0,outFile.lastIndexOf(".")) + "/"+index+".jpg";
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
            return size;
        }
    }
}
