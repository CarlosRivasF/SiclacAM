package Servlets.Material;

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
 * @author ZionSystems
 */
@WebServlet(name = "AddMatU", urlPatterns = {"/AddMatU"})
public class AddMatU extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession sesion = request.getSession();
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        int index = Integer.parseInt(request.getParameter("index").trim());
        int id_unidad = Integer.parseInt(sesion.getAttribute("unidad").toString().trim());
        Material_DAO M = new Material_DAO();
        Empresa_DTO empresa = (Empresa_DTO) sesion.getAttribute("empresa");
        List<Material_DTO> matsU;
        List<Material_DTO> MatsNotUnidad;
        List<Material_DTO> MatsNotEmpresa;
        matsU = (List<Material_DTO>) sesion.getAttribute("matsU");
        MatsNotUnidad = (List<Material_DTO>) sesion.getAttribute("MatsNotUnidad");
        MatsNotEmpresa = (List<Material_DTO>) sesion.getAttribute("MatsNotEmpresa");

        String lst = request.getParameter("list").trim();
        Material_DTO mat;
        switch (lst) {
            case "MatsNotUnidad":
                mat = MatsNotUnidad.get(index);
                mat.setCantidad(Integer.parseInt(request.getParameter("cantidad").trim()));
                M.addMaterialByU(id_unidad, mat);
                MatsNotUnidad.remove(index);
                matsU.add(mat);
                break;
            case "MatsNotEmpresa":
                mat = MatsNotEmpresa.get(index);
                mat.setCantidad(Integer.parseInt(request.getParameter("cantidad").trim()));
                mat.setPrecio(Float.parseFloat(request.getParameter("precio").trim()));
                M.addMaterial(id_unidad, mat);
                MatsNotEmpresa.remove(index);
                matsU.add(mat);
                break;
        }
        ///////////////////////////////
        List<Material_DTO> matsE;
        List<Material_DTO> mats;

        if (sesion.getAttribute("matsU") != null) {
            matsU = (List<Material_DTO>) sesion.getAttribute("matsU");
        } else {
            matsU = M.getMaterialesByUnidad(id_unidad);
            sesion.setAttribute("matsU", matsU);
        }
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
        response.sendRedirect("MatsE");
    }

}
