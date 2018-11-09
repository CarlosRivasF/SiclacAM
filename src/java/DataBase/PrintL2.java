package DataBase;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import jbarcodebean.JBarcodeBean;
import net.sourceforge.jbarcodebean.model.Code39;

@WebServlet(name = "PrintL2", urlPatterns = {"/PrintL2"})
public class PrintL2 extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            response.setContentType("application/pdf");
            response.setHeader("Content-disposition", "inline; filename=\"" + 1 + ".pdf\"");
            String cadena = request.getParameter("idPrac");
            String relativePath = getServletContext().getRealPath("/");
            String path = relativePath + "M/templ.pdf";//Am_LabsWM

            String ca = "Formato-" + cadena + "";
            response.setHeader("Content-disposition", "inline; filename=\"filenameLabel.pdf\"");
            //PROPIEDADES INICIO
            PdfReader reader = new PdfReader(path);

            PdfStamper stamper = new PdfStamper(reader, response.getOutputStream());
            PdfContentByte canvas = stamper.getOverContent(1);

            BaseColor orange = new BaseColor(211, 84, 0);
            BaseColor blue = new BaseColor(52, 152, 219);
            BaseColor green = new BaseColor(40, 180, 99);
            BaseColor BackGr = new BaseColor(234, 236, 238);
            Font Title_Font_Est = FontFactory.getFont("Times Roman", 12, blue);
            Font Title_Font_Prec = FontFactory.getFont("Times Roman", 12, orange);
            Font Title_Font_Prep = FontFactory.getFont("Times Roman", 12, green);
            Font Content_Font = FontFactory.getFont("Arial", 8, BaseColor.BLACK);
            Font Content_Font2 = FontFactory.getFont("Arial", 9, BaseColor.BLACK);
            ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, new Phrase("Carlos Francisco Rivas Futero", Content_Font), 15, 65, 0);
            ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, new Phrase("22 años", Content_Font), 131, 65, 0);
            ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, new Phrase("Masculino", Content_Font), 167, 65, 0);
            ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, new Phrase("ESTUDIO REALIZADO", Content_Font2), 40, 50, 0);
            ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, new Phrase("Fecha: 12-12-2018", Content_Font), 15, 36, 0);
            ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, new Phrase("Mat utilizado", Content_Font), 15, 25, 0);
            ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, new Phrase("Datos adicionales en etiqueta", Content_Font), 15, 13, 0);
            Image barras1;
            JBarcodeBean barcode = new JBarcodeBean();
            barcode.setCodeType(new Code39());
            barcode.setCode(1 + "-2");
            barcode.setCheckDigit(true);
            barcode.setShowText(true);
            BufferedImage bi = barcode.draw(new BufferedImage(100, 15, BufferedImage.TYPE_INT_RGB));
            barras1 = Image.getInstance(Toolkit.getDefaultToolkit().createImage(bi.getSource()), null);

            barras1.setAbsolutePosition(110, 25);
            canvas.addImage(barras1, false);
            ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, new Phrase("(1-2)", Content_Font), 160, 13, 0);
            stamper.close();
        } catch (DocumentException ex) {
            Logger.getLogger(PrintL2.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
