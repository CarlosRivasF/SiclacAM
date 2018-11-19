/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets.Promocion;

import DataBase.PrintL2;
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
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import jbarcodebean.JBarcodeBean;
import net.sourceforge.jbarcodebean.model.Code39;

/**
 *
 * @author KODE
 */
@WebServlet(name = "CodePromocion", urlPatterns = {"/CodePromocion"})
public class CodePromocion extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
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
            Font Content_Font2 = FontFactory.getFont("Arial", 7, BaseColor.BLACK);
            Image barras1;
            JBarcodeBean barcode = new JBarcodeBean();
            barcode.setCodeType(new Code39());
            barcode.setCode(pars[1] + "-2-");
            barcode.setCheckDigit(true);
            barcode.setShowText(false);
            BufferedImage bi = barcode.draw(new BufferedImage(100, 20, BufferedImage.TYPE_INT_RGB));
            barras1 = Image.getInstance(Toolkit.getDefaultToolkit().createImage(bi.getSource()), null);
            barras1.setAbsolutePosition(72, 20);//x,y
            canvas.addImage(barras1, false);
            ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, new Phrase("Nombre de la Promoción:", Content_Font), 73, 70, 0);
            ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, new Phrase(pars[2], Content_Font), 15, 53, 0);
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