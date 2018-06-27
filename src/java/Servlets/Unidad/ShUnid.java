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
@WebServlet(name = "ShUnid", urlPatterns = {"/ShUnid"})
public class ShUnid extends HttpServlet {
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");        
        HttpSession sesion = request.getSession();        
        Empresa_DTO empresa = (Empresa_DTO) sesion.getAttribute("empresa");        
        Unidad_DAO U = new Unidad_DAO();
        List<Unidad_DTO> uns;

            uns = U.getUnidadesByEmpresa(empresa.getId_Empresa());
            sesion.setAttribute("uns", uns);        
        try (PrintWriter out = response.getWriter()) {                       
            out.print("<div class='nav-scroller bg-white box-shadow'>"
                    + "    <nav class='nav nav-underline'>"
                    + "        <a class='nav-link' href='#' onclick=mostrarForm('" + request.getContextPath() + "/Menu/Unidad/Registro.jsp');>Nueva Unidad</a>"
                    + "        <a class='nav-link active' href='#'  style=\"color: blue\"><ins>Ver Unidades</ins></a>"
                    + "    </nav>"
                    + "</div><br>");            
            out.println("<div style='color: white' class='table-responsive'>"
                    + "<table style=' text-align: center' class='table table-bordered table-hover table-sm'>"
                    + "<tr class='bg-primary' style='color: black'>"
                    + "<th >Clave</th>"
                    + "<th >Nombre de Unidad</th>"
                    + "<th >Usuario</th>"
                    + "<th >Estado</th>"
                    + "<th >Detalles</th>"
                    + "</tr>");
            int i = 0;
            for (Unidad_DTO dto : uns) {
                out.println("<tr>"
                        + "<td >" + dto.getClave() + "</td>"
                        + "<td >" + dto.getNombre_Unidad() + "</td>"
                        + "<td >" + dto.getUsuario().getNombre_Usuario() + "</td>"
                        + "<td >" + dto.getUsuario().getEstado() + "</td>"
                        + "<td><button href=# class='btn btn-default' onclick=ShDetUn(" + i + ") ><span><img src='images/details.png'></span></button></td>"
                        + "</tr>");
                i++;
            }
            out.println("</table>");
            out.println("</div>");            
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
