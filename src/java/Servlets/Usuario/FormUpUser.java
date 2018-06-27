package Servlets.Usuario;

import DataAccesObject.Usuario_DAO;
import DataTransferObject.Empleado_DTO;
import DataTransferObject.Unidad_DTO;
import DataTransferObject.Usuario_DTO;
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

@WebServlet(name = "FormUpUser", urlPatterns = {"/FormUpUser"})
public class FormUpUser extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        HttpSession sesion = request.getSession();
        PrintWriter out = response.getWriter();
        String list = request.getParameter("list").trim();
        int index = Integer.parseInt(request.getParameter("index").trim());
        ArrayList lst;
        Usuario_DTO usuario = new Usuario_DTO();
        switch (list) {
            case "uns":
                List<Unidad_DTO> uns;
                uns = (List<Unidad_DTO>) sesion.getAttribute("uns");
                lst = (ArrayList) uns;
                Unidad_DTO u = (Unidad_DTO) lst.get(index);
                usuario = u.getUsuario();
                break;
            case "emps":
                List<Empleado_DTO> emps;
                emps = (List<Empleado_DTO>) sesion.getAttribute("emps");
                lst = (ArrayList) emps;
                Empleado_DTO e = (Empleado_DTO) lst.get(index);
                usuario = e.getUsuario();
                break;
            case "meds":
                break;
            case "pacs":
                break;
        }
        String acc = request.getParameter("acc").trim();
        if ("upd".equals(acc)) {
            Usuario_DAO U = new Usuario_DAO();
            usuario.setNombre_Usuario(request.getParameter("nameUs"));
            usuario.setContraseña(request.getParameter("passUs"));
            usuario.setEstado(request.getParameter("edoUs"));
            U.ActualizarUsuario(usuario);
            out.println("<table style=' text-align: center' class='table table-bordered table-hover table-sm'>"
                    + "<tr class='table-danger' style='color: black'>"
                    + "<th colspan='4' >Datos de Acceso</th>"
                    + "</tr>"
                    + "<tr>"
                    + "<th>Usuario</th>"
                    + "<th>Contraseña</th>"
                    + "<th>Estado</th>"
                    + "<th >Modificar</th>"
                    + "</tr><tr>"
                    + "<td>" + usuario.getNombre_Usuario() + "</td>"
                    + "<td>" + usuario.getContraseña() + "</td>"
                    + "<td>" + usuario.getEstado() + "</td>"
                    + "<th><button href=# class='btn btn-warning btn-sm' onclick=FormUpUser(" + index + ",'form','" + list + "') ><span><img src='images/pencil.png'></span></button></th>"                    
                    + "</tr></table>");
        } else {
            out.println("<table style=' text-align: center' class='table table-bordered table-hover table-sm'>"
                    + "<tr class='table-danger' style='color: black'>"
                    + "<th colspan='4' >Datos de Acceso</th>"
                    + "</tr>"
                    + "<tr>"
                    + "<th>Usuario</th>"
                    + "<th>Contraseña</th>"
                    + "<th>Estado</th>"
                    + "<th >Guardar</th>"
                    + "</tr>");
            out.println("<tr><td><input style='text-align: center' type='text' class='form-control form-control-sm' name='nameUs' value='" + usuario.getNombre_Usuario() + "' id='nameUs' placeholder='Usuario' required></td>"
                    + "<td><input style='text-align: center' type='text' class='form-control form-control-sm' name='passUs' value='" + usuario.getContraseña() + "' id='passUs' placeholder='Contraseña' required></td>");
            out.println("<td><select class='custom-select d-block w-100 form-control-sm' id='edoUs' name='edoUs' required>");
            out.print(" <option value='" + usuario.getEstado() + "'>" + usuario.getEstado() + "</option>");
            switch (usuario.getEstado()) {
                case "Activo":
                    out.print(" <option value='Inactivo'>Inactivo</option>");
                    break;
                case "Inactivo":
                    out.print(" <option value='Activo'>Activo</option>");
                    break;
            }
            out.print("</select></td>"
                    + "<th><button href=# class='btn btn-success btn-sm' onclick=FormUpUser(" + index + ",'upd','" + list + "') ><span><img src='images/save.png'></span></button></th>"
                    + "</tr></table>");
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
