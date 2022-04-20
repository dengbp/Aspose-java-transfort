package com.yrnet.transfer.business.transfer.file.convert;


import com.aspose.pdf.DocSaveOptions;
import com.aspose.pdf.Document;
import com.aspose.pdf.SaveFormat;
import com.yrnet.transfer.business.transfer.file.Out;

import java.io.File;


/**
 * @author dengbp
 * @ClassName PdfToWord
 * @Description TODO
 * @date 4/18/22 10:06 AM
 */
public class PdfToWord {

    public static long pdfToDoc(String inFile, String outFile) {
        if (!com.yrnet.transfer.business.transfer.file.License.getPptLicense()) {
            return 0;
        }
        long old = System.currentTimeMillis();
        // Open the source PDF document
        Document pdfDocument = new Document(inFile);
        // Save the file into Microsoft document format
        pdfDocument.save(outFile, SaveFormat.Doc);
        long now = System.currentTimeMillis();
        Out.print(inFile, outFile, now, old);
        return new File(outFile).length();
    }

    public static long pdfToDOCX(String inFile, String outFile) {
        if (!com.yrnet.transfer.business.transfer.file.License.getPptLicense()) {
            return 0 ;
        }
        long old = System.currentTimeMillis();
        // Load source PDF file
        Document doc = new Document(inFile);
        // Instantiate Doc SaveOptions instance
        DocSaveOptions saveOptions = new DocSaveOptions();
        // Set output file format as DOCX
        saveOptions.setFormat(DocSaveOptions.DocFormat.DocX);
        // Save resultant DOCX file
        doc.save(outFile, saveOptions);
        long now = System.currentTimeMillis();
        Out.print(inFile, outFile, now, old);
        return new File(outFile).length();
    }
}
