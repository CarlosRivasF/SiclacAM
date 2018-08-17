package Servlets.Orden;

import DataAccesObject.Orden_DAO;
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
 * @author ZionSystem
 */
@WebServlet(name = "ShowSaldos", urlPatterns = {"/ShowSaldos"})
public class ShowSaldos extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        HttpSession sesion = request.getSession();
        int id_unidad = Integer.parseInt(sesion.getAttribute("unidad").toString().trim());
        PrintWriter out = response.getWriter();
        Orden_DAO O = new Orden_DAO();
        List<Orden_DTO> ords = O.getOrdenesSaldo(id_unidad);

        out.print("<div class='nav-scroller bg-white box-shadow'>"
                + "    <nav class='nav nav-underline'>"
                + "        <a class='nav-link' href='#' onclick=mostrarForm('" + request.getContextPath() + "/Menu/Orden/Registro.jsp');>Nueva Órden</a>"
                + "        <a class='nav-link' href='#' onclick=mostrarForm('" + request.getContextPath() + "/ShowOrds'); >Pendientes</a>"
                + "        <a class='nav-link active' href='#' onclick=mostrarForm('" + request.getContextPath() + "/ShowSaldos');  style=\"color: blue\"><ins>Saldos</ins></a>"
                + "    </nav>"
                + "</div><br>");
        out.print("<div class='form-row'>"
                + "<div class='offset-2 col-8 col-sm-8 col-md-8 mb-3'>"
                + "<label class='sr-only' >Codigo de Estudio</label>"
                + "<input style='text-align: center' type='text' class='form-control' name='medi' onkeyup=SrchOrd(this,'pac','sald'); id='medi' placeholder='Nombre de paciente' autofocus required>"
                + "</div>"
                + "</div>"
                + "<div id='SerchOrd' style='color: white' class='table-responsive'>");
        out.println("<div style='color: white' class='table-responsive'>"
                + "<table style=' text-align: center' class='table table-bordered table-hover table-sm'>"
                + "<tr class='table-active'>"
                + "<th >Clave</th>"
                + "<th >Paciente</th>"
                + "<th >Saldo</th>"
                + "<th >Pagar</th>"
                + "</tr>");
        ords.forEach((dto) -> {
            String CodeCot = dto.getPaciente().getCodPac().substring(0, 4) + "-" + dto.getId_Orden();
            out.println("<tr>"
                    + "<td >" + CodeCot + "</td>"
                    + "<td >" + dto.getPaciente().getNombre() + " " + dto.getPaciente().getAp_Paterno() + " " + dto.getPaciente().getAp_Materno() + "</td>"
                    + "<td>" + dto.getMontoRestante()+ "</td>"
                    + "<td><div id='ord-" + ords.indexOf(dto) + "'><button href=# class='btn btn-success' onclick=mostrarForm('" + request.getContextPath() + "/Menu/Pago/formAddPay.jsp?id_Orden=" + dto.getId_Orden() + "');><span><img src='images/pay.png'></span></button></div></td>"
                    + "</tr>");
        });
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
