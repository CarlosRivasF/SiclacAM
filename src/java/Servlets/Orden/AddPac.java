package Servlets.Orden;

import DataAccesObject.Paciente_DAO;
import DataAccesObject.Persona_DAO;
import DataAccesObject.Unidad_DAO;
import DataBase.Fecha;
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
 * @author ZionSystems
 */
@WebServlet(name = "AddPac", urlPatterns = {"/AddPac"})
public class AddPac extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        HttpSession sesion = request.getSession();
        PrintWriter out = response.getWriter();
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
            pacs = P.getPacientes();
            sesion.setAttribute("pacs", pacs);
        }
        Orden_DTO Orden = new Orden_DTO();
        Orden.setRestante(Float.parseFloat("0"));
        Orden.setUnidad(U.getUnidadAll(id_unidad));
        Persona_DAO P = new Persona_DAO();
        Persona_DTO por = P.getPersona(Integer.parseInt(sesion.getAttribute("persona").toString().trim()));
        Orden.setEmpleado(por);
        String mode = request.getParameter("mode").trim();
        if (mode.equals("code")) {
            if (request.getParameter("CodePac") != null) {
                String CodePac = request.getParameter("CodePac");
                pacs.stream().filter((dto) -> (dto.getCodPac() != null)).filter((dto)
                        -> (dto.getCodPac().trim().equalsIgnoreCase(CodePac.trim())))
                        .forEachOrdered((Paciente_DTO dto) -> {
                            try {
                                Orden.setPaciente(dto);
                                Orden.setFecha(f.getFechaActual());
                                Orden.setHora(f.getHoraActual());
                                sesion.setAttribute("Orden", Orden);
                                request.getRequestDispatcher("Menu/Orden/AddEsts.jsp").forward(request, response);
                            } catch (ServletException | IOException ex) {
                                out.println("<br>'ShowMats'<br><h1 style='color: white'>" + ex.getMessage() + "...<br>Por favor capture una imagen del error y comuniquelo de inmediato a ZionSystems</h1>");
                            }
                        });
            } else {
                request.getRequestDispatcher("Menu/Orden/Registro.jsp").forward(request, response);
            }
        } else {
            int index = Integer.parseInt(mode);
            Paciente_DTO pac = pacs.get(index);
            System.out.println(pac.getNombre());
            try {
                Orden.setPaciente(pacs.get(index));
                sesion.setAttribute("Orden", Orden);
                request.getRequestDispatcher("Menu/Orden/AddEsts.jsp").forward(request, response);
            } catch (ServletException | IOException ex) {
                //si existe una excepcion es porque faltan datos del  paciente.
                request.getRequestDispatcher("ShDetPac?index=" + index + " &modeP=rellenar").forward(request, response);
            }
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
