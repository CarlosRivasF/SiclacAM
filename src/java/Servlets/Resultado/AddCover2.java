/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets.Resultado;

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
import com.itextpdf.text.pdf.PdfImportedPage;
import com.itextpdf.text.pdf.PdfPCell;
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
import jbarcodebean.JBarcodeBean;
import net.sourceforge.jbarcodebean.model.Code39;

/**
 *
 * @author KODE
 */
public class AddCover2 {

    String Source = "web/M/MembreteRes1.pdf";
    /**
     * The original PDF file.
     */
    public static final String COVER
            = "web/M/MembreteRes1.pdf";
    /**
     * The original PDF file.
     */
    public static final String SRC
            = "web/M/MembreteRes1.pdf";

    /**
     * The resulting PDF file.
     */
    public static final String DEST
            = "results/merge/StamperX.pdf";

    public void manipulatePdf(String src, String dest)
            throws IOException, DocumentException {
        PdfReader cover = new PdfReader(COVER);
        PdfReader reader = new PdfReader(src);
        PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(dest));

        Rectangle pagesize = reader.getPageSize(1);

        /*stamper.insertPage(1, cover.getPageSizeWithRotation(1));
        PdfContentByte page1 = stamper.getOverContent(1);
        PdfImportedPage page = stamper.getImportedPage(cover, 1);
        page1.addTemplate(page, 0, 0);*/
        PdfContentByte cb = stamper.getOverContent(1);

        int id_Orden = 1;
        Image barras1;
        JBarcodeBean barcode = new JBarcodeBean();
        barcode.setCodeType(new Code39());
        barcode.setCode(id_Orden + "-");
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
        cb.showText("Carlos Juarez Cazares");
        cb.endText();
        cb.beginText();
        cb.setFontAndSize(bf, 10);
        cb.setTextMatrix(300, 722);
        cb.showText("Edad:");
        cb.endText();
        cb.beginText();
        cb.setFontAndSize(bf1, 12);
        cb.setTextMatrix(335, 722);
        cb.showText("23");
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
        cb.showText("Carlos Rivas Frutero");
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

        PdfPTable table = new PdfPTable(4);
        table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
        table.getDefaultCell().setBorder(0);
        table.setWidths(new int[]{7, 3, 7, 3});
        Boolean textLine = false;
        int IdxOrd;
        for (int l = 0; l < 6; l++) {
            //if(tipoEstudio=="Imagen"){
            if (l == 3) {
                textLine = true;
                IdxOrd = l + 1;
                break;
            }
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

        for (int i = 1; i <= 5; i++) {
            stamper.insertPage(1, cover.getPageSizeWithRotation(1));
            PdfContentByte pageI = stamper.getOverContent(1);
            pageI.beginText();
            pageI.setFontAndSize(bf, 10);
            pageI.setTextMatrix(50, 680);
            pageI.showText("HOLA SOY EL TEXTO NUEVO Num. " + i);
            pageI.endText();
            PdfImportedPage pageA = stamper.getImportedPage(cover, 1);
            pageI.addTemplate(pageA, 0, 0);
        }
        int pagecount = 1;
        Rectangle rectPage2 = new Rectangle(-27, 40, 640, 690);//0,esp-inf,ancho,alto
        int status = column.go();
        while (ColumnText.hasMoreText(status)) {
            status = triggerNewPage(reader, stamper, pagesize, column, rectPage2, ++pagecount);
        }
        stamper.setFormFlattening(true);
        stamper.close();
        reader.close();

        stamper.close();
        cover.close();
        reader.close();
    }

    public int triggerNewPage(PdfReader reader, PdfStamper stamper, Rectangle pagesize, ColumnText column, Rectangle rect, int pagecount) throws DocumentException {
        try {
            PdfContentByte cb = stamper.getOverContent(pagecount);

            int id_Orden = 1;
            Image barras1;
            JBarcodeBean barcode = new JBarcodeBean();
            barcode.setCodeType(new Code39());
            barcode.setCode(id_Orden + "-");
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
            cb.showText("Carlos Juarez Cazares");
            cb.endText();
            cb.beginText();
            cb.setFontAndSize(bf, 10);
            cb.setTextMatrix(300, 722);
            cb.showText("Edad:");
            cb.endText();
            cb.beginText();
            cb.setFontAndSize(bf1, 12);
            cb.setTextMatrix(335, 722);
            cb.showText("23");
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
            cb.showText("Carlos Rivas Frutero");
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
            Logger.getLogger(AddCover2.class.getName()).log(Level.SEVERE, null, ex);
        }
        return column.go();
    }

    public static void main(String[] args)
            throws IOException, DocumentException {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new AddCover2().manipulatePdf(SRC, DEST);
    }
}
