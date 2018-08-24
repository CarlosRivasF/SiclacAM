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
@WebServlet(name = "SrchProm", urlPatterns = {"/SrchProm"})
public class SrchProm extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        HttpSession sesion = request.getSession();
        int id_unidad = Integer.parseInt(sesion.getAttribute("unidad").toString().trim());
        PrintWriter out = response.getWriter();
        Promocion_DAO P = new Promocion_DAO();
        List<Promocion_DTO> proms = P.getPromociones(id_unidad);
        out.println("<div style='color: white' class='table-responsive'>"
                + "<table style=' text-align: center' class='table table-bordered table-hover table-sm'>"
                + "<tr class='table-active'>"
                + "<th >Nombre</th>"
                + "<th >Precio</th>"
                + "<th >Estado</th>"
                + "<th >Detalles</th>"
                + "<th >Eliminar</th>"
                + "</tr>");
        if (request.getParameter("mode") != null) {
            String mode = request.getParameter("mode").trim();
            switch (mode) {
                case "prom":
                    if (request.getParameter("busq") != null) {
                        int n = 0;
                        String[] prts = null;
                        boolean f;
                        String busq = request.getParameter("busq");
                        if (busq.contains(" ")) {
                            prts = busq.split(" ");
                            n = prts.length;
                            f = true;
                        } else {
                            f = false;
                        }
                        if (f) {
                            switch (n) {
                                case 2:
                                    for (Promocion_DTO dto : proms) {
                                        if (dto.getNombre_Promocion().toUpperCase().trim().contains(prts[0].toUpperCase().trim()) & dto.getNombre_Promocion().toUpperCase().trim().contains(prts[1].toUpperCase().trim())) {
                                            out.println("<tr>"
                                                    + "<td >" + dto.getNombre_Promocion() + "</td>"
                                                    + "<td >" + dto.getPrecio_Total() + "</td>"
                                                    + "<td >" + dto.getEstado() + "</td>"
                                                    + "<td><button href=# class='btn btn-default' onclick=ShDetPac(" + proms.indexOf(dto) + ") ><span><img src='images/details.png'></span></button></td>"
                                                    + "<td><div id='pac-" + proms.indexOf(dto) + "'><button href=# class='btn btn-danger' onclick=FormDelPac(" + proms.indexOf(dto) + ",'show') ><span><img src='images/trash.png'></span></button></div></td>"
                                                    + "</tr>");
                                        }
                                    }
                                    break;
                                case 3:
                                    for (Promocion_DTO dto : proms) {
                                        if (dto.getNombre_Promocion().toUpperCase().trim().contains(prts[0].toUpperCase().trim()) & dto.getNombre_Promocion().toUpperCase().trim().contains(prts[1].toUpperCase().trim()) & dto.getNombre_Promocion().toUpperCase().trim().contains(prts[2].toUpperCase().trim())) {
                                            out.println("<tr>"
                                                    + "<td >" + dto.getNombre_Promocion() + "</td>"
                                                    + "<td >" + dto.getPrecio_Total() + "</td>"
                                                    + "<td >" + dto.getEstado() + "</td>"
                                                    + "<td><button href=# class='btn btn-default' onclick=ShDetPac(" + proms.indexOf(dto) + ") ><span><img src='images/details.png'></span></button></td>"
                                                    + "<td><div id='pac-" + proms.indexOf(dto) + "'><button href=# class='btn btn-danger' onclick=FormDelPac(" + proms.indexOf(dto) + ",'show') ><span><img src='images/trash.png'></span></button></div></td>"
                                                    + "</tr>");
                                        }
                                    }
                                    break;
                                default:
                                    proms.stream().filter((dto) -> (dto.getNombre_Promocion().toUpperCase().trim().contains(busq.toUpperCase().trim()) || dto.getEstado().toUpperCase().trim().contains(busq.toUpperCase().trim()))).forEachOrdered((Promocion_DTO dto) -> {
                                        out.println("<tr>"
                                                + "<td >" + dto.getNombre_Promocion() + "</td>"
                                                + "<td >" + dto.getPrecio_Total() + "</td>"
                                                + "<td >" + dto.getEstado() + "</td>"
                                                + "<td><button href=# class='btn btn-default' onclick=ShDetPac(" + proms.indexOf(dto) + ") ><span><img src='images/details.png'></span></button></td>"
                                                + "<td><div id='pac-" + proms.indexOf(dto) + "'><button href=# class='btn btn-danger' onclick=FormDelPac(" + proms.indexOf(dto) + ",'show') ><span><img src='images/trash.png'></span></button></div></td>"
                                                + "</tr>");
                                    });
                                    break;
                            }
                        } else {
                            proms.stream().filter((dto) -> (dto.getNombre_Promocion().toUpperCase().trim().contains(busq.toUpperCase().trim()) || dto.getEstado().toUpperCase().trim().contains(busq.toUpperCase().trim()))).forEachOrdered((Promocion_DTO dto) -> {
                                out.println("<tr>"
                                        + "<td >" + dto.getNombre_Promocion() + "</td>"
                                        + "<td >" + dto.getPrecio_Total() + "</td>"
                                        + "<td >" + dto.getEstado() + "</td>"
                                        + "<td><button href=# class='btn btn-default' onclick=ShDetPac(" + proms.indexOf(dto) + ") ><span><img src='images/details.png'></span></button></td>"
                                        + "<td><div id='pac-" + proms.indexOf(dto) + "'><button href=# class='btn btn-danger' onclick=FormDelPac(" + proms.indexOf(dto) + ",'show') ><span><img src='images/trash.png'></span></button></div></td>"
                                        + "</tr>");
                            });
                        }
                        out.println("</table>");
                        out.println("</div>");
                    }
                    break;
            }
        } else {
            proms.forEach((dto) -> {
                out.println("<tr>"
                        + "<td >" + dto.getNombre_Promocion() + "</td>"
                        + "<td >" + dto.getPrecio_Total() + "</td>"
                        + "<td >" + dto.getEstado() + "</td>"
                        + "<td><button href=# class='btn btn-default' onclick=ShDetPac(" + proms.indexOf(dto) + ") ><span><img src='images/details.png'></span></button></td>"
                        + "<td><div id='pac-" + proms.indexOf(dto) + "'><button href=# class='btn btn-danger' onclick=FormDelPac(" + proms.indexOf(dto) + ",'show') ><span><img src='images/trash.png'></span></button></div></td>"
                        + "</tr>");
            });
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