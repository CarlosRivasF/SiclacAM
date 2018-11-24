package DataBase;

import DataTransferObject.Orden_DTO;
import com.itextpdf.text.BadElementException;
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
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import jbarcodebean.JBarcodeBean;
import net.sourceforge.jbarcodebean.model.Code39;

/**
 *
 * @author ZionSystems
 */
public class PDF {

    public static void ReportRes(String src, String dest) {
        PdfReader reader = null;
        PdfStamper stamper = null;
        try {
            reader = new PdfReader(src);
            stamper = new PdfStamper(reader, new FileOutputStream(dest));
        } catch (IOException | DocumentException ex) {
            Logger.getLogger(PDF.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            Image barras1 = null;
            try {
                String name = "Bar-46S40hj7NOP47";
                JBarcodeBean barcode = new JBarcodeBean();
                //barcode.setCodeType(new Interleaved25());
                barcode.setCodeType(new Code39());
                // nuestro valor a codificar y algunas configuraciones mas
                barcode.setCode("11-KSY");
                barcode.setCheckDigit(true);
                barcode.setShowText(false);
                BufferedImage bufferedImage = barcode.draw(new BufferedImage(150, 16, BufferedImage.TYPE_INT_RGB));
                // guardar en disco como png
                File file = new File(name + ".png");
                ImageIO.write(bufferedImage, "png", file);
                barras1 = Image.getInstance((name + ".png"));
            } catch (BadElementException | IOException e) {

            }

            // PdfReader reader = new PdfReader(src);
            Rectangle pagesize = reader.getPageSize(1);
            //PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(dest));

            PdfContentByte cb = stamper.getUnderContent(1);
            barras1.setAbsolutePosition(73, 708);
            BaseFont bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
            cb.beginText();
            cb.setFontAndSize(bf, 10);
            cb.setTextMatrix(35, 680);
            cb.showText("Nombre (s Apellido Pat Apellido Mat)");
            cb.endText();

            cb.beginText();
            cb.setFontAndSize(bf, 10);
            cb.setTextMatrix(500, 695);
            cb.showText("Fecha de Orden");
            cb.endText();

            cb.addImage(barras1, false);

            PdfPTable table = new PdfPTable(4);
            table.addCell("#");
            table.addCell("description");
            table.addCell("test");
            table.addCell("row");
            table.setHeaderRows(1);
            //table.setWidths(new int[]{2, 5, 5,5});
            for (int i = 1; i <= 50; i++) {
                table.addCell(String.valueOf(i));
                table.addCell("Descrip " + i);
                table.addCell("test " + i);
                table.addCell("row " + i);
            }
            ColumnText column = new ColumnText(stamper.getOverContent(1));
            Rectangle rectPage1 = new Rectangle(0, 40, 617, 650);//0,esp-inf,ancho,alto
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
        } catch (DocumentException | IOException ex) {
            stamper.insertPage(1, reader.getPageSizeWithRotation(1));
        }
    }

    public static void ReportCot(String src, String dest, Orden_DTO orden) throws DocumentException, IOException {
        Image barras1;
        JBarcodeBean barcode = new JBarcodeBean();
        barcode.setCodeType(new Code39());
        barcode.setCode("11-KSY");
        barcode.setCheckDigit(true);
        barcode.setShowText(false);
        BufferedImage bi = barcode.draw(new BufferedImage(150, 16, BufferedImage.TYPE_INT_RGB));
        barras1 = Image.getInstance(Toolkit.getDefaultToolkit().createImage(bi.getSource()), null);

        PdfReader reader = new PdfReader(src);
        Rectangle pagesize = reader.getPageSize(1);
        PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(dest));

        PdfContentByte cb = stamper.getUnderContent(1);
        barras1.setAbsolutePosition(73, 313);
        BaseFont bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
        ////////////////////////// DATOS PACIENTE
        cb.beginText();
        cb.setFontAndSize(bf, 10);
        cb.setTextMatrix(35, 295);
        cb.showText(orden.getPaciente().getNombre() + " " + orden.getPaciente().getAp_Paterno() + " " + orden.getPaciente().getAp_Materno());
        cb.endText();
        ///////////////////  DAATOS ORDEN
        cb.beginText();
        cb.setFontAndSize(bf, 10);
        cb.setTextMatrix(500, 300);
        cb.showText("Fecha de Orden");
        cb.endText();
        ///////////////////////despedida
        cb.beginText();
        cb.setFontAndSize(bf, 10);
        cb.setTextMatrix(286, 20);
        cb.showText("DESPEDIDA");
        cb.endText();
        //////////////  bARCODE
        cb.addImage(barras1, false);
        //////////   Tabla Orden
        PdfPTable table = new PdfPTable(4);
        table.addCell("#");
        table.addCell("description");
        table.addCell("test");
        table.addCell("row");
        table.setHeaderRows(1);
        //table.setWidths(new int[]{2, 5, 5,5});
        for (int i = 1; i <= 13; i++) {
            table.addCell(String.valueOf(i));
            table.addCell("Descrip " + i);
            table.addCell("test " + i);
            table.addCell("row " + i);
        }
        ColumnText column = new ColumnText(stamper.getOverContent(1));
        Rectangle rectPage1 = new Rectangle(0, 40, 617, 270);//0,esp-inf,ancho,alto
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
    }

