package Servlets.Empleado;

import DataAccesObject.Empleado_DAO;
import DataAccesObject.Material_DAO;
import DataAccesObject.Usuario_DAO;
import DataTransferObject.Empleado_DTO;
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
@WebServlet(name = "FormDelEmp", urlPatterns = {"/FormDelEmp"})
public class FormDelEmp extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        HttpSession sesion = request.getSession();
        List<Empleado_DTO> emps = (List<Empleado_DTO>) sesion.getAttribute("emps");
        String i = request.getParameter("index");
        int index = Integer.parseInt(i.trim());
        if (request.getParameter("ADelm") != null) {
            String act = request.getParameter("ADelm");
            Empleado_DTO emp = emps.get(index);
            switch (act) {
                case "show":
                    out.print("<button href=# class='btn btn-light' onclick=FormDelEmp(" + index + ",'SI')>SI</button> "
                            + " <button href=# class='btn btn-info' onclick=FormDelEmp(" + index + ",'NO')>NO</button>");
                    break;
                case "SI":
                    Usuario_DAO U = new Usuario_DAO();
                    if (emp.getUsuario().getEstado().trim().equals("Activo")) {
                        U.DeshabilitarUsuario(emp.getUsuario().getId_Usuario());
                        emp.getUsuario().setEstado("Inactivo");
                        emps.set(index, emp);
                        out.print("<button href=# class='btn btn-danger' onclick=FormDelEmp(" + index + ",'show')><span><img src='images/inactiveU.png'></span></button>");
                    } else if (emp.getUsuario().getEstado().trim().equals("Inactivo")) {
                        U.HabilitarUsuario(emp.getUsuario().getId_Usuario());
                        emp.getUsuario().setEstado("Activo");
                        emps.set(index, emp);
                        out.print("<button href=# class='btn btn-success' onclick=FormDelEmp(" + index + ",'show')><span><img src='images/activeU.png'></span></button>");
                    }
                    break;
                case "NO":
                    String btn = "";
                    String clr = "";
                    if (emp.getUsuario().getEstado().trim().equals("Activo")) {
                        btn = "activeU";
                        clr = "success";
                    } else if (emp.getUsuario().getEstado().trim().equals("Inactivo")) {
                        btn = "inactiveU";
                        clr = "danger";
                    }
                    out.print("<button href=# class='btn btn-" + clr + "' onclick=FormDelEmp(" + index + ",'show')><span><img src='images/" + btn + ".png'></span></button>");
                    break;
            }
        } else {
            out.print("null");
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
