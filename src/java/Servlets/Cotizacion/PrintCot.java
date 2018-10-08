package Servlets.Cotizacion;

import DataAccesObject.Cotizacion_DAO;
import DataTransferObject.Cotizacion_DTO;
import DataTransferObject.Det_Cot_DTO;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
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
            String CodeCot = Cot.getId_Cotizacion() + "-";
            response.setContentType("application/pdf");
            response.setHeader("Content-disposition", "inline; filename=\"Cot_" + Cot.getPaciente().getCodPac().substring(0, 4) + "-" + Cot.getId_Cotizacion() + ".pdf\"");
            String relativePath = getServletContext().getRealPath("/");

            String Source = relativePath + "M/MembreteRes.pdf";
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
            cb.setTextMatrix(290, 760);
            cb.showText("UNIDAD " + Cot.getUnidad().getNombre_Unidad().toUpperCase());
            cb.endText();
            barras1.setAbsolutePosition(260, 732);//x,y
            cb.beginText();
            cb.setFontAndSize(bf1, 9);
            cb.setTextMatrix(310, 722);
            cb.showText(CodeCot);
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

            cb.addImage(barras1, false);

            BaseColor orange = new BaseColor(211, 84, 0);
            BaseColor blue = new BaseColor(52, 152, 219);
            BaseColor green = new BaseColor(40, 180, 99);
            BaseColor BackGr = new BaseColor(234, 236, 238);

            Font Title_Font_Est = FontFactory.getFont("Times Roman", 12, blue);
            Font Title_Font_Prec = FontFactory.getFont("Times Roman", 12, orange);
            Font Title_Font_Prep = FontFactory.getFont("Times Roman", 12, green);
            Font Content_Font = FontFactory.getFont("Arial", 11, BaseColor.BLACK);

            int c = 745;//Coordenada variable Y de la ubicación de la tabla
            for (Det_Cot_DTO dto : Cot.getDet_Cot()) {
                PdfPTable table = new PdfPTable(2);

                PdfPCell cell_Est_Title = new PdfPCell(new Paragraph("Nombre de Estudio", Title_Font_Est));
                cell_Est_Title.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell_Est_Title.setBackgroundColor(BackGr);
                table.addCell(cell_Est_Title);

                PdfPCell cell_Prec_Title = new PdfPCell(new Paragraph("Precio", Title_Font_Prec));
                cell_Prec_Title.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell_Prec_Title);

                table.setHeaderRows(1);
                table.setWidths(new int[]{7, 3});

                PdfPCell cell_Estudio = new PdfPCell(new Paragraph(dto.getEstudio().getNombre_Estudio(), Content_Font));
                table.addCell(cell_Estudio);

                PdfPCell cell_Precio = new PdfPCell(new Paragraph(dto.getSubtotal().toString(), Content_Font));
                table.addCell(cell_Precio);

                PdfPCell cell_Prec_Prep = new PdfPCell(new Paragraph("Modo de Preparación", Title_Font_Prep));
                cell_Prec_Prep.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell_Prec_Prep.setColspan(2);
                table.addCell(cell_Prec_Prep);

                PdfPCell prepar = new PdfPCell(new Paragraph(dto.getEstudio().getPreparacion(), Content_Font));
                prepar.setColspan(2);
                table.addCell(prepar);

                ColumnText column = new ColumnText(stamper.getOverContent(1));
                Rectangle rectPage1 = new Rectangle(-10, 40, 625, c = c - 85);//izq,esp-inf,ancho,alto
                column.setSimpleColumn(rectPage1);
                column.addElement(table);
                column.go();
            }

            cb.beginText();
            cb.setFontAndSize(bf, 12);
            cb.setTextMatrix(452, c - 90);
            cb.showText("TOTAL: ");
            cb.endText();
            cb.beginText();
            cb.setFontAndSize(bf, 12);
            cb.setTextMatrix(500, c = (c - 90));
            cb.showText(Cot.getTotal() + "");
            cb.endText();
            
            cb.beginText();
            cb.setFontAndSize(bf, 10);
            cb.setTextMatrix(23,65);
            cb.showText("Dirección: " + Cot.getUnidad().getEncargado().getCalle() + " No Ext." + Cot.getUnidad().getEncargado().getNo_Ext() + ","
                    + " Col. " + Cot.getUnidad().getEncargado().getNombre_Colonia() + ", " + Cot.getUnidad().getEncargado().getNombre_Municipio() + "     Tel 1.:" + Cot.getUnidad().getEncargado().getTelefono1() + "     Mail.:" + Cot.getUnidad().getEncargado().getMail() + "");
            cb.endText();

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
