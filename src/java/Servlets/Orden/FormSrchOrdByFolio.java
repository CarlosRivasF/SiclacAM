/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets.Orden;

import DataAccesObject.Orden_DAO;
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
@WebServlet(name = "FormSrchOrdByFolio", urlPatterns = {"/FormSrchOrdByFolio"})
public class FormSrchOrdByFolio extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        HttpSession sesion = request.getSession();
        if ("".equals(request.getParameter("Folio").trim())) {
            sesion.setAttribute("MSGOrdFol", "Por favor ingrese un folio valido...");
            request.getRequestDispatcher("ShowOrds?mode=uplRs").forward(request, response);
        } else {
            int Folio = Integer.parseInt(request.getParameter("Folio").trim());

            int id_unidad = Integer.parseInt(sesion.getAttribute("unidad").toString().trim());
            PrintWriter out = response.getWriter();
            Orden_DAO O = new Orden_DAO();
            Orden_DTO dto;

            dto = O.getOrdenByFolio(Folio, id_unidad);

            if (dto.getFolio_Unidad() != 0) {
                if (dto.getEstado().trim().equals("Cancelado")) {
                    sesion.setAttribute("MSGOrdFol", "Órden Cancelada.!");
                    request.getRequestDispatcher("ShowOrds?mode=uplRs").forward(request, response);
                } else {
                    sesion.setAttribute("OrdFol", dto);
                    request.getRequestDispatcher("ShowDetOrdRs").forward(request, response);
                }
            } else {
                sesion.setAttribute("MSGOrdFol", "No se ha encontrado la órden solicitada...");
                request.getRequestDispatcher("ShowOrds?mode=uplRs").forward(request, response);
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
