package Servlets.Convenio;

import DataTransferObject.Cotizacion_DTO;
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
 * @author ZionSystems
 */
@WebServlet(name = "SveConv", urlPatterns = {"/SveConv"})
public class SveConv extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession sesion = request.getSession();
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String convenio = request.getParameter("conv").trim();
        String mode = request.getParameter("mode").trim();
        switch (mode) {
            case "cot":
                Cotizacion_DTO Cotizacion = (Cotizacion_DTO) sesion.getAttribute("Cotizacion");
                Cotizacion.setConvenio(convenio);
                out.println("<label >Convenio de Cotización Guardado</label>"
                        + "<input style='text-align: center' type='text' class='form-control' value='" + Cotizacion.getConvenio() + "'  readonly>");
                sesion.setAttribute("Cotizacion", Cotizacion);
                break;
            case "ord":
                Orden_DTO Orden = (Orden_DTO) sesion.getAttribute("Orden");
                Orden.setConvenio(convenio);
                out.println("<label >Convenio de Órden Guardado</label>"
                        + "<input style='text-align: center' type='text' class='form-control' value='" + Orden.getConvenio() + "'  readonly>");
                sesion.setAttribute("Orden", Orden);
                break;
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
