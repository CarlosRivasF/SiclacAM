package Servlets.Estudio;

import DataAccesObject.Est_Mat_DAO;
import DataAccesObject.Estudio_DAO;
import DataAccesObject.Material_DAO;
import DataAccesObject.Precio_DAO;
import DataTransferObject.Empresa_DTO;
import DataTransferObject.Est_Mat_DTO;
import DataTransferObject.Estudio_DTO;
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
@WebServlet(name = "AddShareEst", urlPatterns = {"/AddShareEst"})
public class AddShareEst extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        HttpSession sesion = request.getSession();
        int id_unidad = Integer.parseInt(sesion.getAttribute("unidad").toString().trim());        
        PrintWriter out = response.getWriter();
        Estudio_DAO E = new Estudio_DAO();
        List<Estudio_DTO> enu;
        enu = E.GetEstudiosNoRegisterInUnidad(id_unidad);
        
//        List<Estudio_DTO> eu = E.getEstudiosByUnidad(id_unidad);
//        enu = E.getEstudiosNotRegUnidad(id_unidad);
//
//        try {
//            for (int i = 0; i < eu.size(); i++) {
//                for (int j = 0; j < enu.size(); j++) {
//                    if (eu.get(i).getId_Estudio() == enu.get(j).getId_Estudio()) {
//                        enu.remove(enu.get(j));
//                    }
//                }
//            }
//            eu = enu;
//            for (int i = 0; i < eu.size(); i++) {
//                for (int j = 0; j < enu.size(); j++) {
//                    if (eu.get(i).getId_Estudio() == enu.get(j).getId_Estudio()) {
//                        enu.remove(enu.get(j));
//                    }
//                }
//            }
//        } catch (Exception e) {
//            out.println("<br>'ShareEst'<br><h1 style='color: white'>" + e.getMessage() + "...<br>Por favor capture una imagen del error y comuniquelo de inmediato a ZionSystems</h1>");
//        }

        Estudio_DAO EO = new Estudio_DAO();
        int index = Integer.parseInt(request.getParameter("index").trim());
        Estudio_DTO est = enu.get(index);
        est.setId_Est_Uni(EO.registrarEst_Uni(est.getId_Estudio(), id_unidad));
        Material_DAO M = new Material_DAO();
        Empresa_DTO empresa = (Empresa_DTO) sesion.getAttribute("empresa");
        List<Material_DTO> matsE;
        List<Material_DTO> MatsNotUnidad;
        List<Material_DTO> matsU;
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
            out.println("<br>'AddShareEst'<br><h1 style='color: white'>" + e.getMessage() + "...<br>Por favor capture una imagen del error y comuniquelo de inmediato a ZionSystems</h1>");
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
        //sesion.setAttribute("MatsNotUnidad", MatsNotUnidad);
        //sesion.setAttribute("MatsNotEmpresa", mats);
        matsE.clear();
        List<Est_Mat_DTO> mesE = new ArrayList<>();
        List<Est_Mat_DTO> mes = new ArrayList<>();
        List<Est_Mat_DTO> mesF = new ArrayList<>();

        Est_Mat_DAO EM = new Est_Mat_DAO();
        Boolean r = false;
        int idUM = 0;
        for (Est_Mat_DTO me : est.getMts()) {
            for (Material_DTO m : matsU) {
                if (me.getId_Material() == m.getId_Material()) {
                    r = true;
                    idUM = m.getId_Unid_Mat();
                } else {
                    r = false;
                }
            }
            if (r) {
                me.setId_Unid_Mat(idUM);
                EM.registrarMat_Est(est.getId_Est_Uni(), me);
            } else {
                mes.add(me);
            }
        }

        if (!mes.isEmpty()) {
            for (Est_Mat_DTO me : mes) {
                for (Material_DTO m : matsE) {
                    r = m.getId_Material() == me.getId_Material();
                }
                if (r) {
                    mesE.add(me);
                } else {
                    mesF.add(me);
                }
            }
        }
        Est_Mat_DAO emd = new Est_Mat_DAO();
        if (mesE.isEmpty() && mesF.isEmpty()) {
            Precio_DAO PO = new Precio_DAO();
            PO.registrarPrecio(est.getId_Est_Uni(), est.getPrecio());
            request.getRequestDispatcher("ShareEst").forward(request, response);
        } else {
            Precio_DAO PO = new Precio_DAO();
            PO.registrarPrecio(est.getId_Est_Uni(), est.getPrecio());
            mesE.stream().map((dto) -> {
                dto.setCantidad(100);
                return dto;
            }).map((dto) -> {
                M.addMaterialByU(id_unidad, dto);
                return dto;
            }).map((dto) -> {
                emd.registrarMat_Est(est.getId_Est_Uni(), dto);
                return dto;
            }).map((dto) -> {
                MatsNotUnidad.remove(dto);
                return dto;
            }).forEachOrdered((dto) -> {
                matsU.add(dto);
            });
            mesF.stream().map((dto) -> {
                dto.setCantidad(100);
                return dto;
            }).map((dto) -> {
                dto.setPrecio(dto.getPrecio());
                return dto;
            }).map((dto) -> {
                M.addMaterial(id_unidad, dto);
                return dto;
            }).map((dto) -> {
                emd.registrarMat_Est(est.getId_Est_Uni(), dto);
                return dto;
            }).map((dto) -> {
                mats.remove(dto);
                return dto;
            }).forEachOrdered((dto) -> {
                matsU.add(dto);
            });

            request.getRequestDispatcher("ShareEst").forward(request, response);
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
