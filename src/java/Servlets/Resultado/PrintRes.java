package Servlets.Resultado;

import DataAccesObject.Orden_DAO;
import DataBase.Util;
import DataTransferObject.Configuracion_DTO;
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
import com.itextpdf.text.pdf.PdfImportedPage;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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

/**
 *
 * @author ZionSystem
 */
@WebServlet(name = "PrintRes", urlPatterns = {"/PrintRes"})
public class PrintRes extends HttpServlet {

    PdfReader cover;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("ProcessRequest of PrintRes");
        Date fac = new Date();
        Util f = new Util();
        f.setHora(fac);
        HttpSession sesion = request.getSession();
        int id_Orden;
        if (request.getParameter("LxOrdSald") != null) {
            id_Orden = Integer.parseInt(request.getParameter("LxOrdSald").trim());
            System.out.println("LxOrdSald");
        } else if (request.getParameter("OrdFol") != null) {
            id_Orden = Integer.parseInt(request.getParameter("OrdFol").trim());
            System.out.println("OrdFol");
        } else if (request.getParameter("ScannBarr") != null) {
            id_Orden = Integer.parseInt(request.getParameter("ScannBarr").trim());
            System.out.println("ScannBarr");
        } else {
            id_Orden = 1;
            System.out.println("null");
        }

