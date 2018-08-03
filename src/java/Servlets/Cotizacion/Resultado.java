package Servlets.Cotizacion;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
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
 * @author ZionSystems
 */
@WebServlet(name = "Resultado", urlPatterns = {"/Resultado"})
public class Resultado extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("application/pdf");
        response.setHeader("Content-disposition", "inline; filename=\"report" + 1 + ".pdf\"");
        String relativePath = getServletContext().getRealPath("/");
        String Source = relativePath + "M/MembreteRes2.pdf";
        try {
            pdfs(Source, response);
        } catch (IOException | DocumentException ex0) {
        }
    }

    public void pdfs(String src, HttpServletResponse response) throws DocumentException, IOException {

        PdfReader reader = new PdfReader(src);
        Rectangle pagesize = reader.getPageSize(1);
        PdfStamper stamper = new PdfStamper(reader, response.getOutputStream());

        PdfContentByte cb = stamper.getOverContent(1);

        Image barras1;
        JBarcodeBean barcode = new JBarcodeBean();
        barcode.setCodeType(new Code39());
        barcode.setCode("SALA700731-914-3");
        barcode.setCheckDigit(true);
        barcode.setShowText(true);
        BufferedImage bi = barcode.draw(new BufferedImage(156, 12, BufferedImage.TYPE_INT_RGB));
        barras1 = Image.getInstance(Toolkit.getDefaultToolkit().createImage(bi.getSource()), null);

        BaseFont bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
        BaseFont bf0 = BaseFont.createFont(BaseFont.HELVETICA_BOLD, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
        BaseFont bf1 = BaseFont.createFont(BaseFont.COURIER, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);

        barras1.setAbsolutePosition(92, 705);//x,y

        ///////////////////  DAATOS ORDEN
        cb.beginText();
        cb.setFontAndSize(bf, 10);
        cb.setTextMatrix(305, 758);
        cb.showText("Fecha de Emisión:");
        cb.endText();
        cb.beginText();
        cb.setFontAndSize(bf1, 12);
        cb.setTextMatrix(390, 758);
        cb.showText("2018-04-10");
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
        cb.showText("Maria Lourdes Esquivel Martinez");
        cb.endText();
        cb.beginText();
        cb.setFontAndSize(bf, 10);
        cb.setTextMatrix(300, 722);
        cb.showText("Edad:");
        cb.endText();
        cb.beginText();
        cb.setFontAndSize(bf1, 12);
        cb.setTextMatrix(335, 722);
        cb.showText("35");
        cb.endText();
        cb.beginText();
        cb.setFontAndSize(bf, 10);
        cb.setTextMatrix(370, 722);
        cb.showText("Sexo:");
        cb.endText();
        cb.beginText();
        cb.setFontAndSize(bf1, 12);
        cb.setTextMatrix(410, 722);
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
        cb.showText("Tobbyas Merriweather Cortis");
        cb.endText();
        ///////////////////////despedida

        cb.beginText();
        cb.setFontAndSize(bf0, 12);
        cb.setTextMatrix(280, 20);
        cb.showText("QFB. MARIA DE LOURDES GONZALEZ   CED. PROF. 1204923");
        cb.endText();

        cb.addImage(barras1, false);

        PdfPTable table = new PdfPTable(4);
        table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
        table.getDefaultCell().setBorder(0);
        table.setWidths(new int[]{7, 3, 7, 3});
        for (int l = 0; l < 8; l++) {
            PdfPCell est = new PdfPCell(new Paragraph("NUEVO ESTUDIO " + l + ""));
            est.setHorizontalAlignment(Element.ALIGN_LEFT);
            est.setColspan(4);
            est.setBorder(0);            
            table.addCell(est);
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
            for (int i = 1; i <= 6; i++) {
                table.addCell("desc solicitado # " + i);
                table.addCell("02 " + i);
                table.addCell("20.00");
                table.addCell("32.12");
            }
        }        
        ColumnText column = new ColumnText(stamper.getOverContent(1));
        Rectangle rectPage1 = new Rectangle(-27, 120, 640, 690);//0,esp-inf,ancho,alto
        column.setSimpleColumn(rectPage1);
        column.addElement(table);

        int pagecount = 1;
        Rectangle rectPage2 = new Rectangle(-27, 40, 640, 690);//0,esp-inf,ancho,alto
        int status = column.go();
        while (ColumnText.hasMoreText(status)) {
            status = triggerNewPage(reader, stamper, pagesize, column, rectPage2, ++pagecount);
        }
        stamper.setFormFlattening(true);
        stamper.close();
        reader.close();
    }

    public int triggerNewPage(PdfReader reader, PdfStamper stamper, Rectangle pagesize, ColumnText column, Rectangle rect, int pagecount) throws DocumentException {
        try {
            PdfContentByte canvas = stamper.getOverContent(pagecount);
            Image barras1;
            JBarcodeBean barcode = new JBarcodeBean();
            barcode.setCodeType(new Code39());
            barcode.setCode("SALA700731-914-3");
            barcode.setCheckDigit(true);
            barcode.setShowText(false);
            BufferedImage bi = barcode.draw(new BufferedImage(156, 12, BufferedImage.TYPE_INT_RGB));
            barras1 = Image.getInstance(Toolkit.getDefaultToolkit().createImage(bi.getSource()), null);

            BaseFont bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
            BaseFont bf0 = BaseFont.createFont(BaseFont.HELVETICA_BOLD, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
            BaseFont bf1 = BaseFont.createFont(BaseFont.COURIER, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);

            barras1.setAbsolutePosition(92, 705);//x,y

            ///////////////////  DAATOS ORDEN
            canvas.beginText();
            canvas.setFontAndSize(bf, 10);
            canvas.setTextMatrix(305, 758);
            canvas.showText("Fecha de Emisión:");
            canvas.endText();
            canvas.beginText();
            canvas.setFontAndSize(bf1, 12);
            canvas.setTextMatrix(390, 758);
            canvas.showText("2018-04-10");
            canvas.endText();
            ////////////////////////// DATOS PACIENTE
            canvas.beginText();
            canvas.setFontAndSize(bf, 10);
            canvas.setTextMatrix(270, 740);
            canvas.showText("Paciente:");
            canvas.endText();
            canvas.beginText();
            canvas.setFontAndSize(bf1, 12);
            canvas.setTextMatrix(320, 740);
            canvas.showText("Maria Lourdes Esquivel Martinez");
            canvas.endText();
            canvas.beginText();
            canvas.setFontAndSize(bf, 10);
            canvas.setTextMatrix(300, 722);
            canvas.showText("Edad:");
            canvas.endText();
            canvas.beginText();
            canvas.setFontAndSize(bf1, 12);
            canvas.setTextMatrix(335, 722);
            canvas.showText("35");
            canvas.endText();
            canvas.beginText();
            canvas.setFontAndSize(bf, 10);
            canvas.setTextMatrix(370, 722);
            canvas.showText("Sexo:");
            canvas.endText();
            canvas.beginText();
            canvas.setFontAndSize(bf1, 12);
            canvas.setTextMatrix(410, 722);
            canvas.showText("Masculino");
            canvas.endText();
            ////////////////////////// DATOS DOCTOR
            canvas.beginText();
            canvas.setFontAndSize(bf, 10);
            canvas.setTextMatrix(270, 707);
            canvas.showText("Doctor:");
            canvas.endText();
            canvas.beginText();
            canvas.setFontAndSize(bf1, 12);
            canvas.setTextMatrix(315, 707);
            canvas.showText("Tobbyas Merriweather Cortis");
            canvas.endText();
            ///////////////////////despedida

            canvas.beginText();
            canvas.setFontAndSize(bf0, 11);
            canvas.setTextMatrix(280, 20);
            canvas.showText("QFB. MARIA DE LOURDES GONZALEZ   CED. PROF. 1204923");
            canvas.endText();

            canvas.addImage(barras1, false);
            column.setCanvas(canvas);
            column.setSimpleColumn(rect);
        } catch (BadElementException | IOException ex) {
            Logger.getLogger(Resultado.class.getName()).log(Level.SEVERE, null, ex);
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
