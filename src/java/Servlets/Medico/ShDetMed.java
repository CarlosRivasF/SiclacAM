package Servlets.Medico;

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
@WebServlet(name = "ShDetMed", urlPatterns = {"/ShDetMed"})
public class ShDetMed extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        HttpSession sesion = request.getSession();
        List<Medico_DTO> meds = (List<Medico_DTO>) sesion.getAttribute("meds");
        int index = Integer.parseInt(request.getParameter("index").trim());
        Medico_DTO med = meds.get(index);
        out.print("<div class='nav-scroller bg-white box-shadow'>"
                + "    <nav class='nav nav-underline'>"
                + "        <a class='nav-link' href='#' onclick=mostrarForm('" + request.getContextPath() + "/Menu/Medico/Registro.jsp');>Nuevo Médico</a>"
                + "        <a class='nav-link' href='#' onclick=mostrarForm('ShowMedi'); >Ver Médicos</a>"
                + "    </nav>"
                + "</div><hr class='mb-1'>");
        out.println("<div style='color: white' class='table-responsive'>");

        out.println("<div id='nameP'>"
                + "<table style=' text-align: center' class='table table-bordered table-hover table-sm'>"
                + "<tr class='table-info' style='color: black'>"
                + "<th colspan='3' >Datos de Empleado</th>"
                + "<th>Modificar</th></tr><tr>"
                + "<td >" + med.getNombre() + "</td>"
                + "<td >" + med.getAp_Paterno() + "</td>"
                + "<td >" + med.getAp_Materno() + "</td>"
                + "<th><button href=# class='btn btn-warning btn-sm' onclick=FormUpDtsP(" + index + ",'name','form','meds') ><span><img src='images/pencil.png'></span></button></th>"
                + "</tr></table>");
        out.println("</div>");

        out.println("<div id='contactoP'>"
                + "<table style=' text-align: center' class='table table-bordered table-hover table-sm'>"
                + "<tr class='table-warning' style='color: black'>"
                + "<th colspan='3' >Datos de Contacto</th>"
                + "<th >Modificar</th></tr><tr>"
                + "<td >" + med.getMail() + "</td>"
                + "<td >" + med.getTelefono1() + "</td>"
                + "<td >" + med.getTelefono2() + "</td>"
                + "<th><button href=# class='btn btn-warning btn-sm' onclick=FormUpDtsP(" + index + ",'contacto','form','meds') ><span><img src='images/pencil.png'></span></button></th>"
                + "</tr></table>");
        out.println("</div>");

        out.println("<div id='direccion'>"
                + "<table style=' text-align: center' class='table table-bordered table-hover table-sm'>"
                + "<tr class='table-success' style='color: black'>"
                + "<th colspan='7' >Dirección</th>"
                + "<th >Modificar</th>"
                + "</tr>");
        out.println("<tr>"
                + "<td>CP:'" + med.getC_p() + "'</td>"
                + "<td>" + med.getNombre_Estado() + "</td>"
                + "<td>" + med.getNombre_Municipio() + "</td>"
                + "<td>" + med.getNombre_Colonia() + "</td>"
                + "<td>" + med.getCalle() + "</td>"
                + "<td>Int. " + med.getNo_Int() + "</td>"
                + "<td>Ext. " + med.getNo_Ext() + "</td>"
                + "<th><button href=# class='btn btn-warning btn-sm' onclick=FormUpDtsP(" + index + ",'direccion','form','meds') ><span><img src='images/pencil.png'></span></button></th>"
                + "</tr></table>");
        out.println("</div>");
        
                out.println("<div id='datosMed'>"
                + "<table style=' text-align: center' class='table table-bordered table-hover table-sm'>"
                + "<tr class='table-danger' style='color: black'>"
                + "<th >Emoresa</th>"
                + "<th >Empresa</th>"
                + "<th >Descuento</th></tr><tr>"
                + "<td >" + med.getEmpresa() + "</td>"
                + "<td >" + med.getDescuento()+ "</td>"
                + "<th><button href=# class='btn btn-warning btn-sm' onclick=FormUpCrNsP(" + index + ",'form','datosEmp') ><span><img src='images/pencil.png'></span></button></th>"
                + "</tr></table>");
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
