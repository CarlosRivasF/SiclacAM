package Servlets.Medico;

import DataAccesObject.Medico_DAO;
import DataTransferObject.Medico_DTO;
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
@WebServlet(name = "SrchMed", urlPatterns = {"/SrchMed"})
public class SrchMed extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        HttpSession sesion = request.getSession();
        Medico_DAO M = new Medico_DAO();
        List<Medico_DTO> meds;
        if (sesion.getAttribute("meds") != null) {
            meds = (List<Medico_DTO>) sesion.getAttribute("meds");
        } else {
            meds = M.getMedicos();
            sesion.setAttribute("meds", meds);
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
                                    for (Medico_DTO dto : meds) {
                                        if (dto.getNombre().toUpperCase().trim().contains(prts[0].toUpperCase().trim()) & dto.getAp_Paterno().toUpperCase().trim().contains(prts[1].toUpperCase().trim())) {
                                            out.println("<tr>"
                                                    + "<td >" + dto.getNombre() + " " + dto.getAp_Paterno() + " " + dto.getAp_Materno() + "</td>"
                                                    + "<td><button href=# class='btn btn-warning' onclick=AddMed(" + meds.indexOf(dto) + ") ><span><img src='images/person.png'></span></button></td>"
                                                    + "</tr>");
                                        }
                                    }
                                    break;
                                case 3:
                                    for (Medico_DTO dto : meds) {
                                        if (dto.getNombre().toUpperCase().trim().contains(prts[0].toUpperCase().trim()) & dto.getAp_Paterno().toUpperCase().trim().contains(prts[1].toUpperCase().trim()) & dto.getAp_Materno().toUpperCase().trim().contains(prts[2].toUpperCase().trim())) {
                                            out.println("<tr>"
                                                    + "<td >" + dto.getNombre() + " " + dto.getAp_Paterno() + " " + dto.getAp_Materno() + "</td>"
                                                    + "<td><button href=# class='btn btn-warning' onclick=AddMed(" + meds.indexOf(dto) + ") ><span><img src='images/person.png'></span></button></td>"
                                                    + "</tr>");
                                        }
                                    }
                                    break;
                                default:
                                    meds.stream().filter((dto) -> (dto.getNombre().toUpperCase().trim().contains(busq.toUpperCase().trim()) || dto.getAp_Paterno().toUpperCase().trim().contains(busq.toUpperCase().trim()) || dto.getAp_Materno().toUpperCase().trim().contains(busq.toUpperCase().trim()))).forEachOrdered((Medico_DTO dto) -> {
                                        out.println("<tr>"
                                                + "<td >" + dto.getNombre() + " " + dto.getAp_Paterno() + " " + dto.getAp_Materno() + "</td>"
                                                + "<td><button href=# class='btn btn-warning' onclick=AddMed(" + meds.indexOf(dto) + ") ><span><img src='images/person.png'></span></button></td>"
                                                + "</tr>");
                                    });
                                    break;
                            }
                        } else {
                            meds.stream().filter((dto) -> (dto.getNombre().toUpperCase().trim().contains(busq.toUpperCase().trim()) || dto.getAp_Paterno().toUpperCase().trim().contains(busq.toUpperCase().trim()) || dto.getAp_Materno().toUpperCase().trim().contains(busq.toUpperCase().trim()))).forEachOrdered((Medico_DTO dto) -> {
                                out.println("<tr>"
                                        + "<td >" + dto.getNombre() + " " + dto.getAp_Paterno() + " " + dto.getAp_Materno() + "</td>"
                                        + "<td><button href=# class='btn btn-warning' onclick=AddMed(" + meds.indexOf(dto) + ") ><span><img src='images/person.png'></span></button></td>"
                                        + "</tr>");
                            });
                        }
                        out.println("</table>");
                        out.println("</div>");
                    }
                    break;
                case "med":
                    out.println("<div style='color: white' class='table-responsive'>"
                            + "<table style=' text-align: center' class='table table-bordered table-hover table-sm'>"
                            + "<tr class='table-active'>"
                            + "<th >Empresa</th>"
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
                                    for (Medico_DTO dto : meds) {
                                        if (dto.getNombre().toUpperCase().trim().contains(prts[0].toUpperCase().trim()) & dto.getAp_Paterno().toUpperCase().trim().contains(prts[1].toUpperCase().trim())) {
                                            out.println("<tr>"
                                                    + "<td >" + dto.getEmpresa() + "</td>"
                                                    + "<td >" + dto.getNombre() + "</td>"
                                                    + "<td >" + dto.getAp_Paterno() + "</td>"
                                                    + "<td >" + dto.getAp_Materno() + "</td>"
                                                    + "<td >" + dto.getTelefono1() + "</td>"
                                                    + "<td><button href=# class='btn btn-default' onclick=ShDetMed(" + meds.indexOf(dto) + ") ><span><img src='images/details.png'></span></button></td>"
                                                    + "<td><div id='medi-" + meds.indexOf(dto) + "'><button href=# class='btn btn-danger' onclick=FormDelMed(" + meds.indexOf(dto) + ",'show') ><span><img src='images/trash.png'></span></button></div></td>"
                                                    + "</tr>");
                                        }
                                    }
                                    break;
                                case 3:
                                    for (Medico_DTO dto : meds) {
                                        if (dto.getNombre().toUpperCase().trim().contains(prts[0].toUpperCase().trim()) & dto.getAp_Paterno().toUpperCase().trim().contains(prts[1].toUpperCase().trim()) & dto.getAp_Materno().toUpperCase().trim().contains(prts[2].toUpperCase().trim())) {
                                            out.println("<tr>"
                                                    + "<td >" + dto.getEmpresa() + "</td>"
                                                    + "<td >" + dto.getNombre() + "</td>"
                                                    + "<td >" + dto.getAp_Paterno() + "</td>"
                                                    + "<td >" + dto.getAp_Materno() + "</td>"
                                                    + "<td >" + dto.getTelefono1() + "</td>"
                                                    + "<td><button href=# class='btn btn-default' onclick=ShDetMed(" + meds.indexOf(dto) + ") ><span><img src='images/details.png'></span></button></td>"
                                                    + "<td><div id='medi-" + meds.indexOf(dto) + "'><button href=# class='btn btn-danger' onclick=FormDelMed(" + meds.indexOf(dto) + ",'show') ><span><img src='images/trash.png'></span></button></div></td>"
                                                    + "</tr>");
                                        }
                                    }
                                    break;
                                default:
                                    meds.stream().filter((dto) -> (dto.getNombre().toUpperCase().trim().contains(busq.toUpperCase().trim()) || dto.getAp_Paterno().toUpperCase().trim().contains(busq.toUpperCase().trim()) || dto.getAp_Materno().toUpperCase().trim().contains(busq.toUpperCase().trim()))).forEachOrdered((Medico_DTO dto) -> {
                                        out.println("<tr>"
                                                + "<td >" + dto.getEmpresa() + "</td>"
                                                + "<td >" + dto.getNombre() + "</td>"
                                                + "<td >" + dto.getAp_Paterno() + "</td>"
                                                + "<td >" + dto.getAp_Materno() + "</td>"
                                                + "<td >" + dto.getTelefono1() + "</td>"
                                                + "<td><button href=# class='btn btn-default' onclick=ShDetMed(" + meds.indexOf(dto) + ") ><span><img src='images/details.png'></span></button></td>"
                                                + "<td><div id='medi-" + meds.indexOf(dto) + "'><button href=# class='btn btn-danger' onclick=FormDelMed(" + meds.indexOf(dto) + ",'show') ><span><img src='images/trash.png'></span></button></div></td>"
                                                + "</tr>");
                                    });
                                    break;
                            }
                        } else {
                            meds.stream().filter((dto) -> (dto.getNombre().toUpperCase().trim().contains(busq.toUpperCase().trim()) || dto.getAp_Paterno().toUpperCase().trim().contains(busq.toUpperCase().trim()) || dto.getAp_Materno().toUpperCase().trim().contains(busq.toUpperCase().trim()))).forEachOrdered((Medico_DTO dto) -> {
                                out.println("<tr>"
                                        + "<td >" + dto.getEmpresa() + "</td>"
                                        + "<td >" + dto.getNombre() + "</td>"
                                        + "<td >" + dto.getAp_Paterno() + "</td>"
                                        + "<td >" + dto.getAp_Materno() + "</td>"
                                        + "<td >" + dto.getTelefono1() + "</td>"
                                        + "<td><button href=# class='btn btn-default' onclick=ShDetMed(" + meds.indexOf(dto) + ") ><span><img src='images/details.png'></span></button></td>"
                                        + "<td><div id='medi-" + meds.indexOf(dto) + "'><button href=# class='btn btn-danger' onclick=FormDelMed(" + meds.indexOf(dto) + ",'show') ><span><img src='images/trash.png'></span></button></div></td>"
                                        + "</tr>");
                            });
                        }
                        out.println("</table>");
                        out.println("</div>");
                    } else {
                        int i = 0;
                        for (Medico_DTO dto : meds) {
                            out.println("<tr>"
                                    + "<td >" + dto.getEmpresa() + "</td>"
                                    + "<td >" + dto.getNombre() + "</td>"
                                    + "<td >" + dto.getAp_Paterno() + "</td>"
                                    + "<td >" + dto.getAp_Materno() + "</td>"
                                    + "<td >" + dto.getTelefono1() + "</td>"
                                    + "<td><button href=# class='btn btn-default' onclick=ShDetMed(" + i + ") ><span><img src='images/details.png'></span></button></td>"
                                    + "<td><div id='medi-" + i + "'><button href=# class='btn btn-danger' onclick=FormDelMed(" + i + ",'show') ><span><img src='images/trash.png'></span></button></div></td>"
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
                                    for (Medico_DTO dto : meds) {
                                        if (dto.getNombre().toUpperCase().trim().contains(prts[0].toUpperCase().trim()) & dto.getAp_Paterno().toUpperCase().trim().contains(prts[1].toUpperCase().trim())) {
                                            out.println("<tr>"
                                                    + "<td >" + dto.getNombre() + " " + dto.getAp_Paterno() + " " + dto.getAp_Materno() + "</td>"
                                                    + "<td><button href=# class='btn btn-warning' onclick=NewMedord(" + meds.indexOf(dto) + ") ><span><img src='images/person.png'></span></button></td>"
                                                    + "</tr>");
                                        }
                                    }
                                    break;
                                case 3:
                                    for (Medico_DTO dto : meds) {
                                        if (dto.getNombre().toUpperCase().trim().contains(prts[0].toUpperCase().trim()) & dto.getAp_Paterno().toUpperCase().trim().contains(prts[1].toUpperCase().trim()) & dto.getAp_Materno().toUpperCase().trim().contains(prts[2].toUpperCase().trim())) {
                                            out.println("<tr>"
                                                    + "<td >" + dto.getNombre() + " " + dto.getAp_Paterno() + " " + dto.getAp_Materno() + "</td>"
                                                    + "<td><button href=# class='btn btn-warning' onclick=NewMedord(" + meds.indexOf(dto) + ") ><span><img src='images/person.png'></span></button></td>"
                                                    + "</tr>");
                                        }
                                    }
                                    break;
                                default:
                                    meds.stream().filter((dto) -> (dto.getNombre().toUpperCase().trim().contains(busq.toUpperCase().trim()) || dto.getAp_Paterno().toUpperCase().trim().contains(busq.toUpperCase().trim()) || dto.getAp_Materno().toUpperCase().trim().contains(busq.toUpperCase().trim()))).forEachOrdered((Medico_DTO dto) -> {
                                        out.println("<tr>"
                                                + "<td >" + dto.getNombre() + " " + dto.getAp_Paterno() + " " + dto.getAp_Materno() + "</td>"
                                                + "<td><button href=# class='btn btn-warning' onclick=NewMedord(" + meds.indexOf(dto) + ") ><span><img src='images/person.png'></span></button></td>"
                                                + "</tr>");
                                    });
                                    break;
                            }
                        } else {
                            meds.stream().filter((dto) -> (dto.getNombre().toUpperCase().trim().contains(busq.toUpperCase().trim()) || dto.getAp_Paterno().toUpperCase().trim().contains(busq.toUpperCase().trim()) || dto.getAp_Materno().toUpperCase().trim().contains(busq.toUpperCase().trim()))).forEachOrdered((Medico_DTO dto) -> {
                                out.println("<tr>"
                                        + "<td >" + dto.getNombre() + " " + dto.getAp_Paterno() + " " + dto.getAp_Materno() + "</td>"
                                        + "<td><button href=# class='btn btn-warning' onclick=NewMedord(" + meds.indexOf(dto) + ") ><span><img src='images/person.png'></span></button></td>"
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
