package Servlets.Paciente;

import DataAccesObject.Direccion_DAO;
import DataAccesObject.Paciente_DAO;
import DataAccesObject.Persona_DAO;
import DataAccesObject.Unidad_DAO;
import DataBase.Fecha;
import DataTransferObject.Direccion_DTO;
import DataTransferObject.Orden_DTO;
import DataTransferObject.Paciente_DTO;
import DataTransferObject.Persona_DTO;
import java.io.IOException;
import java.io.PrintWriter;
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
@WebServlet(name = "InsPac", urlPatterns = {"/InsPac"})
public class InsPac extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        HttpSession sesion = request.getSession();
        if (sesion.getAttribute("user") != null && sesion.getAttribute("unidad") != null) {
            PrintWriter out = response.getWriter();

            Paciente_DTO paciente = new Paciente_DTO();
            Persona_DTO persona = new Persona_DTO();
            Direccion_DTO dir = new Direccion_DTO();
            Date fac = new Date();
            Fecha f = new Fecha();
            f.setHora(fac);
            Direccion_DAO D = new Direccion_DAO();
            Persona_DAO P = new Persona_DAO();
            Paciente_DAO Pa = new Paciente_DAO();

            int Id_Unidad = Integer.parseInt(sesion.getAttribute("unidad").toString().trim());
            List<Paciente_DTO> pacs;
            if (sesion.getAttribute("pacs") != null) {
                pacs = (List<Paciente_DTO>) sesion.getAttribute("pacs");
            } else {
                pacs = Pa.getPacientesByUnidad(Id_Unidad);
            }
            //////////////////////////////////////////////////////////////////
            dir.setId_Colonia(Integer.parseInt(request.getParameter("colonia").trim()));
            dir.setCalle(request.getParameter("calle"));
            dir.setNo_Int(request.getParameter("no_int"));
            dir.setNo_Ext(request.getParameter("no_ext"));
            ////////////////////////////////////////////////////
            persona.setNombre(request.getParameter("nombre_persona"));
            persona.setAp_Paterno(request.getParameter("a_paterno"));
            persona.setAp_Materno(request.getParameter("a_materno"));
            persona.setFecha_Nac(request.getParameter("fecha_nac"));
            persona.setSexo(request.getParameter("SexoP"));
            persona.setMail(request.getParameter("mail"));
            persona.setTelefono1(request.getParameter("telefono"));
            persona.setTelefono2(request.getParameter("celular"));
            //////////////////////////////////////////////////////////
            paciente.setId_Unidad(Id_Unidad);
            String CodPac
                    = persona.getAp_Paterno().substring(0, 2) + persona.getAp_Materno().substring(0, 1)
                    + persona.getNombre().substring(0, 1) + persona.getFecha_Nac().substring(2, 4)
                    + persona.getFecha_Nac().substring(5, 7) + persona.getFecha_Nac().substring(8, 10)
                    + "-" + paciente.getId_Unidad();

            paciente.setSenMail(Boolean.valueOf(request.getParameter("SendMail").trim()));
            persona.setId_Direccion(D.RegistrarDirección(dir));
            if (persona.getId_Direccion() != 0) {
                persona.setId_Persona(P.RegistrarPersona(persona));
                if (persona.getId_Persona() != 0) {
                    paciente.setId_Persona(persona.getId_Persona());
                    paciente.setCodPac(CodPac);
                    paciente.setId_Paciente(Pa.RegistrarPaciente(paciente));
                    if (paciente.getId_Paciente() != 0) {
                        paciente.setAp_Materno(persona.getAp_Materno());
                        paciente.setAp_Paterno(persona.getAp_Paterno());
                        paciente.setFecha_Nac(persona.getFecha_Nac());
                        paciente.setMail(persona.getMail());
                        paciente.setNombre(persona.getNombre());
                        paciente.setTelefono1(persona.getTelefono1());
                        paciente.setSexo(persona.getSexo());
                        pacs.add(paciente);
                        sesion.setAttribute("pacs", pacs);
                        if (request.getParameter("formu") != null) {
                            Unidad_DAO U = new Unidad_DAO();
                            int id_unidad = Integer.parseInt(sesion.getAttribute("unidad").toString().trim());
                            Orden_DTO Orden = new Orden_DTO();
                            Orden.setRestante(Float.parseFloat("0"));
                            Orden.setUnidad(U.getUnidadAll(id_unidad));
                            Persona_DTO por = P.getPersona(Integer.parseInt(sesion.getAttribute("persona").toString().trim()));
                            Orden.setEmpleado(por);
                            Orden.setPaciente(paciente);
                            Orden.setFecha(f.getFechaActual());
                            Orden.setHora(f.getHoraActual());
                            sesion.setAttribute("Orden", Orden);
                            request.getRequestDispatcher("Menu/Orden/AddEsts.jsp").forward(request, response);
                        } else {
                            String msg = "Paciente " + CodPac + " registrado correctamente";
                            sesion.setAttribute("msg", msg);
                            response.sendRedirect("Pacientes.jsp");
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
