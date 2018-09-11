/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets.Orden;

import DataAccesObject.Resultado_DAO;
import DataTransferObject.Configuracion_DTO;
import DataTransferObject.Det_Orden_DTO;
import DataTransferObject.Observacion_DTO;
import DataTransferObject.Orden_DTO;
import java.io.IOException;
import java.io.PrintWriter;
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
@WebServlet(name = "FormUpResObs", urlPatterns = {"/FormUpResObs"})
public class FormUpResObs extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String acc = request.getParameter("acc").trim();
        if (request.getParameter("index") != null) {
            HttpSession sesion = request.getSession();
            Orden_DTO Orden = (Orden_DTO) sesion.getAttribute("OrdenSh");
            int index = Integer.parseInt(request.getParameter("index").trim());
            Det_Orden_DTO det = Orden.getDet_Orden().get(index);
            Resultado_DAO R = new Resultado_DAO();
            if ("upd".equals(acc)) {
                if (det.getEstudio().getObservacion() == null || det.getEstudio().getObservacion().getId_Observacion() == 0) {
                    Observacion_DTO Obs = new Observacion_DTO();
                    Obs.setObservacion(request.getParameter("Observ"));
                    Obs.setId_Observacion(R.RegistrarObserv(det.getId_det_orden(), Obs.getObservacion()));
                    det.getEstudio().setObservacion(Obs);
                } else {
                    det.getEstudio().getObservacion().setObservacion(request.getParameter("Observ"));
                    R.updateObs(det.getId_det_orden(), det.getEstudio().getObservacion());
                }
                Orden.getDet_Orden().set(index, det);
                sesion.setAttribute("OrdenSh", Orden);
                out.print(det.getEstudio().getObservacion().getObservacion());
            } else {
                if (det.getEstudio().getObservacion() == null || det.getEstudio().getObservacion().getObservacion() == null) {
                    out.println("<input style='text-align: center' type='text' class='form-control form-control-sm' name='Observ-" + index + "' id='Observ-" + index + "' placeholder='Observaciones...'>");
                } else {
                    out.println("<input style='text-align: center' type='text' class='form-control form-control-sm' name='Observ-" + index + "' value='" + det.getEstudio().getObservacion().getObservacion() + "' id='Observ-" + index + "' placeholder='Observaciones...'>");
                }
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
