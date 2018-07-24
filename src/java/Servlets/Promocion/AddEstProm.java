package Servlets.Promocion;

import DataAccesObject.Estudio_DAO;
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
        switch (mode) {
            case "lst":
                int index = Integer.parseInt(request.getParameter("estudio").trim());
                Estudio_DTO estudio = ests.get(index);
                int descuento;
                if (request.getParameter("Desc").trim().equals("") || request.getParameter("Desc").trim().equals("0")
                        || Integer.parseInt(request.getParameter("Desc").trim()) < 0) {
                    descuento = 0;
                } else if (Integer.parseInt(request.getParameter("Desc").trim()) > 100) {
                    descuento = 100;
                } else {
                    descuento = Integer.parseInt(request.getParameter("Desc").trim());
                }
                String tpr = request.getParameter("Tprec").trim();
                Det_Prom_DTO detprom = new Det_Prom_DTO();
                detprom.setEstudio(estudio);
                detprom.setDescuento(descuento);
                Float p = Float.parseFloat("0");
                detprom.setT_Entrega(tpr);
                if (detprom.getT_Entrega().equals("Normal")) {
                    p = estudio.getPrecio().getPrecio_N();
                } else if (detprom.getT_Entrega().equals("Urgente")) {
                    p = estudio.getPrecio().getPrecio_U();
                }
                detprom.setSubtotal(p - ((detprom.getDescuento() * p) / 100));
                Det_Prom.add(detprom);
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
                    + "<td >$" + pd + "</td>"
                    + "<td><div id='mat-" + Det_Prom.indexOf(dto) + "'><button href=# class='btn btn-danger' onclick=DelEst(" + Det_Prom.indexOf(dto) + ",'show') ><span><img src='images/trash.png'></span></button></div></td>"
                    + "</tr>");
            total = total + dto.getSubtotal(); 
            Prom.setPrecio_Total(total);
            sesion.setAttribute("Promocion", Prom);
        }
        out.println("</table>");
        out.println("</div>");
        out.println("<p class='offset-8 col-3 col-sm-3 col-md-3'><strong>Precio " + total + " pesos</strong></p>"
                + "<a class='btn btn-success btn-lg btn-block' onclick='saveProm(this);' >Guardar Promoción</a><br>");
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