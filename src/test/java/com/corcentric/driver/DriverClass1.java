package com.corcentric.driver;

import de.redsix.pdfcompare.PdfComparator;
import de.redsix.pdfcompare.RenderingException;
import java.io.IOException;

public class DriverClass1 {
    public static void main(String[] args) {
        String file1 = "C:\\Users\\Gopalkrishna.Gudi\\Documents\\ggudi\\PDFs\\Done\\15Sep22_3005286890_invoice_186221194-19.pdf";
        String file2 = "C:\\Users\\Gopalkrishna.Gudi\\Documents\\ggudi\\PDFs\\Done\\21Oct22_3005286890_invoice_186221194-19.pdf";
        String result = "C:\\Users\\Gopalkrishna.Gudi\\Documents\\ggudi\\PDFs\\Done\\ResultFile.pdf";

        try {
            new PdfComparator(file1, file2).compare().writeTo(result);
        } catch (RenderingException | IOException e) {
            e.printStackTrace();
        }
        System.out.println("process completed");
    }
}
