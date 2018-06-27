package Servlets.Paciente;

import DataAccesObject.Paciente_DAO;
import DataTransferObject.Paciente_DTO;
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
@WebServlet(name = "SrchPac", urlPatterns = {"/SrchPac"})
public class SrchPac extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        HttpSession sesion = request.getSession();
        PrintWriter out = response.getWriter();
        List<Paciente_DTO> pacs;
        if (sesion.getAttribute("pacs") != null) {
            pacs = (List<Paciente_DTO>) sesion.getAttribute("pacs");
        } else {           
            Paciente_DAO P = new Paciente_DAO();
            pacs = P.getPacientes();
            sesion.setAttribute("pacs", pacs);
        }
        if (request.getParameter("mode") != null) {
            String mode = request.getParameter("mode").trim();
            switch (mode) {
                case "est":
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
                        out.println("<div style='color: white' class='table-responsive'>"
                                + "<table style=' text-align: center' class='table table-bordered table-hover table-sm'>"
                                + "<tr class='table-active'>"
                                + "<th >Nombre</th>"
                                + "<th >Elegir</th>"
                                + "</tr>");
                        if (f) {
                            switch (n) {
                                case 2:
                                    for (Paciente_DTO dto : pacs) {
                                        if (dto.getNombre().toUpperCase().trim().contains(prts[0].toUpperCase().trim()) & dto.getAp_Paterno().toUpperCase().trim().contains(prts[1].toUpperCase().trim())) {
                                            out.println("<tr>"
                                                    + "<td >" + dto.getNombre() + " " + dto.getAp_Paterno() + " " + dto.getAp_Materno() + "</td>"
                                                    + "<td><button href=# class='btn btn-success' onclick=AddPac(" + pacs.indexOf(dto) + ") ><span><img src='images/person.png'></span></button></td>"
                                                    + "</tr>");
                                        }
                                    }
                                    break;
                                case 3:
                                    for (Paciente_DTO dto : pacs) {
                                        if (dto.getNombre().toUpperCase().trim().contains(prts[0].toUpperCase().trim()) & dto.getAp_Paterno().toUpperCase().trim().contains(prts[1].toUpperCase().trim()) & dto.getAp_Materno().toUpperCase().trim().contains(prts[2].toUpperCase().trim())) {
                                            out.println("<tr>"
                                                    + "<td >" + dto.getNombre() + " " + dto.getAp_Paterno() + " " + dto.getAp_Materno() + "</td>"
                                                    + "<td><button href=# class='btn btn-success' onclick=AddPac(" + pacs.indexOf(dto) + ") ><span><img src='images/person.png'></span></button></td>"
                                                    + "</tr>");
                                        }
                                    }
                                    break;
                                default:
                                    pacs.stream().filter((dto) -> (dto.getNombre().toUpperCase().trim().contains(busq.toUpperCase().trim()) || dto.getAp_Paterno().toUpperCase().trim().contains(busq.toUpperCase().trim()) || dto.getAp_Materno().toUpperCase().trim().contains(busq.toUpperCase().trim()))).forEachOrdered((Paciente_DTO dto) -> {
                                        out.println("<tr>"
                                                + "<td >" + dto.getNombre() + " " + dto.getAp_Paterno() + " " + dto.getAp_Materno() + "</td>"
                                                + "<td><button href=# class='btn btn-success' onclick=AddPac(" + pacs.indexOf(dto) + ") ><span><img src='images/person.png'></span></button></td>"
                                                + "</tr>");
                                    });
                                    break;
                            }
                        } else {
                            pacs.stream().filter((dto) -> (dto.getNombre().toUpperCase().trim().contains(busq.toUpperCase().trim()) || dto.getAp_Paterno().toUpperCase().trim().contains(busq.toUpperCase().trim()) || dto.getAp_Materno().toUpperCase().trim().contains(busq.toUpperCase().trim()))).forEachOrdered((Paciente_DTO dto) -> {
                                out.println("<tr>"
                                        + "<td >" + dto.getNombre() + " " + dto.getAp_Paterno() + " " + dto.getAp_Materno() + "</td>"
                                        + "<td><button href=# class='btn btn-success' onclick=AddPac(" + pacs.indexOf(dto) + ") ><span><img src='images/person.png'></span></button></td>"
                                        + "</tr>");
                            });
                        }
                        out.println("</table>");
                        out.println("</div>");
                    }
                    break;
                case "pac":
                    out.println("<div style='color: white' class='table-responsive'>"
                            + "<table style=' text-align: center' class='table table-bordered table-hover table-sm'>"
                            + "<tr class='table-active'>"
                            + "<th >Nombre</th>"
                            + "<th >A Paterno</th>"
                            + "<th >A Materno</th>"
                            + "<th >Telefono</th>"
                            + "<th >Detalles</th>"
                            + "<th >Eliminar</th>"
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
                                    for (Paciente_DTO dto : pacs) {
                                        if (dto.getNombre().toUpperCase().trim().contains(prts[0].toUpperCase().trim()) & dto.getAp_Paterno().toUpperCase().trim().contains(prts[1].toUpperCase().trim())) {
                                            out.println("<tr>"
                                                    + "<td >" + dto.getNombre() + "</td>"
                                                    + "<td >" + dto.getAp_Paterno() + "</td>"
                                                    + "<td >" + dto.getAp_Materno() + "</td>"
                                                    + "<td >" + dto.getTelefono1() + "</td>"
                                                    + "<td><button href=# class='btn btn-default' onclick=ShDetPac(" + pacs.indexOf(dto) + ") ><span><img src='images/details.png'></span></button></td>"
                                                    + "<td><div id='mat-" + pacs.indexOf(dto) + "'><button href=# class='btn btn-danger' onclick=FormDelMat(" + pacs.indexOf(dto) + ",'show') ><span><img src='images/trash.png'></span></button></div></td>"
                                                    + "</tr>");
                                        }
                                    }
                                    break;
                                case 3:
                                    for (Paciente_DTO dto : pacs) {
                                        if (dto.getNombre().toUpperCase().trim().contains(prts[0].toUpperCase().trim()) & dto.getAp_Paterno().toUpperCase().trim().contains(prts[1].toUpperCase().trim()) & dto.getAp_Materno().toUpperCase().trim().contains(prts[2].toUpperCase().trim())) {
                                            out.println("<tr>"
                                                    + "<td >" + dto.getNombre() + "</td>"
                                                    + "<td >" + dto.getAp_Paterno() + "</td>"
                                                    + "<td >" + dto.getAp_Materno() + "</td>"
                                                    + "<td >" + dto.getTelefono1() + "</td>"
                                                    + "<td><button href=# class='btn btn-default' onclick=ShDetPac(" + pacs.indexOf(dto) + ") ><span><img src='images/details.png'></span></button></td>"
                                                    + "<td><div id='mat-" + pacs.indexOf(dto) + "'><button href=# class='btn btn-danger' onclick=FormDelMat(" + pacs.indexOf(dto) + ",'show') ><span><img src='images/trash.png'></span></button></div></td>"
                                                    + "</tr>");
                                        }
                                    }
                                    break;
                                default:
                                    pacs.stream().filter((dto) -> (dto.getNombre().toUpperCase().trim().contains(busq.toUpperCase().trim()) || dto.getAp_Paterno().toUpperCase().trim().contains(busq.toUpperCase().trim()) || dto.getAp_Materno().toUpperCase().trim().contains(busq.toUpperCase().trim()))).forEachOrdered((Paciente_DTO dto) -> {
                                        out.println("<tr>"
                                                + "<td >" + dto.getNombre() + "</td>"
                                                + "<td >" + dto.getAp_Paterno() + "</td>"
                                                + "<td >" + dto.getAp_Materno() + "</td>"
                                                + "<td >" + dto.getTelefono1() + "</td>"
                                                + "<td><button href=# class='btn btn-default' onclick=ShDetPac(" + pacs.indexOf(dto) + ") ><span><img src='images/details.png'></span></button></td>"
                                                + "<td><div id='mat-" + pacs.indexOf(dto) + "'><button href=# class='btn btn-danger' onclick=FormDelMat(" + pacs.indexOf(dto) + ",'show') ><span><img src='images/trash.png'></span></button></div></td>"
                                                + "</tr>");
                                    });
                                    break;
                            }
                        } else {
                            pacs.stream().filter((dto) -> (dto.getNombre().toUpperCase().trim().contains(busq.toUpperCase().trim()) || dto.getAp_Paterno().toUpperCase().trim().contains(busq.toUpperCase().trim()) || dto.getAp_Materno().toUpperCase().trim().contains(busq.toUpperCase().trim()))).forEachOrdered((Paciente_DTO dto) -> {
                                out.println("<tr>"
                                        + "<td >" + dto.getNombre() + "</td>"
                                        + "<td >" + dto.getAp_Paterno() + "</td>"
                                        + "<td >" + dto.getAp_Materno() + "</td>"
                                        + "<td >" + dto.getTelefono1() + "</td>"
                                        + "<td><button href=# class='btn btn-default' onclick=ShDetPac(" + pacs.indexOf(dto) + ") ><span><img src='images/details.png'></span></button></td>"
                                        + "<td><div id='mat-" + pacs.indexOf(dto) + "'><button href=# class='btn btn-danger' onclick=FormDelMat(" + pacs.indexOf(dto) + ",'show') ><span><img src='images/trash.png'></span></button></div></td>"
                                        + "</tr>");
                            });
                        }
                        out.println("</table>");
                        out.println("</div>");
                    } else {
                        int i = 0;
                        for (Paciente_DTO dto : pacs) {
                            out.println("<tr>"
                                    + "<td >" + dto.getNombre() + "</td>"
                                    + "<td >" + dto.getAp_Paterno() + "</td>"
                                    + "<td >" + dto.getAp_Materno() + "</td>"
                                    + "<td >" + dto.getTelefono1() + "</td>"
                                    + "<td><button href=# class='btn btn-default' onclick=ShDetPac(" + i + ") ><span><img src='images/details.png'></span></button></td>"
                                    + "<td><div id='mat-" + i + "'><button href=# class='btn btn-danger' onclick=FormDelMat(" + i + ",'show') ><span><img src='images/trash.png'></span></button></div></td>"
                                    + "</tr>");
                            i++;
                        }
                        out.println("</table>");
                        out.println("</div>");
                        out.println("</div>");
                    }
                    break;
                    case "cot":
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
                        out.println("<div style='color: white' class='table-responsive'>"
                                + "<table style=' text-align: center' class='table table-bordered table-hover table-sm'>"
                                + "<tr class='table-active'>"
                                + "<th >Nombre</th>"
                                + "<th >Elegir</th>"
                                + "</tr>");
                        if (f) {
                            switch (n) {
                                case 2:
                                    for (Paciente_DTO dto : pacs) {
                                        if (dto.getNombre().toUpperCase().trim().contains(prts[0].toUpperCase().trim()) & dto.getAp_Paterno().toUpperCase().trim().contains(prts[1].toUpperCase().trim())) {
                                            out.println("<tr>"
                                                    + "<td >" + dto.getNombre() + " " + dto.getAp_Paterno() + " " + dto.getAp_Materno() + "</td>"
                                                    + "<td><button href=# class='btn btn-success' onclick=AddPacCot(" + pacs.indexOf(dto) + ") ><span><img src='images/person.png'></span></button></td>"
                                                    + "</tr>");
                                        }
                                    }
                                    break;
                                case 3:
                                    for (Paciente_DTO dto : pacs) {
                                        if (dto.getNombre().toUpperCase().trim().contains(prts[0].toUpperCase().trim()) & dto.getAp_Paterno().toUpperCase().trim().contains(prts[1].toUpperCase().trim()) & dto.getAp_Materno().toUpperCase().trim().contains(prts[2].toUpperCase().trim())) {
                                            out.println("<tr>"
                                                    + "<td >" + dto.getNombre() + " " + dto.getAp_Paterno() + " " + dto.getAp_Materno() + "</td>"
                                                    + "<td><button href=# class='btn btn-success' onclick=AddPacCot(" + pacs.indexOf(dto) + ") ><span><img src='images/person.png'></span></button></td>"
                                                    + "</tr>");
                                        }
                                    }
                                    break;
                                default:
                                    pacs.stream().filter((dto) -> (dto.getNombre().toUpperCase().trim().contains(busq.toUpperCase().trim()) || dto.getAp_Paterno().toUpperCase().trim().contains(busq.toUpperCase().trim()) || dto.getAp_Materno().toUpperCase().trim().contains(busq.toUpperCase().trim()))).forEachOrdered((Paciente_DTO dto) -> {
                                        out.println("<tr>"
                                                + "<td >" + dto.getNombre() + " " + dto.getAp_Paterno() + " " + dto.getAp_Materno() + "</td>"
                                                + "<td><button href=# class='btn btn-success' onclick=AddPacCot(" + pacs.indexOf(dto) + ") ><span><img src='images/person.png'></span></button></td>"
                                                + "</tr>");
                                    });
                                    break;
                            }
                        } else {
                            pacs.stream().filter((dto) -> (dto.getNombre().toUpperCase().trim().contains(busq.toUpperCase().trim()) || dto.getAp_Paterno().toUpperCase().trim().contains(busq.toUpperCase().trim()) || dto.getAp_Materno().toUpperCase().trim().contains(busq.toUpperCase().trim()))).forEachOrdered((Paciente_DTO dto) -> {
                                out.println("<tr>"
                                        + "<td >" + dto.getNombre() + " " + dto.getAp_Paterno() + " " + dto.getAp_Materno() + "</td>"
                                        + "<td><button href=# class='btn btn-success' onclick=AddPacCot(" + pacs.indexOf(dto) + ") ><span><img src='images/person.png'></span></button></td>"
                                        + "</tr>");
                            });
                        }
                        out.println("</table>");
                        out.println("</div>");
                    }
                    break;
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
