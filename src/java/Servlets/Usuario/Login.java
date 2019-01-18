package Servlets.Usuario;

import DataAccesObject.Empleado_DAO;
import DataAccesObject.Empresa_DAO;
import DataAccesObject.Permiso_DAO;
import DataAccesObject.Salida_DAO;
import DataAccesObject.Unidad_DAO;
import DataAccesObject.Usuario_DAO;
import DataTransferObject.Empleado_DTO;
import DataTransferObject.Empresa_DTO;
import DataTransferObject.Permiso_DTO;
import DataTransferObject.Usuario_DTO;
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
@WebServlet(name = "acces", urlPatterns = {"/acces"})
public class Login extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Usuario_DAO U = new Usuario_DAO();
        Unidad_DAO Un = new Unidad_DAO();
        Usuario_DTO usuario;
        HttpSession sesion = request.getSession();

        if (sesion.getAttribute("Error") != null) {
            sesion.removeAttribute("Error");
        }
        String user = request.getParameter("user");
        String pass = request.getParameter("pass");
        usuario = U.Login(user, pass);
        if (usuario == null) {
            sesion.setAttribute("Error", "Usuario o Contraseña no econtrados");
            response.sendRedirect("" + request.getContextPath() + "");
        } else {
            if (!usuario.getEstado().equals("Activo")) {
                sesion.setAttribute("Error", "Usuario Inactivo");
                response.sendRedirect("" + request.getContextPath() + "");
            } else {
                Permiso_DAO P = new Permiso_DAO();
                List<Permiso_DTO> lst = P.getPermisos(usuario.getId_Usuario());
                if (lst.isEmpty()) {
                    sesion.setAttribute("Error", "Usuario sin permisos");
                    response.sendRedirect("" + request.getContextPath() + "");
                } else {
                    if (usuario.getId_Persona() == 0) {
                        sesion.setMaxInactiveInterval(3600 * 4);
                        sesion.setAttribute("permisos", lst);
                        sesion.setAttribute("user", usuario.getId_Usuario());
                        sesion.setAttribute("unidad", usuario.getId_Unidad());                        
                        sesion.setAttribute("rol", usuario.getRol());
                        Empresa_DAO E = new Empresa_DAO();
                        Empresa_DTO empresa = E.getEmpresa(Integer.parseInt(sesion.getAttribute("unidad").toString()));
                        sesion.setAttribute("empresa", empresa);
                        response.sendRedirect("MainAdmin.jsp");
                    } else {
                        Empleado_DAO e = new Empleado_DAO();
                        Empleado_DTO em = e.getEmpleado(usuario.getId_Persona());
                        if (em != null) {
                            Salida_DAO Sa = new Salida_DAO();
                            String horaS = Sa.getHrSal(em.getId_Empleado());
                            if (horaS != null) {
                                sesion.setAttribute("Error", "Ya ha registrado su salida el día de hoy");
                                response.sendRedirect("" + request.getContextPath() + "");
                            } else {
                                sesion.setMaxInactiveInterval(3600 * 4);
                                sesion.setAttribute("permisos", lst);
                                sesion.setAttribute("user", usuario.getId_Usuario());
                                sesion.setAttribute("empleado", em);
                                sesion.setAttribute("persona", usuario.getId_Persona());
                                sesion.setAttribute("unidad", usuario.getId_Unidad());
                                sesion.setAttribute("nombre_unidad", Un.getUnidad(usuario.getId_Unidad()).getNombre_Unidad());
                                sesion.setAttribute("rol", usuario.getRol());
                                Empresa_DAO E = new Empresa_DAO();
                                Empresa_DTO empresa = E.getEmpresaByUnidad(Integer.parseInt(sesion.getAttribute("unidad").toString()));
                                sesion.setAttribute("empresa", empresa);
                                response.sendRedirect("MainAdmin.jsp");
                            }
                        } else {
                            sesion.setMaxInactiveInterval(3600 * 4);
                            sesion.setAttribute("permisos", lst);
                            sesion.setAttribute("user", usuario.getId_Usuario());
                            sesion.setAttribute("persona", usuario.getId_Persona());
                            sesion.setAttribute("unidad", usuario.getId_Unidad());
                            sesion.setAttribute("nombre_unidad", Un.getUnidad(usuario.getId_Unidad()).getNombre_Unidad());
                            sesion.setAttribute("rol", usuario.getRol());
                            Empresa_DAO E = new Empresa_DAO();
                            Empresa_DTO empresa = E.getEmpresaByUnidad(Integer.parseInt(sesion.getAttribute("unidad").toString()));
                            sesion.setAttribute("empresa", empresa);
                            response.sendRedirect("MainAdmin.jsp");
                        }
                    }
                }
            }
        }
    }
}