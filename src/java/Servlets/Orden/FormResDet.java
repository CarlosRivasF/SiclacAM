package Servlets.Orden;

import DataBase.Fecha;
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
            Orden_DTO Orden = (Orden_DTO) sesion.getAttribute("OrdenSh");
            Fecha f = new Fecha();
            Period edad = f.getEdad(Orden.getPaciente().getFecha_Nac().trim());
            String sexo = Orden.getPaciente().getSexo().toUpperCase();
            int index = Integer.parseInt(request.getParameter("index").trim());
            Det_Orden_DTO det = Orden.getDet_Orden().get(index);
            if (det.getEstudio().getAddRes()) {
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
                            + "<td >" + cnf.getRes().getValor_Obtenido() + "</td>"
                            + "<td >" + cnf.getUniddes() + "</td>"
                            + "</tr>");
                });
                out.println("</table>");
            } else {
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

                out.println("<table style=' text-align: center' class='table table-bordered table-hover table-sm'>"
                        + "<tr class='table-info' style='color: black'>"
                        + "<th >Desc</th>"
                        + "<th >Val 1</th>"
                        + "<th >Val 2</th>"
                        + "<th >Resultado</th>"
                        + "<th >Unidades</th>"
                        + "</tr>");

                cnfRs.forEach((cnf) -> {
                    out.println("<tr>"
                            + "<td >" + cnf.getDescripcion() + "</td>"
                            + "<td >" + cnf.getValor_min() + "</td>"
                            + "<td >" + cnf.getValor_MAX() + "</td>"
                            + "<td><div id='diValRes-" + cnfRs.indexOf(cnf) + "'><input style='text-align: center' type='text' class='form-control form-control-sm' name='valRes-" + cnfRs.indexOf(cnf) + "' id='valRes-" + cnfRs.indexOf(cnf) + "' placeholder='Resultado' required></div></td>"
                            + "<td >" + cnf.getUniddes() + "</td>"
                            + "</tr>");
                });
                out.println("<tr>"
                        + "<td colspan='7'><div id='addcnf'><button href=# class='btn btn-success btn-block' onclick=SaveResDet(" + index + "," + cnfRs.size() + ")>Guardar Resultados</button></div></td>"
                        + "</tr>");
                out.println("</table>");
            }
        } else {
            out.println("<h1>Servlet FormResDet at " + request.getContextPath() + "</h1>");
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
