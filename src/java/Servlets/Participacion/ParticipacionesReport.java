package Servlets.Participacion;

import DataAccesObject.Participacion_DAO;
import DataAccesObject.Persona_DAO;
import DataBase.Util;
import DataTransferObject.Det_Orden_DTO;
import DataTransferObject.Medico_DTO;
import DataTransferObject.Participacion_DTO;
import DataTransferObject.Persona_DTO;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfImportedPage;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Carlos Rivas
 */
@WebServlet(name = "ParticipacionesReport", urlPatterns = {"/ParticipacionesReport"})
public class ParticipacionesReport extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) {
        Float montoTotal = Float.parseFloat(String.valueOf("0"));
        PdfReader cover = null;
        System.out.println("Entró peticion a PrintReporteOrders");
        Date fac = new Date();
        Util f = new Util();
        f.setHora(fac);
        HttpSession sesion = request.getSession();
        int id_Unidad;
        id_Unidad = Integer.parseInt(sesion.getAttribute("unidad").toString().trim());
        String f1 = request.getParameter("fchaI").trim();
        String f2 = request.getParameter("fchaF").trim();
        String Crte;
        String Med;
        Boolean Corte = false;
        Boolean Meds = false;

        if (request.getParameter("Crte") != null) {
            Crte = request.getParameter("Crte").trim();
            if (Crte.equals("Ys")) {
                System.out.println("Debe de imprimir Corte");
                Corte = true;
            }
        }
        if (request.getParameter("Med") != null) {
            Med = request.getParameter("Med").trim();
            if (Med.equals("Ys")) {
                Meds = true;
            }
        }

