package Servlets.Promocion;

import DataAccesObject.Det_Prom_DAO;
import DataAccesObject.Estudio_DAO;
import DataAccesObject.Promocion_DAO;
import DataTransferObject.Det_Prom_DTO;
import DataTransferObject.Estudio_DTO;
import DataTransferObject.Promocion_DTO;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
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
@WebServlet(name = "AddEstProm", urlPatterns = {"/AddEstProm"})
public class AddEstProm extends HttpServlet {

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

        Promocion_DTO Prom;
        List<Det_Prom_DTO> Det_Prom;
        Prom = (Promocion_DTO) sesion.getAttribute("Promocion");
        if (Prom.getDet_Prom() == null) {
            Det_Prom = new ArrayList<>();
        } else {
            Det_Prom = Prom.getDet_Prom();
        }
        String mode = request.getParameter("mode").trim();
        Float p;
        Float pd;
        Float ps;
        Det_Prom_DAO Det = new Det_Prom_DAO();
        Promocion_DAO P = new Promocion_DAO();
        switch (mode) {
            case "lst":
                int index = Integer.parseInt(request.getParameter("estudio").trim());
                Estudio_DTO estudio = ests.get(index);
                Float descuento;
                Float sobrecargo;
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
                String tpr = request.getParameter("Tprec").trim();
                Det_Prom_DTO detprom = new Det_Prom_DTO();
                detprom.setEstudio(estudio);
                detprom.setDescuento(descuento);
                detprom.setSobrecargo(sobrecargo);
                p = Float.parseFloat("0");
                detprom.setT_Entrega(tpr);
                if (detprom.getT_Entrega().equals("Normal")) {
                    p = estudio.getPrecio().getPrecio_N();
                } else if (detprom.getT_Entrega().equals("Urgente")) {
                    p = estudio.getPrecio().getPrecio_U();
                }
                pd = ((detprom.getDescuento() * p) / 100);
                ps = ((detprom.getSobrecargo() * p) / 100);

                detprom.setSubtotal(p - pd);
                p = detprom.getSubtotal();

                detprom.setSubtotal(p + ps);

                Det_Prom.add(detprom);

                if (request.getParameter("shdet") != null) {
                    Det.registrarDetor(Prom.getId_Promocion(), detprom);
                }

                Prom.setDet_Prom(Det_Prom);
                break;
            case "code":

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
                + "<th >Quitar</th>"
                + "</tr>");
        Float total = Float.parseFloat("0");
        for (Det_Prom_DTO dto : Det_Prom) {
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
                    + "<td >$" + ps + "</td>");
            if (request.getParameter("shdet") == null) {
                out.println("<td><div id='mat-" + Det_Prom.indexOf(dto) + "'><button href=# class='btn btn-danger' onclick=DelEst(" + Det_Prom.indexOf(dto) + ",'Prom') ><span><img src='images/trash.png'></span></button></div></td>");
            } else {
                out.println("<td><div id='mat-" + Det_Prom.indexOf(dto) + "'><button href=# class='btn btn-danger' onclick=DelEstSecc(" + Det_Prom.indexOf(dto) + ",'Prom') ><span><img src='images/trash.png'></span></button></div></td>");
            }
            out.println("</tr>");
            total = total + dto.getSubtotal();
            Prom.setPrecio_Total(total);
            if (request.getParameter("shdet") != null) {
                P.ActualizarPrecProm(Prom.getPrecio_Total(), Prom.getId_Promocion());
            }
            sesion.setAttribute("Promocion", Prom);
        }
        if (request.getParameter("shdet") != null) {
            out.println("<tr>"
                    + "<td colspan='7'><button href=# class='btn btn-success btn-block' onclick=addEstMode('Prom')>Agregar nuevo estudio</button></td>"
                    + "</tr>");
        }
        out.println("</table>");

        out.println("</div>");
        out.println("<p class='offset-8 col-3 col-sm-3 col-md-3'><strong>Precio " + total + " pesos</strong></p>");
        if (request.getParameter("shdet") == null) {
            out.println("<a class='btn btn-success btn-lg btn-block' onclick='saveProm(this);' >Guardar Promoción</a><br>");
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
