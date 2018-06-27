package Servlets.Empleado;

import DataAccesObject.Direccion_DAO;
import DataAccesObject.Empleado_DAO;
import DataAccesObject.Permiso_DAO;
import DataAccesObject.Persona_DAO;
import DataAccesObject.Usuario_DAO;
import DataBase.Fecha;
import DataTransferObject.Direccion_DTO;
import DataTransferObject.Empleado_DTO;
import DataTransferObject.Persona_DTO;
import DataTransferObject.Usuario_DTO;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
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
@WebServlet(name = "InsEmp", urlPatterns = {"/InsEmp"})
public class InsEmpleado extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        HttpSession sesion = request.getSession();
        if (sesion.getAttribute("user") != null && sesion.getAttribute("unidad") != null) {
            PrintWriter out = response.getWriter();
            Empleado_DTO empleado = new Empleado_DTO();
            Persona_DTO persona = new Persona_DTO();
            Direccion_DTO dir = new Direccion_DTO();
            Usuario_DTO usuario = new Usuario_DTO();
            Date fac = new Date();
            Fecha f = new Fecha();
            f.setHora(fac);
            Direccion_DAO D = new Direccion_DAO();
            Persona_DAO P = new Persona_DAO();
            Usuario_DAO Us = new Usuario_DAO();
            Permiso_DAO Pr = new Permiso_DAO();
            Empleado_DAO E = new Empleado_DAO();

            int Id_Unidad = Integer.parseInt(sesion.getAttribute("unidad").toString().trim());

            dir.setId_Colonia(Integer.parseInt(request.getParameter("colonia").trim()));
            dir.setCalle(request.getParameter("calle"));
            dir.setNo_Int(request.getParameter("no_int"));
            dir.setNo_Ext(request.getParameter("no_ext"));

            persona.setNombre(request.getParameter("nombre_persona"));
            persona.setAp_Paterno(request.getParameter("a_paterno"));
            persona.setAp_Materno(request.getParameter("a_materno"));
            persona.setFecha_Nac(f.getFechaActual());
            persona.setMail(request.getParameter("mail"));
            persona.setTelefono1(request.getParameter("telefono"));
            persona.setTelefono2(request.getParameter("celular"));

            empleado.setId_Unidad(Id_Unidad);
            empleado.setCurp(request.getParameter("curp"));
            empleado.setNss(request.getParameter("nss"));
            empleado.setFecha_Ing(request.getParameter("fechaIng"));
            empleado.setSalario_Bto(Float.parseFloat(request.getParameter("salario")));

            List<String> dias = new ArrayList<>();
            for (int i = 0; i < 8; i++) {
                if (request.getParameter("dia" + "" + (i + 1) + "") == null) {
                } else {
                    dias.add(request.getParameter("dia" + (i + 1)));
                }
            }
            empleado.setDias_Trabajo(dias);
            empleado.setHora_Ent(request.getParameter("hora_e"));
            empleado.setHora_Com(request.getParameter("hora_c"));
            empleado.setHora_Reg(request.getParameter("hora_r"));
            empleado.setHora_Sal(request.getParameter("hora_s"));

            usuario.setNombre_Usuario(request.getParameter("usuario"));
            usuario.setContraseña(request.getParameter("pass"));
            usuario.setRol("Empleado");
            usuario.setEstado("Activo");

            List<String> ps = new ArrayList<>();
            for (int i = 0; i < 8; i++) {
                if (request.getParameter("permiso" + "" + (i + 1) + "") == null) {
                } else {
                    ps.add(request.getParameter("permiso" + (i + 1)));
                }
            }

            persona.setId_Direccion(D.RegistrarDirección(dir));
            if (persona.getId_Direccion() != 0) {
                persona.setId_Persona(P.RegistrarPersona(persona));
                if (persona.getId_Persona() != 0) {
                    empleado.setId_Persona(persona.getId_Persona());
                    empleado.setId_Empleado(E.RegistrarEmpleado(empleado));
                    if (empleado.getId_Empleado() != 0) {
                        usuario.setId_Unidad(Id_Unidad);
                        usuario.setId_Persona(persona.getId_Persona());
                        usuario.setId_Usuario(Us.RegistrarUsuario(usuario));
                        if (usuario.getId_Usuario() != 0) {
                            int r = Pr.registrarPermisos(usuario.getId_Usuario(), ps);
                            if (r == 1) {
                                String msg = "Empleado registrado correctamente";
                                sesion.setAttribute("msg", msg);
                                response.sendRedirect("Empleados.jsp");
                            }
                        }
                    }
                }
            }

        } else {
            response.sendRedirect("" + request.getContextPath() + "");
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
