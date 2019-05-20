package Servlets.Orden;

import DataAccesObject.Orden_DAO;
import DataAccesObject.Participacion_DAO;
import DataBase.Util;
import DataTransferObject.Orden_DTO;
import DataTransferObject.Participacion_DTO;
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
        HttpSession sesion = request.getSession();
        request.setCharacterEncoding("utf-8");

        Orden_DAO O = new Orden_DAO();
        Boolean r = true;
        if (request.getParameter("LsIxOrd") != null) {
            int Id_Orden = Integer.parseInt(request.getParameter("LsIxOrd").trim());
            sesion.setAttribute("Orden", O.getOrden(Id_Orden));
            r = false;
        }
        Orden_DTO Orden = (Orden_DTO) sesion.getAttribute("Orden");

        if (Orden.getMedico() == null) {
            String msg = "No es posible generar una Órden si no le asigna un Médico. Por favor vuelva a intentar ";
            sesion.setAttribute("msg", msg);
            response.sendRedirect("Ordenes.jsp");
        } else {
            if (r) {
                Date fac = new Date();
                Util f = new Util();
                f.setHora(fac);
                Orden.setFecha(f.getFechaActual());
                Orden.setHora(f.getHoraMas(Util.getHrBD()));
                Orden.setEstado("Pendiente");
                Orden.setFolio_Unidad(O.getNoOrdenByUnidad(Orden.getUnidad().getId_Unidad()) + 1);
                Orden.setId_Orden(O.registrarOrden(Orden));
                Participacion_DTO participacion = new Participacion_DTO();
                participacion.setOrden(Orden);
                participacion.setId_Unidad(Orden.getUnidad().getId_Unidad());
                participacion.setMedico(Orden.getMedico());
                participacion.setFecha(Orden.getFecha());
                participacion.setHora(Orden.getHora());
                participacion.setConvenio(Orden.getConvenio());
                Float p = Orden.getMontoPagado() + Orden.getMontoRestante();
                Float mp = ((Orden.getMedico().getParticipacion() * p) / 100);
                participacion.setMonto(mp);
                Participacion_DAO P = new Participacion_DAO();
                P.registrarParticipacion(participacion);
            }
            sesion.removeAttribute("Orden");
            try {
                String CodeOrd = Orden.getId_Orden() + "";
                response.setContentType("application/pdf");
                response.setHeader("Content-disposition", "inline; filename=\"Órd_" + CodeOrd + ".pdf\"");
                String relativePath = getServletContext().getRealPath("/") + "/";//ruta real del proyecto

                String Source = relativePath + "M/MembrOrden.pdf";
                Image barras1;
                JBarcodeBean barcode = new JBarcodeBean();
                barcode.setCodeType(new Code39());
                barcode.setCode(CodeOrd);
                barcode.setCheckDigit(true);
                barcode.setShowText(true);
                BufferedImage bi = barcode.draw(new BufferedImage(155, 20, BufferedImage.TYPE_INT_RGB));
                barras1 = Image.getInstance(Toolkit.getDefaultToolkit().createImage(bi.getSource()), null);
                PdfReader reader = new PdfReader(Source);
                Rectangle pagesize = reader.getPageSize(1);
                PdfStamper stamper = new PdfStamper(reader, response.getOutputStream());
                PdfContentByte cb = stamper.getOverContent(1);

                BaseFont bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
                BaseFont bf0 = BaseFont.createFont(BaseFont.TIMES_BOLD, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
                BaseFont bf1 = BaseFont.createFont(BaseFont.COURIER, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
                ////////////////////////// DATOS UNIDAD
                cb.beginText();
                cb.setFontAndSize(bf0, 10);
                cb.setTextMatrix(290, 760);
                cb.showText("UNIDAD " + Orden.getUnidad().getNombre_Unidad().toUpperCase());
                cb.endText();
                cb.beginText();

                cb.setFontAndSize(bf0, 10);
                cb.setTextMatrix(443, 740);
                cb.showText("Folio de Unidad: " + Orden.getFolio_Unidad());
                cb.endText();

                barras1.setAbsolutePosition(260, 732);//x,y
                cb.beginText();
                cb.setFontAndSize(bf1, 9);
                cb.setTextMatrix(310, 722);
                cb.showText(CodeOrd);
                cb.endText();
                ////////////////////////// DATOS PACIENTE
                cb.beginText();
                cb.setFontAndSize(bf, 10);
                cb.setTextMatrix(142, 697);
                cb.showText("Paciente:");
                cb.endText();
                cb.beginText();
                cb.setFontAndSize(bf1, 10);
                cb.setTextMatrix(187, 697);
                cb.showText(Orden.getPaciente().getNombre() + " " + Orden.getPaciente().getAp_Paterno() + " " + Orden.getPaciente().getAp_Materno());
                cb.endText();
                cb.beginText();
                cb.setFontAndSize(bf, 10);
                cb.setTextMatrix(395, 697);
                cb.showText("Fecha de Órden:");
                cb.endText();
                cb.beginText();
                cb.setFontAndSize(bf1, 10);
                cb.setTextMatrix(485, 697);
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
                cb.showText("Atendió:");
                cb.endText();
                cb.beginText();
                cb.setFontAndSize(bf1, 10);
                cb.setTextMatrix(455, 678);
                cb.showText(Orden.getEmpleado().getNombre() + " " + Orden.getEmpleado().getAp_Paterno());
                cb.endText();
                ///////////////////////despedida
                cb.beginText();
                cb.setFontAndSize(bf, 10);
                cb.setTextMatrix(36, 410);
                cb.showText("Dirección: " + Orden.getUnidad().getEncargado().getCalle() + " No Ext." + Orden.getUnidad().getEncargado().getNo_Ext() + ","
                        + " Col. " + Orden.getUnidad().getEncargado().getNombre_Colonia() + ", " + Orden.getUnidad().getEncargado().getNombre_Municipio() + "     Tel 1.:" + Orden.getUnidad().getEncargado().getTelefono1() + "     Mail.:" + Orden.getUnidad().getEncargado().getMail() + "");
                cb.endText();
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

                cb.beginText();
                cb.setFontAndSize(bf, 12);
                cb.setTextMatrix(452, 567);
                cb.showText("A/C:");
                cb.endText();
                cb.beginText();
                cb.setFontAndSize(bf, 12);
                cb.setTextMatrix(500, 567);
                cb.showText(Util.redondearDecimales(Orden.getMontoPagado()) + "");
                cb.endText();

                cb.beginText();
                cb.setFontAndSize(bf, 12);
                cb.setTextMatrix(452, 553);
                cb.showText("Saldo: ");
                cb.endText();
                cb.beginText();
                cb.setFontAndSize(bf, 12);
                cb.setTextMatrix(500, 553);
                cb.showText(Util.redondearDecimales(Orden.getMontoRestante()) + "");
                cb.endText();

                cb.beginText();
                cb.setFontAndSize(bf, 12);
                cb.setTextMatrix(452, 539);
                cb.showText("TOTAL: ");
                cb.endText();
                cb.beginText();
                cb.setFontAndSize(bf, 12);
                cb.setTextMatrix(500, 539);
                cb.showText(Util.redondearDecimales(Orden.getMontoPagado() + Orden.getMontoRestante()) + "");
                cb.endText();

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
                response.setContentType("text/html;charset=UTF-8");
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
