package Servlets.Medico;

import DataAccesObject.Direccion_DAO;
import DataAccesObject.Medico_DAO;
import DataAccesObject.Persona_DAO;
import DataAccesObject.Unidad_DAO;
import DataBase.Fecha;
import DataTransferObject.Direccion_DTO;
import DataTransferObject.Medico_DTO;
import DataTransferObject.Persona_DTO;
import DataTransferObject.Unidad_DTO;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
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
@WebServlet(name = "InsMedico", urlPatterns = {"/InsMedico"})
public class InsMedico extends HttpServlet {
    
protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
                HttpSession sesion = request.getSession();
        if (sesion.getAttribute("user") != null && sesion.getAttribute("unidad") != null) {
            PrintWriter out = response.getWriter();
            response.setContentType("text/html;charset=UTF-8");
            Medico_DTO medico = new Medico_DTO();
            Persona_DTO persona = new Persona_DTO();
            Direccion_DTO dir = new Direccion_DTO();
            Date fac = new Date();
            Fecha f = new Fecha();
            f.setHora(fac);
            Direccion_DAO D = new Direccion_DAO();
            Persona_DAO P = new Persona_DAO();
            Medico_DAO M = new Medico_DAO();
            
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
            
            Unidad_DAO U = new Unidad_DAO();
            Unidad_DTO unidad = U.getUnidad(Id_Unidad);
            
            medico.setId_Unidad(Id_Unidad);            
            medico.setEmpresa(request.getParameter("empresa"));            
            medico.setParticipacion(Float.parseFloat(request.getParameter("participacion")));            
            medico.setDescuento(Float.parseFloat(request.getParameter("descuento")));            
            String CodMed
                    = unidad.getClave() + "-" + persona.getAp_Paterno().substring(0, 2) + persona.getAp_Materno().substring(0, 1)
                    + persona.getNombre().substring(0, 1);
            medico.setCodMed(CodMed);
            
            persona.setId_Direccion(D.RegistrarDirección(dir));            
            if (persona.getId_Direccion() != 0) {
                persona.setId_Persona(P.RegistrarPersona(persona));                
                if (persona.getId_Persona() != 0) {
                    medico.setId_Persona(persona.getId_Persona());
                    medico.setId_Medico(M.RegistrarMedico(medico));                    
                    if (medico.getId_Medico() != 0) {
                        String msg = "Médico registrado correctamente";
                        sesion.setAttribute("msg", msg);
                        response.sendRedirect("Medicos.jsp");
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
