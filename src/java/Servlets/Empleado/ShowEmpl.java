package Servlets.Empleado;

import DataAccesObject.Empleado_DAO;
import DataTransferObject.Empleado_DTO;
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
 * @author Carlos Rivas
 */
@WebServlet(name = "ShowEmpl", urlPatterns = {"/ShowEmpl"})
public class ShowEmpl extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        HttpSession sesion = request.getSession();
        int id_unidad = Integer.parseInt(sesion.getAttribute("unidad").toString().trim());
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        Empleado_DAO Em = new Empleado_DAO();
        List<Empleado_DTO> emps;
        emps = Em.getEmpleadosByUnidad(id_unidad);
        sesion.setAttribute("emps", emps);

        out.print("<div class='nav-scroller bg-white box-shadow'>"
                + "    <nav class='nav nav-underline'>"
                + "        <a class='nav-link' href='#' onclick=mostrarForm('" + request.getContextPath() + "/Menu/Empleado/Registro.jsp');>Nuevo Empleado</a>"
                + "        <a class='nav-link active' href='#'  style=\"color: blue\"><ins>Ver Empleados</ins></a>"
                + "    </nav>"
                + "</div><br>");
        out.print("<div class='form-row'>"
                + "<div class='offset-2 col-8 col-sm-8 col-md-8 mb-3'>"
                + "<label class='sr-only' >Codigo de Estudio</label>"
                + "<input style='text-align: center' type='text' class='form-control' name='emple' onkeyup='SchEmpl(this);' id='emple' placeholder='Nombre de empleado...' autofocus required>"
                + "</div>"
                + "</div>"
                + "<div id='BEmpl'>");
        out.println("<div style='color: white' class='table-responsive'>"
                + "<table style=' text-align: center' class='table table-bordered table-hover table-sm'>"
                + "<tr class='table-active'>"
                + "<th >Usuario</th>"
                + "<th >Nombre</th>"
                + "<th >Sancionar</th>"
                + "<th >Pagar</th>"
                + "<th >Detalles</th>"
                + "<th >Estado</th>"
                + "</tr>");
        emps.forEach((dto) -> {
            String btn = "";
            String clr = "";
            if (dto.getUsuario().getEstado().trim().equals("Activo")) {
                btn = "activeU";
                clr = "success";
            } else if (dto.getUsuario().getEstado().trim().equals("Inactivo")) {
                btn = "inactiveU";
                clr = "danger";
            }
            out.println("<tr>"
                    + "<td >" + dto.getUsuario().getNombre_Usuario() + "</td>"
                    + "<td >" + dto.getNombre() + " " + dto.getAp_Paterno() + " " + dto.getAp_Materno() + "</td>"
                    + "<td><button href=# class='btn btn-default btn-sm' onclick=FormInsSanc(" + dto.getId_Empleado() + ") ><span><img src='images/Sancion.png'></span></button></td>"
                    + "<td><button href=# class='btn btn-default btn-sm' onclick=FormInsNom(" + dto.getId_Empleado() + ") ><span><img src='images/Salario.png'></span></button></td>"
                    + "<td><button href=# class='btn btn-default btn-sm' onclick=ShDetEmpl(" + emps.indexOf(dto) + ") ><span><img src='images/details.png'></span></button></td>"
                    + "<td><div id='Empl-" + emps.indexOf(dto) + "'><button href=# class='btn btn-" + clr + " btn-sm' onclick=FormDelEmp(" + emps.indexOf(dto) + ",'show') ><span><img src='images/" + btn + ".png'></span></button></div></td>"
                    + "</tr>");
        });
        out.println("</table>");
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
