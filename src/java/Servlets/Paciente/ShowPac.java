package Servlets.Paciente;

import DataAccesObject.Paciente_DAO;
import DataTransferObject.Paciente_DTO;
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
@WebServlet(name = "ShowPac", urlPatterns = {"/ShowPac"})
public class ShowPac extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        HttpSession sesion = request.getSession();
        int id_unidad = Integer.parseInt(sesion.getAttribute("unidad").toString().trim());
        PrintWriter out = response.getWriter();
        Paciente_DAO P = new Paciente_DAO();
        List<Paciente_DTO> pacs = P.getPacientes();
        sesion.setAttribute("pacs", pacs);

        out.print("<div class='nav-scroller bg-white box-shadow'>"
                + "    <nav class='nav nav-underline'>"
                + "        <a class='nav-link' href='#' onclick=mostrarForm('" + request.getContextPath() + "/Menu/Paciente/Registro.jsp');>Nuevo Paciente</a>"
                + "        <a class='nav-link active' href='#'  style=\"color: blue\"><ins>Ver Pacientes</ins></a>"
                + "    </nav>"
                + "</div><br>");
        out.print("<div class='form-row'>"
                + "<div class='offset-2 col-8 col-sm-8 col-md-8 mb-3'>"
                + "<label class='sr-only' >Codigo de Estudio</label>"
                + "<input style='text-align: center' type='text' class='form-control' name='medi' onkeyup=SrchPac(this,'pac'); id='medi' placeholder='Nombre de paciente' autofocus required>"
                + "</div>"
                + "</div>"
                + "<div id='srchPac'>");
        out.println("<div style='color: white' class='table-responsive'>"
                + "<table style=' text-align: center' class='table table-bordered table-hover table-sm'>"
                + "<tr class='table-active'>"
                + "<th >Nombre</th>"
                + "<th >A Paterno</th>"
                + "<th >A Materno</th>"
                + "<th >Telefono</th>"
                + "<th >Detalles</th>"
                + "<th >Eliminar</th>"
                + "</tr>");
        int i = 0;
        for (Paciente_DTO dto : pacs) {
            out.println("<tr>"
                    + "<td >" + dto.getNombre() + "</td>"
                    + "<td >" + dto.getAp_Paterno() + "</td>"
                    + "<td >" + dto.getAp_Materno() + "</td>"
                    + "<td >" + dto.getTelefono1() + "</td>"
                    + "<td><button href=# class='btn btn-default' onclick=ShDetPac(" + i + ") ><span><img src='images/details.png'></span></button></td>"
                    + "<td><div id='pac-" + i + "'><button href=# class='btn btn-danger' onclick=FormDelPac(" + i + ",'show') ><span><img src='images/trash.png'></span></button></div></td>"
                    + "</tr>");
            i++;
        }
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
