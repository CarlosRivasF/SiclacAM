package Servlets.Cotizacion;

import DataAccesObject.Orden_DAO;
import DataBase.Fecha;
import DataTransferObject.Det_Orden_DTO;
import DataTransferObject.Orden_DTO;
import com.itextpdf.text.BadElementException;
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
 * @author Carlos Rivas
 */
@WebServlet(name = "PrintTest", urlPatterns = {"/PrintTest"})
public class NewServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) {
        Orden_DTO Orden = new Orden_DTO();
        Date fac = new Date();
        Fecha f = new Fecha();
        f.setHora(fac);

        try {
            response.setContentType("application/pdf");
            response.setHeader("Content-disposition", "inline; filename=\"report" + 1 + ".pdf\"");
            String relativePath = getServletContext().getRealPath("/")+"/";//ruta real del proyecto
            int r = 50;
            String Source = "";
            if (r < 35) {
                Source = relativePath + "M/MembreteRes1.pdf";
            } else if (r > 35 & r < 70) {
                Source = relativePath + "M/MembreteRes2.pdf";
            } else if (r > 70 & r < 105) {
                Source = relativePath + "M/MembreteRes3.pdf";
            } else if (r > 140 & r < 175) {
                Source = relativePath + "M/MembreteRes4.pdf";
            }

            int pagecount = 1;

            PdfReader reader = new PdfReader(Source);
            Rectangle pagesize = reader.getPageSize(1);
            PdfStamper stamper = new PdfStamper(reader, response.getOutputStream());

            PdfContentByte cb = stamper.getOverContent(1);

            Image barras1;
            JBarcodeBean barcode = new JBarcodeBean();
            barcode.setCodeType(new Code39());
            barcode.setCode("5-");
            barcode.setCheckDigit(true);
            barcode.setShowText(true);
            BufferedImage bi = barcode.draw(new BufferedImage(156, 12, BufferedImage.TYPE_INT_RGB));
            barras1 = Image.getInstance(Toolkit.getDefaultToolkit().createImage(bi.getSource()), null);

            BaseFont bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
            BaseFont bf0 = BaseFont.createFont(BaseFont.HELVETICA_BOLD, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
            BaseFont bf1 = BaseFont.createFont(BaseFont.COURIER, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);

            barras1.setAbsolutePosition(92, 705);//x,y

            /////////////////// *********** DATOS ORDEN ***********************************  ///////////////////            
            cb.beginText();
            cb.setFontAndSize(bf, 10);
            cb.setTextMatrix(270, 758);
            cb.showText("Fecha de Emisión:");
            cb.endText();
            cb.beginText();
            cb.setFontAndSize(bf1, 12);
            cb.setTextMatrix(355, 758);
            cb.showText(f.getFechaActual());
            cb.endText();
            ////////////////////////// DATOS PACIENTE
            cb.beginText();
            cb.setFontAndSize(bf, 10);
            cb.setTextMatrix(270, 740);
            cb.showText("Paciente:");
            cb.endText();
            cb.beginText();
            cb.setFontAndSize(bf1, 12);
            cb.setTextMatrix(320, 740);
            cb.showText("Jose Antonio Betancourt Ramos");
            //cb.showText(Orden.getPaciente().getNombre() + " " + Orden.getPaciente().getAp_Paterno() + " " + Orden.getPaciente().getAp_Materno());
            cb.endText();

            cb.beginText();
            cb.setFontAndSize(bf, 10);
            cb.setTextMatrix(270, 722);
            cb.showText("Edad:");
            cb.endText();
            cb.beginText();
            cb.setFontAndSize(bf1, 12);
            cb.setTextMatrix(305, 722);
            //cb.showText(f.getEdad(Orden.getPaciente().getFecha_Nac()).getYears() + "");
            cb.showText("15");
            cb.endText();
            cb.beginText();
            cb.setFontAndSize(bf, 10);
            cb.setTextMatrix(340, 722);
            cb.showText("Sexo:");
            cb.endText();
            cb.beginText();
            cb.setFontAndSize(bf1, 12);
            cb.setTextMatrix(380, 722);
            //cb.showText(Orden.getPaciente().getSexo());
            cb.showText("Masculino");
            cb.endText();
            ////////////////////////// DATOS DOCTOR
            cb.beginText();
            cb.setFontAndSize(bf, 10);
            cb.setTextMatrix(270, 707);
            cb.showText("Doctor:");
            cb.endText();
            cb.beginText();
            cb.setFontAndSize(bf1, 12);
            cb.setTextMatrix(315, 707);
            //cb.showText(Orden.getMedico().getNombre() + " " + Orden.getMedico().getAp_Paterno() + " " + Orden.getMedico().getAp_Materno());
            cb.showText("Juan Roberto Pablo Corcega Perez");
            cb.endText();
            ///////////////////////despedida

            cb.beginText();
            cb.setFontAndSize(bf0, 12);
            cb.setTextMatrix(280, 70);
            cb.showText("QFB. MARIA DE LOURDES GONZALEZ");
            cb.endText();

            cb.beginText();
            cb.setFontAndSize(bf0, 12);
            cb.setTextMatrix(450, 55);
            cb.showText("CED. PROF. 1204923");
            cb.endText();

            cb.addImage(barras1, false);

            /////*********************** RESULTADOS *************************/////////////////
            BaseColor orange = new BaseColor(211, 84, 0);
            BaseColor blue = new BaseColor(52, 152, 219);
            BaseColor green = new BaseColor(40, 180, 99);
            BaseColor BackGr = new BaseColor(234, 236, 238);

            Font Title_Font_Est = FontFactory.getFont("Times Roman", 12, blue);
            Font Title_Font_Prec = FontFactory.getFont("Times Roman", 12, orange);
            Font Title_Font_Prep = FontFactory.getFont("Times Roman", 12, green);
            Font Content_Font = FontFactory.getFont("Arial", 10, BaseColor.BLACK);

            PdfPTable table = new PdfPTable(4);
            table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
            table.getDefaultCell().setBorder(0);
            table.setWidths(new int[]{7, 3, 7, 3});
            int idx = 0;
            ColumnText column = new ColumnText(stamper.getOverContent(1));
            int c = 745;//Coordenada variable Y de la ubicación de la tabla
            for (int i = 0; i < 5; i++) {
                if (idx > 0) {
                    PdfPCell cell_Esp_Title = new PdfPCell(new Paragraph("", Title_Font_Est));
                    cell_Esp_Title.setColspan(4);
                    cell_Esp_Title.setBorder(0);
                    table.addCell(cell_Esp_Title);
                }
                idx++;
                PdfPCell cell_Est_Title = new PdfPCell(new Paragraph("Estudio Realizado", Title_Font_Est));
                cell_Est_Title.setHorizontalAlignment(Element.ALIGN_LEFT);
                cell_Est_Title.setColspan(2);
                cell_Est_Title.setBorder(0);
                cell_Est_Title.setBackgroundColor(BackGr);
                table.addCell(cell_Est_Title);

                PdfPCell cell_Met_Title = new PdfPCell(new Paragraph("Meodo: asdfghjkl", Title_Font_Est));
                cell_Met_Title.setHorizontalAlignment(Element.ALIGN_LEFT);
                cell_Met_Title.setColspan(2);
                cell_Met_Title.setBorder(0);
                cell_Met_Title.setBackgroundColor(BackGr);
                table.addCell(cell_Met_Title);

                PdfPCell des = new PdfPCell(new Paragraph("Descripcion"));
                des.setBorderColor(BaseColor.RED);
                table.addCell(des);
                PdfPCell res = new PdfPCell(new Paragraph("Resultado"));
                res.setBorderColor(BaseColor.RED);
                table.addCell(res);
                PdfPCell valR = new PdfPCell(new Paragraph("Valores de Referencia"));
                valR.setBorderColor(BaseColor.RED);
                table.addCell(valR);
                PdfPCell un = new PdfPCell(new Paragraph("Unidad"));
                un.setBorderColor(BaseColor.RED);
                table.addCell(un);
                ///******** RESULTADO DE CONFUGURACION ***********************////
                for (int l = 0; l < 50; i++) {

                    PdfPCell cell_Desc = new PdfPCell(new Paragraph("DESC", Content_Font));
                    cell_Desc.setHorizontalAlignment(Element.ALIGN_LEFT);
                    cell_Desc.setBorder(0);
                    table.addCell(cell_Desc);

                    PdfPCell cell_Res = new PdfPCell(new Paragraph("RES", Content_Font));
                    cell_Res.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cell_Res.setBorder(0);
                    table.addCell(cell_Res);

                    PdfPCell cell_Vals = new PdfPCell(new Paragraph("VALS", Content_Font));
                    cell_Vals.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cell_Vals.setBorder(0);
                    table.addCell(cell_Vals);

                    PdfPCell cell_Uns = new PdfPCell(new Paragraph("UNS", Content_Font));
                    cell_Uns.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cell_Uns.setBorder(0);
                    table.addCell(cell_Uns);
                }
                Rectangle rectPage1 = new Rectangle(-27, 120, 640, 690);//0,esp-inf,ancho,alto
                column.setSimpleColumn(rectPage1);
                column.addElement(table);
            }

            Rectangle rectPage2 = new Rectangle(-27, 40, 640, 690);//0,esp-inf,ancho,alto
            int status = column.go();
            while (ColumnText.hasMoreText(status)) {
                status = triggerNewPage(Orden, reader, stamper, pagesize, column, rectPage2, ++pagecount);
            }
            stamper.setFormFlattening(true);
            stamper.close();
            reader.close();
        } catch (DocumentException | IOException ex) {
            System.out.println("processRequest" + ex.getMessage());
        }
    }

