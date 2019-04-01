package Servlets.Unidad;

import DataAccesObject.Direccion_DAO;
import DataAccesObject.Permiso_DAO;
import DataAccesObject.Persona_DAO;
import DataAccesObject.Unidad_DAO;
import DataAccesObject.Usuario_DAO;
import DataBase.Util;
import DataTransferObject.Direccion_DTO;
import DataTransferObject.Empresa_DTO;
import DataTransferObject.Persona_DTO;
import DataTransferObject.Unidad_DTO;
import DataTransferObject.Usuario_DTO;
import java.io.IOException;
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
@WebServlet(name = "InsUnidad", urlPatterns = {"/InsUnidad"})
public class InsUnidad extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        HttpSession sesion = request.getSession();
        if (sesion.getAttribute("user") != null && sesion.getAttribute("unidad") != null) {
            Empresa_DTO empresa = (Empresa_DTO) sesion.getAttribute("empresa");
            Unidad_DAO U = new Unidad_DAO();
            List<Unidad_DTO> uns;

            if (sesion.getAttribute("uns") != null) {
                uns = (List<Unidad_DTO>) sesion.getAttribute("uns");
            } else {
                uns = U.getUnidadesByEmpresa(empresa.getId_Empresa());
                sesion.setAttribute("uns", uns);
            }

            Unidad_DTO unidad = new Unidad_DTO();
            Persona_DTO persona = new Persona_DTO();
            Direccion_DTO dir = new Direccion_DTO();
            Usuario_DTO usuario = new Usuario_DTO();
            Date fac = new Date();
            Util f = new Util();
            f.setHora(fac);
            Direccion_DAO D = new Direccion_DAO();
            Persona_DAO P = new Persona_DAO();
            Usuario_DAO Us = new Usuario_DAO();
            Permiso_DAO Pr = new Permiso_DAO();

            if (sesion.getAttribute("rol").toString().equals("Dueño")) {
                unidad.setId_Empresa(Integer.parseInt(sesion.getAttribute("unidad").toString()));
            } else {
                unidad.setId_Empresa(U.getIdEmpresa(Integer.parseInt(sesion.getAttribute("unidad").toString())));
            }
            unidad.setNombre_Unidad(request.getParameter("nombre_unidad"));
            unidad.setClave(request.getParameter("clave_unidad"));

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
            dir.setId_Direccion(D.RegistrarDirección(dir));
            if (dir.getId_Direccion() != 0) {
                persona.setId_Direccion(dir.getId_Direccion());
                persona.setId_Persona(P.RegistrarPersona(persona));
                if (persona.getId_Persona() != 0) {
                    unidad.setEncargado(persona);
                    unidad.setId_Unidad(U.RegistrarUnidad(unidad));
                    usuario.setId_Unidad(unidad.getId_Unidad());
                    usuario.setId_Persona(unidad.getEncargado().getId_Persona());
                    usuario.setId_Usuario(Us.RegistrarUsuario(usuario));
                    unidad.setUsuario(usuario);
                    if (unidad.getUsuario().getId_Usuario() != 0) {
                        int r = Pr.registrarPermisos(unidad.getUsuario().getId_Usuario(), ps);
                        if (r == 1) {
                            String msg = "Unidad " + unidad.getNombre_Unidad() + " registrada correctamente";
                            sesion.setAttribute("msg", msg);
                            uns.add(unidad);
                            response.sendRedirect("Unidad.jsp");
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
