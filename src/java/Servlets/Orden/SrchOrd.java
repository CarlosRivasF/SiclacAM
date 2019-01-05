package Servlets.Orden;

import DataAccesObject.Orden_DAO;
import DataBase.Fecha;
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
        List<Orden_DTO> ords;

        String part = request.getParameter("part").trim();
        switch (part) {
            case "ord":
                ords = O.getOrdenesPendientes(id_unidad);
                break;
            case "sald":
                ords = O.getOrdenesSaldo(id_unidad);
                break;
            case "results":
                ords = O.getOrdenesTerminadas(id_unidad);
                break;
            case "uplRs":
                ords = O.getOrdenesPendientes(id_unidad);
                break;
            default:
                ords = O.getOrdenes(id_unidad);
                break;
        }

        if (request.getParameter("mode") != null) {
            String mode = request.getParameter("mode").trim();
            switch (mode) {
                case "pac":
                    out.println("<div style='color: white' class='table-responsive'>"
                            + "<table style=' text-align: center' class='table table-bordered table-hover table-sm'>"
                            + "<tr class='table-active'>"
                            + "<th >Folio de Unidad</th>"
                            + "<th >Clave</th>"
                            + "<th >Paciente</th>");
                    switch (part) {
                        case "ord":
                            out.println("<th >Detalles</th>"
                                    + "<th >Cancelar</th>");
                            break;
                        case "sald":
                            out.println("<th >Saldo</th>");
                            out.println("<th >Pagar</th>");
                            break;
                        case "results":
                            out.println("<th >Resultados</th>");
                            out.println("<th >Imprimir</th>");
                            break;
                        case "uplRs":
                            out.println("<th >LLenar</th>");
                            break;
                    }
                    out.println("</tr>");
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
                                                    + "<td >" + dto.getFolio_Unidad() + "</td>"
                                                    + "<td >" + CodeCot + "</td>"
                                                    + "<td >" + dto.getPaciente().getNombre() + " " + dto.getPaciente().getAp_Paterno() + " " + dto.getPaciente().getAp_Materno() + "</td>");
                                            switch (part) {
                                                case "ord":
                                                    out.print("<td><button href=# class='btn btn-default' onclick=ShDetOrden(" + ords.indexOf(dto) + ",'ord') ><span><img src='images/details.png'></span></button></td>");
                                                    out.println("<td><div id='ord-" + ords.indexOf(dto) + "'><button href=# class='btn btn-primary' onclick=CancelOrd(" + ords.indexOf(dto) + ") ><span><img src='images/cancel.png'></span></button></div></td>");
                                                    break;
                                                case "sald":
                                                    out.println("<td >" + dto.getMontoRestante() + "</td>");
                                                    out.print("<td><div id='ord-" + ords.indexOf(dto) + "'><button href=# class='btn btn-success' onclick=mostrarForm('" + request.getContextPath() + "/Menu/Pago/formAddPay.jsp?id_Orden=" + dto.getId_Orden() + "');><span><img src='images/pay.png'></span></button></div></td>");
                                                    break;
                                                case "results":
                                                    out.println("<td><div id='ord-" + ords.indexOf(dto) + "'><button href=# class='btn btn-primary' onclick=ShDetOrdenRS(" + ords.indexOf(dto) + ") ><span><img src='images/fill.png'></span></button></div></td>");
                                                    out.println("<td><a href=# onclick=OpenRep('PrintRes?LxOrdSald=" + Fecha.Encriptar(String.valueOf(dto.getId_Orden())) + "') class='btn btn-primary' ><span><img src='images/print.png'></span></a></td>");
                                                    break;
                                                case "uplRs":
                                                    out.println("<td><div id='ord-" + ords.indexOf(dto) + "'><button href=# class='btn btn-primary' onclick=ShDetOrdenRS(" + dto.getId_Orden() + ",'folio') ><span><img src='images/fill.png'></span></button></div></td>");
                                                    break;
                                            }
                                            out.print("</tr>");
                                        }
                                    }
                                    break;
                                case 3:
                                    for (Orden_DTO dto : ords) {
                                        String CodeCot = dto.getPaciente().getCodPac().substring(0, 4) + "-" + dto.getId_Orden();
                                        if (dto.getPaciente().getNombre().toUpperCase().trim().contains(prts[0].toUpperCase().trim()) & dto.getPaciente().getAp_Paterno().toUpperCase().trim().contains(prts[1].toUpperCase().trim()) & dto.getPaciente().getAp_Materno().toUpperCase().trim().contains(prts[2].toUpperCase().trim())) {
                                            out.println("<tr>"
                                                    + "<td >" + dto.getFolio_Unidad() + "</td>"
                                                    + "<td >" + CodeCot + "</td>"
                                                    + "<td >" + dto.getPaciente().getNombre() + " " + dto.getPaciente().getAp_Paterno() + " " + dto.getPaciente().getAp_Materno() + "</td>");
                                            switch (part) {
                                                case "ord":
                                                    out.print("<td><button href=# class='btn btn-default' onclick=ShDetOrden(" + ords.indexOf(dto) + ",'ord') ><span><img src='images/details.png'></span></button></td>");
                                                    out.println("<td><div id='ord-" + ords.indexOf(dto) + "'><button href=# class='btn btn-primary' onclick=CancelOrd(" + ords.indexOf(dto) + ") ><span><img src='images/cancel.png'></span></button></div></td>");
                                                    break;
                                                case "sald":
                                                    out.println("<td >" + dto.getMontoRestante() + "</td>");
                                                    out.print("<td><div id='ord-" + ords.indexOf(dto) + "'><button href=# class='btn btn-success' onclick=mostrarForm('" + request.getContextPath() + "/Menu/Pago/formAddPay.jsp?id_Orden=" + dto.getId_Orden() + "');><span><img src='images/pay.png'></span></button></div></td>");
                                                    break;
                                                case "results":
                                                    out.println("<td><div id='ord-" + ords.indexOf(dto) + "'><button href=# class='btn btn-primary' onclick=ShDetOrdenRS(" + ords.indexOf(dto) + ") ><span><img src='images/fill.png'></span></button></div></td>");
                                                    out.println("<td><a href=# onclick=OpenRep('PrintRes?LxOrdSald=" + Fecha.Encriptar(String.valueOf(dto.getId_Orden())) + "') class='btn btn-primary' ><span><img src='images/print.png'></span></a></td>");
                                                    break;
                                                case "uplRs":
                                                    out.println("<td><div id='ord-" + ords.indexOf(dto) + "'><button href=# class='btn btn-primary' onclick=ShDetOrdenRS(" + dto.getId_Orden() + ",'folio') ><span><img src='images/fill.png'></span></button></div></td>");
                                                    break;
                                            }
                                            out.print("</tr>");
                                        }
                                    }
                                    break;
                                default:
                                    ords.stream().filter((dto) -> (dto.getPaciente().getNombre().toUpperCase().trim().contains(busq.toUpperCase().trim()) || dto.getPaciente().getAp_Paterno().toUpperCase().trim().contains(busq.toUpperCase().trim()) || dto.getPaciente().getAp_Materno().toUpperCase().trim().contains(busq.toUpperCase().trim()))).forEachOrdered((Orden_DTO dto) -> {
                                        String CodeCot = dto.getPaciente().getCodPac().substring(0, 4) + "-" + dto.getId_Orden();
                                        out.println("<tr>"
                                                + "<td >" + dto.getFolio_Unidad() + "</td>"
                                                + "<td >" + CodeCot + "</td>"
                                                + "<td >" + dto.getPaciente().getNombre() + " " + dto.getPaciente().getAp_Paterno() + " " + dto.getPaciente().getAp_Materno() + "</td>");
                                        switch (part) {
                                            case "ord":
                                                out.print("<td><button href=# class='btn btn-default' onclick=ShDetOrden(" + ords.indexOf(dto) + ",'ord') ><span><img src='images/details.png'></span></button></td>");
                                                out.println("<td><div id='ord-" + ords.indexOf(dto) + "'><button href=# class='btn btn-primary' onclick=CancelOrd(" + ords.indexOf(dto) + ") ><span><img src='images/cancel.png'></span></button></div></td>");
                                                break;
                                            case "sald":
                                                out.println("<td >" + dto.getMontoRestante() + "</td>");
                                                out.print("<td><div id='ord-" + ords.indexOf(dto) + "'><button href=# class='btn btn-success' onclick=mostrarForm('" + request.getContextPath() + "/Menu/Pago/formAddPay.jsp?id_Orden=" + dto.getId_Orden() + "');><span><img src='images/pay.png'></span></button></div></td>");
                                                break;
                                            case "results":
                                                out.println("<td><div id='ord-" + ords.indexOf(dto) + "'><button href=# class='btn btn-primary' onclick=ShDetOrdenRS(" + ords.indexOf(dto) + ") ><span><img src='images/fill.png'></span></button></div></td>");
                                                out.println("<td><a href=# onclick=OpenRep('PrintRes?LxOrdSald=" + Fecha.Encriptar(String.valueOf(dto.getId_Orden())) + "') class='btn btn-primary' ><span><img src='images/print.png'></span></a></td>");
                                                break;
                                            case "uplRs":
                                                out.println("<td><div id='ord-" + ords.indexOf(dto) + "'><button href=# class='btn btn-primary' onclick=ShDetOrdenRS(" + dto.getId_Orden() + ",'folio') ><span><img src='images/fill.png'></span></button></div></td>");
                                                break;
                                        }
                                        out.print("</tr>");
                                    });
                                    break;
                            }
                        } else {
                            ords.stream().filter((dto) -> (dto.getPaciente().getNombre().toUpperCase().trim().contains(busq.toUpperCase().trim()) || dto.getPaciente().getAp_Paterno().toUpperCase().trim().contains(busq.toUpperCase().trim()) || dto.getPaciente().getAp_Materno().toUpperCase().trim().contains(busq.toUpperCase().trim()))).forEachOrdered((Orden_DTO dto) -> {
                                String CodeCot = dto.getPaciente().getCodPac().substring(0, 4) + "-" + dto.getId_Orden();
                                out.println("<tr>"
                                        + "<td >" + dto.getFolio_Unidad() + "</td>"
                                        + "<td >" + CodeCot + "</td>"
                                        + "<td >" + dto.getPaciente().getNombre() + " " + dto.getPaciente().getAp_Paterno() + " " + dto.getPaciente().getAp_Materno() + "</td>");
                                switch (part) {
                                    case "ord":
                                        out.print("<td><button href=# class='btn btn-default' onclick=ShDetOrden(" + ords.indexOf(dto) + ",'ord') ><span><img src='images/details.png'></span></button></td>");
                                        out.println("<td><div id='ord-" + ords.indexOf(dto) + "'><button href=# class='btn btn-primary' onclick=CancelOrd(" + ords.indexOf(dto) + ") ><span><img src='images/cancel.png'></span></button></div></td>");
                                        break;
                                    case "sald":
                                        out.println("<td >" + dto.getMontoRestante() + "</td>");
                                        out.print("<td><div id='ord-" + ords.indexOf(dto) + "'><button href=# class='btn btn-success' onclick=mostrarForm('" + request.getContextPath() + "/Menu/Pago/formAddPay.jsp?id_Orden=" + dto.getId_Orden() + "');><span><img src='images/pay.png'></span></button></div></td>");
                                        break;
                                    case "results":
                                        out.println("<td><div id='ord-" + ords.indexOf(dto) + "'><button href=# class='btn btn-primary' onclick=ShDetOrdenRS(" + ords.indexOf(dto) + ") ><span><img src='images/fill.png'></span></button></div></td>");
                                        out.println("<td><a href=# onclick=OpenRep('PrintRes?LxOrdSald=" + Fecha.Encriptar(String.valueOf(dto.getId_Orden())) + "') class='btn btn-primary' ><span><img src='images/print.png'></span></a></td>");
                                        break;
                                    case "uplRs":
                                        out.println("<td><div id='ord-" + ords.indexOf(dto) + "'><button href=# class='btn btn-primary' onclick=ShDetOrdenRS(" + dto.getId_Orden() + ",'folio') ><span><img src='images/fill.png'></span></button></div></td>");
                                        break;
                                }
                                out.print("</tr>");
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
