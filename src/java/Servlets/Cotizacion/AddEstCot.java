package Servlets.Cotizacion;

import DataAccesObject.Estudio_DAO;
import DataBase.Fecha;
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
        Fecha f = new Fecha();
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
        int descuento;
        String tpr;
        Det_Cot_DTO detcot;
        Float p;
        switch (mode) {
            case "lst":
                estudio = ests.get(index);
                if (request.getParameter("Desc").trim().equals("") || request.getParameter("Desc").trim().equals("0")
                        || Integer.parseInt(request.getParameter("Desc").trim()) < 0) {
                    descuento = 0;
                } else if (Integer.parseInt(request.getParameter("Desc").trim()) > 100) {
                    descuento = 100;
                } else {
                    descuento = Integer.parseInt(request.getParameter("Desc").trim());
                }
                tpr = request.getParameter("Tprec").trim();
                detcot = new Det_Cot_DTO();
                detcot.setEstudio(estudio);
                detcot.setDescuento(descuento);
                p = Float.parseFloat("0");
                detcot.setT_Entrega(tpr);
                if (detcot.getT_Entrega().equals("Normal")) {
                    p = estudio.getPrecio().getPrecio_N();
                } else if (detcot.getT_Entrega().equals("Urgente")) {
                    p = estudio.getPrecio().getPrecio_U();
                }
                detcot.setSubtotal(p - ((detcot.getDescuento() * p) / 100));
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
                    descuento = 0;
                } else if (Integer.parseInt(request.getParameter("Desc").trim()) > 100) {
                    descuento = 100;
                } else {
                    descuento = Integer.parseInt(request.getParameter("Desc").trim());
                }
                tpr = request.getParameter("Tprec").trim();
                detcot = new Det_Cot_DTO();
                detcot.setEstudio(estudio);
                detcot.setDescuento(descuento);
                p = Float.parseFloat("0");
                detcot.setT_Entrega(tpr);
                if (detcot.getT_Entrega().equals("Normal")) {
                    p = estudio.getPrecio().getPrecio_N();
                } else if (detcot.getT_Entrega().equals("Urgente")) {
                    p = estudio.getPrecio().getPrecio_U();
                }
                detcot.setSubtotal(p - ((detcot.getDescuento() * p) / 100));
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
                + "<th >Descuento</th>"
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
            Float pd = ((dto.getDescuento() * p) / 100);
            out.println("<tr>"
                    + "<td >" + dto.getEstudio().getNombre_Estudio() + "</td>"
                    + "<td >" + dto.getT_Entrega() + "</td>"
                    + "<td >" + p + "</td>"
                    + "<td >$" + pd + "</td>"
                    + "<td >" + e + " días</td>"
                    + "<td><div id='mat-" + Det_Cot.indexOf(dto) + "'><button href=# class='btn btn-danger' onclick=DelEst(" + Det_Cot.indexOf(dto) + ",'Cot') ><span><img src='images/trash.png'></span></button></div></td>"
                    + "</tr>");
            total = total + dto.getSubtotal();
            Cot.setTotal(total);
            sesion.setAttribute("Cot", Cot);
        }
        out.println("</table>");
        out.println("</div>");
        out.println("<p class='offset-8 col-3 col-sm-3 col-md-3'><strong>Pagar " + total + " pesos</strong></p>"
                + "<a class='btn btn-success btn-lg btn-block' href=# onclick=OpenRep('PrintCot') >Imprimir Cotización</a>");
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
