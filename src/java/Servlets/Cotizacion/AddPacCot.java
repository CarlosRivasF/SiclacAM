package Servlets.Cotizacion;

import DataAccesObject.Paciente_DAO;
import DataAccesObject.Persona_DAO;
import DataAccesObject.Unidad_DAO;
import DataBase.Fecha;
import DataTransferObject.Cotizacion_DTO;
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
 * @author ZionSystems
 */
@WebServlet(name = "AddPacCot", urlPatterns = {"/AddPacCot"})
public class AddPacCot extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        response.setContentType("text/html;charset=UTF-8");
        HttpSession sesion = request.getSession();
        Date fac = new Date();
        Fecha f = new Fecha();
        f.setHora(fac);
        Unidad_DAO U = new Unidad_DAO();
        int id_unidad = Integer.parseInt(sesion.getAttribute("unidad").toString().trim());
        List<Paciente_DTO> pacs;
        if (sesion.getAttribute("pacs") != null) {
            pacs = (List<Paciente_DTO>) sesion.getAttribute("pacs");
        } else {
            Paciente_DAO P = new Paciente_DAO();
            pacs = P.getPacientesByUnidad(id_unidad);
            sesion.setAttribute("pacs", pacs);
        }
        Cotizacion_DTO Cotizacion = new Cotizacion_DTO();
        Cotizacion.setUnidad(U.getUnidadAll(id_unidad));
        Persona_DAO P = new Persona_DAO();
        Persona_DTO por = P.getPersona(Integer.parseInt(sesion.getAttribute("persona").toString().trim()));
        Cotizacion.setEmpleado(por);
        String mode = request.getParameter("mode").trim();
        if (mode.equals("dts")) {
            Persona_DTO per = new Persona_DTO();
            Persona_DAO Pr = new Persona_DAO();
            Paciente_DTO pac = new Paciente_DTO();
            Paciente_DAO Pc = new Paciente_DAO();
            String nombre_persona = request.getParameter("nombre_persona");
            String a_paterno = request.getParameter("a_paterno");
            String a_materno = request.getParameter("a_materno");
            per.setNombre(nombre_persona);
            per.setAp_Paterno(a_paterno);
            per.setAp_Materno(a_materno);
            per.setId_Persona(Pr.RegistrarPersona2(per));
            pac.setNombre(nombre_persona);
            pac.setAp_Paterno(a_paterno);
            pac.setAp_Materno(a_materno);
            pac.setId_Unidad(id_unidad);
            String CodPac
                    = per.getAp_Paterno().substring(0, 2) + per.getAp_Materno().substring(0, 1)
                    + per.getNombre().substring(0, 1) + "-" + pac.getId_Unidad();
            pac.setCodPac(CodPac);
            pac.setId_Persona(per.getId_Persona());
            pac.setSenMail(false);
            pac.setId_Paciente(Pc.RegistrarPaciente(pac));
            try {
                Cotizacion.setEstado("Activo");
                Cotizacion.setPaciente(pac);
                Cotizacion.setFecha_Cot(f.getFechaActual());
                Cotizacion.setFecha_Exp(f.SumarDias(30));
                pacs.add(pac);
                sesion.setAttribute("pacs", pacs);
                sesion.setAttribute("Cotizacion", Cotizacion);
                request.getRequestDispatcher("Menu/Cotizacion/AddEsts.jsp").forward(request, response);
            } catch (Exception ex) {
                out.println("<br>'AddPacCot'<br><h1 style='color: white'>" + ex.getMessage() + "...<br>Por favor capture una imagen del error y comuniquelo de inmediato a ZionSystems</h1>");
            }
        } else {
            int index = Integer.parseInt(mode);
            try {
                Cotizacion.setPaciente(pacs.get(index));
                Cotizacion.setEstado("Activo");
                Cotizacion.setFecha_Cot(f.getFechaActual());
                Cotizacion.setFecha_Exp(f.SumarDias(30));
                sesion.setAttribute("Cotizacion", Cotizacion);
                request.getRequestDispatcher("Menu/Cotizacion/AddEsts.jsp").forward(request, response);
            } catch (Exception ex) {
                out.println("<br>'AddPacCot'<br><h1 style='color: white'>" + ex.getMessage() + "...<br>Por favor capture una imagen del error y comuniquelo de inmediato a ZionSystems</h1>");
            }
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
