package Servlets.Estudio;

import DataAccesObject.Estudio_DAO;
import DataAccesObject.Persona_DAO;
import DataBase.Fecha;
import DataTransferObject.Estudio_DTO;
import DataTransferObject.Persona_DTO;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import java.io.IOException;
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

/**
 *
 * @author ZionSystem
 */
@WebServlet(name = "PrintCatalogoPDF", urlPatterns = {"/PrintCatPDF"})
public class PrintCatalogoPDF extends HttpServlet {

    PdfReader cover;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("ProcessRequest");
        Date fac = new Date();
        Fecha f = new Fecha();
        f.setHora(fac);
        HttpSession sesion = request.getSession();
        int id_Unidad;

        id_Unidad = Integer.parseInt(request.getParameter("Id_Unidad"));

        try {
            List<Estudio_DTO> Catalogo;

            Estudio_DAO E = new Estudio_DAO();
            Catalogo = E.getEstudiosByUnidad(id_Unidad);//recupera lista de estudios por unidad

            response.setContentType("application/pdf");
            response.setHeader("Content-disposition", "inline; filename=\"CatalogoEstudiosOrderByTipoEstudios.pdf\"");//nombre de archivo
            String relativePath = getServletContext().getRealPath("/");//ruta real del proyecto
            int r = Catalogo.size() + 23;
            //cantidad de registros
            System.out.println("ROWS: " + r);
            String Source = "";
            if (r < 40) {
                Source = relativePath + "M/MembreteRes1.pdf";
            } else if (r > 40 & r < 80) {
                Source = relativePath + "M/MembreteRes2.pdf";
            } else if (r > 80 & r < 120) {
                Source = relativePath + "M/MembreteRes3.pdf";
            } else if (r > 120 & r < 160) {
                Source = relativePath + "M/MembreteRes4.pdf";
            } else if (r > 160 & r < 200) {
                Source = relativePath + "M/MembreteRes5.pdf";
            } else if (r > 200 & r < 240) {
                Source = relativePath + "M/MembreteRes6.pdf";
            } else if (r > 280 & r < 320) {
                Source = relativePath + "M/MembreteRes7.pdf";
            } else if (r > 360 & r < 400) {
                Source = relativePath + "M/MembreteRes8.pdf";
            } else if (r > 420 & r < 460) {
                Source = relativePath + "M/MembreteRes9.pdf";
            } else if (r > 500 & r < 520) {
                Source = relativePath + "M/MembreteRes10.pdf";
            }
            int pagecount = 1;
            try {
                cover = new PdfReader(Source);//PDF extra para posterior modificacion (omitir)
            } catch (IOException ex) {
                Logger.getLogger(PrintCatalogoPDF.class.getName()).log(Level.SEVERE, null, ex);
            }
            PdfReader reader = new PdfReader(Source);//Lee plantilla PDF
            Rectangle pagesize = reader.getPageSize(1);//obtiene tamaño de pagina
            PdfStamper stamper = new PdfStamper(reader, response.getOutputStream());//Crea nuevo documento PDF en base al template y lo manda al navegador con  response.getOutputStream()
            Persona_DAO P = new Persona_DAO();
            Persona_DTO persona = P.getPersona(Integer.parseInt(sesion.getAttribute("persona").toString()));
            PdfContentByte cb = stamper.getOverContent(1);//Obtiene contenido PDF donde se incrustaran los datos
            BaseFont bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
            BaseFont bf0 = BaseFont.createFont(BaseFont.HELVETICA_BOLD, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
            BaseFont bf1 = BaseFont.createFont(BaseFont.COURIER, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
            cb.beginText();
            cb.setFontAndSize(bf, 10);
            cb.setTextMatrix(270, 758);
            cb.showText("Fecha de Reporte:");
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
            cb.showText("Emitió:");
            cb.endText();
            cb.beginText();
            cb.setFontAndSize(bf1, 12);
            cb.setTextMatrix(320, 740);
            cb.showText(persona.getNombre() + " " + persona.getAp_Paterno() + " " + persona.getAp_Materno());
            cb.endText();

            cb.beginText();
            cb.setFontAndSize(bf, 10);
            cb.setTextMatrix(270, 722);
            cb.showText("Edad:");
            cb.endText();
            cb.beginText();
            cb.setFontAndSize(bf1, 12);
            cb.setTextMatrix(305, 722);
            cb.showText(f.getEdad(persona.getFecha_Nac()).getYears() + "");
            cb.endText();
            cb.beginText();
            cb.setFontAndSize(bf, 10);
            cb.setTextMatrix(340, 722);
            cb.showText("Sexo:");
            cb.endText();
            cb.beginText();
            cb.setFontAndSize(bf1, 12);
            cb.setTextMatrix(380, 722);
            cb.showText(persona.getSexo());
            cb.endText();
            ////////////////////////// DATOS DOCTOR           
            System.out.println("Stamper");// inicia Stamper(Incrustacion de datos)
            /////***************FUENTES PARA FORMATO DEL REPORTE*********************************/////////////////
            BaseColor orange = new BaseColor(211, 84, 0);
            BaseColor blue = new BaseColor(52, 152, 219);
            BaseColor green = new BaseColor(40, 180, 99);
            BaseColor BackGr = new BaseColor(234, 236, 238);

            Font Title_Font_Est = FontFactory.getFont("Times Roman", 12, blue);
            Font Title_Font_Prec = FontFactory.getFont("Times Roman", 12, orange);
            Font Title_Font_Prep = FontFactory.getFont("Times Roman", 12, green);
            Font Content_Font = FontFactory.getFont("Arial", 10, BaseColor.BLACK);

            //COMIENZA TABLA DE CATALOGO
            PdfPTable table = new PdfPTable(4);
            table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
            table.getDefaultCell().setBorder(1);
            table.setWidths(new int[]{5, 10, 4, 4});
            int id_Tipo_Estudio = 0;
            int c = 0;
            for (Estudio_DTO dto : Catalogo) {
                if (id_Tipo_Estudio != dto.getId_Tipo_Estudio()) {
                    id_Tipo_Estudio = dto.getId_Tipo_Estudio();
                    if (c != 0) {
                        PdfPCell Esp = new PdfPCell(new Paragraph("              "));
                        Esp.setBorder(0);
                        Esp.setColspan(4);
                        table.addCell(Esp);
                    }
                    PdfPCell TipoEstudio = new PdfPCell(new Paragraph("Tipo de Estudio: " + dto.getNombre_Tipo_Estudio(), Title_Font_Est));
                    TipoEstudio.setHorizontalAlignment(Element.ALIGN_LEFT);
                    TipoEstudio.setColspan(4);
                    TipoEstudio.setBorder(0);
                    TipoEstudio.setBackgroundColor(BackGr);
                    table.addCell(TipoEstudio);
                    //HEADERS DEL REPORTE
                    PdfPCell HClaveEst = new PdfPCell(new Paragraph("Clave"));
                    HClaveEst.setBorderColor(BaseColor.RED);
                    table.addCell(HClaveEst);
                    PdfPCell HNombreEsudio = new PdfPCell(new Paragraph("Nombre Estudio"));
                    HNombreEsudio.setBorderColor(BaseColor.RED);
                    table.addCell(HNombreEsudio);
                    PdfPCell HPrecioN = new PdfPCell(new Paragraph("Precio Normal"));
                    HPrecioN.setBorderColor(BaseColor.RED);
                    table.addCell(HPrecioN);
                    PdfPCell HPrecioU = new PdfPCell(new Paragraph("Precio Urgente"));
                    HPrecioU.setBorderColor(BaseColor.RED);
                    table.addCell(HPrecioU);
                    c++;
                }
                //CONTENIDO DE LA TABLA EN EL REPORTE            
                PdfPCell ClaveEst = new PdfPCell(new Paragraph("~ " + dto.getClave_Estudio().toUpperCase(), Content_Font));
                ClaveEst.setHorizontalAlignment(Element.ALIGN_LEFT);
                ClaveEst.setBorder(PdfPCell.BOTTOM);
                table.addCell(ClaveEst);

                PdfPCell NombreEstudio = new PdfPCell(new Paragraph(dto.getNombre_Estudio().toUpperCase(), Content_Font));
                NombreEstudio.setHorizontalAlignment(Element.ALIGN_LEFT);
                NombreEstudio.setBorder(PdfPCell.BOTTOM);
                table.addCell(NombreEstudio);

                PdfPCell PrecioN = new PdfPCell(new Paragraph(String.valueOf(dto.getPrecio().getPrecio_N()), Content_Font));
                PrecioN.setHorizontalAlignment(Element.ALIGN_CENTER);
                PrecioN.setBorder(PdfPCell.BOTTOM);
                table.addCell(PrecioN);

                PdfPCell PrecioU = new PdfPCell(new Paragraph(String.valueOf(dto.getPrecio().getPrecio_U()), Content_Font));
                PrecioU.setHorizontalAlignment(Element.ALIGN_CENTER);
                PrecioU.setBorder(PdfPCell.BOTTOM);
                table.addCell(PrecioU);
            }

            //FINALZA CONTENIDO
            ColumnText column = new ColumnText(stamper.getOverContent(1));
            Rectangle rectPage1 = new Rectangle(-27, 60, 640, 690);//0,esp-inf,ancho,alto (SON COORDENADAS EN LA HOJA DEL PDF Y EL TAMAÑO DEL ELEMENTO A AGREGAR)
            //(SANGRIA IZQ,Espacio que queda al final de la hoja,Ancho de Elemento,(no recuerdo))
            column.setSimpleColumn(rectPage1);//envia propiedades del contenido(tamaño)
            column.addElement(table);//añade la tabla creada

            //Realiza pruebas con este bloque
            Rectangle rectPage2 = new Rectangle(-27, 60, 640, 690);//0,esp-inf,ancho,alto
            int status = column.go();
            while (ColumnText.hasMoreText(status)) {
                status = triggerNewPage(reader, stamper, pagesize, column, rectPage2, ++pagecount);//este metodo ingresa una hoja nueva si el conetido es superior al lo que puede tener la hoja principal
            }
            stamper.setFormFlattening(true);
            stamper.close();
            reader.close();

        } catch (DocumentException | IOException ex) {
            System.out.println("processRequest" + ex.getMessage());
        }
    }

    public int triggerNewPage(PdfReader reader, PdfStamper stamper, Rectangle pagesize, ColumnText column, Rectangle rect, int pagecount) throws DocumentException {
        Date fac = new Date();
        Fecha f = new Fecha();
        f.setHora(fac);
        PdfContentByte cb = stamper.getOverContent(pagecount);
        column.setCanvas(cb);
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
