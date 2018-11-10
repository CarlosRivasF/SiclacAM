package Servlets.Estudio;

import DataAccesObject.Estudio_DAO;
import DataBase.Fecha;
import DataTransferObject.Estudio_DTO;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
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
@WebServlet(name = "PrintCatalogo", urlPatterns = {"/PrintCatalogo"})
public class PrintCatalogo extends HttpServlet {

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
            response.setHeader("Content-disposition", "inline; filename=\"Catalogo.pdf\"");//nombre de archivo
            String relativePath = getServletContext().getRealPath("/");//ruta real del proyecto
            int r = Catalogo.size(); //cantidad de registros

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
            System.out.println("Source:" + Source);//ruta del template PDF
            int pagecount = 1;
            try {
                cover = new PdfReader(Source);//PDF extra para posterior modificacion (omitir)
            } catch (IOException ex) {
                Logger.getLogger(PrintCatalogo.class.getName()).log(Level.SEVERE, null, ex);
            }
            PdfReader reader = new PdfReader(Source);//Lee plantilla PDF
            Rectangle pagesize = reader.getPageSize(1);//obtiene tamaño de pagina
            PdfStamper stamper = new PdfStamper(reader, response.getOutputStream());//Crea nuevo documento PDF en base al template y lo manda al navegador con  response.getOutputStream()

            PdfContentByte cb = stamper.getOverContent(1);//Obtiene contenido PDF donde se incrustaran los datos

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
            table.getDefaultCell().setBorder(0);
            table.setWidths(new int[]{7, 3, 7, 3});

            //HEADERS DEL REPORTE
            PdfPCell des = new PdfPCell(new Paragraph("Nombre Estudio"));
            des.setBorderColor(BaseColor.RED);
            table.addCell(des);
            PdfPCell res = new PdfPCell(new Paragraph("Preparacion"));
            res.setBorderColor(BaseColor.RED);
            table.addCell(res);
            PdfPCell valR = new PdfPCell(new Paragraph("Precio Normal"));
            valR.setBorderColor(BaseColor.RED);
            table.addCell(valR);
            PdfPCell prec2 = new PdfPCell(new Paragraph("Precio Urgente"));
            prec2.setBorderColor(BaseColor.RED);
            table.addCell(prec2);
            //CONTENIDO DE LA TABLA EN EL REPORTE
            for (Estudio_DTO dto : Catalogo) {
                PdfPCell cell_Desc = new PdfPCell(new Paragraph(dto.getNombre_Estudio(), Content_Font));
                cell_Desc.setHorizontalAlignment(Element.ALIGN_LEFT);
                cell_Desc.setBorder(0);
                table.addCell(cell_Desc);
                // table.addCell(cnf.getDescripcion());          
                table.addCell(dto.getPreparacion());

                table.addCell(String.valueOf(dto.getPrecio().getPrecio_N()));
                table.addCell(String.valueOf(dto.getPrecio().getPrecio_U()));
            }

            //FINALZA CONTENIDO
            ColumnText column = new ColumnText(stamper.getOverContent(1));
            Rectangle rectPage1 = new Rectangle(-27, 120, 640, 690);//0,esp-inf,ancho,alto (SON COORDENADAS EN LA HOJA DEL PDF Y EL TAMAÑO DEL ELEMENTO A AGREGAR)
                                           //(SANGRIA IZQ,Espacio que queda al final de la hoja,Ancho de Elemento,(no recuerdo))
            column.setSimpleColumn(rectPage1);//envia propiedades del contenido(tamaño)
            column.addElement(table);//añade la tabla creada

            
            //Realiza pruebas con este bloque
            Rectangle rectPage2 = new Rectangle(-27, 40, 640, 690);//0,esp-inf,ancho,alto
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
