package Servlets.Material;

import DataAccesObject.Material_DAO;
import DataTransferObject.Material_DTO;
import java.io.IOException;
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
@WebServlet(name = "InsMat", urlPatterns = {"/InsMat"})
public class InsMat extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession sesion = request.getSession();
        int id_unidad = Integer.parseInt(sesion.getAttribute("unidad").toString().trim());
        Material_DAO M = new Material_DAO();
        if (sesion.getAttribute("user") != null && sesion.getAttribute("unidad") != null) {
            String msg;
            if (sesion.getAttribute("msg") != null) {
                sesion.removeAttribute("msg");
            }
            List<Material_DTO> matsU;
            if (sesion.getAttribute("matsU") != null) {
                matsU = (List<Material_DTO>) sesion.getAttribute("matsU");
            } else {
                matsU = M.getMaterialesByUnidad(id_unidad);
                sesion.setAttribute("matsU", matsU);
            }
            Material_DTO mat = new Material_DTO();
            mat.setClave(request.getParameter("clave_mat"));
            mat.setNombre_Material(request.getParameter("nombre_material"));
            mat.setPrecio(Float.parseFloat(request.getParameter("precio")));
            mat.setCantidad(Integer.parseInt(request.getParameter("cantidad").trim()));
            mat.setId_Material(M.RegistrarMaterial(id_unidad, mat));
            if (mat.getId_Material() != 0) {
                matsU.add(mat);
                msg = "Material registrado correctamente";
                sesion.setAttribute("msg", msg);
                response.sendRedirect("Material.jsp");
            }

        } else {
            response.sendRedirect("" + request.getContextPath() + "");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
