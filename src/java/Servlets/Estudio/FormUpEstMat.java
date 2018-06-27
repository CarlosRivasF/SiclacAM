package Servlets.Estudio;

import DataAccesObject.Estudio_DAO;
import DataAccesObject.Material_DAO;
import DataTransferObject.Est_Mat_DTO;
import DataTransferObject.Estudio_DTO;
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
@WebServlet(name = "FormUpEstMat", urlPatterns = {"/FormUpEstMat"})
public class FormUpEstMat extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        HttpSession sesion = request.getSession();
        PrintWriter out = response.getWriter();
        List<Estudio_DTO> ests;
        ests = (List<Estudio_DTO>) sesion.getAttribute("ests");
        Estudio_DAO E = new Estudio_DAO();
        int id_unidad = Integer.parseInt(sesion.getAttribute("unidad").toString().trim());
        Material_DAO M = new Material_DAO();
        List<Material_DTO> matsU;
        if (sesion.getAttribute("matsU") != null) {
            matsU = ((List<Material_DTO>) sesion.getAttribute("matsU"));
        } else {
            matsU = M.getMaterialesByUnidad(id_unidad);
            sesion.setAttribute("matsU", matsU);
        }
        int index = Integer.parseInt(request.getParameter("index").trim());
        String part = request.getParameter("part").trim();
        String acc = request.getParameter("acc").trim();
        Estudio_DTO est = ests.get(index);
        int ixM = Integer.parseInt(part);
        if ("upd".equals(acc)) {
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
            Est_Mat_DTO mat = new Est_Mat_DTO();
            for (Est_Mat_DTO mt : est.getMts()) {
                if (i == ixM) {
                    int id_Unid_Mat = mt.getId_Unid_Mat();
                    mt.setId_Unid_Mat(Integer.parseInt(request.getParameter("mater").trim()));
                    matsU.stream().filter((dto) -> (dto.getId_Unid_Mat() == mt.getId_Unid_Mat())).forEachOrdered((dto) -> {
                        mt.setNombre_Material(dto.getNombre_Material());
                    });
                    mt.setCantidadE(Integer.parseInt(request.getParameter("canti").trim()));
                    mt.setUnidadE(request.getParameter("unid"));
                    mt.setT_Muestra(request.getParameter("TMstra"));
                    E.ActualizarMat_Est(est.getId_Est_Uni(), id_Unid_Mat, mt);
                    mat = mt;
                    out.println("<tr>"
                            + "<td >" + mt.getNombre_Material() + "</td>"
                            + "<td >" + mt.getCantidadE() + "</td>"
                            + "<td >" + mt.getUnidadE() + "</td>"
                            + "<td >" + mt.getT_Muestra() + "</td>"
                            + "<td><button href=# class='btn btn-warning btn-sm' onclick=FormUpEstMat(" + index + "," + i + ",'form') ><span><img src='images/pencil.png'></span></button></td>"
                            + "<td><div id='estMt-" + i + "'><button href=# class='btn btn-danger btn-sm' onclick=FormDelEst_Mat(" + index + "," + i + ",'show') ><span><img src='images/trash.png'></span></button></div></td>"
                            + "</tr>");
                    i++;
                } else {
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
            }
            est.getMts().set(ixM, mat);
            request.getRequestDispatcher("ShDetEst#matis");
            out.println("</table>");
        } else {
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
            int idum = est.getMts().get(ixM).getId_Unid_Mat();
            for (Est_Mat_DTO mt : est.getMts()) {
                if (i == ixM) {
                    out.println("<tr><td>");
                    out.println("<select class='custom-select d-block w-100 form-control-sm' id='mater' name='mater>' required>");
                    out.print(" <option value='" + mt.getId_Unid_Mat() + "'>" + mt.getNombre_Material() + "</option>");
                    if (!matsU.isEmpty()) {
                        matsU.forEach((dto) -> {
                            if (dto.getId_Unid_Mat() == idum) {
                            } else {
                                out.print(" <option value='" + dto.getId_Unid_Mat() + "'>" + dto.getNombre_Material() + "</option>");
                            }
                        });
                    }
                    out.println("</select></td><td><input style='text-align: center' type='number' class='form-control form-control-sm' name='canti' value='" + mt.getCantidadE() + "' id='canti' placeholder='Cantidad' required></td>"
                            + "<td><input style='text-align: center' type='text'  class='form-control form-control-sm' name='unid' value='" + mt.getUnidadE() + "' id='unid' placeholder='Unidad' required></td>"
                            + "<td><input style='text-align: center' type='text' class='form-control form-control-sm' name='TMstra' value='" + mt.getT_Muestra() + "' id='TMstra' placeholder='Muestra' required></td>"
                            + "<td><button href=# class='btn btn-success btn-sm' onclick=FormUpEstMat(" + index + "," + i + ",'upd') ><span><img src='images/save.png'></span></button></td>"
                            + "<td><div id='estMt-" + i + "'><button href=# class='btn btn-danger btn-sm' onclick=FormDelEst_Mat(" + index + "," + i + ",'show') ><span><img src='images/trash.png'></span></button></div></td>"
                            + "</tr>");
                    i++;
                } else {
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
            }
            request.getRequestDispatcher("ShDetEst#matis");
            out.println("</table>");
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
