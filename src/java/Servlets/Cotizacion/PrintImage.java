package Servlets.Cotizacion;

import com.itextpdf.text.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import jbarcodebean.JBarcodeBean;
import net.sourceforge.jbarcodebean.model.Code39;

/**
 *
 * @author ZionSystems
 */
@WebServlet(name = "NewServlet", urlPatterns = {"/NewServlet"})
public class PrintImage extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("image/png");
        Image barras1 = null;
        try {
            String name = "Serv";
            String path = request.getContextPath();
            String r = path + "/PDF/" + name + ".png";
            JBarcodeBean barcode = new JBarcodeBean();
            barcode.setCodeType(new Code39());
            barcode.setCode("11-KSY");
            barcode.setCheckDigit(true);
            barcode.setShowText(false);
            BufferedImage bufferedImage = barcode.draw(new BufferedImage(150, 16, BufferedImage.TYPE_INT_RGB));
            File file = new File(r);
            try (OutputStream out = response.getOutputStream()) {
                ImageIO.write(bufferedImage, "png", out);   
            }
        } catch (IOException e) {

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
