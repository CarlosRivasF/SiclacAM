package Servlets.Orden;

import DataAccesObject.Orden_DAO;
import DataAccesObject.Persona_DAO;
import DataBase.Util;
import DataTransferObject.Det_Orden_DTO;
import DataTransferObject.Empleado_DTO;
import DataTransferObject.Orden_DTO;
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
 * @author c
 */
@WebServlet(name = "PrintReporteOrders", urlPatterns = {"/PrintReporteOrders"})
public class PrintReporteOrders extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) {
        Float montoTotalPagado = Float.parseFloat(String.valueOf("0"));
        Float montoTotalRestante = Float.parseFloat(String.valueOf("0"));
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
        String Emps;
        Boolean Corte = false;
        Boolean Empls = false;

        if (request.getParameter("Crte") != null) {
            Crte = request.getParameter("Crte").trim();
            if (Crte.equals("Ys")) {
                Corte = true;
            }
        }
        if (request.getParameter("Emps") != null) {
            Emps = request.getParameter("Emps").trim();
            if (Emps.equals("Ys")) {
                Empls = true;
            }
        }

        try {
            List<Orden_DTO> Reporte;
            List<Empleado_DTO> ReporteEmpleado = null;
            Orden_DAO O = new Orden_DAO();
            Reporte = O.getReporteGeneralOrdenes(id_Unidad, f1, f2);//recupera lista de ordenes
            if (Empls) {
                ReporteEmpleado = new ArrayList<>();
                int c = 0;
                int TEMPid_Empleado = 0;
                Empleado_DTO empleado = null;
                for (Orden_DTO dto : Reporte) {
                    c++;
                    if (dto.getEmpleado().getId_Persona() != TEMPid_Empleado) {
                        if (c != 1) {
                            System.out.println("AddEmpleado to ReporteEmpleado");
                            ReporteEmpleado.add(empleado);
                        }
                        TEMPid_Empleado = dto.getEmpleado().getId_Persona();
                        empleado = new Empleado_DTO();
                        empleado = dto.getEmpleado();
                        empleado.getOrdenes().add(dto);
                    } else {
                        empleado.getOrdenes().add(dto);
                    }
                }
                System.out.println("AddEmpleadoFIN to ReporteEmpleado");
                ReporteEmpleado.add(empleado);
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
            if (Corte) {
                table = new PdfPTable(7);
            } else {
                table = new PdfPTable(5);
            }

            //COMIENZA TABLA DE CATALOGO
            table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
            table.getDefaultCell().setBorder(1);
            if (Corte) {
                table.setWidths(new int[]{3, 5, 12, 12, 10, 7, 8});
            } else {
                table.setWidths(new int[]{3, 5, 12, 12, 10});
            }
            //HEADERS DEL REPORTE
            int c = 0;
            if (Empls) {
                System.out.println("Reporte con empleados");
                for (Empleado_DTO empl : ReporteEmpleado) {
                    c++;
                    if (c != 1) {
                        PdfPCell Espacio1 = new PdfPCell(new Paragraph("       "));
                        Espacio1.setHorizontalAlignment(Element.ALIGN_LEFT);
                        Espacio1.setColspan(7);
                        Espacio1.setBorder(0);
                        table.addCell(Espacio1);
                    }
                    PdfPCell Espacio0 = new PdfPCell(new Paragraph("Empleado: " + empl.getNombre().toUpperCase() + " " + empl.getAp_Paterno().toUpperCase() + " " + empl.getAp_Materno().toUpperCase()));
                    Espacio0.setHorizontalAlignment(Element.ALIGN_LEFT);
                    Espacio0.setColspan(7);
                    Espacio0.setBorder(0);
                    table.addCell(Espacio0);

                    PdfPCell HFolio = new PdfPCell(new Paragraph("Folio"));
                    HFolio.setBorderColor(BaseColor.RED);
                    HFolio.setHorizontalAlignment(Element.ALIGN_CENTER);
                    table.addCell(HFolio);
                    PdfPCell HFecha = new PdfPCell(new Paragraph("Fecha"));
                    HFecha.setBorderColor(BaseColor.RED);
                    HFecha.setHorizontalAlignment(Element.ALIGN_CENTER);
                    table.addCell(HFecha);
                    PdfPCell HMedico = new PdfPCell(new Paragraph("Médico"));
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

                    if (Corte) {
                        PdfPCell HMontoPagado = new PdfPCell(new Paragraph("MontoPagado"));
                        HMontoPagado.setBorderColor(BaseColor.RED);
                        HMontoPagado.setHorizontalAlignment(Element.ALIGN_CENTER);
                        table.addCell(HMontoPagado);
                        PdfPCell HMontoRestante = new PdfPCell(new Paragraph("MontoRestante"));
                        HMontoRestante.setBorderColor(BaseColor.RED);
                        HMontoRestante.setHorizontalAlignment(Element.ALIGN_CENTER);
                        table.addCell(HMontoRestante);
                    }
                    Float montoPagadoEmpl = Float.parseFloat(String.valueOf("0"));
                    Float montoRestanteEmpl = Float.parseFloat(String.valueOf("0"));
                    //for (int i = 1; i <= 8789; i++) {
                    for (Orden_DTO dto : empl.getOrdenes()) {
                        //CONTENIDO DE LA TABLA EN EL REPORTE            
                        //PdfPCell Folio = new PdfPCell(new Paragraph(String.valueOf(i), Content_Font));
                        PdfPCell Folio = new PdfPCell(new Paragraph(String.valueOf(dto.getFolio_Unidad()), Content_Font));
                        Folio.setHorizontalAlignment(Element.ALIGN_CENTER);
                        Folio.setBorder(PdfPCell.BOTTOM);
                        table.addCell(Folio);

                        //PdfPCell Fecha = new PdfPCell(new Paragraph("fecha", Content_Font));
                        PdfPCell CllFecha = new PdfPCell(new Paragraph(dto.getFecha(), Content_Font));
                        CllFecha.setHorizontalAlignment(Element.ALIGN_CENTER);
                        CllFecha.setBorder(PdfPCell.BOTTOM);
                        table.addCell(CllFecha);

                        if (dto.getMedico().getNombre() != null) {
                            PdfPCell Medico = new PdfPCell(new Paragraph(dto.getMedico().getNombre().toLowerCase().trim() + " " + dto.getMedico().getAp_Paterno().toLowerCase().trim() + " " + dto.getMedico().getAp_Materno().trim().toLowerCase(), Content_Font));
                            Medico.setHorizontalAlignment(Element.ALIGN_CENTER);
                            Medico.setBorder(PdfPCell.BOTTOM);
                            table.addCell(Medico);
                        } else {
                            PdfPCell Medico = new PdfPCell(new Paragraph("-----", Content_Font));
                            Medico.setHorizontalAlignment(Element.ALIGN_CENTER);
                            Medico.setBorder(PdfPCell.BOTTOM);
                            table.addCell(Medico);
                        }

                        if (dto.getPaciente().getNombre() != null) {
                            PdfPCell Paciente = new PdfPCell(new Paragraph(dto.getPaciente().getNombre().toLowerCase().trim() + " " + dto.getPaciente().getAp_Paterno().toLowerCase().trim() + " " + dto.getPaciente().getAp_Materno().toLowerCase().trim(), Content_Font));
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
                        for (Det_Orden_DTO deto : dto.getDet_Orden()) {
                            if (deto.getEstudio().getClave_Estudio() != null) {
                                strEstudios = strEstudios + deto.getEstudio().getClave_Estudio().trim() + ",";
                            }
                        }

                        PdfPCell Estudios = new PdfPCell(new Paragraph(strEstudios.toLowerCase(), Content_Font));
                        //PdfPCell Estudios = new PdfPCell(new Paragraph("Estudios", Content_Font));
                        Estudios.setHorizontalAlignment(Element.ALIGN_CENTER);
                        Estudios.setBorder(PdfPCell.BOTTOM);
                        table.addCell(Estudios);
                        if (Corte) {
                            //PdfPCell MontoPagado = new PdfPCell(new Paragraph(String.valueOf(300 + i), Content_Font));
                            PdfPCell MontoPagado = new PdfPCell(new Paragraph(String.valueOf(Util.redondearDecimales(dto.getMontoPagado())), Content_Font));
                            MontoPagado.setHorizontalAlignment(Element.ALIGN_CENTER);
                            MontoPagado.setBorder(PdfPCell.BOTTOM);
                            table.addCell(MontoPagado);
                            montoPagadoEmpl += Util.redondearDecimales(dto.getMontoPagado());
                            montoTotalPagado += Util.redondearDecimales(dto.getMontoPagado());

                            //PdfPCell MontoRestante = new PdfPCell(new Paragraph(String.valueOf(20 + i), Content_Font));
                            PdfPCell MontoRestante = new PdfPCell(new Paragraph(String.valueOf(Util.redondearDecimales(dto.getMontoRestante())), Content_Font));
                            MontoRestante.setHorizontalAlignment(Element.ALIGN_CENTER);
                            MontoRestante.setBorder(PdfPCell.BOTTOM);
                            table.addCell(MontoRestante);
                            montoTotalRestante += Util.redondearDecimales(dto.getMontoRestante());
                            montoRestanteEmpl += Util.redondearDecimales(dto.getMontoRestante());
                        }
                    }
                    //FINALZA CONTENIDO

                    if (Corte) {
                        PdfPCell Espacio2 = new PdfPCell(new Paragraph("       "));
                        Espacio2.setHorizontalAlignment(Element.ALIGN_LEFT);
                        Espacio2.setBorder(0);
                        table.addCell(Espacio2);

                        PdfPCell TitMontoTotalPagado = new PdfPCell(new Paragraph("Monto Pagado"));
                        TitMontoTotalPagado.setBorderColor(BaseColor.RED);
                        TitMontoTotalPagado.setHorizontalAlignment(Element.ALIGN_CENTER);
                        TitMontoTotalPagado.setColspan(2);
                        table.addCell(TitMontoTotalPagado);
                        PdfPCell CellMontoTotalPagado = new PdfPCell(new Paragraph(String.valueOf(montoPagadoEmpl)));
                        CellMontoTotalPagado.setBorderColor(BaseColor.RED);
                        CellMontoTotalPagado.setHorizontalAlignment(Element.ALIGN_CENTER);
                        table.addCell(CellMontoTotalPagado);

                        PdfPCell TitMontoTotalRest = new PdfPCell(new Paragraph("Monto Restante"));
                        TitMontoTotalRest.setBorderColor(BaseColor.RED);
                        TitMontoTotalRest.setHorizontalAlignment(Element.ALIGN_CENTER);
                        TitMontoTotalRest.setColspan(2);
                        table.addCell(TitMontoTotalRest);
                        PdfPCell CellMontoTotalRest = new PdfPCell(new Paragraph(String.valueOf(montoRestanteEmpl)));
                        CellMontoTotalRest.setBorderColor(BaseColor.RED);
                        CellMontoTotalRest.setHorizontalAlignment(Element.ALIGN_CENTER);
                        table.addCell(CellMontoTotalRest);
                    }
                }
                if (Corte) {
                    PdfPCell Espacio1 = new PdfPCell(new Paragraph("       "));
                    Espacio1.setHorizontalAlignment(Element.ALIGN_LEFT);
                    Espacio1.setColspan(7);
                    Espacio1.setBorder(0);
                    table.addCell(Espacio1);

                    PdfPCell Espacio2 = new PdfPCell(new Paragraph("       "));
                    Espacio2.setHorizontalAlignment(Element.ALIGN_LEFT);
                    Espacio2.setBorder(0);
                    table.addCell(Espacio2);

                    PdfPCell TitMontoTotalPagado = new PdfPCell(new Paragraph("Monto TOTAL Pagado"));
                    TitMontoTotalPagado.setBorderColor(BaseColor.RED);
                    TitMontoTotalPagado.setHorizontalAlignment(Element.ALIGN_CENTER);
                    TitMontoTotalPagado.setColspan(2);
                    table.addCell(TitMontoTotalPagado);
                    PdfPCell CellMontoTotalPagado = new PdfPCell(new Paragraph(String.valueOf(montoTotalPagado)));
                    CellMontoTotalPagado.setBorderColor(BaseColor.RED);
                    CellMontoTotalPagado.setHorizontalAlignment(Element.ALIGN_CENTER);
                    table.addCell(CellMontoTotalPagado);

                    PdfPCell TitMontoTotalRest = new PdfPCell(new Paragraph("Monto TOTAL Restante"));
                    TitMontoTotalRest.setBorderColor(BaseColor.RED);
                    TitMontoTotalRest.setHorizontalAlignment(Element.ALIGN_CENTER);
                    TitMontoTotalRest.setColspan(2);
                    table.addCell(TitMontoTotalRest);
                    PdfPCell CellMontoTotalRest = new PdfPCell(new Paragraph(String.valueOf(montoTotalRestante)));
                    CellMontoTotalRest.setBorderColor(BaseColor.RED);
                    CellMontoTotalRest.setHorizontalAlignment(Element.ALIGN_CENTER);
                    table.addCell(CellMontoTotalRest);
                }
            } else {
                PdfPCell HFolio = new PdfPCell(new Paragraph("Folio"));
                HFolio.setBorderColor(BaseColor.RED);
                HFolio.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(HFolio);
                PdfPCell HFecha = new PdfPCell(new Paragraph("Fecha"));
                HFecha.setBorderColor(BaseColor.RED);
                HFecha.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(HFecha);
                PdfPCell HMedico = new PdfPCell(new Paragraph("Médico"));
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

                if (Corte) {
                    PdfPCell HMontoPagado = new PdfPCell(new Paragraph("MontoPagado"));
                    HMontoPagado.setBorderColor(BaseColor.RED);
                    HMontoPagado.setHorizontalAlignment(Element.ALIGN_CENTER);
                    table.addCell(HMontoPagado);
                    PdfPCell HMontoRestante = new PdfPCell(new Paragraph("MontoRestante"));
                    HMontoRestante.setBorderColor(BaseColor.RED);
                    HMontoRestante.setHorizontalAlignment(Element.ALIGN_CENTER);
                    table.addCell(HMontoRestante);
                }

                //for (int i = 1; i <= 8789; i++) {
                for (Orden_DTO dto : Reporte) {
                    //CONTENIDO DE LA TABLA EN EL REPORTE            
                    //PdfPCell Folio = new PdfPCell(new Paragraph(String.valueOf(i), Content_Font));
                    PdfPCell Folio = new PdfPCell(new Paragraph(String.valueOf(dto.getFolio_Unidad()), Content_Font));
                    Folio.setHorizontalAlignment(Element.ALIGN_CENTER);
                    Folio.setBorder(PdfPCell.BOTTOM);
                    table.addCell(Folio);

                    //PdfPCell Fecha = new PdfPCell(new Paragraph("fecha", Content_Font));
                    PdfPCell CllFecha = new PdfPCell(new Paragraph(dto.getFecha(), Content_Font));
                    CllFecha.setHorizontalAlignment(Element.ALIGN_CENTER);
                    CllFecha.setBorder(PdfPCell.BOTTOM);
                    table.addCell(CllFecha);

                    if (dto.getMedico().getNombre() != null) {
                        PdfPCell Medico = new PdfPCell(new Paragraph(dto.getMedico().getNombre().toLowerCase().trim() + " " + dto.getMedico().getAp_Paterno().toLowerCase().trim() + " " + dto.getMedico().getAp_Materno().trim().toLowerCase(), Content_Font));
                        Medico.setHorizontalAlignment(Element.ALIGN_CENTER);
                        Medico.setBorder(PdfPCell.BOTTOM);
                        table.addCell(Medico);
                    } else {
                        PdfPCell Medico = new PdfPCell(new Paragraph("-----", Content_Font));
                        Medico.setHorizontalAlignment(Element.ALIGN_CENTER);
                        Medico.setBorder(PdfPCell.BOTTOM);
                        table.addCell(Medico);
                    }

                    if (dto.getPaciente().getNombre() != null) {
                        PdfPCell Paciente = new PdfPCell(new Paragraph(dto.getPaciente().getNombre().toLowerCase().trim() + " " + dto.getPaciente().getAp_Paterno().toLowerCase().trim() + " " + dto.getPaciente().getAp_Materno().toLowerCase().trim(), Content_Font));
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
                    for (Det_Orden_DTO deto : dto.getDet_Orden()) {
                        if (deto.getEstudio().getClave_Estudio() != null) {
                            strEstudios = strEstudios + deto.getEstudio().getClave_Estudio().trim() + ",";
                        }
                    }

                    PdfPCell Estudios = new PdfPCell(new Paragraph(strEstudios.toLowerCase(), Content_Font));
                    //PdfPCell Estudios = new PdfPCell(new Paragraph("Estudios", Content_Font));
                    Estudios.setHorizontalAlignment(Element.ALIGN_CENTER);
                    Estudios.setBorder(PdfPCell.BOTTOM);
                    table.addCell(Estudios);

                    if (Corte) {
                        //PdfPCell MontoPagado = new PdfPCell(new Paragraph(String.valueOf(300 + i), Content_Font));
                        PdfPCell MontoPagado = new PdfPCell(new Paragraph(String.valueOf(Util.redondearDecimales(dto.getMontoPagado())), Content_Font));
                        MontoPagado.setHorizontalAlignment(Element.ALIGN_CENTER);
                        MontoPagado.setBorder(PdfPCell.BOTTOM);
                        table.addCell(MontoPagado);
                        montoTotalPagado += dto.getMontoPagado();

                        //PdfPCell MontoRestante = new PdfPCell(new Paragraph(String.valueOf(20 + i), Content_Font));
                        PdfPCell MontoRestante = new PdfPCell(new Paragraph(String.valueOf(Util.redondearDecimales(dto.getMontoRestante())), Content_Font));
                        MontoRestante.setHorizontalAlignment(Element.ALIGN_CENTER);
                        MontoRestante.setBorder(PdfPCell.BOTTOM);
                        table.addCell(MontoRestante);
                        montoTotalRestante += Util.redondearDecimales(dto.getMontoRestante());
                    }
                }
                //FINALZA CONTENIDO

                if (Corte) {
                    PdfPCell Espacio1 = new PdfPCell(new Paragraph("       "));
                    Espacio1.setHorizontalAlignment(Element.ALIGN_LEFT);
                    Espacio1.setColspan(7);
                    Espacio1.setBorder(0);
                    table.addCell(Espacio1);

                    PdfPCell Espacio2 = new PdfPCell(new Paragraph("       "));
                    Espacio2.setHorizontalAlignment(Element.ALIGN_LEFT);
                    Espacio2.setBorder(0);
                    table.addCell(Espacio2);

                    PdfPCell TitMontoTotalPagado = new PdfPCell(new Paragraph("Monto Total Pagado"));
                    TitMontoTotalPagado.setBorderColor(BaseColor.RED);
                    TitMontoTotalPagado.setHorizontalAlignment(Element.ALIGN_CENTER);
                    TitMontoTotalPagado.setColspan(2);
                    table.addCell(TitMontoTotalPagado);
                    PdfPCell CellMontoTotalPagado = new PdfPCell(new Paragraph(String.valueOf(montoTotalPagado)));
                    CellMontoTotalPagado.setBorderColor(BaseColor.RED);
                    CellMontoTotalPagado.setHorizontalAlignment(Element.ALIGN_CENTER);
                    table.addCell(CellMontoTotalPagado);

                    PdfPCell TitMontoTotalRest = new PdfPCell(new Paragraph("Monto Total Restante"));
                    TitMontoTotalRest.setBorderColor(BaseColor.RED);
                    TitMontoTotalRest.setHorizontalAlignment(Element.ALIGN_CENTER);
                    TitMontoTotalRest.setColspan(2);
                    table.addCell(TitMontoTotalRest);
                    PdfPCell CellMontoTotalRest = new PdfPCell(new Paragraph(String.valueOf(montoTotalRestante)));
                    CellMontoTotalRest.setBorderColor(BaseColor.RED);
                    CellMontoTotalRest.setHorizontalAlignment(Element.ALIGN_CENTER);
                    table.addCell(CellMontoTotalRest);
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

//    public void PrintHead(PdfContentByte cb, Util f, Persona_DTO persona) {
//        try {
//            BaseFont bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
//            BaseFont bf0 = BaseFont.createFont(BaseFont.HELVETICA_BOLD, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
//            BaseFont bf1 = BaseFont.createFont(BaseFont.COURIER, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
//            cb.beginText();
//            cb.setFontAndSize(bf, 10);
//            cb.setTextMatrix(270, 758);
//            cb.showText("Fecha de Reporte:");
//            cb.endText();
//            cb.beginText();
//            cb.setFontAndSize(bf1, 12);
//            cb.setTextMatrix(355, 758);
//            cb.showText(f.getFechaActual());
//            cb.endText();
//            ////////////////////////// DATOS PACIENTE
//            cb.beginText();
//            cb.setFontAndSize(bf, 10);
//            cb.setTextMatrix(270, 740);
//            cb.showText("Emitió:");
//            cb.endText();
//            cb.beginText();
//            cb.setFontAndSize(bf1, 12);
//            cb.setTextMatrix(320, 740);
//            cb.showText(persona.getNombre() + " " + persona.getAp_Paterno() + " " + persona.getAp_Materno());
//            cb.endText();
//
////            cb.beginText();
////            cb.setFontAndSize(bf, 10);
////            cb.setTextMatrix(270, 722);
////            cb.showText("Edad:");
////            cb.endText();
////            cb.beginText();
////            cb.setFontAndSize(bf1, 12);
////            cb.setTextMatrix(305, 722);
////            cb.showText(f.getEdad(persona.getFecha_Nac()).getYears() + "");
////            cb.endText();
////            cb.beginText();
////            cb.setFontAndSize(bf, 10);
////            cb.setTextMatrix(340, 722);
////            cb.showText("Sexo:");
////            cb.endText();
////            cb.beginText();
////            cb.setFontAndSize(bf1, 12);
////            cb.setTextMatrix(380, 722);
////            cb.showText(persona.getSexo());
////            cb.endText();
//        } catch (DocumentException | IOException ex) {
//            System.out.println("");
//        }
//    }
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
