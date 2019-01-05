package Servlets.Estudio;

import DataAccesObject.Tipo_Estudio_DAO;
import DataTransferObject.Configuracion_DTO;
import DataTransferObject.Est_Mat_DTO;
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

@WebServlet(name = "ShDetEst", urlPatterns = {"/ShDetEst"})
public class ShDetEst extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        HttpSession sesion = request.getSession();
        PrintWriter out = response.getWriter();
        List<Estudio_DTO> ests = (List<Estudio_DTO>) sesion.getAttribute("ests");
        int index = Integer.parseInt(request.getParameter("index").trim());
        Estudio_DTO est = ests.get(index);
        out.print("<div class='nav-scroller bg-white box-shadow'>"
                + "    <nav class='nav nav-underline'>"
                + "        <a class='nav-link' href='#' onclick=mostrarForm('" + request.getContextPath() + "/Menu/Estudio/Registro.jsp');>Nuevo Estudio</a>"
                + "        <a class='nav-link' href='#' onclick=mostrarForm('ShowEst'); >Lista de Estudios</a>"
                + "    </nav>"
                + "</div>"
                + "<div><hr class='mb-1'>"
                + "<h4 style='text-align: center; color: white'>" + est.getClave_Estudio() + " - " + est.getNombre_Estudio() + " <a onclick=OpenRep('PrintLabelEstudio?CodeEst=" + est.getClave_Estudio().replace("-", " ").trim() + "-" + est.getId_Est_Uni() + "-" + est.getNombre_Estudio().replace("-", " ").trim() + "-'); href=# class='btn btn-warning btn-sm' ><span><img src='images/barcode.png'></span></a></h4>"
                + "<hr class='mb-1'>");
        out.println("<div style='color: white' class='table-responsive'>");
        out.println("<div id='metodo'>"
                + "<table style=' text-align: center' class='table table-bordered table-hover table-sm'>"
                + "<tr class='table-info' style='color: black'>"
                + "<th style='color: black'>Tipo de Estudio</th>"
                + "<th style='color: black'>Metodología</th>"
                + "<th style='color: black'>Modificar</th></tr><tr>");
        Tipo_Estudio_DAO TE = new Tipo_Estudio_DAO();
        List<Tipo_Estudio_DTO> tipos = TE.getTipo_Estudios();
        tipos.stream().filter((tipo) -> (est.getId_Tipo_Estudio() == tipo.getId_Tipo_Estudio())).forEachOrdered((tipo) -> {
            out.print("<td >" + tipo.getNombre_Tipo_Estudio() + "</td>");
        });
        out.println("<td >" + est.getMetodo() + "</td>"
                + "<th><button href=# class='btn btn-warning btn-sm' onclick=FormUpDesc(" + index + ",'met','form') ><span><img src='images/pencil.png'></span></button></th>"
                + "</tr></table>");
        out.println("</div>");
        out.println("<div id='preparacion'>"
                + "<table style=' text-align: center' class='table table-bordered table-hover table-sm'>"
                + "<tr class='table-info' style='color: black'>"
                + "<th style='color: black'>Preparación</th>"
                + "<td >" + est.getPreparacion() + "</td>"
                + "<th><button href=# class='btn btn-warning btn-sm' onclick=FormUpDesc(" + index + ",'prep','form') ><span><img src='images/pencil.png'></span></button></th>"
                + "</tr></table>");
        out.println("</div>");

        out.println("<div id='utilidad'>"
                + "<table style=' text-align: center' class='table table-bordered table-hover table-sm'>"
                + "<tr class='table-success' style='color: black'>"
                + "<th style='color: black'>Utilidad</th>"
                + "<td >" + est.getUtilidad() + "</td>"
                + "<th><button href=# class='btn btn-warning btn-sm' onclick=FormUpDesc(" + index + ",'util','form') ><span><img src='images/pencil.png'></span></button></th>"
                + "</tr></table>");
        out.println("</div>");

        out.println("<div id='precio'>"
                + "<table style=' text-align: center' class='table table-bordered table-hover table-sm'>"
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
        out.println("</div>");

        out.println("<div id='configs'>");
        out.println("<table style=' text-align: center' class='table table-bordered table-hover table-sm'>"
                + "<tr class='table-info' style='color: black'>"
                + "<th >Desc</th>"
                + "<th >Sexo</th>"
                + "<th >Val 1</th>"
                + "<th >Val 2</th>"
                + "<th >Unidades</th>"
                + "<th>Modificar</th>"
                + "<th>Eliminar</th>"
                + "</tr>");
        int l = 0;
        for (Configuracion_DTO cnf : est.getCnfs()) {
            out.println("<tr>"
                    + "<td >" + cnf.getDescripcion() + "</td>"
                    + "<td >" + cnf.getSexo() + "</td>"
                    + "<td >" + cnf.getValor_min() + "</td>"
                    + "<td >" + cnf.getValor_MAX() + "</td>"
                    + "<td >" + cnf.getUniddes() + "</td>"
                    + "<td><button href=# class='btn btn-warning btn-sm' onclick=FormUpCn(" + index + "," + l + ",'form') ><span><img src='images/pencil.png'></span></button></td>"
                    + "<td><div id='estCn-" + l + "'><button href=# class='btn btn-danger btn-sm' onclick=FormDelEst_Conf(" + index + "," + l + ",'show') ><span><img src='images/trash.png'></span></button></div></td>"
                    + "</tr>");
            l++;
        }
        out.println("<tr>"
                + "<td colspan='7'><div id='addcnf'><button href=# class='btn btn-success btn-block' onclick=FormAddNWConf(" + index + ")>Agregar nueva configuración</button></div></td>"
                + "</tr>");
        out.println("</table>");
        out.println("</div>");

        out.println("<div id='matis'>");
        out.println("<table style=' text-align: center' class='table table-bordered table-hover table-sm'>"
                + "<tr class='table-success' style='color: black'>"
                + "<th >Material</th>"
                + "<th >Cantidad</th>"
                + "<th >Unidad</th>"
                + "<th >Muestra</th>"
                + "<th>Modificar</th>"
                + "<th>Eliminar</th>"
                + "</tr>");
        int i = 0;
        for (Est_Mat_DTO mt : est.getMts()) {
            out.println("<tr>"
                    + "<td >" + mt.getNombre_Material() + "</td>"
                    + "<td >" + mt.getCantidadE() + "</td>"
                    + "<td >" + mt.getUnidadE() + "</td>"
                    + "<td >" + mt.getT_Muestra() + "</td>"
                    + "<td><button href=# class='btn btn-warning btn-sm' onclick=FormUpEstMat(" + index + "," + i + ",'form') ><span><img src='images/pencil.png'></span></button></td>"
                    + "<td><div id='estMt-" + i + "'><button href=# class='btn btn-danger btn-sm' onclick=FormDelEst_Mat(" + index + "," + i + ",'show') ><span><img src='images/trash.png'></span></button></div></td>"
                    + "</tr>");
            i++;
        }
        out.println("</table>");
        out.println("</div>");

        out.println("</div>");
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
