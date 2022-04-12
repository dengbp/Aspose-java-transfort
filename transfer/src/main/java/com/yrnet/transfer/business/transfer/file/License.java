package com.yrnet.transfer.business.transfer.file;


import java.io.InputStream;


/**
 * Description todo
 * @Author dengbp
 * @Date 6:47 PM 3/11/22
 **/
public class License {

    public static boolean getExcelLicense() {
        boolean result = false;
        try {
            InputStream is = License.class.getClassLoader().getResourceAsStream("/license.xml");
            com.aspose.cells.License aposeLic = new com.aspose.cells.License();
            aposeLic.setLicense(is);
            result = true;
        }catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static boolean getWordLicense() {
        boolean result = false;
        try {
            InputStream is = License.class.getResourceAsStream("/license.xml");
            com.aspose.words.License aposeLic = new com.aspose.words.License();
            aposeLic.setLicense(is);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    public static boolean getPptLicense() {
        boolean result = false;
        try {
            InputStream is = License.class.getResourceAsStream("/license.xml");
            com.aspose.slides.License aposeLic = new com.aspose.slides.License();
            aposeLic.setLicense(is);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

}
