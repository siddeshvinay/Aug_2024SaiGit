package com.Fifty50.seventh7day;


import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.tika.metadata.PDF;
/*import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.element.Paragraph;*/

import java.io.FileOutputStream;
import java.io.IOException;

public class DocNPDF {
    public static void doc1() throws IOException, InvalidFormatException {
        XWPFDocument doc= new XWPFDocument();

        XWPFParagraph paragraph= doc.createParagraph();
        XWPFRun run=paragraph.createRun();
        run.setText("This the is the document created");

        FileOutputStream fil= new FileOutputStream("E:\\sapmle.docx");
        doc.write(fil);
        doc.close();
    }

    public static void CreatePdfFile(){
/*        PdfDocument pdf = new PdfDocument(new PdfWriter("sample.pdf"));

        Paragraph paragraph = new Paragraph("This is a sample PDF created from Java Selenium.");
        pdf.add(paragraph);

        pdf.close();
        */
/*        WebDriver driver = new ChromeDriver();s

        driver.get("https://example.com");

        TakesScreenshot screenshot = (TakesScreenshot) driver;
        File screenshotFile = screenshot.getScreenshotAs(OutputType.FILE);

        // Convert the screenshot to a PDF file using iText
        PdfDocument pdf = new PdfDocument(new PdfWriter("sample.pdf"));
        pdf.add(new Paragraph("This is a sample PDF created from Java Selenium."));
        pdf.add(new Image(screenshotFile.getAbsolutePath()));
        pdf.close();*/
    }

    public static void main(String[] args) throws IOException, InvalidFormatException {
        doc1();
    }
}
