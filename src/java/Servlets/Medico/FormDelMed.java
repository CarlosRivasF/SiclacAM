package Servlets.Medico;

import DataAccesObject.Estudio_DAO;
import DataAccesObject.Medico_DAO;
import DataTransferObject.Medico_DTO;
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
 * @author ZionSystems
 */
@WebServlet(name = "FormDelMed", urlPatterns = {"/FormDelMed"})
public class FormDelMed extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        HttpSession sesion = request.getSession();
        Medico_DAO M = new Medico_DAO();
        List<Medico_DTO> meds = (List<Medico_DTO>) sesion.getAttribute("meds");
        String i = request.getParameter("index");
        int index = Integer.parseInt(i.trim());
        if (request.getParameter("ADelm") != null) {
            String act = request.getParameter("ADelm");
            switch (act) {
                case "show":
                    try (PrintWriter out = response.getWriter()) {
                        out.print("<button href=# class='btn btn-light' onclick=FormDelMed(" + index + ",'SI')>SI</button> "
                                + "<button href=# class='btn btn-info' onclick=FormDelMed(" + index + ",'NO')>NO</button>");
                    }
                    request.getRequestDispatcher("ShowMedi#est-" + i);
                    break;
                case "SI":
                    Medico_DTO med = meds.get(index);
                    M.EliminarMed(med);
                    meds.remove(index);
                    response.sendRedirect("ShowMedi");
                    break;
                case "NO":
                    try (PrintWriter out = response.getWriter()) {
                        out.print("<button href=# class='btn btn-danger' onclick=FormDelMed(" + index + ",'show')><span><img src='images/trash.png'></span></button>");
                    }
                    request.getRequestDispatcher("ShowMedi#est-" + i);
                    break;
            }
        } else {
            try (PrintWriter out = response.getWriter()) {
                out.print("null");
            }
            request.getRequestDispatcher("ShowMedi#est-" + i);
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
