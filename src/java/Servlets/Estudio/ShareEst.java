package Servlets.Estudio;

import DataAccesObject.Estudio_DAO;
import DataAccesObject.Tipo_Estudio_DAO;
import DataTransferObject.Estudio_DTO;
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
@WebServlet(name = "ShareEst", urlPatterns = {"/ShareEst"})
public class ShareEst extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        HttpSession sesion = request.getSession();
        int id_unidad = Integer.parseInt(sesion.getAttribute("unidad").toString().trim());
        PrintWriter out = response.getWriter();        
        Estudio_DAO E = new Estudio_DAO();
        List<Estudio_DTO> eu = E.getEstudiosByUnidad(id_unidad);
        List<Estudio_DTO> enu = E.getEstudiosNotRegUnidad(id_unidad);

        try {
            for (int i = 0; i < eu.size(); i++) {
                for (int j = 0; j < enu.size(); j++) {
                    if (eu.get(i).getId_Estudio() == enu.get(j).getId_Estudio()) {
                        enu.remove(enu.get(j));
                    }
                }
            }
        } catch (Exception e) {
out.println("<br>'ShareEst'<br><h1 style='color: white'>" + e.getMessage() + "...<br>Por favor capture una imagen del error y comuniquelo de inmediato a ZionSystems</h1>");
        }
        if (request.getParameter("busq") != null) {
                        
        } else {
            out.print("<div class='nav-scroller bg-white box-shadow'>"
                    + "<nav class='nav nav-underline'>"
                    + "<a class='nav-link' href='#' onclick=mostrarForm('" + request.getContextPath() + "/Menu/Estudio/Registro.jsp');>Nuevo Estudio</a>"
                    + "<a class='nav-link' href='#' onclick=mostrarForm('ShowEst'); >Lista de Estudios</a>"
                    + "<a class='nav-link active' href='#'style='color: blue'><ins>Nuevos Estudios</ins></a>"
                    + "</nav>"
                    + "</div>"
                    + "<div><br>");
            out.println("<div style='color: white' class='table-responsive'>"
                    + "<table style=' text-align: center' class='table table-bordered table-hover table-sm'>"
                    + "<tr class='bg-primary' style='color: black'>"
                    + "<th >Clave</th>"
                    + "<th >Nombre de Estudio</th>"
                    + "<th >Añadir</th>"
                    + "</tr>");
            enu.forEach((dto) -> {
                out.println("<tr>"
                        + "<td >" + dto.getClave_Estudio() + "</td>"
                        + "<td >" + dto.getNombre_Estudio() + "</td>"
                        + "<td><button href=# class='btn btn-default btn-sm' onclick=ShareEst(" + enu.indexOf(dto) + ") ><span><img src='images/mas.png'></span></button></td>"
                        + "</tr>");
            });
            out.println("</table>");
            out.println("</div>");
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
