package Servlets.Medico;

import DataAccesObject.Medico_DAO;
import DataTransferObject.Medico_DTO;
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
@WebServlet(name = "ShowMedi", urlPatterns = {"/ShowMedi"})
public class ShowMedi extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        HttpSession sesion = request.getSession();
        PrintWriter out = response.getWriter();
        Medico_DAO M = new Medico_DAO();
        List<Medico_DTO> meds;
        meds = M.getMedicos();
        sesion.setAttribute("meds", meds);

        out.print("<div class='nav-scroller bg-white box-shadow'>"
                + "    <nav class='nav nav-underline'>"
                + "        <a class='nav-link' href='#' onclick=mostrarForm('" + request.getContextPath() + "/Menu/Medico/Registro.jsp');>Nuevo Médico</a>"
                + "        <a class='nav-link active' href='#'  style=\"color: blue\"><ins>Ver Médicos</ins></a>"
                + "    </nav>"
                + "</div><br>");
        out.print("<div class='form-row'>"
                + "<div class='offset-2 col-8 col-sm-8 col-md-8 mb-3'>"
                + "<label class='sr-only' >Codigo de Estudio</label>"
                + "<input style='text-align: center' type='text' class='form-control' name='medi' onkeyup=SrchMed(this,'med'); id='medi' placeholder='Nombre de médico' autofocus required>"
                + "</div>"
                + "</div>"
                + "<div id='srchMed'>");
        out.println("<div style='color: white' class='table-responsive'>"
                + "<table style=' text-align: center' class='table table-bordered table-hover table-sm'>"
                + "<tr class='table-active'>"
                + "<th >ID</th>"
                + "<th >Empresa</th>"
                + "<th >Nombre</th>"
                + "<th >Telefono</th>"
                + "<th >Detalles</th>"
                + "<th >Eliminar</th>"
                + "</tr>");
        int i = 0;
        for (Medico_DTO dto : meds) {
            out.println("<tr>"
                    + "<td >" + dto.getId_Medico() + "</td>"
                    + "<td >" + dto.getEmpresa() + "</td>"
                    + "<td >" + dto.getNombre() + " " + dto.getAp_Paterno() + " " + dto.getAp_Materno() + "</td>"
                    + "<td >" + dto.getTelefono1() + "</td>"
                    + "<td><button href=# class='btn btn-default' onclick=ShDetMed(" + i + ") ><span><img src='images/details.png'></span></button></td>"
                    + "<td><div id='medi-" + i + "'><button href=# class='btn btn-danger' onclick=FormDelMed(" + i + ",'show') ><span><img src='images/trash.png'></span></button></div></td>"
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
