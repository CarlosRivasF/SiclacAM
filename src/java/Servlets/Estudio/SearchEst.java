package Servlets.Estudio;

import DataAccesObject.Estudio_DAO;
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
@WebServlet(name = "SearchEst", urlPatterns = {"/SearchEst"})
public class SearchEst extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        HttpSession sesion = request.getSession();
        int id_unidad = Integer.parseInt(sesion.getAttribute("unidad").toString().trim());
        PrintWriter out = response.getWriter();
        Estudio_DAO E = new Estudio_DAO();
        List<Estudio_DTO> ests;
        if (sesion.getAttribute("ests") != null) {
            ests = (List<Estudio_DTO>) sesion.getAttribute("ests");
        } else {
            ests = E.getEstudiosByUnidad(id_unidad);
            sesion.setAttribute("ests", ests);
        }
        if (request.getParameter("busq") != null) {
            String mode = request.getParameter("mode").trim();
            String function = "";
            switch (mode) {
                case "Orden":
                    function = "AddEst";
                    break;
                case "Cotizacion":
                    function = "AddEstCot";
                    break;
                case "Prom":
                    function = "AddEstProm";
                    break;
            }
            out.println("<div style='color: white' class='table-responsive'>"
                    + "<table style=' text-align: center' class='table table-bordered table-hover table-sm'>"
                    + "<tr class='bg-primary' style='color: black'>"
                    + "<th >Nombre de Estudio</th>"
                    + "<th >$ Normal</th>"
                    + "<th >$ Urgente</th>"
                    + "<th >% de Desc</th>"
                    + "<th >% de Carg</th>"
                    + "<th >Añadir</th>"
                    + "</tr>");
            String busq = request.getParameter("busq");
            int tipoE = Integer.parseInt(request.getParameter("tipoE").trim());
            for (Estudio_DTO dto : ests) {
                if (dto.getId_Tipo_Estudio() == tipoE) {
                    String[] parts = dto.getNombre_Estudio().split(" ");
                    if (parts[0].toUpperCase().contains(busq.toUpperCase().trim()) || dto.getClave_Estudio().toUpperCase().trim().contains(busq.toUpperCase().trim())
                            || dto.getNombre_Estudio().toUpperCase().trim().contains(busq.toUpperCase().trim())
                            || dto.getUtilidad().toUpperCase().trim().contains(busq.toUpperCase().trim())) {
                        out.println("<tr>"
                                + "<td >" + dto.getNombre_Estudio() + "</td>"
                                + "<td ><div class='col mb-3 custom-control custom-radio custom-control-inline'>"
                                + "<input id='PreN" + ests.indexOf(dto) + "' class='custom-control-input' name='prec' type='radio' required>"
                                + "<label class='custom-control-label mb-3'for='PreN" + ests.indexOf(dto) + "'>" + dto.getPrecio().getPrecio_N() + "</label>"
                                + "</div></td>"
                                + "<td ><div class='col mb-3 custom-control custom-radio custom-control-inline'>"
                                + "<input id='PreU" + ests.indexOf(dto) + "' class='custom-control-input' name='prec' type='radio' required>"
                                + "<label class='custom-control-label mb-3'for='PreU" + ests.indexOf(dto) + "'>" + dto.getPrecio().getPrecio_U() + "</label>"
                                + "</div></td>"
                                + "<td><input style='text-align: center' type='number' class='form-control' onkeypress='return soloNumeros(event)' name='desc" + ests.indexOf(dto) + "' id='desc" + ests.indexOf(dto) + "' placeholder='Descuento' required=''></td>"
                                + "<td><input style='text-align: center' type='number' class='form-control' onkeypress='return soloNumeros(event)' name='desc" + ests.indexOf(dto) + "' id='sca" + ests.indexOf(dto) + "' placeholder='Sobrecargo' required=''></td>"
                                + "<td><button href=# class='btn btn-default btn-sm' onclick=" + function + "(" + ests.indexOf(dto) + ",'lst') ><span><img src='images/mas.png'></span></button></td>"
                                + "</tr>");
                    }
                }
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
