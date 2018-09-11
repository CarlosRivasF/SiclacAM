package Servlets.Orden;

import DataAccesObject.Orden_DAO;
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
 * @author Carlos Rivas
 */
@WebServlet(name = "SrchOrdFolio", urlPatterns = {"/SrchOrdFolio"})
public class SrchOrdFolio extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        HttpSession sesion = request.getSession();
        int id_unidad = Integer.parseInt(sesion.getAttribute("unidad").toString().trim());
        PrintWriter out = response.getWriter();
        Orden_DAO O = new Orden_DAO();
        Orden_DTO dto;
        int Folio = Integer.parseInt(request.getParameter("Folio").trim());
        String part = request.getParameter("mode").trim();
        switch (part) {
            case "ord":
                dto = O.getOrdenPendiente(Folio, id_unidad);
                break;
            case "sald":
                dto = O.getOrdenSaldo(Folio, id_unidad);
                break;
            case "results":
                dto = O.getOrdenTerminada(Folio, id_unidad);
                break;
            default:
                dto = O.getOrdenByFolio(Folio, id_unidad);
                break;
        }

        out.println("<div style='color: white' class='table-responsive'>"
                + "<table style=' text-align: center' class='table table-bordered table-hover table-sm'>"
                + "<tr class='table-active'>"
                + "<th >Folio de Unidad</th>"
                + "<th >Clave</th>"
                + "<th >Paciente</th>");
        switch (part) {
            case "ord":
                out.println("<th >Detalles</th>"
                        + "<th >Cancelar</th>");
                break;
            case "sald":
                out.println("<th >Saldo</th>");
                out.println("<th >Pagar</th>");
                break;
            case "results":
                out.println("<th >Resultados</th>");
                out.println("<th >Imprimir</th>");
                break;
        }
        if (dto.getFolio_Unidad() != 0) {
            String CodeCot = dto.getPaciente().getCodPac().substring(0, 4) + "-" + dto.getId_Orden();
            out.println("<tr>"
                    + "<td >" + dto.getFolio_Unidad() + "</td>"
                    + "<td >" + CodeCot + "</td>"
                    + "<td >" + dto.getPaciente().getNombre() + " " + dto.getPaciente().getAp_Paterno() + " " + dto.getPaciente().getAp_Materno() + "</td>");
            switch (part) {
                case "ord":
                    out.print("<td><button href=# class='btn btn-default' onclick=ShDetOrden(" + dto.getId_Orden() + ",'folio') ><span><img src='images/details.png'></span></button></td>");
                    out.println("<td><div id='ord-" + dto.getId_Orden() + "'><button href=# class='btn btn-primary' onclick=CancelOrd(" + dto.getId_Orden() + ") ><span><img src='images/cancel.png'></span></button></div></td>");
                    break;
                case "sald":
                    out.println("<td >" + dto.getMontoRestante() + "</td>");
                    out.print("<td><div id='ord-" + dto.getId_Orden() + "'><button href=# class='btn btn-success' onclick=mostrarForm('" + request.getContextPath() + "/Menu/Pago/formAddPay.jsp?id_Orden=" + dto.getId_Orden() + "');><span><img src='images/pay.png'></span></button></div></td>");
                    break;
                case "results":
                    out.println("<td><div id='ord-" + dto.getId_Orden() + "'><button href=# class='btn btn-primary' onclick=ShDetOrdenRS(" + dto.getId_Orden() + ",'folio') ><span><img src='images/fill.png'></span></button></div></td>");
                    out.println("<td><div id='ord-" + dto.getId_Orden() + "'><button href='PrintRes?LxOrdSald=" + dto.getId_Orden() + "' class='btn btn-primary' ><span><img src='images/print.png'></span></button></div></td>");
                    break;
            }
            out.print("</tr>");
        } else {

            out.println("<tr>"
                    + "<td colspan='5'>Sin Resultados... [Folio de Unidad:" + Folio + "]</td>"
                    + "</tr>");

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
