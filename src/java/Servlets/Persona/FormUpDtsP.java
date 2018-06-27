package Servlets.Persona;

import DataAccesObject.CP_DAO;
import DataAccesObject.Colonia_DAO;
import DataAccesObject.Persona_DAO;
import DataTransferObject.CP_DTO;
import DataTransferObject.Colonia_DTO;
import DataTransferObject.Empleado_DTO;
import DataTransferObject.Medico_DTO;
import DataTransferObject.Paciente_DTO;
import DataTransferObject.Persona_DTO;
import DataTransferObject.Unidad_DTO;
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

/**
 *
 * @author ZionSystems
 */
@WebServlet(name = "FormUpDtsP", urlPatterns = {"/FormUpDtsP"})
public class FormUpDtsP extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        HttpSession sesion = request.getSession();
        PrintWriter out = response.getWriter();
        String list = request.getParameter("list").trim();
        int index = Integer.parseInt(request.getParameter("index").trim());
        ArrayList lst = null;
        Persona_DTO persona = new Persona_DTO();
        switch (list) {
            case "uns":
                List<Unidad_DTO> uns;
                uns = (List<Unidad_DTO>) sesion.getAttribute("uns");
                lst = (ArrayList) uns;
                Unidad_DTO u = (Unidad_DTO) lst.get(index);
                persona = u.getEncargado();
                break;
            case "emps":
                List<Empleado_DTO> emps;
                emps = (List<Empleado_DTO>) sesion.getAttribute("emps");
                lst = (ArrayList) emps;
                Empleado_DTO e = (Empleado_DTO) lst.get(index);
                persona = (Persona_DTO) e;
                break;
            case "meds":
                List<Medico_DTO> meds = (List<Medico_DTO>) sesion.getAttribute("meds");
                lst = (ArrayList) meds;
                Medico_DTO m = (Medico_DTO) lst.get(index);
                persona = (Persona_DTO) m;
                break;
            case "pacs":
                List<Paciente_DTO> pacs = (List<Paciente_DTO>) sesion.getAttribute("pacs");
                lst = (ArrayList) pacs;
                Paciente_DTO p = (Paciente_DTO) lst.get(index);
                persona = (Persona_DTO) p;
                break;
        }
        String part = request.getParameter("part").trim();
        String acc = request.getParameter("acc").trim();
        Persona_DAO P = new Persona_DAO();
        switch (part) {
            case "name":
                if ("upd".equals(acc)) {
                    persona.setNombre(request.getParameter("namep"));
                    persona.setAp_Paterno(request.getParameter("patp"));
                    persona.setAp_Materno(request.getParameter("matp"));
                    P.ActualizarNombre(persona);
                    out.println("<table style=' text-align: center' class='table table-bordered table-hover table-sm'>"
                            + "<tr class='table-info' style='color: black'>"
                            + "<th colspan='3' >Datos de Empleado</th>"
                            + "<th>Modificar</th></tr><tr>"
                            + "<td >" + persona.getNombre() + "</td>"
                            + "<td >" + persona.getAp_Paterno() + "</td>"
                            + "<td >" + persona.getAp_Materno() + "</td>"
                            + "<th><button href=# class='btn btn-warning btn-sm' onclick=FormUpDtsP(" + index + ",'name','form','" + list + "') ><span><img src='images/pencil.png'></span></button></th>"
                            + "</tr></table>");
                } else {
                    out.println("<table style=' text-align: center' class='table table-bordered table-hover table-sm'>"
                            + "<tr class='table-info' style='color: black'>"
                            + "<th colspan='3' >Datos de Empleado</th>"
                            + "<th>Guardar</th></tr><tr>"
                            + "<td><input style='text-align: center' type='text' class='form-control form-control-sm' name='namep' value='" + persona.getNombre() + "' id='namep' placeholder='Nombre' required></td>"
                            + "<td><input style='text-align: center' type='text' class='form-control form-control-sm' name='patp' value='" + persona.getAp_Paterno() + "' id='patp' placeholder='A Paterno' required></td>"
                            + "<td><input style='text-align: center' type='text' class='form-control form-control-sm' name='matp' value='" + persona.getAp_Materno() + "' id='matp' placeholder='A Materno' required></td>"
                            + "<th><button href=# class='btn btn-success btn-sm' onclick=FormUpDtsP(" + index + ",'name','upd','" + list + "') ><span><img src='images/save.png'></span></button></th>"
                            + "</tr></table>");
                }
                break;
            case "contacto":
                int cpn;
                switch (list) {
                    case "uns":
                        cpn = 2;
                        break;
                    default:
                        cpn = 3;
                        break;
                }
                if ("upd".equals(acc)) {
                    persona.setMail(request.getParameter("mailP").trim());
                    persona.setTelefono1(request.getParameter("tel1P").trim());
                    if (request.getParameter("tel2P") != null) {
                        persona.setTelefono2(request.getParameter("tel2P").trim());
                    }
                    P.ActualizarContacto(persona);
                    out.println("<table style=' text-align: center' class='table table-bordered table-hover table-sm'>"
                            + "<tr class='table-success' style='color: black'>"
                            + "<th colspan='" + cpn + "' >Datos de Contacto</th>"
                            + "<th >Modificar</th></tr><tr>"
                            + "<td >" + persona.getMail() + "</td>"
                            + "<td >" + persona.getTelefono1() + "</td>");
                    if (!list.equals("uns")) {
                        out.println("<td >" + persona.getTelefono2() + "</td>");
                    }
                    out.println("<th><button href=# class='btn btn-warning btn-sm' onclick=FormUpDtsP(" + index + ",'contacto','form','" + list + "') ><span><img src='images/pencil.png'></span></button></th>"
                            + "</tr></table>");
                } else {
                    out.println("<table style=' text-align: center' class='table table-bordered table-hover table-sm'>"
                            + "<tr class='table-success' style='color: black'>"
                            + "<th colspan='" + cpn + "' >Datos de Contacto</th>"
                            + "<th >Guardar</th></tr><tr>");
                    if (persona.getMail() != null && persona.getTelefono1() != null) {
                        out.println("<td><input style='text-align: center' type='email' class='form-control form-control-sm' name='mailP' value='" + persona.getMail().trim() + "' id='mailP' placeholder='Correo Electrónico' required></td>"
                                + "<td><input style='text-align: center' type='number' class='form-control form-control-sm' name='tel1P' value='" + persona.getTelefono1().trim() + "' id='tel1P' placeholder='Teléfono' required></td>");
                    } else {
                        out.println("<td><input style='text-align: center' type='email' class='form-control form-control-sm' name='mailP' id='mailP' placeholder='Correo Electrónico' required></td>"
                                + "<td><input style='text-align: center' type='number' class='form-control form-control-sm' name='tel1P' id='tel1P' placeholder='Teléfono' required></td>");
                    }
                    if (!list.equals("uns")) {
                        if (persona.getTelefono2() != null) {
                            out.println("<td><input style='text-align: center' type='number' class='form-control form-control-sm' name='tel2P' value='" + persona.getTelefono2().trim() + "' id='tel2P' placeholder='Celular' required></td>");
                        } else {
                            out.println("<td><input style='text-align: center' type='number' class='form-control form-control-sm' name='tel2P' id='tel2P' placeholder='Celular' required></td>");
                        }
                    }
                    out.println("<th><button href=# class='btn btn-success btn-sm' onclick=FormUpDtsP(" + index + ",'contacto','upd','" + list + "') ><span><img src='images/save.png'></span></button></th>"
                            + "</tr></table>");
                }
                break;
            case "direccion":
                CP_DAO CP = new CP_DAO();
                CP_DTO c_p;
                Colonia_DAO C = new Colonia_DAO();
                if ("upd".equals(acc)) {
                    persona.setC_p(request.getParameter("c_p").trim());
                    c_p = CP.getDatosMex(persona.getC_p());
                    persona.setId_Estado(c_p.getId_Estado());
                    persona.setNombre_Estado(c_p.getNombre_Estado());
                    persona.setId_Municipio(c_p.getId_Municipio());
                    persona.setNombre_Municipio(c_p.getNombre_Municipio());
                    persona.setId_CP(c_p.getId_CP());
                    persona.setId_Colonia(Integer.parseInt(request.getParameter("colonia").trim()));
                    persona.setNombre_Colonia(C.getCol(persona.getId_Colonia()));
                    persona.setCalle(request.getParameter("calle"));
                    persona.setNo_Int(request.getParameter("no_int").trim());
                    persona.setNo_Ext(request.getParameter("no_ext").trim());
                    if (persona.getId_Direccion() == 0 || persona.getC_p() == null) {
                        persona.setId_Direccion(P.ActualizarDireccion(persona));
                    } else {
                        P.ActualizarDireccion(persona);
                    }
                    switch (list) {
                        case "uns":
                            try {
                                Unidad_DTO un = (Unidad_DTO) lst.get(index);
                                un.setEncargado(persona);
                                lst.set(index, un);
                            } catch (Exception e) {
                            }
                            break;
                        default:
                            try {
                                lst.set(index, persona);
                            } catch (Exception e) {
                            }
                            break;
                    }
                    out.println("<table style=' text-align: center' class='table table-bordered table-hover table-sm'>"
                            + "<tr class='table-success' style='color: black'>"
                            + "<th colspan='7' >Dirección</th>"
                            + "<th >Modificar</th>"
                            + "</tr>");
                    out.println("<tr>"
                            + "<td>CP:'" + persona.getC_p() + "'</td>"
                            + "<td>" + persona.getNombre_Estado() + "</td>"
                            + "<td>" + persona.getNombre_Municipio() + "</td>"
                            + "<td>" + persona.getNombre_Colonia() + "</td>"
                            + "<td>" + persona.getCalle() + "</td>"
                            + "<td>Int. " + persona.getNo_Int() + "</td>"
                            + "<td>Ext. " + persona.getNo_Ext() + "</td>"
                            + "<th><button href=# class='btn btn-warning btn-sm' onclick=FormUpDtsP(" + index + ",'direccion','form','" + list + "') ><span><img src='images/pencil.png'></span></button></th>"
                            + "</tr></table>");
                } else {
                    out.println("<table style=' text-align: center' class='table table-bordered table-hover table-sm'>"
                            + "<tr class='table-success' style='color: black'>"
                            + "<th colspan='6' >Dirección</th>"
                            + "<th >Guardar</th>"
                            + "</tr>");
                    out.println("<tr>");
                    if (!"".equals(persona.getC_p()) && persona.getC_p() != null) {
                        int Idc_p = CP.getIdCP(persona.getC_p());
                        List<Colonia_DTO> cols = C.getColonias(Idc_p);
                        out.println("<td><input type='number' onchange='llenaColonia();' class='form-control form-control-sm' name='c_p' value='" + persona.getC_p().trim() + "' id='c_p' placeholder='Código Postal' required></td>");
                        out.println("<td><div id='dir'>");
                        out.println(" <select class='custom-select d-block w-100 form-control-sm' id='colonia' name='colonia' required>");
                        out.print(" <option value='" + persona.getId_Colonia() + "'>" + persona.getNombre_Colonia() + "</option>");
                        if (!cols.isEmpty()) {
                            for (Colonia_DTO dto : cols) {
                                if (dto.getId_Colonia() == persona.getId_Colonia()) {
                                } else {
                                    out.print(" <option value='" + dto.getId_Colonia() + "'>" + dto.getNombre_Colonia() + "</option>");
                                }
                            }
                        }
                        out.print("</select></div></td>");
                    } else {
                        out.println("<td><input type='number' onchange='llenaColonia();' class='form-control form-control-sm' name='c_p' id='c_p' placeholder='Código Postal' required></td>");
                        out.println("<td><div id='dir'>");
                        out.println(" <select class='custom-select d-block w-100 form-control-sm' id='colonia' name='colonia' required>");
                        out.print(" <option value=''>Colonia</option>");
                        out.print("</select></div></td>");
                    }
                    if (persona.getCalle() != null && persona.getNo_Int().trim() != null && persona.getNo_Ext().trim() != null) {
                        out.print("<td><input style='text-align: center' type='text' class='form-control form-control-sm' name='calle' value='" + persona.getCalle() + "' id='calle' placeholder='Calle' required></td>"
                                + "<td colspan='2'><input style='text-align: center' type='text' class='form-control form-control-sm' name='no_int' value='" + persona.getNo_Int().trim() + "' id='no_int' placeholder='No. Int' required></td>"
                                + "<td><input style='text-align: center' type='text' class='form-control form-control-sm' name='no_ext' value='" + persona.getNo_Ext().trim() + "' id='no_ext' placeholder='No. Ext' required></td>"
                                + "<th><button href=# class='btn btn-success btn-sm' onclick=FormUpDtsP(" + index + ",'direccion','upd','" + list + "') ><span><img src='images/save.png'></span></button></th>"
                                + "</tr>");
                    } else {
                        out.print("<td><input style='text-align: center' type='text' class='form-control form-control-sm' name='calle'  id='calle' placeholder='Calle' required></td>"
                                + "<td colspan='2'><input style='text-align: center' type='text' class='form-control form-control-sm' name='no_int' id='no_int' placeholder='No. Int' required></td>"
                                + "<td><input style='text-align: center' type='text' class='form-control form-control-sm' name='no_ext' id='no_ext' placeholder='No. Ext' required></td>"
                                + "<th><button href=# class='btn btn-success btn-sm' onclick=FormUpDtsP(" + index + ",'direccion','upd','" + list + "') ><span><img src='images/save.png'></span></button></th>"
                                + "</tr>");
                    }
                    out.println("</table>");
                }
                break;
            case "fcsx":
                if ("upd".equals(acc)) {
                    persona.setFecha_Nac(request.getParameter("fNac").trim());
                    persona.setSexo(request.getParameter("Sexo").trim());
                    P.ActualizarFxSx(persona);
                    out.println("<table style=' text-align: center' class='table table-bordered table-hover table-sm'>"
                            + "<tr class='table-success' style='color: black'>"
                            + "<th >Fecha de Nacimiento</th>"
                            + "<th >Sexo</th>"
                            + "<th >Modificar</th></tr><tr>"
                            + "<td >" + persona.getFecha_Nac()+ "</td>"
                            + "<td >" + persona.getSexo() + "</td>");
                    out.println("<th><button href=# class='btn btn-warning btn-sm' onclick=FormUpDtsP(" + index + ",'fcsx','form','" + list + "') ><span><img src='images/pencil.png'></span></button></th>"
                            + "</tr></table>");
                } else {
                    out.println("<table style=' text-align: center' class='table table-bordered table-hover table-sm'>"
                            + "<tr class='table-success' style='color: black'>"
                            + "<th >Fecha de Nacimiento</th>"
                            + "<th >Sexo</th>"
                            + "<th >Guardar</th></tr><tr>");
                    if (persona.getFecha_Nac() != null && persona.getSexo() != null) {
                        out.println("<td><input style='text-align: center' type='date' class='form-control form-control-sm' name='fNac' value='" + persona.getFecha_Nac().trim() + "' id='fNac' required></td><td>");
                        out.println("<select class='custom-select d-block w-100 form-control-sm' id='Sexo' name='Sexo>' required>");
                        out.print("<option value='" + persona.getSexo() + "'>" + persona.getSexo() + "</option>");
                        if (persona.getSexo().equals("Femenino")) {
                            out.print("<option value='Masculino'>Masculino</option></select></td>");
                        } else {
                            out.print("<option value='Femenino'>Femenino</option></select></td>");
                        }
                    } else {
                        out.println("<td><input style='text-align: center' type='date' class='form-control form-control-sm' name='fNac' id='fNac' required></td><td>");
                        out.println("<select class='custom-select d-block w-100 form-control-sm' id='Sexo' name='Sexo>' required>");
                        out.print("<option value='Sexo'>Sexo</option>");
                        out.print("<option value='Masculino'>Masculino</option>");
                        out.print("<option value='Femenino'>Femenino</option></select></td>");
                    }
                    out.println("<th><button href=# class='btn btn-success btn-sm' onclick=FormUpDtsP(" + index + ",'fcsx','upd','" + list + "') ><span><img src='images/save.png'></span></button></th>"
                            + "</tr></table>");
                }
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
