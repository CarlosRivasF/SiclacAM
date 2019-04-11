package Servlets.Orden;

import DataAccesObject.Det_Prom_DAO;
import DataAccesObject.Promocion_DAO;
import DataBase.Util;
import DataTransferObject.Cotizacion_DTO;
import DataTransferObject.Det_Cot_DTO;
import DataTransferObject.Det_Orden_DTO;
import DataTransferObject.Det_Prom_DTO;
import DataTransferObject.Orden_DTO;
import DataTransferObject.Promocion_DTO;
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
        Date fac = new Date();
        Util f = new Util();
        f.setHora(fac);
        if (request.getParameter("modulo") != null) {
            String Modulo = request.getParameter("modulo").trim();
            switch (Modulo) {
                case "Ord":
                    Orden_DTO Orden = (Orden_DTO) sesion.getAttribute("Orden");
                    List<Det_Orden_DTO> Det_Orden = Orden.getDet_Orden();
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
                                    + "<td >" + f.SumarDias(e) + "</td>");
                            if (request.getParameter("shdet") == null) {
                                out.println("<td><div id='mat-" + Det_Orden.indexOf(dto) + "'><button href=# class='btn btn-danger' onclick=DelEst(" + Det_Orden.indexOf(dto) + ",'Ord') ><span><img src='images/trash.png'></span></button></div></td>");
                            } else {
                                out.println("<td><div id='mat-" + Det_Orden.indexOf(dto) + "'><button href=# class='btn btn-danger' onclick=DelEstSecc(" + Det_Orden.indexOf(dto) + ",'Ord') ><span><img src='images/trash.png'></span></button></div></td>");
                            }
                            out.println("</tr>");
                            total = total + dto.getSubtotal();
                            Orden.setMontoRestante(total);
                            sesion.setAttribute("Orden", Orden);
                        }
                        out.println("</table>");
                        out.println("</div>");
                        out.println("<p class='offset-8 col-3 col-sm-3 col-md-3'><strong>Pagar" + Util.redondearDecimales(Orden.getMontoRestante()) + " pesos</strong></p>");

                        if (request.getParameter("shdet") == null) {
                            out.println("<button class='btn btn-success btn-lg btn-block' id='ConPay' onclick='contOr(ord);' name='ConPay'>Continuar</button>");
                        }
                    } else {
                        out.println("<div id='BEst'></div>");
                    }
                    break;
                case "Cot":                    
                    Cotizacion_DTO Cot = (Cotizacion_DTO) sesion.getAttribute("Cotizacion");                    
                    List<Det_Cot_DTO> Det_Cot = null;
                    if (Cot.getDet_Cot() == null) {
                        System.out.println("Det_Cot NULL");
                    } else if (Cot.getDet_Cot().isEmpty()) {
                        System.out.println("Det_Cot IS EMPTY");
                    } else {
                        Det_Cot = Cot.getDet_Cot();
                    }
                    int index2 = Integer.parseInt(request.getParameter("index").trim());
                    Det_Cot.remove(index2);
                    System.out.println("Se elimino el estudio " + index2);
                    if (!Det_Cot.isEmpty()) {
                        out.println("<div id='BEst'></div>"
                                + "<div style='color: white' class='table-responsive'>"
                                + "<table style=' text-align: center' class='table table-bordered table-hover table-sm'>"
                                + "<tr class='bg-warning' style='color: black'>"
                                + "<th >Nombre de Estudio</th>"
                                + "<th >Entrega</th>"
                                + "<th >Precio</th>"
                                + "<th >Descuento</th>"
                                + "<th >Espera</th>"
                                + "<th >Quitar</th>"
                                + "</tr>");
                        Float total = Float.parseFloat("0");
                        for (Det_Cot_DTO dto : Det_Cot) {
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
                                    + "<td >" + e + " días</td>");
                            if (request.getParameter("shdet") == null) {
                                out.println("<td><div id='mat-" + Det_Cot.indexOf(dto) + "'><button href=# class='btn btn-danger' onclick=DelEst(" + Det_Cot.indexOf(dto) + ",'Cot') ><span><img src='images/trash.png'></span></button></div></td>");
                            } else {
                                out.println("<td><div id='mat-" + Det_Cot.indexOf(dto) + "'><button href=# class='btn btn-danger' onclick=DelEstSecc(" + Det_Cot.indexOf(dto) + ",'Cot') ><span><img src='images/trash.png'></span></button></div></td>");
                            }
                            out.println("</tr>");
                            total = total + dto.getSubtotal();
                            Cot.setTotal(total);
                            sesion.setAttribute("Cot", Cot);
                        }
                        out.println("</table>");
                        out.println("</div>");

                        out.println("<p class='offset-8 col-3 col-sm-3 col-md-3'><strong>Pagar " + total + " pesos</strong></p>");
                        if (request.getParameter("shdet") == null) {
                            out.println("<a class='btn btn-success btn-lg btn-block' href=# onclick=OpenRep('PrintCot') >Imprimir Cotización</a>");
                        }
                    } else {
                        out.println("<div id='BEst'></div>");
                    }
                    break;
                case "Prom":
                    Promocion_DTO Prom = (Promocion_DTO) sesion.getAttribute("Promocion");
                    List<Det_Prom_DTO> Det_Prom = Prom.getDet_Prom();
                    int index3 = Integer.parseInt(request.getParameter("index").trim());
                    if (request.getParameter("shdet") != null) {
                        Det_Prom_DTO det = Det_Prom.get(index3);
                        Det_Prom_DAO Dp = new Det_Prom_DAO();
                        Dp.EliminarMaterial(det.getId_Det_Prom());
                    }
                    Det_Prom.remove(index3);
                    if (!Det_Prom.isEmpty()) {
                        out.println("<div id='BEst'></div>"
                                + "<div style='color: white' class='table-responsive'>"
                                + "<table style=' text-align: center' class='table table-bordered table-hover table-sm'>"
                                + "<tr class='bg-warning' style='color: black'>"
                                + "<th >Nombre de Estudio</th>"
                                + "<th >Entrega</th>"
                                + "<th >Precio</th>"
                                + "<th >Descuento</th>"
                                + "<th >Quitar</th>"
                                + "</tr>");
                        Float total = Float.parseFloat("0");
                        for (Det_Prom_DTO dto : Det_Prom) {
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
                                    + "<td >$" + pd + "</td>");
                            if (request.getParameter("shdet") == null) {
                                out.println("<td><div id='mat-" + Det_Prom.indexOf(dto) + "'><button href=# class='btn btn-danger' onclick=DelEst(" + Det_Prom.indexOf(dto) + ",'Prom') ><span><img src='images/trash.png'></span></button></div></td>");
                            } else {
                                out.println("<td><div id='mat-" + Det_Prom.indexOf(dto) + "'><button href=# class='btn btn-danger' onclick=DelEstSecc(" + Det_Prom.indexOf(dto) + ",'Prom') ><span><img src='images/trash.png'></span></button></div></td>");
                            }
                            out.println("</tr>");
                            total = total + dto.getSubtotal();
                            Prom.setPrecio_Total(total);
                            sesion.setAttribute("Promocion", Prom);
                        }
                        if (request.getParameter("shdet") != null) {
                            out.println("<tr>"
                                    + "<td colspan='7'><button href=# class='btn btn-success btn-block' onclick=addEstMode('Prom')>Agregar nuevo estudio</button></td>"
                                    + "</tr>");
                        }
                        out.println("</table>");
                        out.println("</div>");
                        if (request.getParameter("shdet") != null) {
                            Promocion_DAO P = new Promocion_DAO();
                            P.ActualizarPrecProm(Prom.getPrecio_Total(), Prom.getId_Promocion());
                        }
                        out.println("<p class='offset-8 col-3 col-sm-3 col-md-3'><strong>Precio " + total + " pesos</strong></p>");
                        if (request.getParameter("shdet") == null) {
                            out.println("<a class='btn btn-success btn-lg btn-block' onclick='saveProm(this);' >Guardar Promoción</a><br>");
                        }
                    } else {
                        out.println("<div id='BEst'></div>");
                    }
                    break;
                default:
                    break;
            }
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
