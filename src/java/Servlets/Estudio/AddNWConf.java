package Servlets.Estudio;

import DataAccesObject.Configuracion_DAO;
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
 * @author ZionSystem
 */
@WebServlet(name = "AddNWConf", urlPatterns = {"/AddNWConf"})
public class AddNWConf extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        HttpSession sesion = request.getSession();
        PrintWriter out = response.getWriter();
        Configuracion_DAO CD = new Configuracion_DAO();
        List<Estudio_DTO> ests = (List<Estudio_DTO>) sesion.getAttribute("ests");
        int index = Integer.parseInt(request.getParameter("index").trim());
        Estudio_DTO est = ests.get(index);
        Configuracion_DTO conf = new Configuracion_DTO();
        conf.setDescripcion(request.getParameter("desc"));
        conf.setSexo(request.getParameter("sexo"));
        conf.setValor_min(request.getParameter("min"));
        conf.setValor_MAX(request.getParameter("max"));
        conf.setUniddes(request.getParameter("unidades"));
        conf.setId_Configuración(CD.registrarConfiguracion(conf));
        CD.registrarConf_Est(est.getId_Est_Uni(), conf.getId_Configuración());
        est.getCnfs().add(conf);

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
        for (Configuracion_DTO cnf : est.getCnfs()) {
            out.println("<tr>"
                    + "<td >" + cnf.getDescripcion() + "</td>"
                    + "<td >" + cnf.getSexo() + "</td>"
                    + "<td >" + cnf.getValor_min() + "</td>"
                    + "<td >" + cnf.getValor_MAX() + "</td>"
                    + "<td >" + cnf.getUniddes() + "</td>"
                    + "<td><button href=# class='btn btn-warning btn-sm' onclick=FormUpCn(" + index + "," + l + ",'form') ><span><img src='images/pencil.png'></span></button></td>"
                    + "<td><div id='estCn-" + l + "'><button href=# class='btn btn-danger btn-sm' onclick=FormDelEst_Conf(" + index + "," + l + ",'show') ><span><img src='images/trash.png'></span></button></div></td>"
                    + "</tr>");
            l++;
        }
        out.println("<tr>"
                + "<td colspan='7'><div id='addcnf'><button href=# class='btn btn-success btn-block' onclick=FormAddNWConf(" + index + ")>Agregar nueva configuración</button></div></td>"
                + "</tr>");
        out.println("</table>");
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
