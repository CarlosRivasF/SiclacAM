package Servlets.Material;

import DataTransferObject.Material_DTO;
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
@WebServlet(name = "SearchMat", urlPatterns = {"/SearchMat"})
public class SearchMat extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        HttpSession sesion = request.getSession();
        List<Material_DTO> matsU = (List<Material_DTO>) sesion.getAttribute("matsU");
        PrintWriter out = response.getWriter();
        out.println("<div style='color: white' class='table-responsive'>"
                + "<table style=' text-align: center' class='table table-bordered table-hover table-sm'>"
                + "<tr class='table-active'>"
                + "<th >Material</th>"
                + "<th >Precio</th>"
                + "<th >Cantidad</th>"
                + "<th>Modificar</th>"
                + "<th>Eliminar</th>"
                + "</tr>");
        boolean r = false;
        if (request.getParameter("busq") != null) {
            String busq = request.getParameter("busq");
            matsU.forEach((dto) -> {
                String[] parts = dto.getNombre_Material().split(" ");
                if (parts[0].toUpperCase().contains(busq.toUpperCase().trim()) || dto.getClave().toUpperCase().trim().contains(busq.toUpperCase().trim()) || dto.getNombre_Material().toUpperCase().trim().contains(busq.toUpperCase().trim())) {
                    out.println("<tr>"
                            + "<td >" + dto.getNombre_Material() + "</td>"
                            + "<td >" + dto.getPrecio() + "</td>"
                            + "<td >" + dto.getCantidad() + "</td>"
                            + "<td><button href=# class='btn btn-warning' onclick=FormUpMat(" + matsU.indexOf(dto) + ") ><span><img src='images/pencil.png'></span></button></td>"
                            + "<td><div id='mat-" + matsU.indexOf(dto) + "'><button href=# class='btn btn-danger' onclick=FormDelMat(" + matsU.indexOf(dto) + ",'show') ><span><img src='images/trash.png'></span></button></div></td>"
                            + "</tr>");
                }
            });
        } else {
            int i = 0;
            for (Material_DTO dto : matsU) {
                out.println("<tr>"
                        + "<td >" + dto.getNombre_Material() + "</td>"
                        + "<td >" + dto.getPrecio() + "</td>"
                        + "<td >" + dto.getCantidad() + "</td>"
                        + "<td><button href=# class='btn btn-warning' onclick=FormUpMat(" + i + ") ><span><img src='images/pencil.png'></span></button></td>"
                        + "<td><div id='mat-" + i + "'><button href=# class='btn btn-danger' onclick=FormDelMat(" + i + ",'show') ><span><img src='images/trash.png'></span></button></div></td>"
                        + "</tr>");
                i++;
            }
        }
        out.println("</table>");
        out.println("</div></div>");
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
