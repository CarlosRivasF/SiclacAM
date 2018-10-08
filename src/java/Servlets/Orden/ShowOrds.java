package Servlets.Orden;

import DataTransferObject.Orden_DTO;
import java.io.IOException;
import java.io.PrintWriter;
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
@WebServlet(name = "ShowOrds", urlPatterns = {"/ShowOrds"})
public class ShowOrds extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        HttpSession sesion = request.getSession();
        PrintWriter out = response.getWriter();
        String mode = request.getParameter("mode").trim();
        System.out.println(mode);
        out.print("<div class='nav-scroller bg-white box-shadow'>"
                + "    <nav class='nav nav-underline'>");
        switch (mode) {
            case "ord":
                out.println("<a class='nav-link' href='#' onclick=mostrarForm('" + request.getContextPath() + "/ShowOrds?mode=ord'); style=\"color: blue\"><ins>Órdenes Pendientes</ins></a>"
                        + "<a class='nav-link' href='#' onclick=mostrarForm('" + request.getContextPath() + "/ShowOrds?mode=sald'); > Órdenes con Saldo</a>"
                        + "<a class='nav-link' href='#' onclick=mostrarForm('" + request.getContextPath() + "/ShowOrds?mode=results'); >Órdenes Terminadas</a>"
                        + "<a class='nav-link' href='#' onclick=mostrarForm('" + request.getContextPath() + "/ShowOrds?mode=uplRs'); >Cargar Resultados</a>");
                break;
            case "sald":
                out.println("<a class='nav-link' href='#' onclick=mostrarForm('" + request.getContextPath() + "/ShowOrds?mode=ord'); >Órdenes Pendientes</a>"
                        + "<a class='nav-link' href='#' onclick=mostrarForm('" + request.getContextPath() + "/ShowOrds?mode=sald'); style=\"color: blue\"><ins> Órdenes con Saldo</ins></a>"
                        + "<a class='nav-link' href='#' onclick=mostrarForm('" + request.getContextPath() + "/ShowOrds?mode=results'); >Órdenes Terminadas</a>"
                        + "<a class='nav-link' href='#' onclick=mostrarForm('" + request.getContextPath() + "/ShowOrds?mode=uplRs'); >Cargar Resultados</a>");
                break;
            case "results":
                out.println("<a class='nav-link' href='#' onclick=mostrarForm('" + request.getContextPath() + "/ShowOrds?mode=ord'); >Órdenes Pendientes</a>"
                        + "<a class='nav-link' href='#' onclick=mostrarForm('" + request.getContextPath() + "/ShowOrds?mode=sald'); > Órdenes con Saldo</a>"
                        + "<a class='nav-link' href='#' onclick=mostrarForm('" + request.getContextPath() + "/ShowOrds?mode=results');  style=\"color: blue\"><ins>Órdenes Terminadas</ins></a>"
                        + "<a class='nav-link' href='#' onclick=mostrarForm('" + request.getContextPath() + "/ShowOrds?mode=uplRs'); >Cargar Resultados</a>");
                break;
            case "uplRs":
                out.println("<a class='nav-link' href='#' onclick=mostrarForm('" + request.getContextPath() + "/ShowOrds?mode=ord'); >Órdenes Pendientes</a>"
                        + "<a class='nav-link' href='#' onclick=mostrarForm('" + request.getContextPath() + "/ShowOrds?mode=sald'); > Órdenes con Saldo</a>"
                        + "<a class='nav-link' href='#' onclick=mostrarForm('" + request.getContextPath() + "/ShowOrds?mode=results'); >Órdenes Terminadas</a>"
                        + "<a class='nav-link' href='#' onclick=mostrarForm('" + request.getContextPath() + "/ShowOrds?mode=uplRs'); style=\"color: blue\"><ins>Cargar Resultados</ins></a>");
                break;
        }
        out.println("</nav>"
                + "</div><br>");
        out.print("<div class='form-row'>"
                + "<div class='col-8 col-sm-8 col-md-8 mb-3'>"
                + "<label class='sr-only' >Nombre de paciente</label>"
                + "<input style='text-align: center' type='text' class='form-control' name='medi' onkeyup=SrchOrd(this,'pac','" + mode + "'); id='medi' placeholder='Nombre de paciente'>"
                + "</div>"
                + "<div class='col-4 col-sm-4 col-md-4 mb-3'>"
                + "<label class='sr-only' >Folio de Órden</label>");
        if (!mode.equals("uplRs")) {
            out.println("<input style='text-align: center' type='text' class='form-control' name='folio' onchange=SrchOrdFolio(this,'" + mode + "'); id='folio' placeholder='Folio de Unidad'>");
        } else {
            out.println("<input style='text-align: center' type='text' class='form-control' name='folio' onchange=UplResbydFolio(this); id='folio' placeholder='Folio de Unidad'>");
        }
        out.println("</div>"
                + "</div>"
                + "<div id='SerchOrd' style='color: white' class='table-responsive'>");

        if (sesion.getAttribute("MSGOrdFol") != null) {
            out.println("<br><h3 style='color: white'>" + sesion.getAttribute("MSGOrdFol").toString() + "</h3>");
            sesion.removeAttribute("OrdFol");
        }
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
