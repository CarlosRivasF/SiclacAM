package Servlets.Orden;

import DataAccesObject.Resultado_DAO;
import DataBase.Fecha;
import DataTransferObject.Configuracion_DTO;
import DataTransferObject.Det_Orden_DTO;
import DataTransferObject.Orden_DTO;
import DataTransferObject.Resultado_DTO;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.Period;
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
@WebServlet(name = "UplResults", urlPatterns = {"/UplResults"})
public class UplResults extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        HttpSession sesion = request.getSession();
        Orden_DTO Orden = (Orden_DTO) sesion.getAttribute("OrdenSh");
        int index = Integer.parseInt(request.getParameter("index").trim());
        Det_Orden_DTO det = Orden.getDet_Orden().get(index);
        Resultado_DAO R = new Resultado_DAO();
        det.getEstudio().getCnfs().forEach((cnf) -> {
            if (cnf.getRes() == null) {
                if (request.getParameter("valRes-" + det.getEstudio().getCnfs().indexOf(cnf)) != null) {
                    Resultado_DTO res = new Resultado_DTO();
                    res.setValor_Obtenido(request.getParameter("valRes-" + det.getEstudio().getCnfs().indexOf(cnf)));
                    res.setId_resultado(R.RegistrarResultado(det.getId_det_orden(), cnf.getId_Configuración(), res.getValor_Obtenido()));
                    cnf.setRes(res);
                }
            }
        });
        det.getEstudio().setAddRes(true);

        Orden.getDet_Orden().set(index, det);
        sesion.setAttribute("OrdenSh", Orden);

        try (PrintWriter out = response.getWriter()) {
            out.println("<table style=' text-align: center' class='table table-bordered table-hover table-sm'>"
                    + "<tr class='table-info' style='color: black'>"
                    + "<th >Nombre</th>"
                    + "<th >Precio</th>"
                    + "<th >Metodología</th>"
                    + "<th>Llenar</th>"
                    + "</tr>");
            Orden.getDet_Orden().forEach((detor) -> {

                out.println("<tr>"
                        + "<td >" + detor.getEstudio().getNombre_Estudio() + "</td>"
                        + "<td >" + detor.getSubtotal() + "</td>"
                        + "<td >" + detor.getEstudio().getMetodo() + "</td>");
                if (detor.getEstudio().getAddRes()) {
                    out.println("<td><div id='estCn-" + Orden.getDet_Orden().indexOf(detor) + "'><button href=# class='btn btn-danger btn-sm' onclick=FormResDet(" + Orden.getDet_Orden().indexOf(detor) + ") ><span><img src='images/eye.png'></span></button></div></td>");
                } else {
                    out.println("<td><div id='estCn-" + Orden.getDet_Orden().indexOf(detor) + "'><button href=# class='btn btn-danger btn-sm' onclick=FormResDet(" + Orden.getDet_Orden().indexOf(detor) + ") ><span><img src='images/fill.png'></span></button></div></td>");
                }
                out.println("</tr>");
            });
            out.println("</table>"
                + "<a class='btn btn-success btn-lg btn-block' href='PrintRes' >Imprimir Resultados</a><br>");
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
