package Servlets.Empleado;

import DataAccesObject.Empleado_DAO;
import DataAccesObject.Permiso_DAO;
import DataAccesObject.Semana;
import DataTransferObject.Empleado_DTO;
import DataTransferObject.Permiso_DTO;
import java.io.IOException;
import java.io.PrintWriter;
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
 * @author ZionSystems
 */
@WebServlet(name = "FormUpDiPrivHrs", urlPatterns = {"/FormUpDiPrivHrs"})
public class FormUpDiPrivHrs extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        HttpSession sesion = request.getSession();
        int index = Integer.parseInt(request.getParameter("index").trim());
        List<Empleado_DTO> emps;
        emps = (List<Empleado_DTO>) sesion.getAttribute("emps");
        Empleado_DTO empleado = emps.get(index);
        String acc = request.getParameter("acc").trim();
        String part = request.getParameter("part").trim();
        Empleado_DAO E = new Empleado_DAO();
        switch (part) {
            case "DiasEmp":
                Semana S = new Semana();
                List<Semana> semana = S.semana();
                List<Semana> semnaCD = S.semanaCheckDisabled();
                if ("upd".equals(acc)) {
                    List<String> dias = new ArrayList<>();
                    for (int i = 0; i < 8; i++) {
                        if (request.getParameter("dia" + "" + (i + 1) + "") == null) {
                        } else {
                            dias.add(request.getParameter("dia" + (i + 1)));
                        }
                    }
                    E.ActualizarDias(empleado.getId_Empleado(), dias);
                    empleado.setDias_Trabajo(E.getDias(empleado.getId_Empleado()));
                    emps.set(index, empleado);
                    sesion.setAttribute("emps", emps);
                    out.println("<table style=' text-align: center' class='table table-bordered table-hover table-sm'>"
                            + "<tr class='table-warning' style='color: black'>"
                            + "<th >Dias de trabajo</th>"
                            + "<th >Modificar</th>"
                            + "</tr><tr>");
                    out.println("<td align='left'><div class='form-row'>");
                    for (Semana ds : semana) {
                        for (String dt : empleado.getDias_Trabajo()) {
                            if (ds.getDia().equals(dt)) {
                                out.print(semnaCD.get(semana.indexOf(ds)).getInput());
                                break;
                            }
                        }
                    }
                    out.println("</div></td>");
                    out.print("<th align='center'><button href=# class='btn btn-warning btn-sm' onclick=FormUpDiPrivHrs(" + index + ",'form','DiasEmp') ><span><img src='images/pencil.png'></span></button></th>"
                            + "</tr></table>");
                } else {
                    List<Semana> semnaC = S.semanaChecked();
                    out.println("<table style=' text-align: center' class='table table-bordered table-hover table-sm'>"
                            + "<tr class='table-warning' style='color: black'>"
                            + "<th >Dias de trabajo</th>"
                            + "<th >Guardar</th>"
                            + "</tr><tr>");
                    out.println("<td align='left'><div class='form-row'>");
                    boolean found;
                    for (Semana ds : semana) {
                        found = false;
                        for (String dt : empleado.getDias_Trabajo()) {
                            if (ds.getDia().equals(dt)) {
                                found = true;
                                break;
                            }
                        }
                        if (found) {
                            out.print(semnaC.get(semana.indexOf(ds)).getInput());
                        } else {
                            out.print(ds.getInput());
                        }
                    }
                    out.println("</div></td>"
                            + "<th align='center'><button href=# class='btn btn-success btn-sm' onclick=FormUpDiPrivHrs(" + index + ",'upd','DiasEmp') ><span><img src='images/save.png'></span></button></th>"
                            + "</tr></table>");
                }
                break;
            case "PrivsEmp":
                Permiso_DAO P = new Permiso_DAO();
                List<Permiso_DTO> pers = P.getPermisos();
                if ("upd".equals(acc)) {
                    List<String> ps = new ArrayList<>();
                    for (int i = 0; i < 8; i++) {
                        if (request.getParameter("permiso" + "" + (i + 1) + "") == null) {
                        } else {
                            ps.add(request.getParameter("permiso" + (i + 1)));
                        }
                    }
                    P.ActualizarPermisos(empleado.getUsuario().getId_Usuario(), ps);
                    empleado.setPermisos(E.getPers(empleado.getUsuario().getId_Usuario()));
                    emps.set(index, empleado);
                    sesion.setAttribute("emps", emps);
                    out.println("<table style=' text-align: center' class='table table-bordered table-hover table-sm'>"
                            + "<tr class='table-warning' style='color: black'>"
                            + "<th>Privilegios</th>"
                            + "<th >Modificar</th>"
                            + "</tr><tr>");
                    out.println("<td><div class='form-row' style='color: white'>");
                    List<Permiso_DTO> persCD = P.getPermisosCheckedDisabled();
                    for (Permiso_DTO p : pers) {
                        for (String pe : empleado.getPermisos()) {
                            if (p.getNombre().equals(pe)) {
                                out.print(persCD.get(pers.indexOf(p)).getInput());
                                break;
                            }
                        }
                    }
                    out.println("</div></td>");
                    out.print("<th><button href=# class='btn btn-warning btn-sm' onclick=FormUpDiPrivHrs(" + index + ",'form','PrivsEmp') ><span><img src='images/pencil.png'></span></button></th>"
                            + "</tr></table>");
                } else {
                    List<Permiso_DTO> persC = P.getPermisosChecked();
                    out.println("<table style=' text-align: center' class='table table-bordered table-hover table-sm'>"
                            + "<tr class='table-warning' style='color: black'>"
                            + "<th>Privilegios</th>"
                            + "<th >Guardar</th>"
                            + "</tr><tr>");
                    out.println("<td><div class='form-row' style='color: white'>");
                    boolean found;
                    for (Permiso_DTO p : pers) {
                        found = false;
                        for (String pe : empleado.getPermisos()) {
                            if (p.getNombre().equals(pe)) {
                                found = true;
                                break;
                            }
                        }
                        if (found) {
                            out.print(persC.get(pers.indexOf(p)).getInput());
                        } else {
                            out.print(p.getInput());
                        }
                    }
                    out.println("</div></td>");
                    out.print("<th><button href=# class='btn btn-success btn-sm' onclick=FormUpDiPrivHrs(" + index + ",'upd','PrivsEmp') ><span><img src='images/save.png'></span></button></th>"
                            + "</tr></table>");
                }
                break;
            case "HorasEmp":
                if ("upd".equals(acc)) {
                    empleado.setHora_Ent(request.getParameter("horaE").trim());
                    empleado.setHora_Sal(request.getParameter("horaS").trim());
                    empleado.setHora_Com(request.getParameter("horaC").trim());
                    empleado.setHora_Reg(request.getParameter("horaR").trim());
                    E.ActualizarHorario(empleado);
                    out.println("<table style=' text-align: center' class='table table-bordered table-hover table-sm'>"
                            + "<tr class='table-warning' style='color: black'>"
                            + "<th colspan='5' >Horario</th></tr><tr>"
                            + "<th >Hora de Entrada</th>"
                            + "<th >Hora de Salida</th>"
                            + "<th >Hora de Comida</th>"
                            + "<th >Hora de Regreso</th>"
                            + "<th >Modificar</th></tr><tr>"
                            + "<td >" + empleado.getHora_Ent() + "</td>"
                            + "<td >" + empleado.getHora_Sal() + "</td>"
                            + "<td >" + empleado.getHora_Com() + "</td>"
                            + "<td >" + empleado.getHora_Reg() + "</td>"
                            + "<th><button href=# class='btn btn-warning btn-sm' onclick=FormUpDiPrivHrs(" + index + ",'form','HorasEmp') ><span><img src='images/pencil.png'></span></button></th>"
                            + "</tr></table>");
                    emps.set(index, empleado);
                } else {
                    out.println("<table style=' text-align: center' class='table table-bordered table-hover table-sm'>"
                            + "<tr class='table-warning' style='color: black'>"
                            + "<th colspan='5' >Horario</th></tr><tr>"
                            + "<th >Hora de Entrada</th>"
                            + "<th >Hora de Salida</th>"
                            + "<th >Hora de Comida</th>"
                            + "<th >Hora de Regreso</th>"
                            + "<th >Modificar</th></tr><tr>"
                            + "<td><input style='text-align: center' type='time' class='form-control form-control-sm' name='horaE' value='" + empleado.getHora_Ent().trim() + "' id='horaE' required></td>"
                            + "<td><input style='text-align: center' type='time' class='form-control form-control-sm' name='horaS' value='" + empleado.getHora_Sal().trim() + "' id='horaS' required></td>"
                            + "<td><input style='text-align: center' type='time' class='form-control form-control-sm' name='horaC' value='" + empleado.getHora_Com().trim() + "' id='horaC' required></td>"
                            + "<td><input style='text-align: center' type='time' class='form-control form-control-sm' name='horaR' value='" + empleado.getHora_Reg().trim() + "' id='horaR' required></td>"
                            + "<th><button href=# class='btn btn-success btn-sm' onclick=FormUpDiPrivHrs(" + index + ",'upd','HorasEmp') ><span><img src='images/save.png'></span></button></th>"
                            + "</tr></table>");
                }
                break;
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
