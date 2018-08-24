package Servlets.Cotizacion;

import DataAccesObject.Cotizacion_DAO;
import DataTransferObject.Cotizacion_DTO;
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
@WebServlet(name = "PrintCot", urlPatterns = {"/PrintCot"})
public class PrintCot extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        try {
            HttpSession sesion = request.getSession();            
            Cotizacion_DTO Cot = (Cotizacion_DTO) sesion.getAttribute("Cotizacion");
            sesion.removeAttribute("Cotizacion");
            Cotizacion_DAO CD = new Cotizacion_DAO();            
            Cot.setId_Cotizacion(CD.RegistrarCotizacion(Cot));
            String CodeCot = Cot.getPaciente().getCodPac().substring(0, 4) + "-" + Cot.getId_Cotizacion();
            response.setContentType("application/pdf");
            response.setHeader("Content-disposition", "inline; filename=\"Cot_" + CodeCot + ".pdf\"");
            String relativePath = getServletContext().getRealPath("/");
            String Source = relativePath + "M/MembrOrd.pdf";
            Image barras1;
            JBarcodeBean barcode = new JBarcodeBean();
            barcode.setCodeType(new Code39());
            barcode.setCode(CodeCot);
            barcode.setCheckDigit(true);
            barcode.setShowText(false);
            BufferedImage bi = barcode.draw(new BufferedImage(155, 20, BufferedImage.TYPE_INT_RGB));
            barras1 = Image.getInstance(Toolkit.getDefaultToolkit().createImage(bi.getSource()), null);
            PdfReader reader = new PdfReader(Source);
            Rectangle pagesize = reader.getPageSize(1);
            PdfStamper stamper = new PdfStamper(reader, response.getOutputStream());

            PdfContentByte cb = stamper.getUnderContent(1);

            BaseFont bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
            BaseFont bf0 = BaseFont.createFont(BaseFont.TIMES_BOLD, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
            BaseFont bf1 = BaseFont.createFont(BaseFont.COURIER, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
            ////////////////////////// DATOS UNIDAD
            cb.beginText();
            cb.setFontAndSize(bf0, 10);
            cb.setTextMatrix(330, 760);
            cb.showText("UNIDAD " + Cot.getUnidad().getNombre_Unidad().toUpperCase());
            cb.endText();
            barras1.setAbsolutePosition(350, 732);//x,y
            ////////////////////////// DATOS PACIENTE
            cb.beginText();
            cb.setFontAndSize(bf, 10);
            cb.setTextMatrix(142, 697);
            cb.showText("Paciente:");
            cb.endText();
            cb.beginText();
            cb.setFontAndSize(bf1, 10);
            cb.setTextMatrix(187, 697);
            cb.showText(Cot.getPaciente().getNombre() + " " + Cot.getPaciente().getAp_Paterno() + " " + Cot.getPaciente().getAp_Materno());
            cb.endText();
            cb.beginText();
            cb.setFontAndSize(bf, 10);
            cb.setTextMatrix(395, 697);
            cb.showText("Fecha de Emisión:");
            cb.endText();
            cb.beginText();
            cb.setFontAndSize(bf1, 10);
            cb.setTextMatrix(485, 697);
            cb.showText(Cot.getFecha_Cot());
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
            cb.showText(Cot.getMedico().getNombre() + " " + Cot.getMedico().getAp_Paterno() + " " + Cot.getMedico().getAp_Materno());
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
            cb.showText(Cot.getFecha_Exp());
            cb.endText();
            ///////////////////////despedida
            cb.beginText();
            cb.setFontAndSize(bf, 10);
            cb.setTextMatrix(36, 410);
            cb.showText("Dirección: " + Cot.getUnidad().getEncargado().getCalle() + " No Ext." + Cot.getUnidad().getEncargado().getNo_Ext() + ","
                    + " Col. " + Cot.getUnidad().getEncargado().getNombre_Colonia() + ", " + Cot.getUnidad().getEncargado().getNombre_Municipio() + "     Tel 1.:" + Cot.getUnidad().getEncargado().getTelefono1() + "     Mail.:" + Cot.getUnidad().getEncargado().getMail() + "");
            cb.endText();

            cb.addImage(barras1, false);

            PdfPTable table = new PdfPTable(2);
            table.addCell("Nombre de Estudio");
            table.addCell("Precio");
            table.setHeaderRows(1);
            table.setWidths(new int[]{7, 3});
            Cot.getDet_Cot().stream().map((dto) -> {
                table.addCell(dto.getEstudio().getNombre_Estudio());
                return dto;
            }).forEachOrdered((dto) -> {
                table.addCell(dto.getSubtotal().toString());
            });

            ColumnText column = new ColumnText(stamper.getOverContent(1));
            Rectangle rectPage1 = new Rectangle(108, 40, 617, 663);//0,esp-inf,ancho,alto
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

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        try {
            processRequest(request, response);
        } catch (IOException ex) {
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
