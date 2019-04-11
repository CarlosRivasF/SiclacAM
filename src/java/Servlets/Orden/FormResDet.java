package Servlets.Orden;

import DataBase.Util;
import DataTransferObject.Configuracion_DTO;
import DataTransferObject.Det_Orden_DTO;
import DataTransferObject.Orden_DTO;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
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
@WebServlet(name = "FormResDet", urlPatterns = {"/FormResDet"})
public class FormResDet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        if (request.getParameter("index") != null) {
            HttpSession sesion = request.getSession();
            if (sesion.getAttribute("OrdenSh") != null) {
                Orden_DTO Orden = (Orden_DTO) sesion.getAttribute("OrdenSh");
                int index = Integer.parseInt(request.getParameter("index").trim());
                Det_Orden_DTO det = Orden.getDet_Orden().get(index);
                Util f = new Util();
                Period edad = f.getEdad(Orden.getPaciente().getFecha_Nac().trim());
                String sexo = Orden.getPaciente().getSexo().toUpperCase();
                List<Configuracion_DTO> cnfRs = new ArrayList<>();
                for (Configuracion_DTO cnf : det.getEstudio().getCnfs()) {
                    String[] CFsexo = cnf.getSexo().split("-");
                    String ed = CFsexo[0].trim().toUpperCase();
                    String sex = CFsexo[1].trim().toUpperCase();
                    switch (ed) {
                        case "RN":
                            switch (sex) {
                                case "AMBOS":
                                    if (edad.getYears() == 0 && edad.getMonths() < 6) {
                                        cnfRs.add(cnf);
                                    }
                                    break;
                                case "MASCULINO":
                                    if (edad.getYears() == 0 && edad.getMonths() < 6) {
                                        if (sex.equals(sexo)) {
                                            cnfRs.add(cnf);
                                        }
                                    }
                                    break;
                                case "FEMENINO":
                                    if (edad.getYears() == 0 && edad.getMonths() < 6) {
                                        if (sex.equals(sexo)) {
                                            cnfRs.add(cnf);
                                        }
                                    }
                                    break;
                            }
                            break;
                        case "N":
                            switch (sex) {
                                case "AMBOS":
                                    if (edad.getYears() == 0 && edad.getMonths() >= 6) {
                                        cnfRs.add(cnf);
                                    } else if (edad.getYears() < 12) {
                                        cnfRs.add(cnf);
                                    }
                                    break;
                                case "MASCULINO":
                                    if (edad.getYears() == 0 && edad.getMonths() >= 6) {
                                        if (sex.equals(sexo)) {
                                            cnfRs.add(cnf);
                                        }
                                    } else if (edad.getYears() < 12) {
                                        if (sex.equals(sexo)) {
                                            cnfRs.add(cnf);
                                        }
                                    }
                                    break;
                                case "FEMENINO":
                                    if (edad.getYears() == 0 && edad.getMonths() >= 6) {
                                        if (sex.equals(sexo)) {
                                            cnfRs.add(cnf);
                                        }
                                    } else if (edad.getYears() < 12) {
                                        if (sex.equals(sexo)) {
                                            cnfRs.add(cnf);
                                        }
                                    }
                                    break;
                            }
                            break;
                        case "A":
                            switch (sex) {
                                case "AMBOS":
                                    if (edad.getYears() >= 12 && edad.getMonths() < 60) {
                                        cnfRs.add(cnf);
                                    }
                                    break;
                                case "MASCULINO":
                                    if (edad.getYears() >= 12 && edad.getMonths() < 60) {
                                        if (sex.equals(sexo)) {
                                            cnfRs.add(cnf);
                                        }
                                    }
                                    break;
                                case "FEMENINO":
                                    if (edad.getYears() >= 12 && edad.getMonths() < 60) {
                                        if (sex.equals(sexo)) {
                                            cnfRs.add(cnf);
                                        }
                                    }
                                    break;
                            }
                            break;
                        case "3E":
                            switch (sex) {
                                case "AMBOS":
                                    if (edad.getYears() > 60) {
                                        cnfRs.add(cnf);
                                    }
                                    break;
                                case "MASCULINO":
                                    if (edad.getYears() > 60) {
                                        if (sex.equals(sexo)) {
                                            cnfRs.add(cnf);
                                        }
                                    }
                                    break;
                                case "FEMENINO":
                                    if (edad.getYears() > 60) {
                                        if (sex.equals(sexo)) {
                                            cnfRs.add(cnf);
                                        }
                                    }
                                    break;
                            }
                            break;
                        case "ALL":
                            switch (sex) {
                                case "AMBOS":
                                    cnfRs.add(cnf);
                                    break;
                                case "MASCULINO":
                                    if (sex.equals(sexo)) {
                                        cnfRs.add(cnf);
                                    }
                                    break;
                                case "FEMENINO":
                                    if (sex.equals(sexo)) {
                                        cnfRs.add(cnf);
                                    }
                                    break;
                            }
                            break;
                    }
                }

                det.getEstudio().setCnfs(cnfRs);
                Orden.getDet_Orden().set(index, det);
                sesion.setAttribute("OrdenSh", Orden);
                if (det.getEstudio().getAddRes()) {
                    Boolean r;
                    Boolean r1 = false;
                    //<textarea class="form-control" id="exampleFormControlTextarea1" rows="3"></textarea>
                    if (det.getEstudio().getId_Tipo_Estudio() == 2 || det.getEstudio().getId_Tipo_Estudio() == 4 || det.getEstudio().getId_Tipo_Estudio() == 5 || det.getEstudio().getId_Tipo_Estudio() == 6) {

                        for (Configuracion_DTO cnf : det.getEstudio().getCnfs()) {
                            //out.println("<div id='diValRes-" + det.getEstudio().getCnfs().indexOf(cnf) + "'><textarea rows='15' class='form-control form-control-sm' name='valRes-" + det.getEstudio().getCnfs().indexOf(cnf) + "' id='valRes-" + det.getEstudio().getCnfs().indexOf(cnf) + "' placeholder='Valoración del Médico...' required></textarea></div>");
                            r = false;
                            if (cnf.getRes() != null) {
                                out.println("<div class='offset-2 col-8 mb-3' id='diValRes-" + det.getEstudio().getCnfs().indexOf(cnf) + "'><textarea rows='15' class='form-control form-control-sm' name='valRes-" + det.getEstudio().getCnfs().indexOf(cnf) + "' id='valRes-" + det.getEstudio().getCnfs().indexOf(cnf) + "' placeholder='Valoración del Médico...' readonly>" + cnf.getRes().getValor_Obtenido() + "</textarea></div>");
                            } else {
                                r = true;
                                r1 = true;
                                out.println("<div class='offset-2 col-8 mb-3' id='diValRes-" + det.getEstudio().getCnfs().indexOf(cnf) + "'><textarea rows='15' class='form-control form-control-sm' name='valRes-" + det.getEstudio().getCnfs().indexOf(cnf) + "' id='valRes-" + det.getEstudio().getCnfs().indexOf(cnf) + "' placeholder='Valoración del Médico...' required></textarea></div>");
                            }
                            out.println("<br>");
                            if (!r) {
                                out.print("<div id='BTdiValRes-" + det.getEstudio().getCnfs().indexOf(cnf) + "'><button href=# class='btn btn-warning btn-sm btn-block' onclick=FormUpRes(" + index + "," + det.getEstudio().getCnfs().indexOf(cnf) + ",'form'," + det.getEstudio().getId_Tipo_Estudio() + ") >Modificar valoración de médico</button></div>");
                            }
                            out.print("<br>");
                        }
                    } else {
                        out.println("<table style=' text-align: center' class='table table-bordered table-hover table-sm'>"
                                + "<tr class='table-info' style='color: black'>"
                                + "<th >Desc</th>"
                                + "<th >Val 1</th>"
                                + "<th >Val 2</th>"
                                + "<th >Resultado</th>"
                                + "<th >Unidades</th>"
                                + "<th >Modificar</th>"
                                + "</tr>");
                        for (Configuracion_DTO cnf : det.getEstudio().getCnfs()) {
                            r = false;
                            out.println("<tr>"
                                    + "<td >" + cnf.getDescripcion() + "</td>"
                                    + "<td >" + cnf.getValor_min() + "</td>"
                                    + "<td >" + cnf.getValor_MAX() + "</td>");
                            if (cnf.getRes() != null) {
                                out.println("<td ><div id='diValRes-" + det.getEstudio().getCnfs().indexOf(cnf) + "'>" + cnf.getRes().getValor_Obtenido() + "</div></td>");
                            } else {
                                r = true;
                                r1 = true;
                                out.println("<td><div id='diValRes-" + det.getEstudio().getCnfs().indexOf(cnf) + "'><input style='text-align: center' type='text' class='form-control form-control-sm' name='valRes-" + det.getEstudio().getCnfs().indexOf(cnf) + "' id='valRes-" + det.getEstudio().getCnfs().indexOf(cnf) + "' placeholder='Resultado' required></div></td>");
                            }
                            out.println("<td >" + cnf.getUniddes() + "</td>");
                            if (!r) {
                                out.print("<th><div id='BTdiValRes-" + det.getEstudio().getCnfs().indexOf(cnf) + "'><button href=# class='btn btn-warning btn-sm' onclick=FormUpRes(" + index + "," + det.getEstudio().getCnfs().indexOf(cnf) + ",'form') ><span><img src='images/pencil.png'></span></button></div></th>");
                            }
                            out.print("</tr>");
                        }
                        //Observaciones
                        if (det.getEstudio().getObservacion() == null || det.getEstudio().getObservacion().getObservacion() == null) {
                            out.println("<tr>"
                                    + "<td colspan='5'><div id='diValObs-" + index + "'><div></td>");
                            out.print("<th><div id='BTdiValObs-" + index + "'><button href=# class='btn btn-warning btn-sm' onclick=FormUpResObs(" + index + ",'form') ><span><img src='images/pencil.png'></span></button></div></th>");
                            out.println("</tr>");
                        } else {
                            out.println("<tr>"
                                    + "<td colspan='5'><div id='diValObs-" + index + "'>" + det.getEstudio().getObservacion().getObservacion() + "<div></td>");
                            out.print("<th><div id='BTdiValObs-" + index + "'><button href=# class='btn btn-warning btn-sm' onclick=FormUpResObs(" + index + ",'form') ><span><img src='images/pencil.png'></span></button></div></th>");
                            out.println("</tr>");
                        }
                        out.println("</table>");
                    }
                } else {
                    if (det.getEstudio().getId_Tipo_Estudio() == 2 || det.getEstudio().getId_Tipo_Estudio() == 4 || det.getEstudio().getId_Tipo_Estudio() == 5 || det.getEstudio().getId_Tipo_Estudio() == 6) {
                        for (Configuracion_DTO cnf : det.getEstudio().getCnfs()) {
                            out.println("<div class='offset-2 col-8 mb-3' id='diValRes-" + det.getEstudio().getCnfs().indexOf(cnf) + "'><textarea rows='15' class='form-control form-control-sm' name='valRes-" + det.getEstudio().getCnfs().indexOf(cnf) + "' id='valRes-" + det.getEstudio().getCnfs().indexOf(cnf) + "' placeholder='Valoración del Médico...' required></textarea></div>");
                            out.println("<br>");//                        
                        }
                        out.println("<input style='text-align: center' type='text' class='form-control form-control-sm' name='Observ-" + index + "' id='Observ-" + index + "' placeholder='Obervaciones...' required><br>");
                        out.println("<div id='addcnf'><button href=# class='btn btn-success btn-block' onclick=SaveResDet(" + index + "," + det.getEstudio().getCnfs().size() + ")>Guardar Resultados</button></div>");
                    } else {
                        //Tabla de Resultados
                        out.println("<table style=' text-align: center' class='table table-bordered table-hover table-sm'>"
                                + "<tr class='table-info' style='color: black'>"
                                + "<th >Desc</th>"
                                + "<th >Val 1</th>"
                                + "<th >Val 2</th>"
                                + "<th >Resultado</th>"
                                + "<th >Unidades</th>"
                                + "</tr>");
                        det.getEstudio().getCnfs().forEach((cnf) -> {
                            out.println("<tr>"
                                    + "<td >" + cnf.getDescripcion() + "</td>"
                                    + "<td >" + cnf.getValor_min() + "</td>"
                                    + "<td >" + cnf.getValor_MAX() + "</td>"
                                    + "<td><div id='diValRes-" + det.getEstudio().getCnfs().indexOf(cnf) + "'><input style='text-align: center' type='text' class='form-control form-control-sm' name='valRes-" + det.getEstudio().getCnfs().indexOf(cnf) + "' id='valRes-" + det.getEstudio().getCnfs().indexOf(cnf) + "' placeholder='Resultado' required></div></td>"
                                    + "<td >" + cnf.getUniddes() + "</td>"
                                    + "</tr>");
                        });
                        //Observaciones
                        out.println("<tr>"
                                + "<td colspan='5'><input style='text-align: center' type='text' class='form-control form-control-sm' name='Observ-" + index + "' id='Observ-" + index + "' placeholder='Obervaciones...' required></td>"
                                + "</tr>");
                        //Boton de Guardar Resultados
                        out.println("<tr>"
                                + "<td colspan='5'><div id='addcnf'><button href=# class='btn btn-success btn-block' onclick=SaveResDet(" + index + "," + det.getEstudio().getCnfs().size() + ")>Guardar Resultados</button></div></td>"
                                + "</tr>");
                        out.println("</table>");
                    }
                }
            } else {
                out.println("<h3>Vuelava a realizar su busqueda. Ocurrio un error al cargar la Órden Solicitada</h3>");
            }
        } else {
            out.println("<h3>Vuelava a realizar su busqueda. Ocurrio un error al cargar la Órden Solicitada</h3>");
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