    public int triggerNewPage(Orden_DTO Orden, PdfReader reader, PdfStamper stamper, Rectangle pagesize, ColumnText column, Rectangle rect, int pagecount) throws DocumentException {
        Date fac = new Date();
        Fecha f = new Fecha();
        f.setHora(fac);
        try {
            BaseFont bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
            BaseFont bf0 = BaseFont.createFont(BaseFont.HELVETICA_BOLD, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
            BaseFont bf1 = BaseFont.createFont(BaseFont.COURIER, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
            PdfContentByte cb = stamper.getOverContent(pagecount);
            Image barras1 = null;
            JBarcodeBean barcode = new JBarcodeBean();
            barcode.setCodeType(new Code39());
            barcode.setCode("5-");
            barcode.setCheckDigit(true);
            barcode.setShowText(true);
            BufferedImage bi = barcode.draw(new BufferedImage(156, 12, BufferedImage.TYPE_INT_RGB));
            barras1.setAbsolutePosition(92, 705);//x,y
            /////////////////// *********** DATOS ORDEN ***********************************  ///////////////////            
            cb.beginText();
            cb.setFontAndSize(bf, 10);
            cb.setTextMatrix(270, 758);
            cb.showText("Fecha de Emisión:");
            cb.endText();
            cb.beginText();
            cb.setFontAndSize(bf1, 12);
            cb.setTextMatrix(355, 758);
            cb.showText(f.getFechaActual());
            cb.endText();
            ////////////////////////// DATOS PACIENTE
            cb.beginText();
            cb.setFontAndSize(bf, 10);
            cb.setTextMatrix(270, 740);
            cb.showText("Paciente:");
            cb.endText();
            cb.beginText();
            cb.setFontAndSize(bf1, 12);
            cb.setTextMatrix(320, 740);
            cb.showText("Jose Antonio Betancourt Ramos");
            //cb.showText(Orden.getPaciente().getNombre() + " " + Orden.getPaciente().getAp_Paterno() + " " + Orden.getPaciente().getAp_Materno());
            cb.endText();

            cb.beginText();
            cb.setFontAndSize(bf, 10);
            cb.setTextMatrix(270, 722);
            cb.showText("Edad:");
            cb.endText();
            cb.beginText();
            cb.setFontAndSize(bf1, 12);
            cb.setTextMatrix(305, 722);
            //cb.showText(f.getEdad(Orden.getPaciente().getFecha_Nac()).getYears() + "");
            cb.showText("15");
            cb.endText();
            cb.beginText();
            cb.setFontAndSize(bf, 10);
            cb.setTextMatrix(340, 722);
            cb.showText("Sexo:");
            cb.endText();
            cb.beginText();
            cb.setFontAndSize(bf1, 12);
            cb.setTextMatrix(380, 722);
            //cb.showText(Orden.getPaciente().getSexo());
            cb.showText("Masculino");
            cb.endText();
            ////////////////////////// DATOS DOCTOR
            cb.beginText();
            cb.setFontAndSize(bf, 10);
            cb.setTextMatrix(270, 707);
            cb.showText("Doctor:");
            cb.endText();
            cb.beginText();
            cb.setFontAndSize(bf1, 12);
            cb.setTextMatrix(315, 707);
            //cb.showText(Orden.getMedico().getNombre() + " " + Orden.getMedico().getAp_Paterno() + " " + Orden.getMedico().getAp_Materno());
            cb.showText("Juan Roberto Pablo Corcega Perez");
            cb.endText();
            ///////////////////////despedida

            cb.beginText();
            cb.setFontAndSize(bf0, 12);
            cb.setTextMatrix(280, 70);
            cb.showText("QFB. MARIA DE LOURDES GONZALEZ");
            cb.endText();

            cb.beginText();
            cb.setFontAndSize(bf0, 12);
            cb.setTextMatrix(450, 55);
            cb.showText("CED. PROF. 1204923");
            cb.endText();

            cb.addImage(barras1, false);

            column.setCanvas(cb);
            column.setSimpleColumn(rect);
        } catch (BadElementException | IOException ex) {
            System.out.println("triggerNewPage" + ex.getMessage());
        }
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
