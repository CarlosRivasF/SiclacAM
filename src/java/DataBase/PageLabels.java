/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataBase;

/**
 *
 * @author ZionSystem
 */
import com.itextpdf.text.Anchor;
import com.itextpdf.text.Annotation;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfName;
import com.itextpdf.text.pdf.PdfNumber;
import com.itextpdf.text.pdf.PdfPageLabels;
import com.itextpdf.text.pdf.PdfTemplate;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class PageLabels {

    public static final String DEST = "results/objects/pagelabels.pdf";

    public static void main(String[] args) throws IOException,
            DocumentException {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new PageLabels().createPdf(DEST);
    }

    public void createPdf(String filename) throws IOException, DocumentException {
        Document document = new Document();
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(filename));
        writer.setViewerPreferences(PdfWriter.PageLayoutTwoPageLeft | PdfWriter.PageModeUseThumbs);
        writer.addViewerPreference(PdfName.PRINTSCALING, PdfName.NONE);
        PdfPageLabels labels = new PdfPageLabels();
        labels.addPageLabel(1, PdfPageLabels.UPPERCASE_LETTERS);
        labels.addPageLabel(3, PdfPageLabels.DECIMAL_ARABIC_NUMERALS);
        labels.addPageLabel(4,
                PdfPageLabels.DECIMAL_ARABIC_NUMERALS, "Custom-", 2);
        writer.setPageLabels(labels);
        document.open();                

        document.setPageSize(new Rectangle(200, 100));
        document.newPage();
        document.add(new Paragraph("Hello World4"));

        document.close();
    }
}
