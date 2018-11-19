package Servlets.Estudio;

import DataBase.PrintL2;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.Barcode;
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
import net.sourceforge.jbarcodebean.model.Interleaved25;

/**
 *
 * @author KODE
 */
@WebServlet(name = "PrintLabelEstudio", urlPatterns = {"/PrintLabelEstudio"})
public class PrintCode extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String par = request.getParameter("CodeEst");
            String[] pars = par.split("-");
            response.setContentType("application/pdf");
            response.setHeader("Content-disposition", "inline; filename=\"" + 1 + ".pdf\"");
            String cadena = request.getParameter("idPrac");
            String relativePath = getServletContext().getRealPath("/");
            String path = relativePath + "M/templ0.pdf";

            String ca = "Est-" + pars[0] + "";
            response.setHeader("Content-disposition", "inline; filename=\"" + ca + ".pdf\"");
            //PROPIEDADES INICIO
            PdfReader reader = new PdfReader(path);

            PdfStamper stamper = new PdfStamper(reader, response.getOutputStream());
            PdfContentByte canvas = stamper.getOverContent(1);

            BaseColor orange = new BaseColor(211, 84, 0);
            BaseColor blue = new BaseColor(52, 152, 219);
            BaseColor green = new BaseColor(40, 180, 99);
            Font Content_Font = FontFactory.getFont("Arial", 9, BaseColor.BLACK);
            Font Content_FontG = FontFactory.getFont("Arial", 15, BaseColor.BLACK);
            Font Content_Font2 = FontFactory.getFont("Arial", 8, BaseColor.BLACK);
            Font Content_Font3 = FontFactory.getFont("Arial", 7, BaseColor.BLACK);
            Image barras1;
            JBarcodeBean barcode = new JBarcodeBean();            
            //barcode.setCodeType(new Interleaved25());
            barcode.setCodeType(new Code39());
            barcode.setCode(pars[1]+"-");
            barcode.setCheckDigit(true);
            barcode.setShowText(true);
            BufferedImage bi = barcode.draw(new BufferedImage(140, 30, BufferedImage.TYPE_INT_RGB));
            barras1 = Image.getInstance(Toolkit.getDefaultToolkit().createImage(bi.getSource()), null);
            barras1.setAbsolutePosition(90, 18);//x,y
            canvas.addImage(barras1, false);
            ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, new Phrase("Nombre del Estudio:", Content_Font2), 73, 70, 0);
            ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, new Phrase("Clave del Estudio:", Content_Font3), 20, 34, 0);
            ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, new Phrase("("+pars[1]+")", Content_Font3), 150, 10, 0);
            ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, new Phrase("("+pars[0]+")", Content_Font3), 28, 20, 0);
            if(pars[2].length()<=30){
            ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, new Phrase(pars[2], Content_FontG), 15, 53, 0);
            }else{
            ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, new Phrase(pars[2], Content_Font), 15, 53, 0);
            }            
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
