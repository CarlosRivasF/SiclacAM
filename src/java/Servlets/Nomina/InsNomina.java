package Servlets.Nomina;

import DataAccesObject.Nomina_DAO;
import DataAccesObject.Persona_DAO;
import DataAccesObject.Sancion_DAO;
import DataTransferObject.Empleado_DTO;
import DataTransferObject.Nomina_DTO;
import DataTransferObject.Persona_DTO;
import DataTransferObject.Sancion_DTO;
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
@WebServlet(name = "InsNomina", urlPatterns = {"/InsNomina"})
public class InsNomina extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        HttpSession sesion = request.getSession();
        List<Empleado_DTO> emps;
        emps = (List<Empleado_DTO>) sesion.getAttribute("emps");

        if (request.getParameter("idEmpl") != null) {
            int idEmpl = Integer.parseInt(request.getParameter("idEmpl").trim());

            out.print("<div class='nav-scroller bg-white box-shadow'>"
                    + "    <nav class='nav nav-underline'>"
                    + "        <a class='nav-link' href='#' onclick=mostrarForm('" + request.getContextPath() + "/Menu/Empleado/Registro.jsp');>Nuevo Empleado</a>"
                    + "        <a class='nav-link' href='#' onclick=mostrarForm('ShowEmpl'); >Ver Empleados</a>"
                    + "    </nav>"
                    + "</div><br>");
            out.print("<div class='form-row'>"
                    + "<div class='offset-2 col-8 col-sm-8 col-md-8 mb-3'>"
                    + "<label class='sr-only' >Codigo de Estudio</label>"
                    + "<input style='text-align: center' type='text' class='form-control' name='emple' onkeyup='SchEmpl(this);' id='emple' placeholder='Nombre de empleado...' autofocus required>"
                    + "</div>"
                    + "</div>"
                    + "<div id='BEmpl'>");
            out.println("<div style='color: white' class='table-responsive'>"
                    + "<table style=' text-align: center' class='table table-bordered table-hover table-sm'>"
                    + "<tr class='table-active'>"
                    + "<th >Usuario</th>"
                    + "<th >Nombre</th>"
                    + "<th >Sancionar</th>"
                    + "<th >Pagar</th>"
                    + "<th >Detalles</th>"
                    + "<th >Estado</th>"
                    + "</tr>");
            emps.forEach((dto) -> {
                String btn = "";
                String clr = "";
                if (dto.getUsuario().getEstado().trim().equals("Activo")) {
                    btn = "activeU";
                    clr = "success";
                } else if (dto.getUsuario().getEstado().trim().equals("Inactivo")) {
                    btn = "inactiveU";
                    clr = "danger";
                }
                if (idEmpl == dto.getId_Empleado()) {
                    out.println("<tr>"
                            + "<td >" + dto.getUsuario().getNombre_Usuario() + "</td>"
                            + "<td >" + dto.getNombre() + " " + dto.getAp_Paterno() + " " + dto.getAp_Materno() + "</td>"
                            + "<td colspan='3'><div class=' form-row'> "             
                            + "<div class='col-6 col-md-6 mb-3'>"
                            + "<label class='sr-only' >Monto</label>"
                            + "<input style='text-align: center' type='number' step='any' class='form-control' name='montoN-" + emps.indexOf(dto) + "' id='montoN-" + emps.indexOf(dto) + "' placeholder='Monto' required>"
                            + "</div>"
                            + "<div class='col-6 col-md-6 mb-3'>"
                            + "<label class='sr-only' >Fefcha</label>"
                            + "<input style='text-align: center' type='date' class='form-control' name='fechaN-" + emps.indexOf(dto) + "' id='fechaN-" + emps.indexOf(dto) + "' required>"
                            + "</div>"
                            + "</div></td>"
                            + "<td><button href=# class='btn btn-default btn-sm' onclick=InsNom(" + emps.indexOf(dto) + ") ><span><img src='images/save.png'></span></button></td>"
                            + "</tr>");
                } else {
                    out.println("<tr>"
                            + "<td >" + dto.getUsuario().getNombre_Usuario() + "</td>"
                            + "<td >" + dto.getNombre() + " " + dto.getAp_Paterno() + " " + dto.getAp_Materno() + "</td>"
                            + "<td><button href=# class='btn btn-default btn-sm' onclick=FormInsSanc(" + dto.getId_Empleado() + ") ><span><img src='images/Sancion.png'></span></button></td>"
                            + "<td><button href=# class='btn btn-default btn-sm' onclick=FormInsNom(" + dto.getId_Empleado() + ") ><span><img src='images/Salario.png'></span></button></td>"
                            + "<td><button href=# class='btn btn-default btn-sm' onclick=ShDetEmpl(" + emps.indexOf(dto) + ") ><span><img src='images/details.png'></span></button></td>"
                            + "<td><div id='Empl-" + emps.indexOf(dto) + "'><button href=# class='btn btn-" + clr + " btn-sm' onclick=FormDelEmp(" + emps.indexOf(dto) + ",'show') ><span><img src='images/" + btn + ".png'></span></button></div></td>"
                            + "</tr>");
                }
            });
            out.println("</table>");
            out.println("</div>");
            out.println("</div>");
        } else if (request.getParameter("index") != null) {
            Persona_DAO P=new Persona_DAO();                
            Persona_DTO por=P.getPersona(Integer.parseInt(sesion.getAttribute("persona").toString().trim()));            
            int index = Integer.parseInt(request.getParameter("index").trim());
            Empleado_DTO empl = emps.get(index);            
            Nomina_DAO N=new Nomina_DAO();
            Nomina_DTO nom = new Nomina_DTO();
            nom.setPor(por);
            nom.setMonto(Float.parseFloat(request.getParameter("montoN").trim()));            
            nom.setFecha(request.getParameter("fechaN").trim());            
            N.RegistrarNomina(nom, empl.getId_Empleado());
            request.getRequestDispatcher("ShowEmpl").forward(request, response);
        } else {
            request.getRequestDispatcher("ShowEmpl").forward(request, response);
        }
    }
}
