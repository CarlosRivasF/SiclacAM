package Servlets.Estudio;

import DataAccesObject.Estudio_DAO;
import DataAccesObject.Tipo_Estudio_DAO;
import DataTransferObject.Estudio_DTO;
import DataTransferObject.Tipo_Estudio_DTO;
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
@WebServlet(name = "FormUpEst", urlPatterns = {"/FormUpEst"})
public class FormUpEst extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession sesion = request.getSession();
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        List<Estudio_DTO> ests = (List<Estudio_DTO>) sesion.getAttribute("ests");
        Estudio_DAO E = new Estudio_DAO();
        int index = Integer.parseInt(request.getParameter("index").trim());
        String part = request.getParameter("part").trim();
        String acc = request.getParameter("acc").trim();
        Estudio_DTO est = ests.get(index);
        switch (part) {
            case "met":
                Tipo_Estudio_DAO TE = new Tipo_Estudio_DAO();
                List<Tipo_Estudio_DTO> tipos = TE.getTipo_Estudios();
                if ("upd".equals(acc)) {
                    est.setId_Tipo_Estudio(Integer.parseInt(request.getParameter("tipoE").trim()));
                    est.setMetodo(request.getParameter("metodoE"));
                    E.ActualizarMet(est.getId_Estudio(), est.getId_Tipo_Estudio(), est.getMetodo());
                    out.println("<table style=' text-align: center' class='table table-bordered table-hover table-sm'>"
                            + "<tr class='table-info' style='color: black'>"
                            + "<th style='color: black'>Tipo de Estudio</th>"
                            + "<th style='color: black'>Metodología</th>"
                            + "<th style='color: black'>Modificar</th></tr><tr>");
                    tipos.stream().filter((tipo) -> (est.getId_Tipo_Estudio() == tipo.getId_Tipo_Estudio())).forEachOrdered((tipo) -> {
                        out.print("<td >" + tipo.getNombre_Tipo_Estudio() + "</td>");
                    });                   
                    out.print("<td >" + est.getMetodo() + "</td>"
                            + "<th><button href=# class='btn btn-warning btn-sm' onclick=FormUpDesc(" + index + ",'met','form') ><span><img src='images/pencil.png'></span></button></th>"
                            + "</tr></table>");
                    request.getRequestDispatcher("ShDetEst#detPr");
                } else {
                    out.println("<table style=' text-align: center' class='table table-bordered table-hover table-sm'>"
                            + "<tr class='table-info' style='color: black'>"
                            + "<th style='color: black'>Tipo de Estudio</th>"
                            + "<th style='color: black'>Metodología</th>"
                            + "<th style='color: black'>Modificar</th></tr><tr>"
                            + "<td><select class='custom-select d-block w-100 form-control-sm' id='tipoE' name='tipoE' required>");
                    tipos.stream().filter((tipo) -> (est.getId_Tipo_Estudio() == tipo.getId_Tipo_Estudio())).forEachOrdered((tipo) -> {
                        out.print("<option value='" + est.getId_Tipo_Estudio() + "'>" + tipo.getNombre_Tipo_Estudio() + "</option>");
                    });
                    tipos.stream().filter((tipo) -> (est.getId_Tipo_Estudio() != tipo.getId_Tipo_Estudio())).forEachOrdered((tipo) -> {
                        out.print("<option value='" + tipo.getId_Tipo_Estudio()+ "'>" + tipo.getNombre_Tipo_Estudio() + "</option>");
                    });
                    out.print("<option value=''>TIPO DE ESTUDIO</option>");
                    out.print("</select></td>"
                            + "<td><input style='text-align: center' type='text' class='form-control form-control-sm' name='metodoE' value='" + est.getMetodo() + "' id='metodoE' placeholder='Metodología' required></td>"
                            + "<th><button href=# class='btn btn-warning btn-sm' onclick=FormUpDesc(" + index + ",'met','upd') ><span><img src='images/save.png'></span></button></th>"
                            + "</tr></table>");
                    request.getRequestDispatcher("ShDetEst#detPr");
                }
                break;
            case "prep":
                if ("upd".equals(acc)) {
                    est.setPreparacion(request.getParameter("prepar"));
                    E.ActualizarPrep(est.getPreparacion(), est.getId_Estudio());
                    out.println("<table style=' text-align: center' class='table table-bordered table-hover table-sm'>"
                            + "<tr class='table-info' style='color: black'>"
                            + "<th >Preparación</th>"
                            + "<td >" + est.getPreparacion() + "</td>"
                            + "<th><button href=# class='btn btn-warning btn-sm' onclick=FormUpDesc(" + index + ",'prep','form') ><span><img src='images/pencil.png'></span></button></th>"
                            + "</tr></table>");
                } else {
                    out.println("<table style=' text-align: center' class='table table-bordered table-hover table-sm'>"
                            + "<tr class='table-info' style='color: black'>"
                            + "<th >Preparación</th>"
                            + "<td><input style='text-align: center' type='text' class='form-control form-control-sm' name='prepar' value='" + est.getPreparacion() + "' id='prepar' placeholder='Preparación' required></td>"
                            + "<th><button href=# class='btn btn-success btn-sm' onclick=FormUpDesc(" + index + ",'prep','upd') ><span><img src='images/save.png'></span></button></th>"
                            + "</tr></table>");
                }
                break;
            case "util":
                if ("upd".equals(acc)) {
                    est.setUtilidad(request.getParameter("util"));
                    E.ActualizarUtilidad(est.getId_Estudio(), est.getUtilidad());
                    out.println("<table style=' text-align: center' class='table table-bordered table-hover table-sm'>"
                            + "<tr class='table-success' style='color: black'>"
                            + "<th >Utilidad</th>"
                            + "<td >" + est.getUtilidad() + "</td>"
                            + "<th><button href=# class='btn btn-warning btn-sm' onclick=FormUpDesc(" + index + ",'util','form') ><span><img src='images/pencil.png'></span></button></th>"
                            + "</tr></table>");
                } else {
                    out.println("<table style=' text-align: center' class='table table-bordered table-hover table-sm'>"
                            + "<tr class='table-success' style='color: black'>"
                            + "<th >Utilidad</th>"
                            + "<td><input style='text-align: center' type='text' class='form-control form-control-sm' name='util' value='" + est.getUtilidad() + "' id='util' placeholder='Utilidad' required></td>"
                            + "<th><button href=# class='btn btn-success btn-sm' onclick=FormUpDesc(" + index + ",'util','upd') ><span><img src='images/save.png'></span></button></th>"
                            + "</tr></table>");
                }
                break;
            case "prec":
                if ("upd".equals(acc)) {
                    est.getPrecio().setPrecio_N(Float.parseFloat(request.getParameter("precN")));
                    est.getPrecio().setT_Entrega_N(Integer.parseInt(request.getParameter("entrN").trim()));
                    est.getPrecio().setPrecio_U(Float.parseFloat(request.getParameter("precU")));
                    est.getPrecio().setT_Entrega_U(Integer.parseInt(request.getParameter("entrU").trim()));
                    E.ActualizarPrec(est.getPrecio().getId_Precio(), est.getId_Est_Uni(), est.getPrecio().getPrecio_N(), est.getPrecio().getT_Entrega_N(), est.getPrecio().getPrecio_U(), est.getPrecio().getT_Entrega_U());
                    out.println("<table style=' text-align: center' class='table table-bordered table-hover table-sm'>"
                            + "<tr class='table-warning' style='color: black'>"
                            + "<th >Precio Normal</th>"
                            + "<th >Entrega Normal</th>"
                            + "<th >Precio Urgente</th>"
                            + "<th >Entrega Urgente</th>"
                            + "<th >Modificar</th>"
                            + "</tr>");
                    out.println("<tr>"
                            + "<td >" + est.getPrecio().getPrecio_N() + " Pesos</td>"
                            + "<td >" + est.getPrecio().getT_Entrega_N() + " Dias</td>"
                            + "<td >" + est.getPrecio().getPrecio_U() + " Pesos</td>"
                            + "<td >" + est.getPrecio().getT_Entrega_U() + " Dias</td>"
                            + "<th><div id='detPr'><button href=# class='btn btn-warning btn-sm' onclick=FormUpDesc(" + index + ",'prec','form') ><span><img src='images/pencil.png'></span></button></div></th>"
                            + "</tr></table>");
                    request.getRequestDispatcher("ShDetEst#detPr");
                } else {
                    out.println("<table style=' text-align: center' class='table table-bordered table-hover table-sm'>"
                            + "<tr class='table-warning' style='color: black'>"
                            + "<th >Precio Normal</th>"
                            + "<th >Entrega Normal</th>"
                            + "<th >Precio Urgente</th>"
                            + "<th >Entrega Urgente</th>"
                            + "<th >Modificar</th>"
                            + "</tr>");
                    out.println("<tr>"
                            + "<td><input style='text-align: center' type='number' step='any' class='form-control form-control-sm' name='precN' value='" + est.getPrecio().getPrecio_N() + "' id='precN' placeholder='Precio Normal' required></td>"
                            + "<td><input style='text-align: center' type='number' class='form-control form-control-sm' name='entrN' value='" + est.getPrecio().getT_Entrega_N() + "' id='entrN' placeholder='Entrega Normal' required></td>"
                            + "<td><input style='text-align: center' type='number' step='any' class='form-control form-control-sm' name='precU' value='" + est.getPrecio().getPrecio_U() + "' id='precU' placeholder='Precio Urgente' required></td>"
                            + "<td><input style='text-align: center' type='number' class='form-control form-control-sm' name='entrU' value='" + est.getPrecio().getT_Entrega_U() + "' id='entrU' placeholder='Entrega Urgente' required></td>"
                            + "<th><div id='detPr'><button href=# class='btn btn-success btn-sm' onclick=FormUpDesc(" + index + ",'prec','upd') ><span><img src='images/save.png'></span></button></div></th>"
                            + "</tr>");
                    out.println("</table>");
                    request.getRequestDispatcher("ShDetEst#detPr");
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
