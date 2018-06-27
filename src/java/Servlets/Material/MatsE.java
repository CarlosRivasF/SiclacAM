package Servlets.Material;

import DataAccesObject.Empresa_DAO;
import DataAccesObject.Material_DAO;
import DataTransferObject.Empresa_DTO;
import DataTransferObject.Material_DTO;
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
 * @author ZionSystems
 */
@WebServlet(name = "MatsE", urlPatterns = {"/MatsE"})
public class MatsE extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {//MatsNotEmpresa
        response.setContentType("text/html;charset=UTF-8");
        HttpSession sesion = request.getSession();
        int id_unidad = Integer.parseInt(sesion.getAttribute("unidad").toString().trim());
        PrintWriter out = response.getWriter();
        Material_DAO M = new Material_DAO();
        Empresa_DTO empresa = (Empresa_DTO) sesion.getAttribute("empresa");
        List<Material_DTO> matsU;
        if (sesion.getAttribute("matsU") != null) {
            matsU = (List<Material_DTO>) sesion.getAttribute("matsU");
        } else {
            matsU = M.getMaterialesByUnidad(id_unidad);
            sesion.setAttribute("matsU", matsU);
        }
        List<Material_DTO> MatsNotUnidad;
        List<Material_DTO> MatsNotEmpresa;
        MatsNotUnidad = (List<Material_DTO>) sesion.getAttribute("MatsNotUnidad");
        MatsNotEmpresa = (List<Material_DTO>) sesion.getAttribute("MatsNotEmpresa");
        out.print("<div class='nav-scroller bg-white box-shadow'>"
                + "    <nav class='nav nav-underline'>"
                + "        <a class='nav-link' href='#' onclick=mostrarForm('" + request.getContextPath() + "/Menu/Material/Registro.jsp');>Nueva Material</a>"
                + "        <a class='nav-link' href='#' onclick=mostrarForm('ShowMats'); >Ver Materiales <span class='badge badge-pill bg-dark align-text-bottom' style='color: white'> " + (matsU.size()) + "</span></a>"
                + "        <a class='nav-link active' href='#' onclick=mostrarForm('MatsE'); style='color: blue' ><ins>Materiales de Empresa</ins> <span class='badge badge-pill bg-dark align-text-bottom' style='color: white'> " + ((MatsNotEmpresa.size()) + (MatsNotUnidad.size())) + "</span></a>"
                + "    </nav>"
                + "</div>"
                + "<div><hr class='mb-4'>"
                + "<h5 style='text-align: center; color: white'>Aquí encontrará materiales que probablemete pueda utilizar y agregar de manera más rapida en " + empresa.getNombre_Empresa() + "</h5>"
                + "<hr class='mb-4'>");
        out.println("<div style='color: white' class='table-responsive'>"
                + "<table style=' text-align: center' class='table table-bordered table-hover table-sm'>"
                + "<tr class='table-active'>"
                + "<th >Material</th>"
                + "<th >Precio</th>"
                + "<th >Cantidad</th>"
                + "<th>Agregar Material</th>"
                + "</tr>");
        int l = 0;

        for (int i = 0; i < MatsNotUnidad.size(); i++) {
            out.println("<tr>"
                    + "<td >" + MatsNotUnidad.get(i).getNombre_Material() + "</td>"
                    + "<td >" + MatsNotUnidad.get(i).getPrecio() + "</td>"
                    + "<td ><input style='text-align: center'class='form-control' type='number' onkeypress='return soloNumeros(event)' min='1'  name='cantidad" + l + "' id='cantidad" + l + "' placeholder='Cantidad' required></td>"
                    + "<td><a href=# class='btn btn-success' onclick=AddMat(" + l + "," + i + ",'MatsNotUnidad') ><span><img src='images/addMat.png'></span></a></td>"
                    + "</tr>");
            l++;
        }
        for (int i = 0; i < MatsNotEmpresa.size(); i++) {
            out.println("<tr>"
                    + "<td >" + MatsNotEmpresa.get(i).getNombre_Material() + "</td>"
                    + "<td ><input style='text-align: center' class='form-control' type='number' onkeypress='return soloNumeros(event)' name='precio" + l + "' id='precio" + l + "' step='any' placeholder='Precio' required></td>"
                    + "<td ><input style='text-align: center'class='form-control' type='number' onkeypress='return soloNumeros(event)' min='1' name='cantidad" + l + "' id='cantidad" + l + "' placeholder='Cantidad' required></td>"
                    + "<td><a href=# class='btn btn-success' onclick=AddMat(" + l + "," + i + ",'MatsNotEmpresa') ><span><img src='images/addMat.png'></span></a></td>"
                    + "</tr>");
            l++;
        }
        out.println("</table>");
        out.println("</div>");
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
