package com.yrnet.transfer;

import com.spire.pdf.FileFormat;
import com.spire.pdf.PdfDocument;
import com.yrnet.transfer.business.transfer.file.convert.*;
import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.List;

/**
 * @author dengbp
 * @ClassName TransferTest
 * @Description TODO
 * @date 5/2/22 12:37 AM
 */
public class TransferTest extends TestCase {



    public void testPdfToDoc(){
        System.out.println(11111);

        String inFile = "/Users/dengbp/Downloads/tmp_source.pdf";
        String outFile = "/Users/dengbp/Downloads/tmp_target_ok.doc";
         long  fileSize = PdfToWord.pdfToDoc(inFile,outFile);

        System.out.println(000);
    }

    public void testPdfToDOCX(){
        System.out.println(11111);

        String inFile = "/Users/dengbp/Downloads/tmp_source.pdf";
        String outFile = "/Users/dengbp/Downloads/tmp_target_ok.docx";
         long  fileSize = PdfToWord.pdfToDOCX(inFile,outFile);

        System.out.println(000);
    }







    public void testPdfToExcel(){
        String inFile = "/Users/dengbp/Downloads/doc-sys/transfer/pdf/模板-ENG4-old.pdf";
        String outFile = "/Users/dengbp/Downloads/tmp_target_ok22.xlsx";
        long  fileSize = PdfToExcel.pdfToExcel(inFile,outFile);

        System.out.println(000);
    }

    public void testPdfToPpt(){

        String inFile = "/Users/dengbp/Downloads/tmp_source.pdf";
        String outFile = "/Users/dengbp/Downloads/tmp_target_spire21.ppt";

        PdfToPpt.pdfToPpt(inFile,outFile);
    }



    public void testPdfToPng(){

        String inFile = "/Users/dengbp/Downloads/tmp_source.pdf";

        List<String> outPng = new ArrayList<>();
        PdfToPng.pdfToPng(inFile,outPng);
    }



    public void testPdfToJpg(){

        String inFile = "/Users/dengbp/Downloads/tmp_source.pdf";
        List<String> outJpg = new ArrayList<>();
        PdfToJpg.pdfToJpg(inFile,outJpg);
    }


    public void testPicToPdf(){
        List<String> inFile = new ArrayList<>();
        inFile.add("/Users/dengbp/Downloads/11111.jpg");
        inFile.add("/Users/dengbp/Downloads/tmp_source_1.png");
        String outFile = "/Users/dengbp/Downloads/pic.pdf";
        JpgToPdf.jpgToPdf(inFile,outFile);
    }

}
