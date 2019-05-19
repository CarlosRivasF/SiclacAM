package Servlets.Orden;

import DataAccesObject.Estudio_DAO;
import DataBase.Util;
import DataTransferObject.Det_Orden_DTO;
import DataTransferObject.Estudio_DTO;
import DataTransferObject.Orden_DTO;
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
@WebServlet(name = "AddEst", urlPatterns = {"/AddEst"})
public class AddEst extends HttpServlet {

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

        Orden_DTO Orden;
        List<Det_Orden_DTO> Det_Orden;
        Orden = (Orden_DTO) sesion.getAttribute("Orden");
        if (Orden.getDet_Orden() == null || Orden.getDet_Orden().isEmpty()) {
            Det_Orden = new ArrayList<>();
        } else {
            Det_Orden = Orden.getDet_Orden();
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
        Det_Orden_DTO detor;
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
                detor = new Det_Orden_DTO();
                detor.setEstudio(estudio);
                detor.setDescuento(descuento);
                detor.setSobrecargo(sobrecargo);
                p = Float.parseFloat("0");
                detor.setT_Entrega(tpr);

                if (detor.getT_Entrega().equals("Normal")) {
                    detor.setFecha_Entrega(f.SumarDias(detor.getEstudio().getPrecio().getT_Entrega_N()));
                    p = estudio.getPrecio().getPrecio_N();
                } else if (detor.getT_Entrega().equals("Urgente")) {
                    detor.setFecha_Entrega(f.SumarDias(detor.getEstudio().getPrecio().getT_Entrega_U()));
                    p = estudio.getPrecio().getPrecio_U();
                }

                pd = ((detor.getDescuento() * p) / 100);
                ps = ((detor.getSobrecargo() * p) / 100);

                detor.setSubtotal(p - pd);
                p = detor.getSubtotal();

                detor.setSubtotal(p + ps);

                Det_Orden.add(detor);
                Orden.setDet_Orden(Det_Orden);
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
                detor = new Det_Orden_DTO();
                detor.setEstudio(estudio);
                detor.setDescuento(descuento);
                detor.setSobrecargo(sobrecargo);
                p = Float.parseFloat("0");
                detor.setT_Entrega(tpr);
                switch (detor.getT_Entrega()) {
                    case "Normal":
                        detor.setFecha_Entrega(f.SumarDias(detor.getEstudio().getPrecio().getT_Entrega_N()));
                        p = estudio.getPrecio().getPrecio_N();
                        break;
                    case "Urgente":
                        detor.setFecha_Entrega(f.SumarDias(detor.getEstudio().getPrecio().getT_Entrega_U()));
                        p = estudio.getPrecio().getPrecio_U();
                        break;
                }
                pd = ((detor.getDescuento() * p) / 100);
                ps = ((detor.getSobrecargo() * p) / 100);
                System.out.println("");
                detor.setSubtotal(p - pd);
                p = detor.getSubtotal();

                detor.setSubtotal(p + ps);
                Det_Orden.add(detor);
                Orden.setDet_Orden(Det_Orden);
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
        out.println("<p class='offset-8 col-3 col-sm-3 col-md-3'><strong>Pagar " + Util.redondearDecimales(Orden.getMontoRestante())
                + " pesos</strong></p>");
        if (Orden.getMedico() == null) {
            out.print("<div class='alert alert-danger alert-dismissible fade show' role='alert'>"
                    + "            <center><strong>Alerta.!</strong> Aún no elige médico para esta orden.</center>"
                    + "            <button type='button' class='close' data-dismiss='alert' aria-label='Close'>"
                    + "                <span aria-hidden='true'>&times;</span>"
                    + "            </button>"
                    + "        </div>");
        }
        out.println("<button class='btn btn-success btn-lg btn-block' id='ConPay' onclick=contOr('ord'); name='ConPay'>Continuar</button>");
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