        try {
            Orden_DTO Orden;
            if (sesion.getAttribute("OrdenSh") != null) {
                System.out.println("GETOrdenSh");
                Orden = (Orden_DTO) sesion.getAttribute("OrdenSh");
                sesion.removeAttribute("OrdenSh");
            } else if (request.getParameter("OrdFol") != null) {
                System.out.println("GETOrdFol");
                Orden_DAO O = new Orden_DAO();
                int id_Unidad = Integer.parseInt(sesion.getAttribute("unidad").toString().trim());
                Orden = O.getOrdenByFolio(id_Orden, id_Unidad);
            } else if (request.getParameter("ScannBarr") != null) {
                System.out.println("GETScannBarr");
                Orden_DAO O = new Orden_DAO();
                Orden = O.getOrden(id_Orden);
            } else {
                System.out.println("GETElse");
                Orden_DAO O = new Orden_DAO();
                Orden = O.getOrden(id_Orden);
            }
            response.setContentType("application/pdf");
            response.setHeader("Content-disposition", "inline; filename=\"" + Orden.getPaciente().getCodPac() + "_" + Orden.getId_Orden() + ".pdf\"");
            String relativePath = getServletContext().getRealPath("/") + "/";//ruta real del proyecto

            List<Det_Orden_DTO> DetImage = new ArrayList<>();
            Orden.getDet_Orden().stream().filter((d) -> (d.getEstudio().getId_Tipo_Estudio() == 2 || d.getEstudio().getId_Tipo_Estudio() == 4 || d.getEstudio().getId_Tipo_Estudio() == 5 || d.getEstudio().getId_Tipo_Estudio() == 6)).forEachOrdered((d) -> {
                DetImage.add(d);
            });
            System.out.println("DetImage.Size()" + DetImage.size());
            Orden.getDet_Orden().removeAll(DetImage);

            //aqui se agrupan las configuraciones que tengan el mismo nombre para ponerlas en una sola fila
            //****Programar****
            //termina agrupacion de configuraciones
            String Source = relativePath + "M/MembreteRes1.pdf";

            System.out.println("Source:" + Source);
            int pagecount = 1;
            cover = new PdfReader(Source);
            PdfReader reader = new PdfReader(Source);
            Rectangle pagesize = reader.getPageSize(1);
            PdfStamper stamper = new PdfStamper(reader, response.getOutputStream());

            PdfContentByte cb = stamper.getOverContent(1);

            PrintDataHead(cb, Orden, true);
            System.out.println("Stamper");
            /////************************************************/////////////////
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
            for (Det_Orden_DTO dto : Orden.getDet_Orden()) {
                PdfPCell cell_Esp_Title = new PdfPCell(new Paragraph("                                   ", Title_Font_Est));
                cell_Esp_Title.setColspan(4);
                cell_Esp_Title.setBorder(0);
                table.addCell(cell_Esp_Title);

                PdfPCell cell_Est_Title = new PdfPCell(new Paragraph(dto.getEstudio().getNombre_Estudio(), Title_Font_Est));
                cell_Est_Title.setHorizontalAlignment(Element.ALIGN_LEFT);
                cell_Est_Title.setColspan(2);
                cell_Est_Title.setBorder(0);
                cell_Est_Title.setBackgroundColor(BackGr);
                table.addCell(cell_Est_Title);

                PdfPCell cell_Met_Title = new PdfPCell(new Paragraph("Método: " + dto.getEstudio().getMetodo(), Title_Font_Est));
                cell_Met_Title.setHorizontalAlignment(Element.ALIGN_LEFT);
                cell_Met_Title.setColspan(2);
                cell_Met_Title.setBorder(0);
                cell_Met_Title.setBackgroundColor(BackGr);
                table.addCell(cell_Met_Title);

                PdfPCell des = new PdfPCell(new Paragraph("Descripción"));
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
                dto.getEstudio().getCnfs().stream().filter((cnf) -> (cnf.getRes() != null)).map((cnf) -> {
                    PdfPCell cell_Desc = new PdfPCell(new Paragraph(cnf.getDescripcion(), Content_Font));
                    cell_Desc.setHorizontalAlignment(Element.ALIGN_LEFT);
                    cell_Desc.setBorder(0);
                    table.addCell(cell_Desc);
                    return cnf;
                }).map((cnf) -> {
                    table.addCell(cnf.getRes().getValor_Obtenido());
                    return cnf;
                }).map((cnf) -> {
                    table.addCell(cnf.getValor_min() + "-" + cnf.getValor_MAX());
                    return cnf;
                }).forEachOrdered((cnf) -> {
                    table.addCell(cnf.getUniddes());
                });
            }

            ColumnText column = new ColumnText(stamper.getOverContent(1));
            Rectangle rectPage1 = new Rectangle(-27, 120, 640, 690);//0,esp-inf,ancho,alto
            column.setSimpleColumn(rectPage1);
            column.addElement(table);

            Rectangle rectPage2 = new Rectangle(-27, 40, 640, 690);//0,esp-inf,ancho,alto
            int status = column.go();
            while (ColumnText.hasMoreText(status)) {
                pagecount++;
                f.setHora(fac);

                PdfContentByte pageI;
                stamper.insertPage(pagecount, cover.getPageSizeWithRotation(1));
                pageI = stamper.getOverContent(pagecount);
                PrintDataHead(cb, Orden, false);
                PdfImportedPage pageA = stamper.getImportedPage(cover, 1);
                pageI.addTemplate(pageA, 0, 0);
                column.setCanvas(pageI);
                column.setSimpleColumn(rectPage1);
                status = column.go();
            }

            /*Comienza a recorrer los estudios de tipo Imagen,etc que sean en hoja blanca*/
            BaseFont bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
            int c = 0;
            for (Det_Orden_DTO dto : DetImage) {//Validar que el etudio de imagen contenga un resultado
                //if(){}
                if (!dto.getEstudio().getAddRes()) {
                    break;
                }
                int IxImage = DetImage.indexOf(dto);
                System.out.println("IxImage: " + IxImage);
                c++;
                PdfContentByte pageI;
                if (Orden.getDet_Orden().isEmpty() && DetImage.size() == 1) {
                    pageI = cb;
                } else if (!Orden.getDet_Orden().isEmpty() && DetImage.size() > 1) {
                    stamper.insertPage(1, cover.getPageSizeWithRotation(1));
                    pageI = stamper.getOverContent(1);
                } else {
                    if (c == 1) {
                        pageI = cb;
                    } else {
                        stamper.insertPage(1, cover.getPageSizeWithRotation(1));
                        pageI = stamper.getOverContent(1);
                    }
                }
                PrintDataHead(pageI, Orden, false);
                int y = 680;
                /*
                 Maximo de caracteres por linea: 76
                 Lineas por página: MAX(32) Recomend: 28
                 */
                BaseFont bf0 = BaseFont.createFont(BaseFont.HELVETICA_BOLD, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
                for (Configuracion_DTO cnf : dto.getEstudio().getCnfs()) {
                    Boolean cnfRs = false;
                    if (cnf.getRes().getValor_Obtenido() != null) {
                        cnfRs = true;
                    }
                    System.out.println("Cnf:" + IxImage + "." + dto.getEstudio().getCnfs().indexOf(cnf) + " - " + cnfRs);
                    int idx = 75;
                    String line = cnf.getRes().getValor_Obtenido();
                    int rows = 0;
                    while (idx <= line.length() && rows <= 30) {
                        rows++;
                        pageI.beginText();
                        pageI.setFontAndSize(bf, 10);
                        pageI.setTextMatrix(50, y);
                        pageI.showText(line.substring((idx - 75), idx));
                        pageI.endText();
                        y = y - 15;
                        idx = idx + 75;
                    }
                    pageI.beginText();
                    pageI.setFontAndSize(bf, 11);
                    pageI.setTextMatrix(244, 200);
                    pageI.showText("ATENTAMENTE");
                    pageI.endText();
                    pageI.beginText();
                    pageI.setFontAndSize(bf, 11);
                    pageI.setTextMatrix(165, 140);
                    pageI.showText("_________________________________________");
                    pageI.endText();
                    pageI.beginText();
                    pageI.setFontAndSize(bf0, 11);
                    pageI.setTextMatrix(210, 125);
                    pageI.showText(Orden.getMedico().getNombre() + " " + Orden.getMedico().getAp_Paterno() + " " + Orden.getMedico().getAp_Materno());
                    //pageI.showText("Doctor Profesor Patricio Estrella");
                    pageI.endText();
                    pageI.beginText();
                    pageI.setFontAndSize(bf, 10);
                    pageI.setTextMatrix(245, 110);
                    pageI.showText("Médico Inmunólogo");
                    pageI.endText();
                    pageI.beginText();
                    pageI.setFontAndSize(bf, 10);
                    pageI.setTextMatrix(242, 95);
                    pageI.showText("CED. PROF. 1204923o");
                    pageI.endText();
                    PdfImportedPage pageA = stamper.getImportedPage(cover, 1);
                    pageI.addTemplate(pageA, 0, 0);
                }
            }
            stamper.setFormFlattening(true);
            stamper.close();
            reader.close();
        } catch (DocumentException | IOException ex) {
            System.out.println("processRequest" + ex.getMessage());
        }
    }

