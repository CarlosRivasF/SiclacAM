package DataBase;

/**
 *
 * @author ZionSystem
 */
import com.itextpdf.text.Anchor;
import com.itextpdf.text.Annotation;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.Barcode;
import com.itextpdf.text.pdf.Barcode128;
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

    public static final String DEST = "results/objects/templ.pdf";

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
        labels.addPageLabel(4,PdfPageLabels.DECIMAL_ARABIC_NUMERALS, "Custom-", 2);
        writer.setPageLabels(labels);
        document.open();                
        Font Content_Font = FontFactory.getFont("Arial", 5, BaseColor.BLACK);       
        document.setPageSize(new Rectangle(240, 90));
        document.newPage();
        Paragraph p= new Paragraph("Hello Word", Content_Font);
        p.setAlignment(Element.ALIGN_TOP);
        p.setIndentationRight(50);
       document.add(new Paragraph(" "));        
        Image img = null,img2 = null;
        //Es el tipo de clase 
        Barcode128 codeEAN = new Barcode128();
        //Seteo el tipo de codigo
        codeEAN.setCodeType(Barcode.CODE128);
        //Setep el codigo
        codeEAN.setCode("1-2A");
        //Paso el codigo a imagen   
        img = codeEAN.createImageWithBarcode(writer.getDirectContent(), BaseColor.BLACK, BaseColor.RED);
        img.setAbsolutePosition(165, 10);
        //Agrego la imagen al documento
        //document.add(img);
        
        img2 = codeEAN.createImageWithBarcode(writer.getDirectContent(), BaseColor.BLACK, BaseColor.RED);
        img2.setAbsolutePosition(10, 55);
        //Agrego la imagen al documento
       // document.add(img2);

        document.close();
    }
}