        try {
            List<Participacion_DTO> Reporte;
            List<Medico_DTO> ReporteMedico = null;
            Participacion_DAO Part = new Participacion_DAO();
            Reporte = Part.GetPartsByUnidad(id_Unidad, f1, f2);//recupera lista de ordenes
            if (Meds) {
                ReporteMedico = new ArrayList<>();
                int c = 0;
                int TEMPid_Medico = 0;
                Medico_DTO medico = null;
                for (Participacion_DTO dto : Reporte) {
                    c++;
                    if (dto.getOrden().getMedico().getId_Medico() != TEMPid_Medico) {
                        if (c != 1) {
                            System.out.println("AddMedico to ReporteMedico");
                            ReporteMedico.add(medico);
                        }
                        TEMPid_Medico = dto.getOrden().getMedico().getId_Medico();
                        medico = new Medico_DTO();
                        medico = dto.getOrden().getMedico();
                        medico.getParticipaciones().add(dto);
                    } else {
                        medico.getParticipaciones().add(dto);
                    }
                }
                System.out.println("AddMedicoFIN to ReporteMedico");
                ReporteMedico.add(medico);
            }

            response.setContentType("application/pdf");
            response.setHeader("Content-disposition", "inline; filename=\"ReportOrdenes_" + id_Unidad + "_" + f1.trim().replace("-", "") + f2.trim().replace("-", "") + ".pdf\"");//nombre de archivo
            String relativePath = getServletContext().getRealPath("/") + "/";//ruta real del proyecto

            String Source = relativePath + "M/MembreteHtl.pdf";

            int pagecount = 1;
            cover = new PdfReader(Source);//PDF extra para posterior modificacion (omitir)
            PdfReader reader = new PdfReader(Source);
            PdfStamper stamper = new PdfStamper(reader, response.getOutputStream());//Crea nuevo documento PDF en base al template y lo manda al navegador con  response.getOutputStream()

            Persona_DAO P = new Persona_DAO();
            Persona_DTO persona = P.getPersona(Integer.parseInt(sesion.getAttribute("persona").toString()));
            PdfContentByte cb = stamper.getOverContent(1);//Obtiene contenido PDF donde se incrustaran los datos
//            PrintHead(cb, f, persona);
            /////***************FUENTES PARA FORMATO DEL REPORTE*********************************/////////////////
            BaseColor orange = new BaseColor(211, 84, 0);
            BaseColor blue = new BaseColor(52, 152, 219);
            BaseColor green = new BaseColor(40, 180, 99);
            BaseColor BackGr = new BaseColor(234, 236, 238);

            Font Title_Font_Est = FontFactory.getFont("Times Roman", 12, blue);
            Font Title_Font_Prec = FontFactory.getFont("Times Roman", 12, orange);
            Font Title_Font_Prep = FontFactory.getFont("Times Roman", 12, green);
            Font Content_Font = FontFactory.getFont("Arial", 10, BaseColor.BLACK);

            PdfPTable table = null;

            if (Meds) {
                table = new PdfPTable(6);
                table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
                table.getDefaultCell().setBorder(1);

                table.setWidths(new int[]{3, 10, 12, 6, 5, 5});
            } else {
                table = new PdfPTable(7);
                table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
                table.getDefaultCell().setBorder(1);

                table.setWidths(new int[]{3, 6, 12, 12, 6, 7, 7});
            }

            //HEADERS DEL REPORTE
            int c = 0;
            if (Meds) {

                System.out.println("Reporte con medicos");
                for (Medico_DTO medic : ReporteMedico) {
                    c++;
                    if (c != 1) {
                        PdfPCell Espacio1 = new PdfPCell(new Paragraph("       "));
                        Espacio1.setHorizontalAlignment(Element.ALIGN_LEFT);
                        Espacio1.setColspan(7);
                        Espacio1.setBorder(0);
                        table.addCell(Espacio1);
                    }
                    if (medic != null) {
                        if (medic.getNombre() != null) {
                            PdfPCell Espacio0 = new PdfPCell(new Paragraph("Médico: " + medic.getNombre().toLowerCase() + " " + medic.getAp_Paterno().toLowerCase() + " " + medic.getAp_Materno().toLowerCase()));
                            Espacio0.setHorizontalAlignment(Element.ALIGN_LEFT);
                            Espacio0.setColspan(2);
                            Espacio0.setBorder(0);
                            table.addCell(Espacio0);
                        } else {
                            PdfPCell Espacio0 = new PdfPCell(new Paragraph("Médico: ----"));
                            Espacio0.setHorizontalAlignment(Element.ALIGN_LEFT);
                            Espacio0.setColspan(2);
                            Espacio0.setBorder(0);
                            table.addCell(Espacio0);
                        }

                        if (medic.getTelefono1() != null) {
                            PdfPCell Espacio01 = new PdfPCell(new Paragraph("Teléfono: " + medic.getTelefono1()));
                            Espacio01.setHorizontalAlignment(Element.ALIGN_LEFT);
                            Espacio01.setBorder(0);
                            table.addCell(Espacio01);
                        } else {
                            PdfPCell Espacio01 = new PdfPCell(new Paragraph("Teléfono: ---"));
                            Espacio01.setHorizontalAlignment(Element.ALIGN_LEFT);
                            Espacio01.setBorder(0);
                            table.addCell(Espacio01);
                        }

                        if (medic.getMail() != null) {
                            PdfPCell Espacio02 = new PdfPCell(new Paragraph("Correo: " + medic.getMail().toLowerCase()));
                            Espacio02.setHorizontalAlignment(Element.ALIGN_LEFT);
                            Espacio02.setColspan(3);
                            Espacio02.setBorder(0);
                            table.addCell(Espacio02);
                        } else {
                            PdfPCell Espacio02 = new PdfPCell(new Paragraph("Correo: ---"));
                            Espacio02.setHorizontalAlignment(Element.ALIGN_LEFT);
                            Espacio02.setColspan(3);
                            Espacio02.setBorder(0);
                            table.addCell(Espacio02);
                        }

////////////////////////////////////////////////////////////////////////////////////////////////////////
                        PdfPCell HFolio = new PdfPCell(new Paragraph("Folio"));
                        HFolio.setBorderColor(BaseColor.RED);
                        HFolio.setHorizontalAlignment(Element.ALIGN_CENTER);
                        table.addCell(HFolio);
                        PdfPCell HFecha = new PdfPCell(new Paragraph("Fecha"));
                        HFecha.setBorderColor(BaseColor.RED);
                        HFecha.setHorizontalAlignment(Element.ALIGN_CENTER);
                        table.addCell(HFecha);
                        PdfPCell HPaciente = new PdfPCell(new Paragraph("Paciente"));
                        HPaciente.setBorderColor(BaseColor.RED);
                        HPaciente.setHorizontalAlignment(Element.ALIGN_CENTER);
                        table.addCell(HPaciente);
                        PdfPCell HEstudios = new PdfPCell(new Paragraph("Estudios"));
                        HEstudios.setBorderColor(BaseColor.RED);
                        HEstudios.setHorizontalAlignment(Element.ALIGN_CENTER);
                        table.addCell(HEstudios);
                        PdfPCell HPrecioOrden = new PdfPCell(new Paragraph("PrecioOrden"));
                        HPrecioOrden.setBorderColor(BaseColor.RED);
                        HPrecioOrden.setHorizontalAlignment(Element.ALIGN_CENTER);
                        table.addCell(HPrecioOrden);
                        PdfPCell HParticipacion = new PdfPCell(new Paragraph("Participación"));
                        HParticipacion.setBorderColor(BaseColor.RED);
                        HParticipacion.setHorizontalAlignment(Element.ALIGN_CENTER);
                        table.addCell(HParticipacion);
                        Float montoParticipacion = Float.parseFloat(String.valueOf("0"));
                        //for (int i = 1; i <= 8789; i++) {
                        for (Participacion_DTO dto : medic.getParticipaciones()) {

                            //CONTENIDO DE LA TABLA EN EL REPORTE            
                            //PdfPCell Folio = new PdfPCell(new Paragraph(String.valueOf(i), Content_Font));
                            PdfPCell Folio = new PdfPCell(new Paragraph(String.valueOf(dto.getOrden().getFolio_Unidad()), Content_Font));
                            Folio.setHorizontalAlignment(Element.ALIGN_CENTER);
                            Folio.setBorder(PdfPCell.BOTTOM);
                            table.addCell(Folio);

                            //PdfPCell Fecha = new PdfPCell(new Paragraph("fecha", Content_Font));
                            PdfPCell CllFecha = new PdfPCell(new Paragraph(dto.getOrden().getFecha(), Content_Font));
                            CllFecha.setHorizontalAlignment(Element.ALIGN_CENTER);
                            CllFecha.setBorder(PdfPCell.BOTTOM);
                            table.addCell(CllFecha);

                            if (dto.getOrden().getPaciente().getNombre() != null) {
                                PdfPCell Paciente = new PdfPCell(new Paragraph(dto.getOrden().getPaciente().getNombre().toLowerCase().trim() + " " + dto.getOrden().getPaciente().getAp_Paterno().toLowerCase().trim() + " " + dto.getOrden().getPaciente().getAp_Materno().toLowerCase().trim(), Content_Font));
                                Paciente.setHorizontalAlignment(Element.ALIGN_CENTER);
                                Paciente.setBorder(PdfPCell.BOTTOM);
                                table.addCell(Paciente);
                            } else {
                                PdfPCell Paciente = new PdfPCell(new Paragraph("-----", Content_Font));
                                Paciente.setHorizontalAlignment(Element.ALIGN_CENTER);
                                Paciente.setBorder(PdfPCell.BOTTOM);
                                table.addCell(Paciente);
                            }

                            String strEstudios = "";
                            for (Det_Orden_DTO deto : dto.getOrden().getDet_Orden()) {
                                if (deto.getEstudio().getClave_Estudio() != null) {
                                    strEstudios = strEstudios + deto.getEstudio().getClave_Estudio().trim() + ",";
                                }
                            }

                            PdfPCell Estudios = new PdfPCell(new Paragraph(strEstudios.toLowerCase(), Content_Font));
                            //PdfPCell Estudios = new PdfPCell(new Paragraph("Estudios", Content_Font));
                            Estudios.setHorizontalAlignment(Element.ALIGN_CENTER);
                            Estudios.setBorder(PdfPCell.BOTTOM);
                            table.addCell(Estudios);

                            PdfPCell montoOrden = new PdfPCell(new Paragraph("$" + String.valueOf(Util.redondearDecimales(dto.getOrden().getMontoRestante() + dto.getOrden().getMontoPagado())), Content_Font));
                            montoOrden.setHorizontalAlignment(Element.ALIGN_CENTER);
                            montoOrden.setBorder(PdfPCell.BOTTOM);
                            table.addCell(montoOrden);

                            PdfPCell Participacion = new PdfPCell(new Paragraph("$" + String.valueOf(Util.redondearDecimales(dto.getMonto())), Content_Font));
                            Participacion.setHorizontalAlignment(Element.ALIGN_CENTER);
                            Participacion.setBorder(PdfPCell.BOTTOM);
                            table.addCell(Participacion);
                            montoParticipacion += Util.redondearDecimales(dto.getMonto());
                            montoTotal += Util.redondearDecimales(dto.getMonto());
                        }
                        //FINALZA CONTENIDO
                        if (Corte) {
                            //PdfPCell MontoPagado = new PdfPCell(new Paragraph(String.valueOf(300 + i), Content_Font));
                            PdfPCell HParticipacionTotal = new PdfPCell(new Paragraph("Participacion Médico:"));
                            HParticipacionTotal.setBorderColor(BaseColor.RED);
                            HParticipacionTotal.setHorizontalAlignment(Element.ALIGN_CENTER);
                            HParticipacionTotal.setColspan(3);
                            table.addCell(HParticipacionTotal);
                            PdfPCell ParticipacionTotal = new PdfPCell(new Paragraph("$" + String.valueOf(Util.redondearDecimales(montoParticipacion))));
                            ParticipacionTotal.setHorizontalAlignment(Element.ALIGN_CENTER);
                            ParticipacionTotal.setBorder(PdfPCell.BOTTOM);
                            ParticipacionTotal.setColspan(3);
                            table.addCell(ParticipacionTotal);
                        }
                    }
                }
                if (Corte) {
                    PdfPCell Espacio2 = new PdfPCell(new Paragraph("       "));
                    Espacio2.setHorizontalAlignment(Element.ALIGN_LEFT);
                    Espacio2.setBorder(0);
                    Espacio2.setColspan(6);
                    table.addCell(Espacio2);

                    PdfPCell Espacio3 = new PdfPCell(new Paragraph("       "));
                    Espacio3.setHorizontalAlignment(Element.ALIGN_LEFT);
                    Espacio3.setBorder(0);
                    table.addCell(Espacio3);

                    PdfPCell TitMontoTotalPart = new PdfPCell(new Paragraph("Monto Total de Participaciones a Pagar: "));
                    TitMontoTotalPart.setBorderColor(BaseColor.RED);
                    TitMontoTotalPart.setHorizontalAlignment(Element.ALIGN_CENTER);
                    TitMontoTotalPart.setColspan(3);
                    table.addCell(TitMontoTotalPart);
                    PdfPCell CellMontoTotalPart = new PdfPCell(new Paragraph("$" + String.valueOf(montoTotal)));
                    CellMontoTotalPart.setBorderColor(BaseColor.RED);
                    CellMontoTotalPart.setHorizontalAlignment(Element.ALIGN_CENTER);
                    table.addCell(CellMontoTotalPart);
                    System.out.println("CORTE final");
                }
            } else {
                System.out.println("SinMedicos");

                PdfPCell HFolio = new PdfPCell(new Paragraph("Folio"));
                HFolio.setBorderColor(BaseColor.RED);
                HFolio.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(HFolio);
                PdfPCell HFecha = new PdfPCell(new Paragraph("Fecha"));
                HFecha.setBorderColor(BaseColor.RED);
                HFecha.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(HFecha);
                PdfPCell HMedico = new PdfPCell(new Paragraph("Medico"));
                HMedico.setBorderColor(BaseColor.RED);
                HMedico.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(HMedico);
                PdfPCell HPaciente = new PdfPCell(new Paragraph("Paciente"));
                HPaciente.setBorderColor(BaseColor.RED);
                HPaciente.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(HPaciente);
                PdfPCell HEstudios = new PdfPCell(new Paragraph("Estudios"));
                HEstudios.setBorderColor(BaseColor.RED);
                HEstudios.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(HEstudios);
                PdfPCell HPrecioOrden = new PdfPCell(new Paragraph("PrecioOrden"));
                HPrecioOrden.setBorderColor(BaseColor.RED);
                HPrecioOrden.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(HPrecioOrden);
                PdfPCell HParticipacion = new PdfPCell(new Paragraph("Participación"));
                HParticipacion.setBorderColor(BaseColor.RED);
                HParticipacion.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(HParticipacion);

                //for (int i = 1; i <= 8789; i++) {
                for (Participacion_DTO dto : Reporte) {
                    //CONTENIDO DE LA TABLA EN EL REPORTE            
                    //PdfPCell Folio = new PdfPCell(new Paragraph(String.valueOf(i), Content_Font));
                    PdfPCell Folio = new PdfPCell(new Paragraph(String.valueOf(dto.getOrden().getFolio_Unidad()), Content_Font));
                    Folio.setHorizontalAlignment(Element.ALIGN_CENTER);
                    Folio.setBorder(PdfPCell.BOTTOM);
                    table.addCell(Folio);

                    //PdfPCell Fecha = new PdfPCell(new Paragraph("fecha", Content_Font));
                    PdfPCell CllFecha = new PdfPCell(new Paragraph(dto.getOrden().getFecha(), Content_Font));
                    CllFecha.setHorizontalAlignment(Element.ALIGN_CENTER);
                    CllFecha.setBorder(PdfPCell.BOTTOM);
                    table.addCell(CllFecha);

                    if (dto.getOrden().getMedico().getNombre() != null) {
                        PdfPCell Medico = new PdfPCell(new Paragraph(dto.getOrden().getMedico().getNombre().toLowerCase().trim() + " " + dto.getOrden().getMedico().getAp_Paterno().toLowerCase().trim() + " " + dto.getOrden().getMedico().getAp_Materno().toLowerCase().trim(), Content_Font));
                        Medico.setHorizontalAlignment(Element.ALIGN_CENTER);
                        Medico.setBorder(PdfPCell.BOTTOM);
                        table.addCell(Medico);
                    } else {
                        PdfPCell Medico = new PdfPCell(new Paragraph("-----", Content_Font));
                        Medico.setHorizontalAlignment(Element.ALIGN_CENTER);
                        Medico.setBorder(PdfPCell.BOTTOM);
                        table.addCell(Medico);
                    }

                    if (dto.getOrden().getPaciente().getNombre() != null) {
                        PdfPCell Paciente = new PdfPCell(new Paragraph(dto.getOrden().getPaciente().getNombre().toLowerCase().trim() + " " + dto.getOrden().getPaciente().getAp_Paterno().toLowerCase().trim() + " " + dto.getOrden().getPaciente().getAp_Materno().toLowerCase().trim(), Content_Font));
                        Paciente.setHorizontalAlignment(Element.ALIGN_CENTER);
                        Paciente.setBorder(PdfPCell.BOTTOM);
                        table.addCell(Paciente);
                    } else {
                        PdfPCell Paciente = new PdfPCell(new Paragraph("-----", Content_Font));
                        Paciente.setHorizontalAlignment(Element.ALIGN_CENTER);
                        Paciente.setBorder(PdfPCell.BOTTOM);
                        table.addCell(Paciente);
                    }

                    String strEstudios = "";
                    for (Det_Orden_DTO deto : dto.getOrden().getDet_Orden()) {
                        if (deto.getEstudio().getClave_Estudio() != null) {
                            strEstudios = strEstudios + deto.getEstudio().getClave_Estudio().trim() + ",";
                        }
                    }

                    PdfPCell Estudios = new PdfPCell(new Paragraph(strEstudios.toLowerCase(), Content_Font));
                    //PdfPCell Estudios = new PdfPCell(new Paragraph("Estudios", Content_Font));
                    Estudios.setHorizontalAlignment(Element.ALIGN_CENTER);
                    Estudios.setBorder(PdfPCell.BOTTOM);
                    table.addCell(Estudios);
                    PdfPCell montoOrden = new PdfPCell(new Paragraph("$" + String.valueOf(Util.redondearDecimales(dto.getOrden().getMontoRestante() + dto.getOrden().getMontoPagado())), Content_Font));
                    montoOrden.setHorizontalAlignment(Element.ALIGN_CENTER);
                    montoOrden.setBorder(PdfPCell.BOTTOM);
                    table.addCell(montoOrden);

                    PdfPCell Participacion = new PdfPCell(new Paragraph("$" + String.valueOf(Util.redondearDecimales(dto.getMonto())), Content_Font));
                    Participacion.setHorizontalAlignment(Element.ALIGN_CENTER);
                    Participacion.setBorder(PdfPCell.BOTTOM);
                    table.addCell(Participacion);
                    montoTotal += Util.redondearDecimales(dto.getMonto());
                }
                //FINALZA CONTENIDO
                System.out.println("FINALZA CONTENIDO");
                if (Corte) {

                    PdfPCell Espacio2 = new PdfPCell(new Paragraph("       "));
                    Espacio2.setHorizontalAlignment(Element.ALIGN_LEFT);
                    Espacio2.setBorder(0);
                    Espacio2.setColspan(7);
                    table.addCell(Espacio2);

                    PdfPCell Espacio3 = new PdfPCell(new Paragraph("       "));
                    Espacio3.setHorizontalAlignment(Element.ALIGN_LEFT);
                    Espacio3.setBorder(0);
                    table.addCell(Espacio3);

                    PdfPCell TitMontoTotalPart = new PdfPCell(new Paragraph("Monto Total de Participaciones a Pagar: "));
                    TitMontoTotalPart.setBorderColor(BaseColor.RED);
                    TitMontoTotalPart.setHorizontalAlignment(Element.ALIGN_CENTER);
                    TitMontoTotalPart.setColspan(4);
                    table.addCell(TitMontoTotalPart);

                    PdfPCell CellMontoTotalPart = new PdfPCell(new Paragraph("$" + String.valueOf(montoTotal)));
                    CellMontoTotalPart.setBorderColor(BaseColor.RED);
                    CellMontoTotalPart.setHorizontalAlignment(Element.ALIGN_CENTER);
                    table.addCell(CellMontoTotalPart);
                    System.out.println("Imprime Resultado FIN");
                }
            }

            ColumnText column = new ColumnText(stamper.getOverContent(1));
            Rectangle rectPage1 = new Rectangle(-32, 60, 830, 520);//izq,esp-inf,ancho,alto (SON COORDENADAS EN LA HOJA DEL PDF Y EL TAMAÑO DEL ELEMENTO A AGREGAR)
            //(SANGRIA IZQ,Espacio que queda al final de la hoja,Ancho de Elemento,(no recuerdo))
            column.setSimpleColumn(rectPage1);//envia propiedades del contenido(tamaño)
            column.addElement(table);//añade la tabla creada

            Rectangle rectPage2 = new Rectangle(-32, 60, 830, 520);//0,esp-inf,ancho,alto
            int status = column.go();
            while (ColumnText.hasMoreText(status)) {
                pagecount++;
                f.setHora(fac);
                PdfContentByte pageI;
                stamper.insertPage(pagecount, cover.getPageSizeWithRotation(1));
                pageI = stamper.getOverContent(pagecount);
                //        PrintHead(cb, f, persona);
                PdfImportedPage pageA = stamper.getImportedPage(cover, 1);
                pageI.addTemplate(pageA, 0, 0);
                column.setCanvas(pageI);
                column.setSimpleColumn(rectPage1);
                status = column.go();
            }
            stamper.setFormFlattening(true);
            stamper.close();
            reader.close();

        } catch (DocumentException | IOException ex) {
            ex.printStackTrace();
        }
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
