package Servlets.Material;

import DataAccesObject.Empresa_DAO;
import DataAccesObject.Material_DAO;
import DataTransferObject.Empresa_DTO;
import DataTransferObject.Material_DTO;
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
 * @author Carlos Rivas
 */
@WebServlet(name = "ShowMats", urlPatterns = {"/ShowMats"})
public class ShowMats extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        HttpSession sesion = request.getSession();
        int id_unidad = Integer.parseInt(sesion.getAttribute("unidad").toString().trim());
        PrintWriter out = response.getWriter();
        Material_DAO M = new Material_DAO();
        Empresa_DAO E = new Empresa_DAO();
        Empresa_DTO empresa = (Empresa_DTO) sesion.getAttribute("empresa");
        List<Material_DTO> matsE;
        List<Material_DTO> MatsNotUnidad;
        List<Material_DTO> matsU;
        List<Material_DTO> mats;

        matsU = M.getMaterialesByUnidad(id_unidad);
        sesion.setAttribute("matsU", matsU);

        matsE = M.getMaterialesByEmpresa(empresa.getId_Empresa());
        MatsNotUnidad = M.getMaterialesByEmpresa(empresa.getId_Empresa());
        mats = M.getMatsNotRegistedUnid(id_unidad);

        try {
            for (int i = 0; i < matsU.size(); i++) {
                for (int j = 0; j < MatsNotUnidad.size(); j++) {
                    if (matsU.get(i).getId_Material() == MatsNotUnidad.get(j).getId_Material()) {
                        MatsNotUnidad.remove(MatsNotUnidad.get(j));
                    }
                }
            }
        } catch (Exception e) {

        }
        matsE.forEach((_item) -> {
            for (int I = 0; I < matsE.size(); I++) {
                for (int J = 0; J < mats.size(); J++) {
                    if (matsE.get(I).getId_Material() == mats.get(J).getId_Material()) {
                        mats.remove(mats.get(J));
                    }
                }
            }
        });

        sesion.setAttribute("MatsNotUnidad", MatsNotUnidad);
        sesion.setAttribute("MatsNotEmpresa", mats);
        matsE.clear();

        out.print("<div class='nav-scroller bg-white box-shadow'>"
                + "<nav class='nav nav-underline'>"
                + "<a class='nav-link' href='#' onclick=mostrarForm('" + request.getContextPath() + "/Menu/Material/Registro.jsp');>Nueva Material</a>"
                + "<a class='nav-link active' href='#' onclick=mostrarForm('ShowMats');  style='color: blue'><ins>Ver Materiales</ins> <span class='badge badge-pill bg-dark align-text-bottom' style='color: white'> " + (matsU.size()) + "</span></a>"
                + "<a class='nav-link active' href='#' onclick=mostrarForm('MatsE'); >Materiales de Empresa <span class='badge badge-pill bg-dark align-text-bottom' style='color: white'> " + ((mats.size()) + (MatsNotUnidad.size())) + "</span></a>"
                + "</nav>"
                + "</div>"
                + "<div><br>");
        out.print("<div class='form-row'>"
                + "<div class='offset-2 col-8 col-sm-8 col-md-8 mb-3'>"
                + "<label class='sr-only' >Codigo de Estudio</label>"
                + "<input style='text-align: center' type='text' class='form-control' name='clave_mat' onkeyup='SchMat(this);' id='clave_mat' placeholder='Clave o nombre de material...' autofocus required>"
                + "</div>"
                + "</div>"
                + "<div id='BMat'>");
        out.println("<div style='color: white' class='table-responsive'>"
                + "<table style=' text-align: center' class='table table-bordered table-hover table-sm'>"
                + "<tr class='table-active'>"
                + "<th >Clave</th>"
                + "<th >Material</th>"
                + "<th >Precio</th>"
                + "<th >Cantidad</th>"
                + "<th>Modificar</th>"
                + "<th>Eliminar</th>"
                + "</tr>");
        int i = 0;
        for (Material_DTO dto : matsU) {
            out.println("<tr>"
                    + "<td >" + dto.getClave() + "</td>"
                    + "<td >" + dto.getNombre_Material() + "</td>"
                    + "<td >" + dto.getPrecio() + "</td>"
                    + "<td >" + dto.getCantidad() + "</td>"
                    + "<td><button href=# class='btn btn-warning' onclick=FormUpMat(" + i + ") ><span><img src='images/pencil.png'></span></button></td>"
                    + "<td><div id='mat-" + i + "'><button href=# class='btn btn-danger' onclick=FormDelMat(" + i + ",'show') ><span><img src='images/trash.png'></span></button></div></td>"
                    + "</tr>");
            i++;
        }
        out.println("</table>");
        out.println("</div></div>");

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
