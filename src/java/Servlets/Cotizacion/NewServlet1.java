package Servlets.Cotizacion;

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
@WebServlet(name = "NewServlet1", urlPatterns = {"/NewServlet1"})
public class NewServlet1 extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/pdf");
        response.setHeader("Content-disposition", "inline; filename=\"report" + 1 + ".pdf\"");
        String relativePath = getServletContext().getRealPath("/");
        String Source = relativePath + "M/MembrOrden.pdf";        
        try {
            Image barras1;
            JBarcodeBean barcode = new JBarcodeBean();
            barcode.setCodeType(new Code39());
            String Code="MOEM-987";
            barcode.setCode(Code);
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
            cb.showText("UNIDAD EL CHARDCO NICOLAS ROMERO");
            cb.endText();
            cb.beginText();

            cb.setFontAndSize(bf0, 10);
            cb.setTextMatrix(443, 740);
            cb.showText("Folio de Unidad: 1");
            cb.endText();

            barras1.setAbsolutePosition(260, 732);//x,y
            cb.beginText();
            cb.setFontAndSize(bf1, 9);
            cb.setTextMatrix(310, 722);
            cb.showText(Code);
            cb.endText();
            ////////////////////////// DATOS PACIENTE
            cb.beginText();
            cb.setFontAndSize(bf, 10);
            cb.setTextMatrix(65, 697);
            cb.showText("Paciente:");
            cb.endText();
            cb.beginText();
            cb.setFontAndSize(bf1, 10);
            cb.setTextMatrix(110, 697);
            cb.showText("Maria Lourdes Esquivel Martinez");
            cb.endText();
            cb.beginText();
            cb.setFontAndSize(bf, 10);
            cb.setTextMatrix(336, 697);
            cb.showText("Edad:");
            cb.endText();
            cb.beginText();
            cb.setFontAndSize(bf1, 10);
            cb.setTextMatrix(366, 697);
            cb.showText("35");
            cb.endText();
            cb.beginText();
            cb.setFontAndSize(bf, 10);
            cb.setTextMatrix(406, 697);
            cb.showText("TEL.:");
            cb.endText();
            cb.beginText();
            cb.setFontAndSize(bf1, 10);
            cb.setTextMatrix(426, 697);
            cb.showText("5531663729");
            cb.endText();
            ////////////////////////// DATOS DOCTOR
            cb.beginText();
            cb.setFontAndSize(bf, 10);
            cb.setTextMatrix(65, 678);
            cb.showText("Doctor:");
            cb.endText();
            cb.beginText();
            cb.setFontAndSize(bf1, 10);
            cb.setTextMatrix(103, 678);
            cb.showText("Tobbyas Merriweather Cortis");
            cb.endText();
            ///////////////////  DAATOS ORDEN
            cb.beginText();
            cb.setFontAndSize(bf, 10);
            cb.setTextMatrix(406, 678);
            cb.showText("Fecha de Emisión:");
            cb.endText();
            cb.beginText();
            cb.setFontAndSize(bf1, 10);
            cb.setTextMatrix(491, 678);
            cb.showText("2018-04-10");
            cb.endText();
            ///////////////////////despedida
            cb.beginText();
            cb.setFontAndSize(bf, 10);
            cb.setTextMatrix(36, 410);
            cb.showText("Dirección: Av. 1ero de Mayo No.46, Col. Hidalgo Nicolas Romero, Mexico     Tel 1.:5531663729     Tel 2.:5531663729");
            cb.endText();

            cb.addImage(barras1, false);

            cb.beginText();
            cb.setFontAndSize(bf, 12);
            cb.setTextMatrix(452, 567);
            cb.showText("A/C:");
            cb.endText();
            cb.beginText();
            cb.setFontAndSize(bf, 12);
            cb.setTextMatrix(500, 567);
            cb.showText("300.50");
            cb.endText();
            
            cb.beginText();
            cb.setFontAndSize(bf, 12);
            cb.setTextMatrix(452, 553);
            cb.showText("Saldo: ");
            cb.endText();
            cb.beginText();
            cb.setFontAndSize(bf, 12);
            cb.setTextMatrix(500, 553);
            cb.showText("272.30");
            cb.endText();
            
            cb.beginText();
            cb.setFontAndSize(bf, 12);
            cb.setTextMatrix(452, 539);
            cb.showText("TOTAL: ");
            cb.endText();
            cb.beginText();
            cb.setFontAndSize(bf, 12);
            cb.setTextMatrix(500, 539);
            cb.showText("572.80");
            cb.endText();
            
            PdfPTable table = new PdfPTable(2);
            table.addCell("Nombre de Estudio");
            table.addCell("Precio");  
            table.setHeaderRows(1);
            table.setWidths(new int[]{7, 3});
            for (int i = 1; i <= 12; i++) {
                table.addCell("Estudio solicitado solicitado # " + i);
                table.addCell("Precio " + i);
            }            
            
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
        } catch (DocumentException ex) {
            Logger.getLogger(NewServlet1.class.getName()).log(Level.SEVERE, null, ex);
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