    public static void ReportOrd(String src, String dest, Orden_DTO orden) throws DocumentException, IOException {
        Image barras1;
        JBarcodeBean barcode = new JBarcodeBean();
        barcode.setCodeType(new Code39());
        barcode.setCode("11-KSY");
        barcode.setCheckDigit(true);
        barcode.setShowText(false);
        BufferedImage bi = barcode.draw(new BufferedImage(150, 16, BufferedImage.TYPE_INT_RGB));
        barras1 = Image.getInstance(Toolkit.getDefaultToolkit().createImage(bi.getSource()), null);
        PdfReader reader = new PdfReader(src);
        Rectangle pagesize = reader.getPageSize(1);
        PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(dest));

        PdfContentByte cb = stamper.getUnderContent(1);
        barras1.setAbsolutePosition(350, 333);//x,y
        BaseFont bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
        BaseFont bf0 = BaseFont.createFont(BaseFont.TIMES_BOLD, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
        ////////////////////////// DATOS UNIDAD
        cb.beginText();
        cb.setFontAndSize(bf0, 10);
        cb.setTextMatrix(330, 365);
        cb.showText("UNIDAD EL CHARCO NICOLÁS ROMERO");
        cb.endText();
        ////////////////////////// DATOS PACIENTE
        cb.beginText();
        cb.setFontAndSize(bf, 10);
        cb.setTextMatrix(142, 305);
        cb.showText("PACIENTE: Maria Lourdes Esquivel Martinez");
        cb.endText();
        cb.beginText();
        cb.setFontAndSize(bf, 10);
        cb.setTextMatrix(370, 305);
        cb.showText("EDAD: 34");
        cb.endText();
        cb.beginText();
        cb.setFontAndSize(bf, 10);
        cb.setTextMatrix(450, 305);
        cb.showText("TEL.: 5531663729");
        cb.endText();
        ////////////////////////// DATOS DOCTOR
        cb.beginText();
        cb.setFontAndSize(bf, 10);
        cb.setTextMatrix(142, 285);
        cb.showText("DOCTOR: Nombre (s Apellido Pat Apellido Mat)");
        cb.endText();
        ///////////////////  DAATOS ORDEN
        cb.beginText();
        cb.setFontAndSize(bf, 10);
        cb.setTextMatrix(435, 285);
        cb.showText("Fecha: 2018-04-10");
        cb.endText();
        ///////////////////////despedida
        cb.beginText();
        cb.setFontAndSize(bf, 10);
        cb.setTextMatrix(286, 20);
        cb.showText("DESPEDIDA");
        cb.endText();

        cb.addImage(barras1, false);

        PdfPTable table = new PdfPTable(4);
        table.addCell("#");
        table.addCell("description");
        table.addCell("test");
        table.addCell("row");
        table.setHeaderRows(1);
        //table.setWidths(new int[]{2, 5, 5,5});
        for (int i = 1; i <= 10; i++) {
            table.addCell(String.valueOf(i));
            table.addCell("Descrip " + i);
            table.addCell("test " + i);
            table.addCell("row " + i);
        }
        ColumnText column = new ColumnText(stamper.getOverContent(1));
        Rectangle rectPage1 = new Rectangle(108, 40, 617, 265);//0,esp-inf,ancho,alto
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
    }

    public static int triggerNewPage(PdfReader reader, PdfStamper stamper, Rectangle pagesize, ColumnText column, Rectangle rect, int pagecount) throws DocumentException {
        PdfContentByte canvas = stamper.getOverContent(pagecount);
        column.setCanvas(canvas);
        column.setSimpleColumn(rect);
        return column.go();
    }

    public static void main(String[] args) {
        ReportRes("C:\\Users\\KODE\\Documents\\NetBeansProjects\\SiclacAM_CRF\\web\\M\\MembreteRes1.pdf", "Rep.pdf");
    }
}
