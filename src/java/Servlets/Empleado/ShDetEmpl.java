package Servlets.Empleado;

import DataAccesObject.Permiso_DAO;
import DataAccesObject.Semana;
import DataTransferObject.Empleado_DTO;
import DataTransferObject.Permiso_DTO;
import java.io.IOException;
import java.io.PrintWriter;
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
@WebServlet(name = "ShDetEmpl", urlPatterns = {"/ShDetEmpl"})
public class ShDetEmpl extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        HttpSession sesion = request.getSession();
        List<Empleado_DTO> emps;
        emps = (List<Empleado_DTO>) sesion.getAttribute("emps");
        int index = Integer.parseInt(request.getParameter("index").trim());
        Empleado_DTO emp = emps.get(index);
        out.print("<div class='nav-scroller bg-white box-shadow'>"
                + "    <nav class='nav nav-underline'>"
                + "        <a class='nav-link' href='#' onclick=mostrarForm('" + request.getContextPath() + "/Menu/Empleado/Registro.jsp');>Nuevo Empleado</a>"
                + "        <a class='nav-link' href='#' onclick=mostrarForm('ShowEmpl'); >Ver Empleados</a>"
                + "    </nav>"
                + "</div><hr class='mb-1'>");
        out.println("<div style='color: white' class='table-responsive'>");

        out.println("<div id='nameP'>"
                + "<table style=' text-align: center' class='table table-bordered table-hover table-sm'>"
                + "<tr class='table-info' style='color: black'>"
                + "<th colspan='3' >Datos de Empleado</th>"
                + "<th>Modificar</th></tr><tr>"
                + "<td >" + emp.getNombre() + "</td>"
                + "<td >" + emp.getAp_Paterno() + "</td>"
                + "<td >" + emp.getAp_Materno() + "</td>"
                + "<th><button href=# class='btn btn-warning btn-sm' onclick=FormUpDtsP(" + index + ",'name','form','emps') ><span><img src='images/pencil.png'></span></button></th>"
                + "</tr></table>");
        out.println("</div>");

        out.println("<div id='datosEmp'>"
                + "<table style=' text-align: center' class='table table-bordered table-hover table-sm'>"
                + "<tr class='table-info' style='color: black'>"
                + "<th >CURP</th>"
                + "<th >NSS</th>"
                + "<th >Modificar</th></tr><tr>"
                + "<td >" + emp.getCurp() + "</td>"
                + "<td >" + emp.getNss() + "</td>"
                + "<th><button href=# class='btn btn-warning btn-sm' onclick=FormUpCrNsP(" + index + ",'form','datosEmp') ><span><img src='images/pencil.png'></span></button></th>"
                + "</tr></table>");
        out.println("</div>");

        out.println("<div id='contactoP'>"
                + "<table style=' text-align: center' class='table table-bordered table-hover table-sm'>"
                + "<tr class='table-success' style='color: black'>"
                + "<th colspan='3' >Datos de Contacto</th>"
                + "<th >Modificar</th></tr><tr>"
                + "<td >" + emp.getMail() + "</td>"
                + "<td >" + emp.getTelefono1() + "</td>"
                + "<td >" + emp.getTelefono2() + "</td>"
                + "<th><button href=# class='btn btn-warning btn-sm' onclick=FormUpDtsP(" + index + ",'contacto','form','emps') ><span><img src='images/pencil.png'></span></button></th>"
                + "</tr></table>");
        out.println("</div>");

        out.println("<div id='direccion'>"
                + "<table style=' text-align: center' class='table table-bordered table-hover table-sm'>"
                + "<tr class='table-success' style='color: black'>"
                + "<th colspan='7' >Dirección</th>"
                + "<th >Modificar</th>"
                + "</tr>");
        out.println("<tr>"
                + "<td>CP:'" + emp.getC_p() + "'</td>"
                + "<td>" + emp.getNombre_Estado() + "</td>"
                + "<td>" + emp.getNombre_Municipio() + "</td>"
                + "<td>" + emp.getNombre_Colonia() + "</td>"
                + "<td>" + emp.getCalle() + "</td>"
                + "<td>Int. " + emp.getNo_Int() + "</td>"
                + "<td>Ext. " + emp.getNo_Ext() + "</td>"
                + "<th><button href=# class='btn btn-warning btn-sm' onclick=FormUpDtsP(" + index + ",'direccion','form','emps') ><span><img src='images/pencil.png'></span></button></th>"
                + "</tr></table>");
        out.println("</div>");

        out.println("<div id='datoslab'>"
                + "<table style=' text-align: center' class='table table-bordered table-hover table-sm'>"
                + "<tr class='table-warning' style='color: black'>"
                + "<th colspan='3' >Datos Laborales</th></tr><tr>"
                + "<th >Fecha Ingreso</th>"
                + "<th >Salario</th>"
                + "<th >Modificar</th></tr><tr>"
                + "<td >" + emp.getFecha_Ing() + "</td>"
                + "<td >" + emp.getSalario_Bto() + "</td>"
                + "<th><button href=# class='btn btn-warning btn-sm' onclick=FormUpCrNsP(" + index + ",'form','datoslab') ><span><img src='images/pencil.png'></span></button></th>"
                + "</tr></table>");
        out.println("</div>");
        Semana S = new Semana();
        List<Semana> semana = S.semana();
        List<Semana> semnaCD = S.semanaCheckDisabled();
        out.println("<div id='DiasEmp'>"
                + "<table style=' text-align: center' class='table table-bordered table-hover table-sm'>"
                + "<tr class='table-warning' style='color: black'>"
                + "<th >Dias de trabajo</th>"
                + "<th >Modificar</th>"
                + "</tr><tr>");
        out.println("<td align='left'><div class='form-row'>");
        for (Semana ds : semana) {
            for (String dt : emp.getDias_Trabajo()) {
                if (ds.getDia().equals(dt)) {
                    out.print(semnaCD.get(semana.indexOf(ds)).getInput());
                    break;
                }
            }
        }
        out.println("</div></td>");
        out.print("<th align='center'><button href=# class='btn btn-warning btn-sm' onclick=FormUpDiPrivHrs(" + index + ",'form','DiasEmp') ><span><img src='images/pencil.png'></span></button></th>"
                + "</tr></table>");
        out.println("</div>");

        out.println("<div id='PrivsEmp'>"
                + "<table style=' text-align: center' class='table table-bordered table-hover table-sm'>"
                + "<tr class='table-warning' style='color: black'>"
                + "<th>Privilegios</th>"
                + "<th >Modificar</th>"
                + "</tr><tr>");
        out.println("<td><div class='form-row' style='color: white'>");
        Permiso_DAO P = new Permiso_DAO();
        List<Permiso_DTO> pers = P.getPermisos();
        List<Permiso_DTO> persCD = P.getPermisosCheckedDisabled();
        for (Permiso_DTO p : pers) {
            for (String pe : emp.getPermisos()) {
                if (p.getNombre().equals(pe)) {
                    out.print(persCD.get(pers.indexOf(p)).getInput());
                    break;
                }
            }
        }
        out.println("</div></td>");
        out.print("<th><button href=# class='btn btn-warning btn-sm' onclick=FormUpDiPrivHrs(" + index + ",'form','PrivsEmp') ><span><img src='images/pencil.png'></span></button></th>"
                + "</tr></table>");
        out.println("</div>");

        out.println("<div id='HorasEmp'>"
                + "<table style=' text-align: center' class='table table-bordered table-hover table-sm'>"
                + "<tr class='table-warning' style='color: black'>"
                + "<th colspan='5' >Horario</th></tr><tr>"
                + "<th >Hora de Entrada</th>"
                + "<th >Hora de Salida</th>"
                + "<th >Hora de Comida</th>"
                + "<th >Hora de Regreso</th>"
                + "<th >Modificar</th></tr><tr>"
                + "<td >" + emp.getHora_Ent() + "</td>"
                + "<td >" + emp.getHora_Sal() + "</td>"
                + "<td >" + emp.getHora_Com() + "</td>"
                + "<td >" + emp.getHora_Reg() + "</td>"
                + "<th><button href=# class='btn btn-warning btn-sm' onclick=FormUpDiPrivHrs(" + index + ",'form','HorasEmp') ><span><img src='images/pencil.png'></span></button></th>"
                + "</tr></table>");
        out.println("</div>");

        out.println("<div id='acceso'>"
                + "<table style=' text-align: center' class='table table-bordered table-hover table-sm'>"
                + "<tr class='table-danger' style='color: black'>"
                + "<th colspan='4' >Datos de Acceso</th>"
                + "</tr>"
                + "<tr>"
                + "<th>Usuario</th>"
                + "<th>Contraseña</th>"
                + "<th>Estado</th>"
                + "<th >Modificar</th>"
                + "</tr>");
        out.println("<tr>"
                + "<td>" + emp.getUsuario().getNombre_Usuario() + "</td>"
                + "<td>" + emp.getUsuario().getContraseña() + "</td>"
                + "<td>" + emp.getUsuario().getEstado() + "</td>"
                + "<th><button href=# class='btn btn-warning btn-sm' onclick=FormUpUser(" + index + ",'form','emps') ><span><img src='images/pencil.png'></span></button></th>"
                + "</tr></table>");
        out.println("</div>");

        out.println("</div>");
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
