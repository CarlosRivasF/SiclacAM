package DataBase;

import DataAccesObject.Orden_DAO;
import DataTransferObject.Orden_DTO;
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
import java.time.Period;
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
import org.apache.tomcat.jni.Local;

@WebServlet(name = "PrintL2", urlPatterns = {"/PrintL2"})
public class PrintL2 extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("ProcessRequest");
        HttpSession sesion = request.getSession();
        if (sesion.getAttribute("permisos") != null && sesion.getAttribute("user") != null && sesion.getAttribute("unidad") != null) {
            Date fac = new Date();
            Util f = new Util();
            f.setHora(fac);

            int id_Orden;
            if (sesion.getAttribute("OrdenSh") == null) {
                id_Orden = Integer.parseInt(request.getParameter("LxOrdSald").trim());
            } else {
                id_Orden = 0;
            }
            Orden_DTO Orden;
            try {
                if (sesion.getAttribute("OrdenSh") != null) {
                    Orden = (Orden_DTO) sesion.getAttribute("OrdenSh");
                    sesion.removeAttribute("OrdenSh");
                } else {
                    Orden_DAO O = new Orden_DAO();
                    Orden = O.getOrden(id_Orden);
                }
                int IxDtOrd;
                int IxDtOrdMt;
                if (request.getParameter("IxDtOrd") == null || request.getParameter("IxDtOrdMt") == null) {
                    IxDtOrd = 0;
                    IxDtOrdMt = 0;
                } else {
                    IxDtOrd = Integer.parseInt(request.getParameter("IxDtOrd"));
                    IxDtOrdMt = Integer.parseInt(request.getParameter("IxDtOrdMt"));
                }

                response.setContentType("application/pdf");
                response.setHeader("Content-disposition", "inline; filename=\"" + Orden.getPaciente().getCodPac() + "_" + IxDtOrd + "." + IxDtOrdMt + ".pdf\"");
                String cadena = request.getParameter("idPrac");
                String relativePath = getServletContext().getRealPath("/") + "/";//ruta real del proyecto
                String path = relativePath + "M/templ.pdf";//Am_LabsWM
                   
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

                Period edad = f.getEdad(Orden.getPaciente().getFecha_Nac().trim());
                ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, new Phrase(Orden.getPaciente().getNombre().toLowerCase() + " " + Orden.getPaciente().getAp_Paterno().toLowerCase() + " " + Orden.getPaciente().getAp_Materno().toLowerCase(), Content_Font), 12, 65, 0);
                ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, new Phrase(edad.getYears() + " años", Content_Font), 150, 65, 0);
                ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, new Phrase(Orden.getPaciente().getSexo(), Content_Font), 185, 65, 0);
                ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, new Phrase(Orden.getDet_Orden().get(IxDtOrd).getEstudio().getNombre_Estudio(), Content_Font2), 30, 50, 0);
                ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, new Phrase(Orden.getFecha(), Content_Font), 15, 36, 0);
                ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, new Phrase(Orden.getDet_Orden().get(IxDtOrd).getEstudio().getMts().get(IxDtOrdMt).getClave(), Content_Font), 15, 25, 0);
                ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, new Phrase("Datos adicionales en etiqueta", Content_Font), 15, 13, 0);
                Image barras1;
                JBarcodeBean barcode = new JBarcodeBean();
                barcode.setCodeType(new Code39());
                barcode.setCode(Orden.getFolio_Unidad() + "-"+Orden.getUnidad().getId_Unidad());
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
        } else {
            response.sendRedirect("" + request.getContextPath() + "");
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
