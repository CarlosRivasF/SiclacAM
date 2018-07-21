package Servlets.Orden;

import DataAccesObject.Orden_DAO;
import DataBase.Fecha;
import DataTransferObject.Orden_DTO;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
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
 * @author ZionSystems
 */
@WebServlet(name = "FinalOrd", urlPatterns = {"/FinalOrd"})
public class FinalOrd extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        HttpSession sesion = request.getSession();
        Date fac = new Date();
        Fecha f = new Fecha();
        f.setHora(fac);
        System.out.println("SESION RECUPERADA");
        Orden_DAO O = new Orden_DAO();
        Orden_DTO Orden = (Orden_DTO) sesion.getAttribute("Orden");
        System.out.println("ÓRDEN RECUPERADA");
        Orden.setFecha(f.getFechaActual());
        Orden.setHora(f.getHoraActual());
        Orden.setEstado("Pendiente");      
        Orden.setFolio_Unidad(O.getNoOrdenByUnidad(Orden.getUnidad().getId_Unidad())+1);
        Orden.setId_Orden(O.registrarOrden(Orden));
        try {
            String CodeCot = Orden.getPaciente().getCodPac().substring(0, 4) + "-" + Orden.getId_Orden();
            System.out.println("Órden: " + CodeCot);
            response.setContentType("application/pdf");
            response.setHeader("Content-disposition", "inline; filename=\"Cot_" + CodeCot + ".pdf\"");
            String relativePath = getServletContext().getRealPath("/");
            String Source = relativePath + "M/MembrOrden.pdf";
            Image barras1;
            JBarcodeBean barcode = new JBarcodeBean();
            System.out.println("******************JBarcodeBean*******************");
            barcode.setCodeType(new Code39());
            barcode.setCode(CodeCot);
            barcode.setCheckDigit(true);
            barcode.setShowText(false);
            BufferedImage bi = barcode.draw(new BufferedImage(155, 20, BufferedImage.TYPE_INT_RGB));
            barras1 = Image.getInstance(Toolkit.getDefaultToolkit().createImage(bi.getSource()), null);
            PdfReader reader = new PdfReader(Source);
            Rectangle pagesize = reader.getPageSize(1);
            PdfStamper stamper = new PdfStamper(reader, response.getOutputStream());
            System.out.println("******************PdfStamper*******************");
            PdfContentByte cb = stamper.getUnderContent(1);

            BaseFont bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
            BaseFont bf0 = BaseFont.createFont(BaseFont.TIMES_BOLD, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
            BaseFont bf1 = BaseFont.createFont(BaseFont.COURIER, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
            ////////////////////////// DATOS UNIDAD
            cb.beginText();
            cb.setFontAndSize(bf0, 10);
            cb.setTextMatrix(330, 760);
            System.out.println("UNIDAD " + Orden.getUnidad().getNombre_Unidad().toUpperCase());
            cb.showText("UNIDAD " + Orden.getUnidad().getNombre_Unidad().toUpperCase());
            cb.endText();
            barras1.setAbsolutePosition(300, 732);//x,y
            ////////////////////////// DATOS PACIENTE
            cb.beginText();
            cb.setFontAndSize(bf, 10);
            cb.setTextMatrix(142, 697);
            cb.showText("Paciente:");
            cb.endText();
            cb.beginText();
            cb.setFontAndSize(bf1, 10);
            cb.setTextMatrix(187, 697);
            System.out.println(Orden.getPaciente().getNombre() + " " + Orden.getPaciente().getAp_Paterno() + " " + Orden.getPaciente().getAp_Materno());
            cb.showText(Orden.getPaciente().getNombre() + " " + Orden.getPaciente().getAp_Paterno() + " " + Orden.getPaciente().getAp_Materno());
            cb.endText();
            cb.beginText();
            cb.setFontAndSize(bf, 10);
            cb.setTextMatrix(395, 697);
            cb.showText("Fecha de Emisión:");
            cb.endText();
            cb.beginText();
            cb.setFontAndSize(bf1, 10);
            cb.setTextMatrix(485, 697);
            System.out.println(Orden.getFecha());
            cb.showText(Orden.getFecha());
            cb.endText();
            ////////////////////////// DATOS DOCTOR
            cb.beginText();
            cb.setFontAndSize(bf, 10);
            cb.setTextMatrix(142, 678);
            cb.showText("Doctor:");
            cb.endText();
            cb.beginText();
            cb.setFontAndSize(bf1, 10);
            cb.setTextMatrix(180, 678);
            cb.showText(Orden.getMedico().getNombre() + " " + Orden.getMedico().getAp_Paterno() + " " + Orden.getMedico().getAp_Materno());
            cb.endText();
            ///////////////////  DAATOS ORDEN
            cb.beginText();
            cb.setFontAndSize(bf, 10);
            cb.setTextMatrix(395, 678);
            cb.showText("Fecha que Expira:");
            cb.endText();
            cb.beginText();
            cb.setFontAndSize(bf1, 10);
            cb.setTextMatrix(485, 678);
            cb.showText("fecha orden");
            cb.endText();
            ///////////////////////despedida
            cb.beginText();
            cb.setFontAndSize(bf, 10);
            cb.setTextMatrix(36, 410);
            cb.showText("Dirección: " + Orden.getUnidad().getEncargado().getCalle() + " No Ext." + Orden.getUnidad().getEncargado().getNo_Ext() + ","
                    + " Col. " + Orden.getUnidad().getEncargado().getNombre_Colonia() + ", " + Orden.getUnidad().getEncargado().getNombre_Municipio() + "     Tel 1.:" + Orden.getUnidad().getEncargado().getTelefono1() + "     Mail.:" + Orden.getUnidad().getEncargado().getMail() + "");
            cb.endText();
System.out.println("Direccion");
            cb.addImage(barras1, false);

            PdfPTable table = new PdfPTable(2);
            table.addCell("Nombre de Estudio");
            table.addCell("Precio");  
            table.setHeaderRows(1);
            table.setWidths(new int[]{7, 3});
            Orden.getDet_Orden().stream().map((dto) -> {
                table.addCell(dto.getEstudio().getNombre_Estudio());
                return dto;
            }).forEachOrdered((dto) -> {
                table.addCell(dto.getSubtotal().toString());
            });
System.out.println("tabla de estudios");
            ColumnText column = new ColumnText(stamper.getOverContent(1));
            Rectangle rectPage1 = new Rectangle(-10, 40, 480, 663);//izq,esp-inf,ancho,alto
            column.setSimpleColumn(rectPage1);
            column.addElement(table);

            int pagecount = 1;
            Rectangle rectPage2 = new Rectangle(0, 40, 617, 650);
            int status = column.go();
            while (ColumnText.hasMoreText(status)) {
                status = triggerNewPage(reader, stamper, pagesize, column, rectPage2, ++pagecount);
            }
            stamper.setFormFlattening(true);
            stamper.close();
            reader.close();
        } catch (IOException | DocumentException ex) {
            try (PrintWriter out = response.getWriter()) {
                out.println("<!DOCTYPE html>");
                out.println("<html>");
                out.println("<head>");
                out.println("<title>Servlet NewServlet3</title>");
                out.println("</head>");
                out.println("<body>");
                out.println("<h1>Exception at " + ex.getMessage() + "</h1>");
                out.println("</body>");
                out.println("</html>");
            }
        }
    }

    public int triggerNewPage(PdfReader reader, PdfStamper stamper, Rectangle pagesize, ColumnText column, Rectangle rect, int pagecount) throws DocumentException {
        PdfContentByte canvas = stamper.getOverContent(pagecount);
        column.setCanvas(canvas);
        column.setSimpleColumn(rect);
        return column.go();
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
