package Servlets.Estudio;

import DataAccesObject.Estudio_DAO;
import DataAccesObject.Tipo_Estudio_DAO;
import DataTransferObject.Estudio_DTO;
import DataTransferObject.Tipo_Estudio_DTO;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "ShowEst", urlPatterns = {"/ShowEst"})
public class ShowEst extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        HttpSession sesion = request.getSession();
        int id_unidad = Integer.parseInt(sesion.getAttribute("unidad").toString().trim());
        PrintWriter out = response.getWriter();
        Tipo_Estudio_DAO TE = new Tipo_Estudio_DAO();
        List<Tipo_Estudio_DTO> tipos = TE.getTipo_Estudios();
        Estudio_DAO E = new Estudio_DAO();
        List<Estudio_DTO> ests;
        ests = E.getEstudiosByUnidad(id_unidad);
        sesion.setAttribute("ests", ests);
        out.print("<div class='nav-scroller bg-white box-shadow'>"
                + "<nav class='nav nav-underline'>"
                + "<a class='nav-link' href='#' onclick=mostrarForm('" + request.getContextPath() + "/Menu/Estudio/Registro.jsp');>Nuevo Estudio</a>"
                + "<a class='nav-link active' href='#'style='color: blue'><ins>Lista de Estudios</ins></a>"
                + "        <a class='nav-link' href='#' onclick=mostrarForm('ShareEst'); >Nuevos Estudios</a>"
                + "        <a class='nav-link' href='Promociones.jsp'>Promociones</a>"
                + "</nav>"
                + "</div>"
                + "<div><br>");
        out.print("<form class='needs-validation' novalidate name='fors' action='#' method='post'>"
                + "<div class='form-row'>"
                + "<div class='col-5 col-sm-5 col-md-5 mb-3'>"
                + "<label for='Tipo_Estudio' class='sr-only'>Tipo de Estudio</label>"
                + "<select onchange='SchEst1();' class='custom-select d-block w-100 form-control' id='Tipo_Estudio' name='Tipo_Estudio' required>"
                + "<option value=''>Tipo de Estudio</option> ");

        tipos.forEach((dto) -> {
            out.print("<option value='" + dto.getId_Tipo_Estudio() + "'>" + dto.getNombre_Tipo_Estudio() + "</option>");
        });
        out.print("</select>"
                + "<div class='invalid-feedback' style='width: 100%;'>"
                + "Por favor seleccione un Tipo de Estudio."
                + "</div>"
                + "</div>"
                + "<div class='col-7 col-sm-7 col-md-7 mb-3'>"
                + "<label class='sr-only' >Buscar...</label>"
                + "<input style='text-align: center' type='text' class='form-control' onkeyup='SchEst(this);' name='Buestu' id='Buestu' placeholder='Buscar...' required>"
                + "<div class='invalid-feedback'>"
                + "Ingresa un criterio de busqueda."
                + "</div>"
                + "</div>"
                + "</div>"
                + "</form>"
                + "<div id='BEstu'>");
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
