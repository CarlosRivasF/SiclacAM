    package Servlets.Estudio;

import DataTransferObject.Estudio_DTO;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.function.Consumer;
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
@WebServlet(name = "SrchEstu", urlPatterns = {"/SrchEstu"})
public class SrchEstu extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        HttpSession sesion = request.getSession();
        PrintWriter out = response.getWriter();
        List<Estudio_DTO> ests = (List<Estudio_DTO>) sesion.getAttribute("ests");
        if (request.getParameter("busq") != null && request.getParameter("tipoE") != null) {
            out.println("<div style='color: white' class='table-responsive'>"
                    + "<table style=' text-align: center' class='table table-bordered table-hover table-sm'>"
                    + "<tr class='bg-primary' style='color: black'>"
                    + "<th >Clave</th>"
                    + "<th >Nombre de Estudio</th>"
                    + "<th >Detalles</th>"
                    + "<th >Eliminar</th>"
                    + "</tr>");
            String busq = request.getParameter("busq");
            int tipoE = Integer.parseInt(request.getParameter("tipoE").trim());
            ests.forEach((dto) -> {
                if (dto.getId_Tipo_Estudio() == tipoE) {
                    String[] parts = dto.getNombre_Estudio().split(" ");
                    if (parts[0].toUpperCase().contains(busq.toUpperCase().trim()) || dto.getClave_Estudio().toUpperCase().trim().contains(busq.toUpperCase().trim())
                            || dto.getNombre_Estudio().toUpperCase().trim().contains(busq.toUpperCase().trim())
                            || dto.getUtilidad().toUpperCase().trim().contains(busq.toUpperCase().trim())) {
                        out.println("<tr>"
                                + "<td >" + dto.getClave_Estudio() + "</td>"
                                + "<td >" + dto.getNombre_Estudio() + "</td>"
                                + "<td><button href=# class='btn btn-default btn-sm' onclick=ShDetEst(" + ests.indexOf(dto) + ") ><span><img src='images/details.png'></span></button></td>"
                                + "<td><div id='est-" + ests.indexOf(dto) + "'><button href=# class='btn btn-danger btn-sm' onclick=FormDelEst(" + ests.indexOf(dto) + ",'show') ><span><img src='images/trash.png'></span></button></div></td>"
                                + "</tr>");
                    }
                }
            });
            out.println("</table>");
            out.println("</div>");
        } else if (request.getParameter("busq") == null && request.getParameter("tipoE") != null) {
            out.println("<div style='color: white' class='table-responsive'>"
                    + "<table style=' text-align: center' class='table table-bordered table-hover table-sm'>"
                    + "<tr class='bg-primary' style='color: black'>"
                    + "<th >Clave</th>"
                    + "<th >Nombre de Estudio</th>"
                    + "<th >Detalles</th>"
                    + "<th >Eliminar</th>"
                    + "</tr>");

            int tipoE = Integer.parseInt(request.getParameter("tipoE").trim());
            ests.forEach(new ConsumerImpl(tipoE, out, ests));
            out.println("</table>");
            out.println("</div>");
        } else {
            out.println("<div style='color: white' class='table-responsive'>"
                    + "<table style=' text-align: center' class='table table-bordered table-hover table-sm'>"
                    + "<tr class='bg-primary' style='color: black'>"
                    + "<th >Clave</th>"
                    + "<th >Nombre de Estudio</th>"
                    + "<th >Detalles</th>"
                    + "<th >Eliminar</th>"
                    + "</tr>");
            int i = 0;
            for (Estudio_DTO dto : ests) {
                out.println("<tr>"
                        + "<td >" + dto.getClave_Estudio() + "</td>"
                        + "<td >" + dto.getNombre_Estudio() + "</td>"
                        + "<td><button href=# class='btn btn-default btn-sm' onclick=ShDetEst(" + i + ") ><span><img src='images/details.png'></span></button></td>"
                        + "<td><div id='est-" + i + "'><button href=# class='btn btn-danger btn-sm' onclick=FormDelEst(" + i + ",'show') ><span><img src='images/trash.png'></span></button></div></td>"
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

    private static class ConsumerImpl implements Consumer<Estudio_DTO> {

        private final int tipoE;
        private final PrintWriter out;
        private final List<Estudio_DTO> ests;

        public ConsumerImpl(int tipoE, PrintWriter out, List<Estudio_DTO> ests) {
            this.tipoE = tipoE;
            this.out = out;
            this.ests = ests;
        }

        @Override
        public void accept(Estudio_DTO dto) {
            if (dto.getId_Tipo_Estudio() == tipoE) {
                out.println("<tr>"
                        + "<td >" + dto.getClave_Estudio() + "</td>"
                        + "<td >" + dto.getNombre_Estudio() + "</td>"
                        + "<td><button href=# class='btn btn-default btn-sm' onclick=ShDetEst(" + ests.indexOf(dto) + ") ><span><img src='images/details.png'></span></button></td>"
                        + "<td><div id='est-" + ests.indexOf(dto) + "'><button href=# class='btn btn-danger btn-sm' onclick=FormDelEst(" + ests.indexOf(dto) + ",'show') ><span><img src='images/trash.png'></span></button></div></td>"
                        + "</tr>");
            }
        }
    }

}
