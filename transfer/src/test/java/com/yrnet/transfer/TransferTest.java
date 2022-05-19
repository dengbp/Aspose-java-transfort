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

        String inFile = "/Users/dengbp/Downloads/批判性思考.pdf";
        String outFile = "/Users/dengbp/Downloads/批判性思考.docx";
         long  fileSize = PdfToWord.pdfToDOCX(inFile,outFile);

        System.out.println(000);
    }







    public void testPdfToExcel() throws Exception {
        String inFile = "/Users/dengbp/Downloads/doc-sys/transfer/pdf/模板-ENG4-old.pdf";
        String outFile = "/Users/dengbp/Downloads/tmp_target_ok22.xlsx";
        long  fileSize = PdfToExcel.pdfToExcel(inFile,outFile);

        System.out.println(000);
    }

    public void testPdfToPpt(){

        String inFile = "/Users/dengbp/Downloads/JD数科 电子合同中心-系统架构.pdf";
        String outFile = "/Users/dengbp/Downloads/JD数科 电子合同中心-系统架构.pdf.ppt";

        PdfToPpt.pdfToPpt(inFile,outFile);
    }

    public void testExcelToPdf() throws Exception {

        String inFile = "/Users/dengbp/Downloads/交接资源(汪三丁院红).xlsx";
        String outFile = "/Users/dengbp/Downloads/tmp_source.pdf";

        ExcelToPdf.excelToPdf(inFile,outFile);
    }



    public void testPdfToPng() throws Exception {

        String inFile = "/Users/dengbp/Downloads/tmp_source.pdf";

        List<String> outPng = new ArrayList<>();
        PdfToPng.pdfToPng(inFile,outPng);
    }



    public void testPdfToJpg() throws Exception {

        String inFile = "/Users/dengbp/Downloads/tmp_source.pdf";
        List<String> outJpg = new ArrayList<>();
        PdfToJpg.pdfToJpg(inFile,outJpg);
    }


    public void testPicToPdf() throws Exception {
        List<String> inFile = new ArrayList<>();
        inFile.add("/Users/dengbp/Downloads/11111.jpg");
        inFile.add("/Users/dengbp/Downloads/tmp_source_1.png");
        String outFile = "/Users/dengbp/Downloads/pic.pdf";
        JpgToPdf.jpgToPdf(inFile,outFile);
    }

    public void testWordToPdf() throws Exception {
        String path2 = "/Users/dengbp/Downloads/doc-sys/transfer/模板-LL-SP-DIS30216.doc";
        String fileName2 = "模板-LL-SP-DIS30217.pdf";
        WordToPdf.wordToPdf(path2, "/Users/dengbp/Downloads/doc-sys/transfer/pdf/"+fileName2);
    }



}
