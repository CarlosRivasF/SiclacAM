package Servlets.Cotizacion;

import DataAccesObject.Estudio_DAO;
import DataBase.Util;
import DataTransferObject.Det_Cot_DTO;
import DataTransferObject.Estudio_DTO;
import DataTransferObject.Cotizacion_DTO;
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
@WebServlet(name = "AddEstCot", urlPatterns = {"/AddEstCot"})
public class AddEstCot extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        HttpSession sesion = request.getSession();
        int id_unidad = Integer.parseInt(sesion.getAttribute("unidad").toString().trim());
        PrintWriter out = response.getWriter();
        Estudio_DAO E = new Estudio_DAO();
        List<Estudio_DTO> ests;
        if (sesion.getAttribute("ests") != null) {
            ests = (List<Estudio_DTO>) sesion.getAttribute("ests");
        } else {
            ests = E.getEstudiosByUnidad(id_unidad);
            sesion.setAttribute("ests", ests);
        }

        Cotizacion_DTO Cot;
        List<Det_Cot_DTO> Det_Cot;
        Cot = (Cotizacion_DTO) sesion.getAttribute("Cotizacion");

        if (Cot.getDet_Cot() == null) {
            Det_Cot = new ArrayList<>();
        } else {
            Det_Cot = Cot.getDet_Cot();
        }
        Date fac = new Date();
        Util f = new Util();
        f.setHora(fac);
        String mode = request.getParameter("mode").trim();
        int index;
        String est = request.getParameter("estudio").trim();
        if (est.contains("-")) {
            String[] ixs = est.split("-");
            index = Integer.parseInt(ixs[0]);
        } else {
            index = Integer.parseInt(request.getParameter("estudio").trim());
        }

        Estudio_DTO estudio = null;
        Float descuento;
        Float sobrecargo;
        String tpr;
        Det_Cot_DTO detcot;
        Float p;
        Float pd;
        Float ps;
        switch (mode) {
            case "lst":
                estudio = ests.get(index);
                if (request.getParameter("Desc").trim().equals("") || request.getParameter("Desc").trim().equals("0")
                        || Float.parseFloat(request.getParameter("Desc").trim()) < 0) {
                    descuento = Float.parseFloat("0");
                } else if (Float.parseFloat(request.getParameter("Desc").trim()) > 100) {
                    descuento = Float.parseFloat("100");
                } else {
                    descuento = Float.parseFloat(request.getParameter("Desc").trim());
                }

                if (request.getParameter("Sco").trim().equals("") || request.getParameter("Sco").trim().equals("0")
                        || Float.parseFloat(request.getParameter("Sco").trim()) < 0) {
                    sobrecargo = Float.parseFloat("0");
                } else if (Float.parseFloat(request.getParameter("Sco").trim()) > 100) {
                    sobrecargo = Float.parseFloat("100");
                } else {
                    sobrecargo = Float.parseFloat(request.getParameter("Sco").trim());
                }

                tpr = request.getParameter("Tprec").trim();
                detcot = new Det_Cot_DTO();
                detcot.setEstudio(estudio);
                detcot.setDescuento(descuento);
                detcot.setSobrecargo(sobrecargo);
                p = Float.parseFloat("0");
                detcot.setT_Entrega(tpr);
                if (detcot.getT_Entrega().equals("Normal")) {
                    p = estudio.getPrecio().getPrecio_N();
                } else if (detcot.getT_Entrega().equals("Urgente")) {
                    p = estudio.getPrecio().getPrecio_U();
                }

                pd = ((detcot.getDescuento() * p) / 100);
                ps = ((detcot.getSobrecargo() * p) / 100);

                detcot.setSubtotal(p - pd);
                p = detcot.getSubtotal();

                detcot.setSubtotal(p + ps);

                Det_Cot.add(detcot);
                Cot.setDet_Cot(Det_Cot);
                break;
            case "code":
                for (Estudio_DTO e : ests) {
                    if (e.getId_Est_Uni() == index) {
                        estudio = e;
                    }
                }
                if (estudio == null) {
                    estudio = E.getEst_Uni(index);
                }
                if (request.getParameter("Desc").trim().equals("") || request.getParameter("Desc").trim().equals("0")
                        || Integer.parseInt(request.getParameter("Desc").trim()) < 0) {
                    descuento = Float.parseFloat("0");
                } else if (Float.parseFloat(request.getParameter("Desc").trim()) > 100) {
                    descuento = Float.parseFloat("100");
                } else {
                    descuento = Float.parseFloat(request.getParameter("Desc").trim());
                }

                if (request.getParameter("Sco").trim().equals("") || request.getParameter("Sco").trim().equals("0")
                        || Float.parseFloat(request.getParameter("Sco").trim()) < 0) {
                    sobrecargo = Float.parseFloat("0");
                } else if (Float.parseFloat(request.getParameter("Sco").trim()) > 100) {
                    sobrecargo = Float.parseFloat("100");
                } else {
                    sobrecargo = Float.parseFloat(request.getParameter("Sco").trim());
                }

                tpr = request.getParameter("Tprec").trim();
                detcot = new Det_Cot_DTO();
                detcot.setEstudio(estudio);
                detcot.setDescuento(descuento);
                detcot.setSobrecargo(sobrecargo);
                p = Float.parseFloat("0");
                detcot.setT_Entrega(tpr);
                if (detcot.getT_Entrega().equals("Normal")) {
                    p = estudio.getPrecio().getPrecio_N();
                } else if (detcot.getT_Entrega().equals("Urgente")) {
                    p = estudio.getPrecio().getPrecio_U();
                }
                pd = ((detcot.getDescuento() * p) / 100);
                ps = ((detcot.getSobrecargo() * p) / 100);
                detcot.setSubtotal(p - pd);
                p = detcot.getSubtotal();

                detcot.setSubtotal(p + ps);
                Det_Cot.add(detcot);
                Cot.setDet_Cot(Det_Cot);
                break;
        }
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
        Float total = Float.parseFloat("0");
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
                    + "<td><div id='mat-" + Det_Cot.indexOf(dto) + "'><button href=# class='btn btn-danger' onclick=DelEst(" + Det_Cot.indexOf(dto) + ",'Cot') ><span><img src='images/trash.png'></span></button></div></td>"
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
                    + "            <center><strong>Alerta.!</strong> Aún no elige médico para esta Cotización.</center>"
                    + "            <button type='button' class='close' data-dismiss='alert' aria-label='Close'>"
                    + "                <span aria-hidden='true'>&times;</span>"
                    + "            </button>"
                    + "        </div>");
        }
        out.println("<a class='btn btn-success btn-lg btn-block' href=# onclick=OpenRep('PrintCot') >Imprimir Cotización</a>");
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
