package Servlets.Material;

import DataAccesObject.Material_DAO;
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
 * @author ZionSystems
 */
@WebServlet(name = "UpdMat", urlPatterns = {"/UpdMat"})
public class UpdMat extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        HttpSession sesion = request.getSession();
        List<Material_DTO> matsU = (List<Material_DTO>) sesion.getAttribute("matsU");
        int index = Integer.parseInt(request.getParameter("index").trim());
        Material_DTO mat = matsU.get(index);
        Material_DAO M = new Material_DAO();

        mat.setNombre_Material(request.getParameter("nombre_material"));
        mat.setPrecio(Float.parseFloat(request.getParameter("precio")));
        mat.setCantidad(Integer.parseInt(request.getParameter("cantidad").trim()));
        M.ActualizarMaterial(mat);
        matsU.set(index, mat);
        List<Material_DTO> MatsNotUnidad;
        List<Material_DTO> MatsNotEmpresa;
        MatsNotUnidad = (List<Material_DTO>) sesion.getAttribute("MatsNotUnidad");
        MatsNotEmpresa = (List<Material_DTO>) sesion.getAttribute("MatsNotEmpresa");
        out.print("<div class='nav-scroller bg-white box-shadow'>"
                + "    <nav class='nav nav-underline'>"
                + "        <a class='nav-link' href='#' onclick=mostrarForm('" + request.getContextPath() + "/Menu/Material/Registro.jsp');>Nueva Material</a>"
                + "<a class='nav-link active' href='#' onclick=mostrarForm('ShowMats'); style='color: blue'><ins>Ver Materiales</ins> <span class='badge badge-pill bg-dark align-text-bottom' style='color: white'> " + (matsU.size()) + "</span></a>"
                + "        <a class='nav-link active' href='#' onclick=mostrarForm('MatsE'); style='color: blue' >Materiales de Empresa <span class='badge badge-pill bg-dark align-text-bottom' style='color: white'> " + ((MatsNotEmpresa.size()) + (MatsNotUnidad.size())) + "</span></a>"
                + "    </nav>"
                + "</div>"
                + "<div><br>");
        out.print("<div class='form-row'>"
                + "<div class='offset-2 col-8 col-sm-8 col-md-8 mb-3'>"
                + "<label class='sr-only' >Codigo de Estudio</label>"
                + "<input style='text-align: center' type='text' class='form-control' name='clave_mat' onkeyup='SchMat(this);' id='clave_mat' placeholder='Clave o nombre de material...' required>"
                + "</div>"
                + "</div>"
                + "<div id='BMat'>");
        out.println("<div style='color: white' class='table-responsive'>"
                + "<table style=' text-align: center' class='table table-bordered table-hover table-sm'>"
                + "<tr class='table-active'>"
                + "<th >Material</th>"
                + "<th >Precio</th>"
                + "<th >Cantidad</th>"
                + "<th>Modificar</th>"
                + "<th>Eliminar</th>"
                + "</tr>");
        int i = 0;
        for (Material_DTO dto : matsU) {
            out.println("<tr>"
                    + "<td >" + dto.getNombre_Material() + "</td>"
                    + "<td >" + dto.getPrecio() + "</td>"
                    + "<td >" + dto.getCantidad() + "</td>"
                    + "<td><a href=# class='btn btn-warning' onclick=FormUpMat(" + i + ") ><span><img src='images/pencil.png'></span></a></td>"
                    + "<td><div id='mat-" + i + "'><button href=# class='btn btn-danger' onclick=FormDelMat(" + i + ",'show') ><span><img src='images/trash.png'></span></button></div></td>"
                    + "</tr>");
            i++;
        }
        out.println("</table>");
        out.println("</div>");
        out.println("</div>");
    }
}
