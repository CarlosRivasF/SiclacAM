package Servlets.Empleado;

import DataTransferObject.Empleado_DTO;
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
@WebServlet(name = "SrchEmpl", urlPatterns = {"/SrchEmpl"})
public class SrchEmpl extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        HttpSession sesion = request.getSession();
        List<Empleado_DTO> emps = (List<Empleado_DTO>) sesion.getAttribute("emps");
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
        if (request.getParameter("busq") != null) {
            int n = 0;
            String[] prts = null;
            boolean f;
            String busq = request.getParameter("busq");
            if (busq.contains(" ")) {
                prts = busq.split(" ");
                n = prts.length;
                f = true;
            } else {
                f = false;
            }
            if (f) {
                switch (n) {
                    case 2:
                        for (Empleado_DTO dto : emps) {
                            if (dto.getNombre().toUpperCase().trim().contains(prts[0].toUpperCase().trim()) & dto.getAp_Paterno().toUpperCase().trim().contains(prts[1].toUpperCase().trim())) {
                                String btn = "";
                                String clr = "";
                                if (dto.getUsuario().getEstado().trim().equals("Activo")) {
                                    btn = "activeU";
                                    clr = "success";
                                } else if (dto.getUsuario().getEstado().trim().equals("Inactivo")) {
                                    btn = "inactiveU";
                                    clr = "danger";
                                }
                                out.println("<tr>"
                                        + "<td >" + dto.getUsuario().getNombre_Usuario() + "</td>"
                                        + "<td >" + dto.getNombre() + " " + dto.getAp_Paterno() + " " + dto.getAp_Materno() + "</td>"
                                        + "<td><button href=# class='btn btn-default btn-sm' onclick=ShDetEmpl(" + emps.indexOf(dto) + ") ><span><img src='images/Sancion.png'></span></button></td>"
                                        + "<td><button href=# class='btn btn-default btn-sm' onclick=ShDetEmpl(" + emps.indexOf(dto) + ") ><span><img src='images/Salario.png'></span></button></td>"
                                        + "<td><button href=# class='btn btn-default btn-sm' onclick=ShDetEmpl(" + emps.indexOf(dto) + ") ><span><img src='images/details.png'></span></button></td>"
                                        + "<td><div id='Empl-" + emps.indexOf(dto) + "'><button href=# class='btn btn-" + clr + " btn-sm' onclick=FormDelEmp(" + emps.indexOf(dto) + ",'show') ><span><img src='images/" + btn + ".png'></span></button></div></td>"
                                        + "</tr>");
                            }
                        }
                        break;
                    case 3:
                        for (Empleado_DTO dto : emps) {
                            if (dto.getNombre().toUpperCase().trim().contains(prts[0].toUpperCase().trim()) & dto.getAp_Paterno().toUpperCase().trim().contains(prts[1].toUpperCase().trim()) & dto.getAp_Materno().toUpperCase().trim().contains(prts[2].toUpperCase().trim())) {
                                String btn = "";
                                String clr = "";
                                if (dto.getUsuario().getEstado().trim().equals("Activo")) {
                                    btn = "activeU";
                                    clr = "success";
                                } else if (dto.getUsuario().getEstado().trim().equals("Inactivo")) {
                                    btn = "inactiveU";
                                    clr = "danger";
                                }
                                out.println("<tr>"
                                        + "<td >" + dto.getUsuario().getNombre_Usuario() + "</td>"
                                        + "<td >" + dto.getNombre() + " " + dto.getAp_Paterno() + " " + dto.getAp_Materno() + "</td>"
                                        + "<td><button href=# class='btn btn-default btn-sm' onclick=ShDetEmpl(" + emps.indexOf(dto) + ") ><span><img src='images/Sancion.png'></span></button></td>"
                                        + "<td><button href=# class='btn btn-default btn-sm' onclick=ShDetEmpl(" + emps.indexOf(dto) + ") ><span><img src='images/Salario.png'></span></button></td>"
                                        + "<td><button href=# class='btn btn-default btn-sm' onclick=ShDetEmpl(" + emps.indexOf(dto) + ") ><span><img src='images/details.png'></span></button></td>"
                                        + "<td><div id='Empl-" + emps.indexOf(dto) + "'><button href=# class='btn btn-" + clr + " btn-sm' onclick=FormDelEmp(" + emps.indexOf(dto) + ",'show') ><span><img src='images/" + btn + ".png'></span></button></div></td>"
                                        + "</tr>");
                            }
                        }
                        break;
                    default:
                        emps.stream().filter((dto) -> (dto.getNombre().toUpperCase().trim().contains(busq.toUpperCase().trim()) || dto.getAp_Paterno().toUpperCase().trim().contains(busq.toUpperCase().trim()) || dto.getAp_Materno().toUpperCase().trim().contains(busq.toUpperCase().trim()))).forEachOrdered((Empleado_DTO dto) -> {
                            String btn = "";
                            String clr = "";
                            if (dto.getUsuario().getEstado().trim().equals("Activo")) {
                                btn = "activeU";
                                clr = "success";
                            } else if (dto.getUsuario().getEstado().trim().equals("Inactivo")) {
                                btn = "inactiveU";
                                clr = "danger";
                            }
                            out.println("<tr>"
                                    + "<td >" + dto.getUsuario().getNombre_Usuario() + "</td>"
                                    + "<td >" + dto.getNombre() + " " + dto.getAp_Paterno() + " " + dto.getAp_Materno() + "</td>"
                                    + "<td><button href=# class='btn btn-default btn-sm' onclick=ShDetEmpl(" + emps.indexOf(dto) + ") ><span><img src='images/Sancion.png'></span></button></td>"
                                    + "<td><button href=# class='btn btn-default btn-sm' onclick=ShDetEmpl(" + emps.indexOf(dto) + ") ><span><img src='images/Salario.png'></span></button></td>"
                                    + "<td><button href=# class='btn btn-default btn-sm' onclick=ShDetEmpl(" + emps.indexOf(dto) + ") ><span><img src='images/details.png'></span></button></td>"
                                    + "<td><div id='Empl-" + emps.indexOf(dto) + "'><button href=# class='btn btn-" + clr + " btn-sm' onclick=FormDelEmp(" + emps.indexOf(dto) + ",'show') ><span><img src='images/" + btn + ".png'></span></button></div></td>"
                                    + "</tr>");
                        });
                        break;
                }
            } else {
                emps.stream().filter((dto) -> (dto.getNombre().toUpperCase().trim().contains(busq.toUpperCase().trim()) || dto.getAp_Paterno().toUpperCase().trim().contains(busq.toUpperCase().trim()) || dto.getAp_Materno().toUpperCase().trim().contains(busq.toUpperCase().trim()))).forEachOrdered((Empleado_DTO dto) -> {
                    String btn = "";
                    String clr = "";
                    if (dto.getUsuario().getEstado().trim().equals("Activo")) {
                        btn = "activeU";
                        clr = "success";
                    } else if (dto.getUsuario().getEstado().trim().equals("Inactivo")) {
                        btn = "inactiveU";
                        clr = "danger";
                    }
                    out.println("<tr>"
                            + "<td >" + dto.getUsuario().getNombre_Usuario() + "</td>"
                            + "<td >" + dto.getNombre() + " " + dto.getAp_Paterno() + " " + dto.getAp_Materno() + "</td>"
                            + "<td><button href=# class='btn btn-default btn-sm' onclick=ShDetEmpl(" + emps.indexOf(dto) + ") ><span><img src='images/Sancion.png'></span></button></td>"
                            + "<td><button href=# class='btn btn-default btn-sm' onclick=ShDetEmpl(" + emps.indexOf(dto) + ") ><span><img src='images/Salario.png'></span></button></td>"
                            + "<td><button href=# class='btn btn-default btn-sm' onclick=ShDetEmpl(" + emps.indexOf(dto) + ") ><span><img src='images/details.png'></span></button></td>"
                            + "<td><div id='Empl-" + emps.indexOf(dto) + "'><button href=# class='btn btn-" + clr + " btn-sm' onclick=FormDelEmp(" + emps.indexOf(dto) + ",'show') ><span><img src='images/" + btn + ".png'></span></button></div></td>"
                            + "</tr>");
                });
            }
            out.println("</table>");
            out.println("</div>");
        } else {
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
                out.println("<tr>"
                        + "<td >" + dto.getUsuario().getNombre_Usuario() + "</td>"
                        + "<td >" + dto.getNombre() + " " + dto.getAp_Paterno() + " " + dto.getAp_Materno() + "</td>"
                        + "<td><button href=# class='btn btn-default btn-sm' onclick=ShDetEmpl(" + emps.indexOf(dto) + ") ><span><img src='images/Sancion.png'></span></button></td>"
                        + "<td><button href=# class='btn btn-default btn-sm' onclick=ShDetEmpl(" + emps.indexOf(dto) + ") ><span><img src='images/Salario.png'></span></button></td>"
                        + "<td><button href=# class='btn btn-default btn-sm' onclick=ShDetEmpl(" + emps.indexOf(dto) + ") ><span><img src='images/details.png'></span></button></td>"
                        + "<td><div id='Empl-" + emps.indexOf(dto) + "'><button href=# class='btn btn-" + clr + " btn-sm' onclick=FormDelEmp(" + emps.indexOf(dto) + ",'show') ><span><img src='images/" + btn + ".png'></span></button></div></td>"
                        + "</tr>");
            });
        }
        out.println("</table>");
        out.println("</div></div>");
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
