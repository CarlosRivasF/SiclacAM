package Servlets.Estudio;

import DataAccesObject.Estudio_DAO;
import DataTransferObject.Est_Mat_DTO;
import DataTransferObject.Estudio_DTO;
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
@WebServlet(name = "FormDelEst_Mat", urlPatterns = {"/FormDelEst_Mat"})
public class FormDelEst_Mat extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        HttpSession sesion = request.getSession();
        Estudio_DAO E = new Estudio_DAO();
        List<Estudio_DTO> ests;
        ests = (List<Estudio_DTO>) sesion.getAttribute("ests");
        String ix = request.getParameter("index");
        int index = Integer.parseInt(ix.trim());
        if (request.getParameter("ADelm") != null && request.getParameter("liCo") != null) {
            String act = request.getParameter("ADelm").trim();
            int liCo = Integer.parseInt(request.getParameter("liCo").trim());
            switch (act) {
                case "show":
                    try (PrintWriter out = response.getWriter()) {
                        out.print("<button href=# class='btn btn-light' onclick=FormDelEst_Mat(" + index + "," + liCo + ",'SI')>SI</button> "
                                + "<button href=# class='btn btn-info' onclick=FormDelEst_Mat(" + index + "," + liCo + ",'NO')>NO</button>");
                    }
                    request.getRequestDispatcher("ShDetEst#estMt-" + liCo);
                    break;
                case "SI":
                    Estudio_DTO est = ests.get(index);
                    Est_Mat_DTO mat = est.getMts().get(liCo);
                    E.EliminarEst_Mat(est.getId_Est_Uni(), mat.getId_Unid_Mat());
                    est.getMts().remove(liCo);
                    try (PrintWriter out = response.getWriter()) {
                        out.println("<table style=' text-align: center' class='table table-bordered table-hover table-sm'>"
                                + "<tr class='table-success' style='color: black'>"
                                + "<th >Material</th>"
                                + "<th >Cantidad</th>"
                                + "<th >Unidad</th>"
                                + "<th >Muestra</th>"
                                + "<th>Modificar</th>"
                                + "<th>Eliminar</th>"
                                + "</tr>");
                        int i = 0;
                        for (Est_Mat_DTO mt : est.getMts()) {
                            out.println("<tr>"
                                    + "<td >" + mt.getNombre_Material() + "</td>"
                                    + "<td >" + mt.getCantidadE() + "</td>"
                                    + "<td >" + mt.getUnidadE() + "</td>"
                                    + "<td >" + mt.getT_Muestra() + "</td>"
                                    + "<td><button href=# class='btn btn-warning btn-sm' onclick=FormUpEstMat(" + index + "," + i + ",'form') ><span><img src='images/pencil.png'></span></button></td>"
                                    + "<td><div id='estMt-" + i + "'><button href=# class='btn btn-danger btn-sm' onclick=FormDelEst_Mat(" + index + "," + i + ",'show') ><span><img src='images/trash.png'></span></button></div></td>"
                                    + "</tr>");
                            i++;
                        }
                        out.println("</table>");
                    }
                    request.getRequestDispatcher("ShDetEst#matis");
                    break;
                case "NO":
                    try (PrintWriter out = response.getWriter()) {
                        out.print("<button href=# class='btn btn-danger btn-sm' onclick=FormDelEst_Mat(" + index + "," + liCo + ",'show') ><span><img src='images/trash.png'></span></button>");
                    }
                    request.getRequestDispatcher("ShDetEst#estMt-" + liCo);
                    break;
            }
        } else {
            try (PrintWriter out = response.getWriter()) {
                out.print("null");
            }
            request.getRequestDispatcher("ShowEst#estMt-" + ix);
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
