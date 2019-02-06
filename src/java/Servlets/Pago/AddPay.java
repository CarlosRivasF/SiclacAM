package Servlets.Pago;

import DataAccesObject.Orden_DAO;
import DataAccesObject.Pago_DAO;
import DataBase.Fecha;
import DataTransferObject.Orden_DTO;
import DataTransferObject.Pago_DTO;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
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
@WebServlet(name = "AddPay", urlPatterns = {"/AddPay"})
public class AddPay extends HttpServlet {
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        Date fac = new Date();
        Fecha f = new Fecha();
        f.setHora(fac);
        HttpSession sesion = request.getSession();        
        int id_Orden = Integer.parseInt(sesion.getAttribute("id_Orden").toString().trim());
        sesion.removeAttribute("id_Orden");
        Pago_DTO pago = new Pago_DTO();
        Pago_DAO P=new Pago_DAO();

        Orden_DAO O = new Orden_DAO();
        Orden_DTO Orden = O.getOrden(id_Orden);
        pago.setId_Orden(id_Orden);
        pago.setT_Pago(request.getParameter("Tipo_Pago").trim());
        pago.setMonto(Float.parseFloat(request.getParameter("monto").trim()));
        pago.setFecha(f.getFechaActual());
        pago.setHora(f.getHoraMas(5));
        P.registrarPago(pago);        

        Float MontoPagado = Orden.getMontoPagado();
        Float MontoRestante = Orden.getMontoRestante();

        O.updateSaldo(MontoPagado, MontoRestante, pago);
        
        request.getRequestDispatcher("ShowSaldos").forward(request, response);
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
