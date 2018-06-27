package Servlets.Pago;

import DataTransferObject.Det_Orden_DTO;
import DataTransferObject.Orden_DTO;
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
@WebServlet(name = "FormPago", urlPatterns = {"/FormPago"})
public class FormPago extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");        
        try (PrintWriter out = response.getWriter()) {
            out.println(""
                    + "<div class='form-row'>"                    
                    + "<div class='col-sm-5 col-md-5 mb-3'>"
                    + "<select class='custom-select d-block w-100 form-control' id='Tipo_Pago' name='Tipo_Pago' required>"
                    + "<option value=''>Tipo de Pago</option>"
                    + "<option value='Efectivo'>Efectivo</option>"
                    + "<option value='Tarjeta'>Tarjeta</option>"
                    + "</select>"
                    + "<div class='invalid-feedback' style='width: 100%;'>"
                    + "Por favor selecciona un Tipo de Pago."
                    + "</div>"
                    + "</div>"
                    + "<div class='col-5 col-sm-4 col-md-4 mb-3'>"
                    + "<label class='sr-only' >Monto</label>"
                    + "<input style='text-align: center' type='number' class='form-control' name='monto' id='monto' placeholder='Buscar...' required>"
                    + "<div class='invalid-feedback'>"
                    + "Por favor ingresa un monto."
                    + "</div>"
                    + "</div>"
                    + "<div class='col-7 col-sm-4 col-md-3 mb-3'>"
                    + "<button class='btn btn-success btn-block' onclick='Pagar();'>Realizar Pago</button>"
                    + "</div>"
                    + "</div>");
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
