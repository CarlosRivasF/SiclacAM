package Servlets.Promocion;

import DataAccesObject.Promocion_DAO;
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
@WebServlet(name = "UpdProm", urlPatterns = {"/UpdProm"})
public class UpdProm extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        HttpSession sesion = request.getSession();
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        Promocion_DAO P = new Promocion_DAO();
        int id_unidad = Integer.parseInt(sesion.getAttribute("unidad").toString().trim());
        List<Promocion_DTO> proms = P.getPromociones(id_unidad);
        int index = Integer.parseInt(request.getParameter("index").trim());
        String part = request.getParameter("part").trim();
        String acc = request.getParameter("acc").trim();
        Promocion_DTO prom = proms.get(index);
        switch (part) {
            case "name":
                if ("upd".equals(acc)) {
                    prom.setNombre_Promocion(request.getParameter("nameProm"));
                    P.ActualizarNameProm(prom.getNombre_Promocion(), prom.getId_Unidad());
                    out.println("<table style=' text-align: center' class='table table-bordered table-hover table-sm'>"
                            + "<tr class='table-info' style='color: black'>"
                            + "<th style='color: black'>Nombre</th>"
                            + "<td >" + prom.getNombre_Promocion() + "</td>"
                            + "<th><button href=# class='btn btn-warning btn-sm' onclick=FormUpProm(" + index + ",'name','form') ><span><img src='images/pencil.png'></span></button></th>"
                            + "</tr></table>");
                } else {
                    out.println("<table style=' text-align: center' class='table table-bordered table-hover table-sm'>"
                            + "<tr class='table-info' style='color: black'>"
                            + "<th style='color: black'>Nombre</th>"
                            + "<td><input style='text-align: center' type='text' class='form-control form-control-sm' name='nameProm' value='" + prom.getNombre_Promocion() + "' id='nameProm' placeholder='Nombre' required></td>"
                            + "<th><button href=# class='btn btn-warning btn-sm' onclick=FormUpProm(" + index + ",'name','upd') ><span><img src='images/pencil.png'></span></button></th>"
                            + "</tr></table>");
                }
                break;
            case "desc":
                if ("upd".equals(acc)) {
                    prom.setDescripcion(request.getParameter("descProm"));
                    P.ActualizarDescProm(prom.getDescripcion(), prom.getId_Promocion());
                    out.println("<table style=' text-align: center' class='table table-bordered table-hover table-sm'>"
                            + "<tr class='table-success' style='color: black'>"
                            + "<th style='color: black'>Descripción</th>"
                            + "<td >" + prom.getDescripcion() + "</td>"
                            + "<th><button href=# class='btn btn-warning btn-sm' onclick=FormUpProm(" + index + ",'desc','form') ><span><img src='images/pencil.png'></span></button></th>"
                            + "</tr></table>");
                } else {
                    out.println("<table style=' text-align: center' class='table table-bordered table-hover table-sm'>"
                            + "<tr class='table-success' style='color: black'>"
                            + "<th style='color: black'>Descripción</th>"
                            + "<td><input style='text-align: center' type='text' class='form-control form-control-sm' name='descProm' value='" + prom.getDescripcion() + "' id='descProm' placeholder='Descripción' required></td>"
                            + "<th><button href=# class='btn btn-warning btn-sm' onclick=FormUpProm(" + index + ",'desc','upd') ><span><img src='images/pencil.png'></span></button></th>"
                            + "</tr></table>");
                }
                break;
            case "fchs":
                if ("upd".equals(acc)) {
                    prom.setFecha_I(request.getParameter("fechaI").trim());
                    prom.setFecha_F(request.getParameter("fechaF").trim());
                    P.ActualizarFchsProm(prom.getFecha_I(), prom.getFecha_F(), prom.getId_Promocion());
                    out.println("<table style=' text-align: center' class='table table-bordered table-hover table-sm'>"
                            + "<tr class='table-info' style='color: black'>"
                            + "<th style='color: black'>Fecha Inicial</th>"
                            + "<th style='color: black'>Fecha Final</th>"
                            + "<th style='color: black'>Modificar</th></tr><tr>"
                            + "<td >" + prom.getFecha_I() + "</td>"
                            + "<td >" + prom.getFecha_F() + "</td>"
                            + "<th><button href=# class='btn btn-warning btn-sm' onclick=FormUpProm(" + index + ",'fchs','form') ><span><img src='images/pencil.png'></span></button></th>"
                            + "</tr></table>");
                } else {
                    out.println("<table style=' text-align: center' class='table table-bordered table-hover table-sm'>"
                            + "<tr class='table-info' style='color: black'>"
                            + "<th style='color: black'>Fecha Inicial</th>"
                            + "<th style='color: black'>Fecha Final</th>"
                            + "<th style='color: black'>Modificar</th></tr><tr>"
                            + "<td><input style='text-align: center' type='date' class='form-control form-control-sm' name='fechaI' value='" + prom.getFecha_I() + "' id='fechaI' required></td>"
                            + "<td><input style='text-align: center' type='date' class='form-control form-control-sm' name='fechaF' value='" + prom.getFecha_F() + "' id='fechaF' required></td>"
                            + "<th><button href=# class='btn btn-warning btn-sm' onclick=FormUpProm(" + index + ",'fchs','form') ><span><img src='images/pencil.png'></span></button></th>"
                            + "</tr></table>");
                }
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
