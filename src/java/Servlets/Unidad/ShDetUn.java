package Servlets.Unidad;

import DataAccesObject.Unidad_DAO;
import DataTransferObject.Empresa_DTO;
import DataTransferObject.Unidad_DTO;
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
@WebServlet(name = "ShDetUn", urlPatterns = {"/ShDetUn"})
public class ShDetUn extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        HttpSession sesion = request.getSession();
        PrintWriter out = response.getWriter();
        List<Unidad_DTO> uns;
        uns = (List<Unidad_DTO>) sesion.getAttribute("uns");

        int index = Integer.parseInt(request.getParameter("index").trim());
        Unidad_DTO unid = uns.get(index);
        out.print("<div class='nav-scroller bg-white box-shadow'>"
                + "    <nav class='nav nav-underline'>"
                + "        <a class='nav-link' href='#' onclick=mostrarForm('" + request.getContextPath() + "/Menu/Unidad/Registro.jsp');>Nueva Unidad</a>"
                + "        <a class='nav-link active' href='#' onclick=mostrarForm('ShUnid');>Ver Unidades</a>"
                + "    </nav>"
                + "</div>"
                + "<div><hr class='mb-1'>"
                + "<h4 style='text-align: center; color: white'>Unidad: " + unid.getClave() + " - " + unid.getNombre_Unidad() + "</h4>"
                + "<hr class='mb-1'>");
        out.println("<div style='color: white' class='table-responsive'>");

        out.println("<div id='nameP'>"
                + "<table style=' text-align: center' class='table table-bordered table-hover table-sm'>"
                + "<tr class='table-info' style='color: black'>"
                + "<th colspan='3' >Encargado</th>"
                + "<th>Modificar</th></tr><tr>"
                + "<td >" + unid.getEncargado().getNombre() + "</td>"
                + "<td >" + unid.getEncargado().getAp_Paterno() + "</td>"
                + "<td >" + unid.getEncargado().getAp_Materno() + "</td>"
                + "<th><button href=# class='btn btn-warning btn-sm' onclick=FormUpDtsP(" + index + ",'name','form','uns') ><span><img src='images/pencil.png'></span></button></th>"
                + "</tr></table>");
        out.println("</div>");

        out.println("<div id='contactoP'>"
                + "<table style=' text-align: center' class='table table-bordered table-hover table-sm'>"
                + "<tr class='table-success' style='color: black'>"
                + "<th colspan='2' >Contacto</th>"
                + "<th >Modificar</th></tr><tr>"
                + "<td >" + unid.getEncargado().getTelefono1() + "</td>"
                + "<td >" + unid.getEncargado().getMail() + "</td>"
                + "<th><button href=# class='btn btn-warning btn-sm' onclick=FormUpDtsP(" + index + ",'contacto','form','uns') ><span><img src='images/pencil.png'></span></button></th>"
                + "</tr></table>");
        out.println("</div>");

        out.println("<div id='direccion'>"
                + "<table style=' text-align: center' class='table table-bordered table-hover table-sm'>"
                + "<tr class='table-warning' style='color: black'>"
                + "<th colspan='7' >Dirección</th>"
                + "<th >Modificar</th>"
                + "</tr>");
        out.println("<tr>"
                + "<td>CP:'" + unid.getEncargado().getC_p() + "'</td>"
                + "<td>" + unid.getEncargado().getNombre_Estado() + "</td>"
                + "<td>" + unid.getEncargado().getNombre_Municipio() + "</td>"
                + "<td>" + unid.getEncargado().getNombre_Colonia() + "</td>"
                + "<td>" + unid.getEncargado().getCalle() + "</td>"
                + "<td>" + unid.getEncargado().getNo_Int() + "</td>"
                + "<td>" + unid.getEncargado().getNo_Ext() + "</td>"
                + "<th><button href=# class='btn btn-warning btn-sm' onclick=FormUpDtsP(" + index + ",'direccion','form','uns') ><span><img src='images/pencil.png'></span></button></th>"
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
                + "<td>" + unid.getUsuario().getNombre_Usuario() + "</td>"
                + "<td>" + unid.getUsuario().getContraseña() + "</td>"
                + "<td>" + unid.getUsuario().getEstado() + "</td>"
                + "<th><button href=# class='btn btn-warning btn-sm' onclick=FormUpUser(" + index + ",'form','uns') ><span><img src='images/pencil.png'></span></button></th>"
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
