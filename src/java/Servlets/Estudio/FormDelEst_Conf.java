package Servlets.Estudio;

import DataAccesObject.Estudio_DAO;
import DataTransferObject.Configuracion_DTO;
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
@WebServlet(name = "FormDelEst_Conf", urlPatterns = {"/FormDelEst_Conf"})
public class FormDelEst_Conf extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        HttpSession sesion = request.getSession();
        Estudio_DAO E = new Estudio_DAO();
        List<Estudio_DTO> ests;
        ests = (List<Estudio_DTO>) sesion.getAttribute("ests");
        String i = request.getParameter("index");
        int index = Integer.parseInt(i.trim());
        if (request.getParameter("ADelm") != null && request.getParameter("liCo") != null) {
            String act = request.getParameter("ADelm").trim();
            int liCo = Integer.parseInt(request.getParameter("liCo").trim());
            switch (act) {
                case "show":
                    try (PrintWriter out = response.getWriter()) {
                        out.print("<button href=# class='btn btn-light' onclick=FormDelEst_Conf(" + index + "," + liCo + ",'SI')>SI</button> "
                                + "<button href=# class='btn btn-info' onclick=FormDelEst_Conf(" + index + "," + liCo + ",'NO')>NO</button>");
                    }
                    request.getRequestDispatcher("ShDetEst#estCn-" + i);
                    break;
                case "SI":
                    Estudio_DTO est = ests.get(index);
                    Configuracion_DTO cnf = est.getCnfs().get(liCo);
                    E.EliminarEst_Conf(est.getId_Est_Uni(), cnf.getId_Configuración());
                    est.getCnfs().remove(liCo);
                    try (PrintWriter out = response.getWriter()) {
                        out.println("<table style=' text-align: center' class='table table-bordered table-hover table-sm'>"
                                + "<tr class='table-info' style='color: black'>"
                                + "<th >Desc</th>"
                                + "<th >Sexo</th>"
                                + "<th >Val 1</th>"
                                + "<th >Val 2</th>"
                                + "<th >Unidades</th>"
                                + "<th>Modificar</th>"
                                + "<th>Eliminar</th>"
                                + "</tr>");
                        int l = 0;
                        for (Configuracion_DTO conf : est.getCnfs()) {
                            out.println("<tr>"
                                    + "<td >" + conf.getDescripcion() + "</td>"
                                    + "<td >" + conf.getSexo() + "</td>"
                                    + "<td >" + conf.getValor_min() + "</td>"
                                    + "<td >" + conf.getValor_MAX() + "</td>"
                                    + "<td >" + conf.getUniddes() + "</td>"
                                    + "<td><button href=# class='btn btn-warning btn-sm' onclick=FormUpCn(" + index + "," + l + ",'form') ><span><img src='images/pencil.png'></span></button></td>"
                                    + "<td><div id='estCn-" + l + "'><button href=# class='btn btn-danger btn-sm' onclick=FormDelEst_Conf(" + index + "," + l + ",'show') ><span><img src='images/trash.png'></span></button></div></td>"
                                    + "</tr>");
                            l++;
                        }
                        out.println("</table>");
                    }
                    request.getRequestDispatcher("ShDetEst#configs");
                    break;
                case "NO":
                    try (PrintWriter out = response.getWriter()) {
                        out.print("<button href=# class='btn btn-danger btn-sm' onclick=FormDelEst_Conf(" + index + "," + liCo + ",'show') ><span><img src='images/trash.png'></span></button>");
                    }
                    request.getRequestDispatcher("ShDetEst#estCn-" + i);
                    break;
            }
        } else {
            try (PrintWriter out = response.getWriter()) {
                out.print("null");
            }
            request.getRequestDispatcher("ShowEst#est-" + i);
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
