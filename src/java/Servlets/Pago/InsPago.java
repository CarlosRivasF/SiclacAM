package Servlets.Pago;

import DataBase.Fecha;
import DataTransferObject.Det_Orden_DTO;
import DataTransferObject.Orden_DTO;
import DataTransferObject.Pago_DTO;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
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
@WebServlet(name = "InsPago", urlPatterns = {"/InsPago"})
public class InsPago extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        Date fac = new Date();
        Fecha f = new Fecha();
        f.setHora(fac);
        HttpSession sesion = request.getSession();
        Orden_DTO Orden = (Orden_DTO) sesion.getAttribute("Orden");
        List<Det_Orden_DTO> Det_Orden;
        Det_Orden = Orden.getDet_Orden();
        List<Pago_DTO> Pagos;
        if (Orden.getPagos() != null) {
            Pagos = Orden.getPagos();
        } else {
            Pagos = new ArrayList<>();
        }
        Pago_DTO pago = new Pago_DTO();

        pago.setT_Pago(request.getParameter("Tipo_Pago").trim());
        pago.setMonto(Float.parseFloat(request.getParameter("monto").trim()));
        pago.setFecha(f.getFechaActual());
        pago.setHora(f.getHoraMas(6));

        Float MontoPagado = Orden.getMontoPagado();
        Float MontoRestante = Orden.getMontoRestante();

        Orden.setMontoPagado(MontoPagado + pago.getMonto());
        Orden.setMontoRestante(MontoRestante - pago.getMonto());
        Pagos.add(pago);
        Orden.setPagos(Pagos);
        try (PrintWriter out = response.getWriter()) {
            out.println("<div style='color: white' class='table-responsive'>"
                    + "<table style=' text-align: center' class='table table-bordered table-sm'>"
                    + "<tr class='bg-info' style='color: black'>"
                    + "<th >Nombre de Estudio</th>"
                    + "<th >Fecha Entrega</th>"
                    + "<th >Subtotal</th>"
                    + "</tr>");
            Det_Orden.forEach((dto) -> {
                out.println("<tr>"
                        + "<td >" + dto.getEstudio().getNombre_Estudio() + "</td>"
                        + "<td >" + dto.getFecha_Entrega() + "</td>"
                        + "<td >" + dto.getSubtotal() + "</td>"
                        + "</tr>");
            });
            out.println("</table></div>");
            
            out.print("<div class='offset-7 col'>"
                    + "<table>"
                    + "<tr>"
                    + "<td>Total : </td>"
                    + "<td>" + (Orden.getMontoRestante() + Orden.getMontoPagado()) + "</td>"
                    + "</tr>"
                    + "<tr>"
                    + "<td>A/C : </td>"
                    + "<td>" + Orden.getMontoPagado() + "</td>"
                    + "</tr>"
                    + "<tr>"
                    + "<td>Por Pagar : </td>"
                    + "<td>" + Orden.getMontoRestante() + "</td>"
                    + "</tr>"
                    + "</table>"
                    + "</div>");
            out.print("<div id='pago'>"
                    + "<button class='btn btn-success btn-lg btn-block' id='ConPay' onclick=FormPago('ord'); name='ConPay'>Realizar Pago<span><img src='images/pay.png'></span></button>"
                    + "</div><br>");
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
