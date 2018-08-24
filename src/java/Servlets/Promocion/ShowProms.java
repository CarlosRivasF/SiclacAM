package Servlets.Promocion;

import DataAccesObject.Promocion_DAO;
import DataTransferObject.Promocion_DTO;
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
 * @author ZionSystem
 */
@WebServlet(name = "ShowProms", urlPatterns = {"/ShowProms"})
public class ShowProms extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        HttpSession sesion = request.getSession();
        int id_unidad = Integer.parseInt(sesion.getAttribute("unidad").toString().trim());
        PrintWriter out = response.getWriter();
        Promocion_DAO P = new Promocion_DAO();
        List<Promocion_DTO> proms = P.getPromociones(id_unidad);

        out.print("<div class='nav-scroller bg-white box-shadow'>"
                + "    <nav class='nav nav-underline'>"
                + "        <a class='nav-link' href='#' onclick=mostrarForm('" + request.getContextPath() + "/Menu/Promocion/Registro.jsp');>Nueva Promoción</a>"
                + "        <a class='nav-link active' href='#'  style=\"color: blue\"><ins>Lista de Promociones</ins></a>"
                + "    </nav>"
                + "</div><br>");
        out.print("<div class='form-row'>"
                + "<div class='offset-2 col-8 col-sm-8 col-md-8 mb-3'>"
                + "<label class='sr-only' >Codigo de Estudio</label>"
                + "<input style='text-align: center' type='text' class='form-control' name='medi' onkeyup=SrchProm(this,'prom'); id='medi' placeholder='Nombre de Promoción' autofocus required>"
                + "</div>"
                + "</div>"
                + "<div id='srchProm'>");
        out.println("<div style='color: white' class='table-responsive'>"
                + "<table style=' text-align: center' class='table table-bordered table-hover table-sm'>"
                + "<tr class='table-active'>"
                + "<th >Nombre</th>"
                + "<th >Precio</th>"
                + "<th >Estado</th>"
                + "<th >Detalles</th>"
                + "<th >Eliminar</th>"
                + "</tr>");
        proms.forEach((dto) -> {
            out.println("<tr>"
                    + "<td >" + dto.getNombre_Promocion() + "</td>"
                    + "<td >" + dto.getPrecio_Total() + "</td>"
                    + "<td >" + dto.getEstado() + "</td>"
                    + "<td><button href=# class='btn btn-default' onclick=ShDetProm(" + proms.indexOf(dto) + ") ><span><img src='images/details.png'></span></button></td>"
                    + "<td><div id='pac-" + proms.indexOf(dto) + "'><button href=# class='btn btn-danger' onclick=FormDelProm(" + proms.indexOf(dto) + ",'show') ><span><img src='images/trash.png'></span></button></div></td>"
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
