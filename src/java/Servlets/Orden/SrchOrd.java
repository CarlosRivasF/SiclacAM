package Servlets.Orden;

import DataAccesObject.Orden_DAO;
import DataTransferObject.Orden_DTO;
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
 * @author ZionSystem
 */
@WebServlet(name = "SrchOrd", urlPatterns = {"/SrchOrd"})
public class SrchOrd extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        HttpSession sesion = request.getSession();
        int id_unidad = Integer.parseInt(sesion.getAttribute("unidad").toString().trim());
        PrintWriter out = response.getWriter();
        Orden_DAO O = new Orden_DAO();
        List<Orden_DTO> ords = O.getOrdenes(id_unidad);
        if (request.getParameter("mode") != null) {
            String mode = request.getParameter("mode").trim();
            switch (mode) {
                case "pac":
                    out.println("<div style='color: white' class='table-responsive'>"
                            + "<table style=' text-align: center' class='table table-bordered table-hover table-sm'>"
                            + "<tr class='table-active'>"
                            + "<th >Clave</th>"
                            + "<th >Paciente</th>"
                            + "<th >Detalles</th>"
                            + "<th >Llenar</th>"
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
                                    for (Orden_DTO dto : ords) {
                                        String CodeCot = dto.getPaciente().getCodPac().substring(0, 4) + "-" + dto.getId_Orden();
                                        if (dto.getPaciente().getNombre().toUpperCase().trim().contains(prts[0].toUpperCase().trim()) & dto.getPaciente().getAp_Paterno().toUpperCase().trim().contains(prts[1].toUpperCase().trim())) {
                                            out.println("<tr>"
                                                    + "<td >" + CodeCot + "</td>"
                                                    + "<td >" + dto.getPaciente().getNombre() + " " + dto.getPaciente().getAp_Paterno() + " " + dto.getPaciente().getAp_Materno() + "</td>"
                                                    + "<td><button href=# class='btn btn-default' onclick=ShDetOrden(" + ords.indexOf(dto) + ") ><span><img src='images/details.png'></span></button></td>"
                                                    + "<td><div id='ord-" + ords.indexOf(dto) + "'><button href=# class='btn btn-primary' onclick=FormDelPac(" + ords.indexOf(dto) + ",'show') ><span><img src='images/fill.png'></span></button></div></td>"
                                                    + "</tr>");
                                        }
                                    }
                                    break;
                                case 3:
                                    for (Orden_DTO dto : ords) {
                                        String CodeCot = dto.getPaciente().getCodPac().substring(0, 4) + "-" + dto.getId_Orden();
                                        if (dto.getPaciente().getNombre().toUpperCase().trim().contains(prts[0].toUpperCase().trim()) & dto.getPaciente().getAp_Paterno().toUpperCase().trim().contains(prts[1].toUpperCase().trim()) & dto.getPaciente().getAp_Materno().toUpperCase().trim().contains(prts[2].toUpperCase().trim())) {
                                            out.println("<tr>"
                                                    + "<td >" + CodeCot + "</td>"
                                                    + "<td >" + dto.getPaciente().getNombre() + " " + dto.getPaciente().getAp_Paterno() + " " + dto.getPaciente().getAp_Materno() + "</td>"
                                                    + "<td><button href=# class='btn btn-default' onclick=ShDetOrden(" + ords.indexOf(dto) + ") ><span><img src='images/details.png'></span></button></td>"
                                                    + "<td><div id='ord-" + ords.indexOf(dto) + "'><button href=# class='btn btn-primary' onclick=FormDelPac(" + ords.indexOf(dto) + ",'show') ><span><img src='images/fill.png'></span></button></div></td>"
                                                    + "</tr>");
                                        }
                                    }
                                    break;
                                default:
                                    ords.stream().filter((dto) -> (dto.getPaciente().getNombre().toUpperCase().trim().contains(busq.toUpperCase().trim()) || dto.getPaciente().getAp_Paterno().toUpperCase().trim().contains(busq.toUpperCase().trim()) || dto.getPaciente().getAp_Materno().toUpperCase().trim().contains(busq.toUpperCase().trim()))).forEachOrdered((Orden_DTO dto) -> {
                                        String CodeCot = dto.getPaciente().getCodPac().substring(0, 4) + "-" + dto.getId_Orden();
                                        out.println("<tr>"
                                                + "<td >" + CodeCot + "</td>"
                                                + "<td >" + dto.getPaciente().getNombre() + " " + dto.getPaciente().getAp_Paterno() + " " + dto.getPaciente().getAp_Materno() + "</td>"
                                                + "<td><button href=# class='btn btn-default' onclick=ShDetOrden(" + ords.indexOf(dto) + ") ><span><img src='images/details.png'></span></button></td>"
                                                + "<td><div id='ord-" + ords.indexOf(dto) + "'><button href=# class='btn btn-primary' onclick=FormDelPac(" + ords.indexOf(dto) + ",'show') ><span><img src='images/fill.png'></span></button></div></td>"
                                                + "</tr>");
                                    });
                                    break;
                            }
                        } else {
                            ords.stream().filter((dto) -> (dto.getPaciente().getNombre().toUpperCase().trim().contains(busq.toUpperCase().trim()) || dto.getPaciente().getAp_Paterno().toUpperCase().trim().contains(busq.toUpperCase().trim()) || dto.getPaciente().getAp_Materno().toUpperCase().trim().contains(busq.toUpperCase().trim()))).forEachOrdered((Orden_DTO dto) -> {
                                String CodeCot = dto.getPaciente().getCodPac().substring(0, 4) + "-" + dto.getId_Orden();
                                out.println("<tr>"
                                        + "<td >" + CodeCot + "</td>"
                                        + "<td >" + dto.getPaciente().getNombre() + " " + dto.getPaciente().getAp_Paterno() + " " + dto.getPaciente().getAp_Materno() + "</td>"
                                        + "<td><button href=# class='btn btn-default' onclick=ShDetOrden(" + ords.indexOf(dto) + ") ><span><img src='images/details.png'></span></button></td>"
                                        + "<td><div id='ord-" + ords.indexOf(dto) + "'><button href=# class='btn btn-primary' onclick=FormDelPac(" + ords.indexOf(dto) + ",'show') ><span><img src='images/fill.png'></span></button></div></td>"
                                        + "</tr>");
                            });
                        }
                        out.println("</table>");
                        out.println("</div>");
                    } else {
                        ords.forEach((dto) -> {
                            String CodeCot = dto.getPaciente().getCodPac().substring(0, 4) + "-" + dto.getId_Orden();
                            out.println("<tr>"
                                    + "<td >" + CodeCot + "</td>"
                                    + "<td >" + dto.getPaciente().getNombre() + " " + dto.getPaciente().getAp_Paterno() + " " + dto.getPaciente().getAp_Materno() + "</td>"
                                    + "<td><button href=# class='btn btn-default' onclick=ShDetOrden(" + ords.indexOf(dto) + ") ><span><img src='images/details.png'></span></button></td>"
                                    + "<td><div id='ord-" + ords.indexOf(dto) + "'><button href=# class='btn btn-primary' onclick=FormDelPac(" + ords.indexOf(dto) + ",'show') ><span><img src='images/fill.png'></span></button></div></td>"
                                    + "</tr>");
                        });
                        out.println("</table>");
                        out.println("</div>");
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