    public PdfContentByte PrintDataHead(PdfContentByte cb, Orden_DTO Orden, Boolean footer) {
        Date fac = new Date();
        Util f = new Util();
        f.setHora(fac);
        try {
            Image barras1;
            JBarcodeBean barcode = new JBarcodeBean();
            barcode.setCodeType(new Code39());
            barcode.setCode(Orden.getId_Orden() + "-");
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
            cb.showText(Orden.getPaciente().getNombre() + " " + Orden.getPaciente().getAp_Paterno() + " " + Orden.getPaciente().getAp_Materno());
            cb.endText();

            cb.beginText();
            cb.setFontAndSize(bf, 10);
            cb.setTextMatrix(270, 722);
            cb.showText("Edad:");
            cb.endText();
            cb.beginText();
            cb.setFontAndSize(bf1, 12);
            cb.setTextMatrix(305, 722);
            cb.showText(f.getEdad(Orden.getPaciente().getFecha_Nac()).getYears() + "");
            cb.endText();
            cb.beginText();
            cb.setFontAndSize(bf, 10);
            cb.setTextMatrix(340, 722);
            cb.showText("Sexo:");
            cb.endText();
            cb.beginText();
            cb.setFontAndSize(bf1, 12);
            cb.setTextMatrix(380, 722);
            cb.showText(Orden.getPaciente().getSexo());
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
            cb.showText(Orden.getMedico().getNombre() + " " + Orden.getMedico().getAp_Paterno() + " " + Orden.getMedico().getAp_Materno());
            cb.endText();
            ///////////////////////despedida
            if (footer) {
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
            }
            cb.addImage(barras1, false);
        } catch (BadElementException ex) {
            Logger.getLogger(AddCover2.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException | DocumentException ex) {
            Logger.getLogger(AddCover2.class.getName()).log(Level.SEVERE, null, ex);
        }
        return cb;
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
