package Servlets.Orden;

import DataAccesObject.Orden_DAO;
import DataBase.Util;
import DataTransferObject.Det_Orden_DTO;
import DataTransferObject.Est_Mat_DTO;
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
@WebServlet(name = "ShowDetOrdRs", urlPatterns = {"/ShowDetOrdRs"})
public class ShowDetOrdRs extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        HttpSession sesion = request.getSession();
        int id_unidad = Integer.parseInt(sesion.getAttribute("unidad").toString().trim());
        PrintWriter out = response.getWriter();
        Orden_DAO O = new Orden_DAO();
        Orden_DTO dto;
        if (request.getParameter("index") != null) {
            List<Orden_DTO> ords = O.getOrdenesTerminadas(id_unidad);
            int index = Integer.parseInt(request.getParameter("index").trim());
            dto = ords.get(index);
        } else if (request.getParameter("id_Orden") != null) {
            int id_orden = Integer.parseInt(request.getParameter("id_Orden").trim());
            dto = O.getOrden(id_orden);
        } else if (request.getParameter("Folio") != null) {
            int Folio = Integer.parseInt(request.getParameter("Folio").trim());
            dto = O.getOrdenByFolio(Folio, id_unidad);
        } else {
            dto = (Orden_DTO) sesion.getAttribute("OrdFol");
            sesion.removeAttribute("OrdFol");
        }
        sesion.setAttribute("OrdenSh", dto);
        String CodeCot = dto.getPaciente().getCodPac().substring(0, 4) + "-" + dto.getId_Orden();
        out.print("<div class='nav-scroller bg-white box-shadow'>"
                + " <nav class='nav nav-underline'>"
                + "<a class='nav-link' href='#' onclick=mostrarForm('" + request.getContextPath() + "/ShowOrds?mode=ord'); >Órdenes Pendientes</a>"
                + "<a class='nav-link' href='#' onclick=mostrarForm('" + request.getContextPath() + "/ShowOrds?mode=sald'); > Órdenes con Saldo</a>"
                + "<a class='nav-link' href='#' onclick=mostrarForm('" + request.getContextPath() + "/ShowOrds?mode=results'); >Órdenes Terminadas</a>"
                + "<a class='nav-link' href='#' onclick=mostrarForm('" + request.getContextPath() + "/ShowOrds?mode=uplRs'); >Cargar Resultados</a>"
                + " </nav>"
                + "</div>"
                + "<div><hr class='mb-1'>"
                + "<pre> <h6 style='color: white'>Paciente: " + dto.getPaciente().getNombre() + " " + dto.getPaciente().getAp_Paterno() + " " + dto.getPaciente().getAp_Materno() + ""
                + " Fecha de Órden: " + dto.getFecha() + " Hora: " + dto.getHora() + "</h6></pre><br>"
                + "<pre><h6 style='color: white'>Realizó: " + dto.getEmpleado().getNombre() + " " + dto.getEmpleado().getAp_Paterno() + " " + dto.getEmpleado().getAp_Materno() + "&nbsp;&nbsp;&nbsp;&nbsp;"
                + "</h6></pre>"
                + "<hr class='mb-1'>");
        out.println("<div style='color: white' class='table-responsive'>");
        out.println("<div id='medico'>"
                + "<table style=' text-align: center' class='table table-bordered table-hover table-sm'>"
                + "<tr class='table-info' style='color: black'>"
                + "<th style='color: black'>Médico</th>"
                + "<td >" + dto.getMedico().getNombre() + " " + dto.getMedico().getAp_Paterno() + " " + dto.getMedico().getAp_Materno() + "</td>"
                + "</tr></table>");
        out.println("</div>");

        out.println("<div id='convenio'>"
                + "<table style=' text-align: center' class='table table-bordered table-hover table-sm'>"
                + "<tr class='table-success' style='color: black'>"
                + "<th style='color: black'>Convenio</th>"
                + "<td >" + dto.getConvenio() + "</td>"
                + "</tr></table>");
        out.println("</div>");

        out.println("<div id='precio'>"
                + "<table style=' text-align: center' class='table table-bordered table-hover table-sm'>"
                + "<tr class='table-warning' style='color: black'>"
                + "<th >Saldo a Cuenta</th>"
                + "<th >Saldo Restante</th>"
                + "<th >Precio Total</th>"
                + "</tr>");
        out.println("<tr>"
                + "<td >" + Util.redondearDecimales(dto.getMontoPagado()) + " Pesos</td>"
                + "<td >" + Util.redondearDecimales(dto.getMontoRestante()) + " Pesos</td>"
                + "<td >" + Util.redondearDecimales(dto.getMontoPagado() + dto.getMontoRestante()) + " Pesos</td>"
                + "</tr></table>");
        out.println("</div>");

        out.print("<h5 style='text-align: center; color: white'>INGRESAR RESULTADOS DE ESTUDIOS</h5>");
        out.println("<div id='fill_detor'></div>");
        out.println("<div id='detors'>");
        out.println("<table style=' text-align: center' class='table table-bordered table-hover table-sm'>"
                + "<tr class='table-info' style='color: black'>"
                + "<th >Nombre</th>"
                + "<th >Precio</th>"
                + "<th >Metodología</th>"
                + "<th>Llenar</th>"
                + "</tr>");
        dto.getDet_Orden().forEach((det) -> {
            out.println("<tr>"
                    + "<td >" + det.getEstudio().getNombre_Estudio() + "</td>"
                    + "<td >" + det.getSubtotal() + "</td>"
                    + "<td >" + det.getEstudio().getMetodo() + "</td>");
            if (det.getEstudio().getAddRes()) {
                out.println("<td><div id='estCn-" + dto.getDet_Orden().indexOf(det) + "'><button href=# class='btn btn-danger btn-sm' onclick=FormResDet(" + dto.getDet_Orden().indexOf(det) + ") ><span><img src='images/eye.png'></span></button></div></td>");
            } else {
                out.println("<td><div id='estCn-" + dto.getDet_Orden().indexOf(det) + "'><button href=# class='btn btn-danger btn-sm' onclick=FormResDet(" + dto.getDet_Orden().indexOf(det) + ") ><span><img src='images/fill.png'></span></button></div></td>");
            }
            out.println("</tr>");
        });

        out.println("</table>");
        Boolean pr = false;
        for (Det_Orden_DTO d : dto.getDet_Orden()) {
            if (d.getEstudio().getAddRes()) {
                pr = true;
            }
        }
        out.print("<h5 style='text-align: center; color: white'>EIQUETAS DE MATERIAL</h5>");
        if (pr) {
            out.println("<div id='precio'>"
                    + "<table style=' text-align: center' class='table table-bordered table-hover table-sm'>"
                    + "<tr class='table-warning' style='color: black'>"
                    + "<th >Estudio</th>"
                    + "<th >Material</th>"
                    + "<th >Etiqueta</th>"
                    + "</tr>");
            dto.getDet_Orden().forEach((detor) -> {
                detor.getEstudio().getMts().forEach((mt) -> {
                    out.println("<tr>"
                            + "<td >" + detor.getEstudio().getNombre_Estudio() + "</td>"
                            + "<td >" + mt.getClave() + "</td>"
                            + "<td ><a class='btn btn-outline-light btm-sm' href=# onclick=OpenRep('PrintL2?LxOrdSald=" + Util.Encriptar(String.valueOf(dto.getId_Orden())) + "&IxDtOrd="+dto.getDet_Orden().indexOf(detor)+"&IxDtOrdMt="+detor.getEstudio().getMts().indexOf(mt)+"') >Imprimir Etiqueta</a></td>"
                            + "</tr>");
                });
            });
            out.println("</table></div>");

            out.print("<a class='btn btn-success btn-lg btn-block' href=# onclick=OpenRep('PrintRes?LxOrdSald=" + Util.Encriptar(String.valueOf(dto.getId_Orden())) + "') >Imprimir Resultados</a><br>");
        } else {
            out.println("<div id='precio'>"
                    + "<table style=' text-align: center' class='table table-bordered table-hover table-sm'>"
                    + "<tr class='table-warning' style='color: black'>"
                    + "<th >Estudio</th>"
                    + "<th >Material</th>"
                    + "<th >Etiqueta</th>"
                    + "</tr>");
            dto.getDet_Orden().forEach((detor) -> {
                detor.getEstudio().getMts().forEach((mt) -> {
                    out.println("<tr>"
                            + "<td >" + detor.getEstudio().getNombre_Estudio() + "</td>"
                            + "<td >" + mt.getClave() + " Pesos</td>"
                            + "<td ><a class='btn btn-outline-light btm-sm' href=# onclick=OpenRep('PrintL2?LxOrdSald=" + Util.Encriptar(String.valueOf(dto.getId_Orden())) + "&IxDtOrd="+dto.getDet_Orden().indexOf(detor)+"&IxDtOrdMt="+detor.getEstudio().getMts().indexOf(mt)+"') >Imprimir Etiqueta</a></td>"
                            + "</tr>");
                });
            });
            out.println("</table></div>");

            out.print("<a class='btn btn-success btn-lg btn-block' href=# onclick=OpenRep('PrintRes?LxOrdSald=" + Util.Encriptar(String.valueOf(dto.getId_Orden())) + "') >Imprimir Resultados</a><br>");
        }
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
