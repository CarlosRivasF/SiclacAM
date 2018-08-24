package Servlets.Promocion;

import DataAccesObject.Promocion_DAO;
import DataTransferObject.Det_Prom_DTO;
import DataTransferObject.Promocion_DTO;
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
@WebServlet(name = "ShDetProm", urlPatterns = {"/ShDetProm"})
public class ShDetProm extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        HttpSession sesion = request.getSession();
        int id_unidad = Integer.parseInt(sesion.getAttribute("unidad").toString().trim());
        PrintWriter out = response.getWriter();
        Promocion_DAO P = new Promocion_DAO();
        List<Promocion_DTO> proms = P.getPromociones(id_unidad);
        int index = Integer.parseInt(request.getParameter("index").trim());
        Promocion_DTO prom = proms.get(index);
        out.print("<div class='nav-scroller bg-white box-shadow'>"
                + "    <nav class='nav nav-underline'>"
                + "        <a class='nav-link' href='#' onclick=mostrarForm('" + request.getContextPath() + "/Menu/Promocion/Registro.jsp');>Nueva Promoción</a>"
                + "        <a class='nav-link active' href='#' onclick=mostrarForm('" + request.getContextPath() + "/ShowProms'); >Lista de Promociones</a>"
                + "    </nav>"
                + "</div>"
                + "<hr class='mb-1'>"
                + "<h4 style='text-align: center; color: white'>Registró: " + prom.getEmpleado().getNombre() + " " + prom.getEmpleado().getAp_Paterno() + " " + prom.getEmpleado().getAp_Materno() + "</h4>"
                + "<hr class='mb-1'>");
        out.println("<div style='color: white'>");

        out.println("<div id='DvnameProm' class='table-responsive'>"
                + "<table style=' text-align: center' class='table table-bordered table-hover table-sm'>"
                + "<tr class='table-info' style='color: black'>"
                + "<th style='color: black'>Nombre</th>"
                + "<td >" + prom.getNombre_Promocion() + "</td>"
                + "<th><button href=# class='btn btn-warning btn-sm' onclick=FormUpProm(" + index + ",'name','form') ><span><img src='images/pencil.png'></span></button></th>"
                + "</tr></table>"
                + "</div>");

        out.println("<div id='DvdescProm' class='table-responsive'>"
                + "<table style=' text-align: center' class='table table-bordered table-hover table-sm'>"
                + "<tr class='table-success' style='color: black'>"
                + "<th style='color: black'>Descripción</th>"
                + "<td >" + prom.getDescripcion() + "</td>"
                + "<th><button href=# class='btn btn-warning btn-sm' onclick=FormUpProm(" + index + ",'desc','form') ><span><img src='images/pencil.png'></span></button></th>"
                + "</tr></table>");
        out.println("</div>");

        out.println("<div id='DvfcsProm' class='table-responsive'>"
                + "<table style=' text-align: center' class='table table-bordered table-hover table-sm'>"
                + "<tr class='table-info' style='color: black'>"
                + "<th style='color: black'>Fecha Inicial</th>"
                + "<th style='color: black'>Fecha Final</th>"
                + "<th style='color: black'>Modificar</th></tr><tr>"
                + "<td >" + prom.getFecha_I() + "</td>"
                + "<td >" + prom.getFecha_F() + "</td>"
                + "<th><button href=# class='btn btn-warning btn-sm' onclick=FormUpProm(" + index + ",'fchs','form') ><span><img src='images/pencil.png'></span></button></th>"
                + "</tr></table>"
                + "</div>");

        out.println("<div id='EstsAdded'>"
                + "<div id='BEst'></div>"
                + "<table style=' text-align: center' class='table table-bordered table-hover table-sm'>"
                + "<tr class='bg-warning' style='color: black'>"
                + "<th >Nombre de Estudio</th>"
                + "<th >Entrega</th>"
                + "<th >Precio</th>"
                + "<th >Descuento</th>"
                + "<th >Quitar</th>"
                + "</tr>");
        Float total = Float.parseFloat("0");
        for (Det_Prom_DTO dto : prom.getDet_Prom()) {
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
            dto.setSubtotal(p - pd);
            out.println("<tr>"
                    + "<td >" + dto.getEstudio().getNombre_Estudio() + "</td>"
                    + "<td >" + dto.getT_Entrega() + "</td>"
                    + "<td >" + p + "</td>"
                    + "<td >$" + pd + "</td>"
                    + "<td><div id='mat-" + prom.getDet_Prom().indexOf(dto) + "'><button href=# class='btn btn-danger' onclick=DelEstSecc(" + prom.getDet_Prom().indexOf(dto) + ",'Prom') ><span><img src='images/trash.png'></span></button></div></td>"
                    + "</tr>");
            total = total + dto.getSubtotal();
        }
        prom.setPrecio_Total(total);
        sesion.setAttribute("Promocion", prom);
        out.println("<tr>"
                + "<td colspan='7'><button href=# class='btn btn-success btn-block' onclick=addEstMode('Prom')>Agregar nuevo estudio</button></td>"
                + "</tr>");
        out.println("</table>");
        out.println("<p class='offset-8 col-3 col-sm-3 col-md-3'><strong>Precio " + total + " pesos</strong></p>");
        out.println("</div>");        

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
