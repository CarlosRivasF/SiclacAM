package Servlets.Orden;

import DataBase.Fecha;
import DataTransferObject.Det_Orden_DTO;
import DataTransferObject.Orden_DTO;
import java.io.IOException;
import java.io.PrintWriter;
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
@WebServlet(name = "DelEst", urlPatterns = {"/DelEst"})
public class DelEst extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        HttpSession sesion = request.getSession();
        PrintWriter out = response.getWriter();
        List<Det_Orden_DTO> Det_Orden;
        Date fac = new Date();
        Fecha f = new Fecha();
        f.setHora(fac);
        Orden_DTO Orden = (Orden_DTO) sesion.getAttribute("Orden");
        Det_Orden = Orden.getDet_Orden();
        int index = Integer.parseInt(request.getParameter("index").trim());
        Det_Orden.remove(index);
        if (!Det_Orden.isEmpty()) {
            out.println("<div id='BEst'></div>"
                    + "<div style='color: white' class='table-responsive'>"
                    + "<table style=' text-align: center' class='table table-bordered table-hover table-sm'>"
                    + "<tr class='bg-warning' style='color: black'>"
                    + "<th >Nombre de Estudio</th>"
                    + "<th >Entrega</th>"
                    + "<th >Precio</th>"
                    + "<th >Descuento</th>"
                    + "<th >Espera</th>"
                    + "<th >Entrega</th>"
                    + "<th >Quitar</th>"
                    + "</tr>");
            Float total = Float.parseFloat("0");
            for (Det_Orden_DTO dto : Det_Orden) {
                Float p = Float.parseFloat("0");
                int e = 0;
                if (dto.getT_Entrega().equals("Normal")) {
                    p = dto.getEstudio().getPrecio().getPrecio_N();
                    e = dto.getEstudio().getPrecio().getT_Entrega_N();
                } else if (dto.getT_Entrega().equals("Urgente")) {
                    p = dto.getEstudio().getPrecio().getPrecio_U();
                    e = dto.getEstudio().getPrecio().getT_Entrega_U();
                }
                Float pd = ((dto.getDescuento() * p) / 100);
                out.println("<tr>"
                        + "<td >" + dto.getEstudio().getNombre_Estudio() + "</td>"
                        + "<td >" + dto.getT_Entrega() + "</td>"
                        + "<td >" + p + "</td>"
                        + "<td >$" + pd + "</td>"
                        + "<td >" + e + " días</td>"
                        + "<td >" + f.SumarDias(e) + "</td>"
                        + "<td><div id='mat-" + Det_Orden.indexOf(dto) + "'><button href=# class='btn btn-danger' onclick=DelEst(" + Det_Orden.indexOf(dto) + ",'show') ><span><img src='images/trash.png'></span></button></div></td>"
                        + "</tr>");
                total = total + dto.getSubtotal();
                Orden.setTotal(total);
                sesion.setAttribute("Orden", Orden);
            }
            out.println("</table>");
            out.println("</div>");
            out.println("<p class='offset-8 col-3 col-sm-3 col-md-3'><strong>Pagar" + total + " pesos</strong></p>"
                    + "<button class='btn btn-success btn-lg btn-block' id='ConPay' onclick='contOr();' name='ConPay'>Continuar</button>");
        } else {
            out.println("<div id='BEst'></div>");
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
