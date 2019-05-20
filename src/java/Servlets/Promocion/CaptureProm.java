package Servlets.Promocion;

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
 * @author Carlos Rivas
 */
@WebServlet(name = "CaptureProm", urlPatterns = {"/CaptureProm"})
public class CaptureProm extends HttpServlet {

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

        String mode = request.getParameter("mode").trim();
        Date fac = new Date();
        Util f = new Util();
        f.setHora(fac);
        Float total = Float.parseFloat("0");
        Float p;
        Float pd;
        Float ps;
        switch (mode) {
            case "Orden":
                Orden_DTO Orden;
                List<Det_Orden_DTO> Det_Orden;
                Orden = (Orden_DTO) sesion.getAttribute("Orden");
                if (Orden.getDet_Orden() == null || Orden.getDet_Orden().isEmpty()) {
                    Det_Orden = new ArrayList<>();
                } else {
                    Det_Orden = Orden.getDet_Orden();
                }
                //prom.getDet_Prom().forEach((dtp) -> {
                for (Det_Prom_DTO dtp : prom.getDet_Prom()) {
                    Det_Orden_DTO detor = new Det_Orden_DTO();
                    detor.setEstudio(dtp.getEstudio());
                    detor.setDescuento(dtp.getDescuento());
                    detor.setSobrecargo(dtp.getSobrecargo());
                    p = Float.parseFloat("0");
                    detor.setT_Entrega(dtp.getT_Entrega());
                    if (detor.getT_Entrega().equals("Normal")) {
                        detor.setFecha_Entrega(f.SumarDias(detor.getEstudio().getPrecio().getT_Entrega_N()));
                        p = detor.getEstudio().getPrecio().getPrecio_N();
                    } else if (detor.getT_Entrega().equals("Urgente")) {
                        detor.setFecha_Entrega(f.SumarDias(detor.getEstudio().getPrecio().getT_Entrega_U()));
                        p = detor.getEstudio().getPrecio().getPrecio_U();
                    }
                    pd = ((detor.getDescuento() * p) / 100);
                    ps = ((detor.getSobrecargo() * p) / 100);

                    detor.setSubtotal(p - pd);
                    p = detor.getSubtotal();

                    detor.setSubtotal(p + ps);
                    Det_Orden.add(detor);
                }
                Orden.setDet_Orden(Det_Orden);
                out.println("<div id='BEst'></div>"
                        + "<div style='color: white' class='table-responsive'>"
                        + "<table style=' text-align: center' class='table table-bordered table-hover table-sm'>"
                        + "<tr class='bg-warning' style='color: black'>"
                        + "<th >Nombre de Estudio</th>"
                        + "<th >Entrega</th>"
                        + "<th >Precio</th>"
                        + "<th >Desc.</th>"
                        + "<th >Cargo</th>"
                        + "<th >Espera</th>"
                        + "<th >Quitar</th>"
                        + "</tr>");

                for (Det_Orden_DTO dto : Det_Orden) {
                    p = Float.parseFloat("0");
                    int e = 0;
                    if (dto.getT_Entrega().equals("Normal")) {
                        p = dto.getEstudio().getPrecio().getPrecio_N();
                        e = dto.getEstudio().getPrecio().getT_Entrega_N();
                    } else if (dto.getT_Entrega().equals("Urgente")) {
                        p = dto.getEstudio().getPrecio().getPrecio_U();
                        e = dto.getEstudio().getPrecio().getT_Entrega_U();
                    }
                    pd = ((dto.getDescuento() * p) / 100);
                    ps = ((dto.getSobrecargo() * p) / 100);
                    out.println("<tr>"
                            + "<td >" + dto.getEstudio().getNombre_Estudio() + "</td>"
                            + "<td >" + dto.getT_Entrega() + "</td>"
                            + "<td >" + p + "</td>"
                            + "<td >$" + pd + "</td>"
                            + "<td >$" + ps + "</td>"
                            + "<td >" + e + " días</td>"
                            + "<td><div id='mat-" + Det_Orden.indexOf(dto) + "'><button href=# class='btn btn-danger' onclick=DelEst(" + Det_Orden.indexOf(dto) + ",'Ord') ><span><img src='images/trash.png'></span></button></div></td>"
                            + "</tr>");
                    total = total + dto.getSubtotal();
                    Orden.setMontoRestante(total);
                }
                sesion.setAttribute("Orden", Orden);
                out.println("</table>");
                out.println("</div>");
                out.println("<p class='offset-8 col-3 col-sm-3 col-md-3'><strong>Pagar " + Util.redondearDecimales(Orden.getMontoRestante()) + " pesos</strong></p>");
                if (Orden.getMedico() == null) {
                    out.print("<div class='alert alert-danger alert-dismissible fade show' role='alert'>"
                            + "            <center><strong>Alerta.!</strong> Aún no elige médico para esta orden.</center>"
                            + "            <button type='button' class='close' data-dismiss='alert' aria-label='Close'>"
                            + "                <span aria-hidden='true'>&times;</span>"
                            + "            </button>"
                            + "        </div>");
                }
                out.println("<button class='btn btn-success btn-lg btn-block' id='ConPay' onclick=contOr('ord'); name='ConPay'>Continuar</button>");
                break;
            case "Cotizacion":
                Cotizacion_DTO Cot;
                List<Det_Cot_DTO> Det_Cot;
                Cot = (Cotizacion_DTO) sesion.getAttribute("Cotizacion");
                if (Cot.getDet_Cot() == null || Cot.getDet_Cot().isEmpty()) {
                    Det_Cot = new ArrayList<>();
                } else {
                    Det_Cot = Cot.getDet_Cot();
                }
                for (Det_Prom_DTO dtp : prom.getDet_Prom()) {
                    Det_Cot_DTO detcot = new Det_Cot_DTO();
                    detcot.setEstudio(dtp.getEstudio());
                    detcot.setDescuento(dtp.getDescuento());
                    detcot.setSobrecargo(dtp.getSobrecargo());
                    p = Float.parseFloat("0");
                    detcot.setT_Entrega(dtp.getT_Entrega());
                    if (detcot.getT_Entrega().equals("Normal")) {
                        p = detcot.getEstudio().getPrecio().getPrecio_N();
                    } else if (detcot.getT_Entrega().equals("Urgente")) {
                        p = detcot.getEstudio().getPrecio().getPrecio_U();
                    }
                    pd = ((detcot.getDescuento() * p) / 100);
                    ps = ((detcot.getSobrecargo() * p) / 100);

                    detcot.setSubtotal(p - pd);
                    p = detcot.getSubtotal();

                    detcot.setSubtotal(p + ps);
                    Det_Cot.add(detcot);
                }
                Cot.setDet_Cot(Det_Cot);
                out.println("<div id='BEst'></div>"
                        + "<div style='color: white' class='table-responsive'>"
                        + "<table style=' text-align: center' class='table table-bordered table-hover table-sm'>"
                        + "<tr class='bg-warning' style='color: black'>"
                        + "<th >Nombre de Estudio</th>"
                        + "<th >Entrega</th>"
                        + "<th >Precio</th>"
                        + "<th >Desc.</th>"
                        + "<th >Cargo</th>"
                        + "<th >Espera</th>"
                        + "<th >Quitar</th>"
                        + "</tr>");
                for (Det_Cot_DTO dto : Det_Cot) {
                    p = Float.parseFloat("0");
                    int e = 0;
                    if (dto.getT_Entrega().equals("Normal")) {
                        p = dto.getEstudio().getPrecio().getPrecio_N();
                        e = dto.getEstudio().getPrecio().getT_Entrega_N();
                    } else if (dto.getT_Entrega().equals("Urgente")) {
                        p = dto.getEstudio().getPrecio().getPrecio_U();
                        e = dto.getEstudio().getPrecio().getT_Entrega_U();
                    }
                    pd = ((dto.getDescuento() * p) / 100);
                    ps = ((dto.getSobrecargo() * p) / 100);
                    out.println("<tr>"
                            + "<td >" + dto.getEstudio().getNombre_Estudio() + "</td>"
                            + "<td >" + dto.getT_Entrega() + "</td>"
                            + "<td >" + p + "</td>"
                            + "<td >$" + pd + "</td>"
                            + "<td >$" + ps + "</td>"
                            + "<td >" + e + " días</td>"
                            + "<td><div id='mat-" + Det_Cot.indexOf(dto) + "'><button href=# class='btn btn-danger' onclick=DelEst(" + Det_Cot.indexOf(dto) + ",'Ord') ><span><img src='images/trash.png'></span></button></div></td>"
                            + "</tr>");
                    total = total + dto.getSubtotal();
                }
                Cot.setTotal(total);
                sesion.setAttribute("Cotizacion", Cot);
                out.println("</table>");
                out.println("</div>");
                out.println("<p class='offset-8 col-3 col-sm-3 col-md-3'><strong>Pagar " + Util.redondearDecimales(total) + " pesos</strong></p>");
                if (Cot.getMedico() == null) {
            out.print("<div class='alert alert-danger alert-dismissible fade show' role='alert'>"
                    + "            <center><strong>Alerta.!</strong> Aún no elige médico para esta cotización.</center>"
                    + "            <button type='button' class='close' data-dismiss='alert' aria-label='Close'>"
                    + "                <span aria-hidden='true'>&times;</span>"
                    + "            </button>"
                    + "        </div>");
        }
                out.println("<a class='btn btn-success btn-lg btn-block' href=# onclick=OpenRep('PrintCot') >Imprimir Cotización</a>");
                break;
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
