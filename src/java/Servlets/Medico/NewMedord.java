package Servlets.Medico;

import DataAccesObject.Medico_DAO;
import DataAccesObject.Persona_DAO;
import DataAccesObject.Unidad_DAO;
import DataBase.Fecha;
import DataTransferObject.Cotizacion_DTO;
import DataTransferObject.Medico_DTO;
import DataTransferObject.Persona_DTO;
import DataTransferObject.Unidad_DTO;
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
@WebServlet(name = "NewMedord", urlPatterns = {"/NewMedord"})
public class NewMedord extends HttpServlet {
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        HttpSession sesion = request.getSession();
        Cotizacion_DTO Cotizacion = (Cotizacion_DTO) sesion.getAttribute("Cotizacion");
        Date fac = new Date();
        Fecha f = new Fecha();
        f.setHora(fac);
        int id_unidad = Integer.parseInt(sesion.getAttribute("unidad").toString().trim());
        String mode = request.getParameter("mode").trim();
        List<Medico_DTO> meds;
        Medico_DAO M = new Medico_DAO();
        Medico_DTO med = new Medico_DTO();
        if (sesion.getAttribute("meds") != null) {
            meds = (List<Medico_DTO>) sesion.getAttribute("meds");
        } else {
            meds = M.getMedicos();
            sesion.setAttribute("meds", meds);
        }
        switch (mode) {
            case "form":
                out.print("<div class=' form-row'> "
                        + "<div class='col-md-4 mb-3'>"
                        + "<label class='sr-only' >Nombre</label>"
                        + "<input style='text-align: center' type='text' class='form-control' name='nombre_persona' id='nombre_persona' placeholder='Nombre' required>"
                        + "<div class='invalid-feedback'>"
                        + "Se requiere un nombre valido."
                        + "</div>"
                        + "</div>"
                        + "<div class='col mb-3'>"
                        + "<label class='sr-only' >A Paterno</label>"
                        + "<input style='text-align: center' type='text' class='form-control' name='a_paterno' id='a_paterno' placeholder='Apellido Paterno' required>"
                        + "<div class='invalid-feedback'>"
                        + "Se requiere un apellido valido."
                        + "</div>"
                        + "</div>"
                        + "<div class='col mb-3'>"
                        + "<label class='sr-only' >A Materno</label>"
                        + "<input style='text-align: center' type='text' class='form-control' name='a_materno' id='a_materno' placeholder='Apellido Materno' required>"
                        + "<div class='invalid-feedback'>"
                        + "Se requiere un segundo apellido valido."
                        + "</div>"
                        + "</div>"
                        + "</div>"
                        + "<button class='btn btn-secondary btn-block' onclick=NewMedord('ins');><strong>Guardar Médico</strong></button>");
                break;
            case "ins":
                int Id_Unidad = Integer.parseInt(sesion.getAttribute("unidad").toString().trim());
                Unidad_DAO U = new Unidad_DAO();
                Unidad_DTO unidad = U.getUnidad(Id_Unidad);
                Persona_DTO per = new Persona_DTO();
                Persona_DAO Pr = new Persona_DAO();
                String nombre_persona = request.getParameter("nombre_persona");
                String a_paterno = request.getParameter("a_paterno");
                String a_materno = request.getParameter("a_materno");
                per.setNombre(nombre_persona);
                per.setAp_Paterno(a_paterno);
                per.setAp_Materno(a_materno);
                per.setId_Persona(Pr.RegistrarPersona2(per));
                String CodMed
                        = unidad.getClave() + "-" + per.getAp_Paterno().substring(0, 2) + per.getAp_Materno().substring(0, 1)
                        + per.getNombre().substring(0, 1);
                med.setId_Unidad(id_unidad);                
                med.setId_Persona(per.getId_Persona());
                med.setEmpresa(" ");
                med.setParticipacion(Float.parseFloat("0"));
                med.setDescuento(Float.parseFloat("0"));
                med.setCodMed(CodMed);
                med.setId_Medico(M.RegistrarMedico(med));
                med.setNombre(per.getNombre());
                med.setAp_Paterno(per.getAp_Paterno());
                med.setAp_Materno(per.getAp_Materno());
                meds.add(med);
                sesion.setAttribute("meds", meds);
                Cotizacion.setMedico(med);
                sesion.setAttribute("Cotizacion", Cotizacion);
                out.print("<h5 style='text-align: center'>Datos de Médico</h5><br>"
                        + "<div class='form-row'>"
                        + "<div class='col-8 col-sm-5 col-md-4'>"
                        + "<label><strong>Nombre: </strong>" + Cotizacion.getMedico().getNombre() + " " + Cotizacion.getMedico().getAp_Paterno() + " " + Cotizacion.getMedico().getAp_Materno() + "</label>"
                        + "</div>"
                        + "</div>");
                break;
            default:
                int index = Integer.parseInt(request.getParameter("mode").trim());
                med = meds.get(index);
                Cotizacion.setMedico(med);
                sesion.setAttribute("Cotizacion", Cotizacion);
                out.print("<h6 style='text-align: center'>Datos de Médico</h6><br>"
                        + "<div class='form-row'>"
                        + "<div class='col'>"
                        + "<label><strong>Nombre: </strong>" + Cotizacion.getMedico().getNombre() + " " + Cotizacion.getMedico().getAp_Paterno() + " " + Cotizacion.getMedico().getAp_Materno() + "</label>"
                        + "</div>"
                        + "</div>");
                break;
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
