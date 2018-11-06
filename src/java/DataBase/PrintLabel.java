package DataBase;

import DataAccesObject.Orden_DAO;
import DataTransferObject.Orden_DTO;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.Barcode;
import com.itextpdf.text.pdf.Barcode128;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfName;
import com.itextpdf.text.pdf.PdfPageLabels;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import jbarcodebean.JBarcodeBean;
import net.sourceforge.jbarcodebean.model.Code39;

/**
 *
 * @author carlos
 */
@WebServlet(name = "PrintLabel", urlPatterns = {"/PrintLabel"})
public class PrintLabel extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            response.setContentType("application/pdf");
            response.setHeader("Content-disposition", "inline; filename=\"" + 1 + ".pdf\"");
            Document document = new Document();
            PdfWriter writer = PdfWriter.getInstance(document, response.getOutputStream());
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
            document.add(img);
            img2 = codeEAN.createImageWithBarcode(writer.getDirectContent(), BaseColor.BLACK, BaseColor.RED);
            img2.setAbsolutePosition(10, 55);
            //Agrego la imagen al documento
             document.add(img2);
            document.close();
        } catch (DocumentException ex) {
            Logger.getLogger(PrintLabel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
 
}
