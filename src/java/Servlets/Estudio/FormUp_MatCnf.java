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
@WebServlet(name = "FormUp_MatCnf", urlPatterns = {"/FormUp_MatCnf"})
public class FormUp_MatCnf extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        HttpSession sesion = request.getSession();
        PrintWriter out = response.getWriter();
        List<Estudio_DTO> ests;
        ests = (List<Estudio_DTO>) sesion.getAttribute("ests");
        Estudio_DAO E = new Estudio_DAO();
        int index = Integer.parseInt(request.getParameter("index").trim());
        String part = request.getParameter("part").trim();
        String acc = request.getParameter("acc").trim();
        Estudio_DTO est = ests.get(index);
        int ixC = Integer.parseInt(part);

        if ("upd".equals(acc)) {
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
                if (l == ixC) {
                    cnf.setDescripcion(request.getParameter("descrip"));
                    cnf.setSexo(request.getParameter("sexo"));
                    cnf.setValor_min(request.getParameter("valMin"));
                    cnf.setValor_MAX(request.getParameter("valMax"));
                    cnf.setUniddes(request.getParameter("uns"));
                    E.ActualizarConfig(cnf);
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
                } else {
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
            }
            out.println("<tr>"
                    + "<td colspan='7'><div id='addcnf'><button href=# class='btn btn-success btn-block' onclick=FormAddNWConf(" + index + ")>Agregar nueva configuración</button></div></td>"
                    + "</tr>");
            request.getRequestDispatcher("ShDetEst#configs");
            out.println("</table>");
        } else {
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
                if (l == ixC) {
                    out.println("<tr>"
                            + "<td><input style='text-align: center' type='text' class='form-control form-control-sm' name='descrip' value='" + cnf.getDescripcion() + "' id='descrip' placeholder='Descripción' required></td>"
                            + "<td><select class='custom-select d-block w-100 form-control-sm' id='sexo' name='sexo' required>"
                            + "<option value=''>Sexo</option> "
                            + "<option value='all-Femenino'>TODOS Femenino</option>"
                            + "<option value='all-Masculino'>TODOS Masculino</option>"
                            + "<option value='all-Ambos'>TODOS Ambos</option>\n"
                            + "<option value='3E-Femenino'>3ra Edad Femenino</option>"
                            + "<option value='3E-Masculino'>3ra Edad Masculino</option>"
                            + "<option value='3E-Ambos'>3ra Edad Ambos</option>"
                            + "<option value='A-Femenino'>Adulto Femenino</option>"
                            + "<option value='A-Masculino'>Adulto Masculino</option>"
                            + "<option value='A-Ambos'>Adulto Ambos</option>"
                            + "<option value='N-Femenino'>Niño Femenino</option>"
                            + "<option value='N-Masculino'>Niño Masculino</option>"
                            + "<option value='N-Ambos'>Niño Ambos</option>"
                            + "<option value='RN-Femenino'>Rec Nac Femenino</option>"
                            + "<option value='RN-Masculino'>Rec Nac Masculino</option>"
                            + "<option value='RN-Ambos'>Rec Nac Ambos</option>");
                    out.print("</select></td>"
                            + "<td><input style='text-align: center' type='text' class='form-control form-control-sm' name='valMin' value='" + cnf.getValor_min() + "' id='valMin' placeholder='Val min' required></td>"
                            + "<td><input style='text-align: center' type='text' class='form-control form-control-sm' name='valMax' value='" + cnf.getValor_MAX() + "' id='valMax' placeholder='Val Max' required></td>"
                            + "<td><input style='text-align: center' type='text' class='form-control form-control-sm' name='uns' value='" + cnf.getUniddes() + "' id='uns' placeholder='Unidades' required></td>"
                            + "<th><button href=# class='btn btn-success btn-sm' onclick=FormUpCn(" + index + "," + l + ",'upd') ><span><img src='images/save.png'></span></button></th>"
                            + "<td><div id='estCn-" + l + "'><button href=# class='btn btn-danger btn-sm' onclick=FormDelEst_Conf(" + index + "," + l + ",'show') ><span><img src='images/trash.png'></span></button></div></td>"
                            + "</tr>");
                    l++;
                } else {
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
            }
            request.getRequestDispatcher("ShDetEst#configs");
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
