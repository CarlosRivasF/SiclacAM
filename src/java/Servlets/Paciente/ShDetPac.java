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

@WebServlet(name = "ShDetPac", urlPatterns = {"/ShDetPac"})
public class ShDetPac extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        HttpSession sesion = request.getSession();
        PrintWriter out = response.getWriter();
        List<Paciente_DTO> pacs;
        if (sesion.getAttribute("pacs") != null) {
            pacs = (List<Paciente_DTO>) sesion.getAttribute("pacs");
        } else {
            Paciente_DAO Pa = new Paciente_DAO();
            pacs = Pa.getPacientes();
            sesion.setAttribute("pacs", pacs);
        }
        int index = Integer.parseInt(request.getParameter("index").trim());
        Paciente_DTO pac = pacs.get(index);
        if (request.getParameter("modeP") != null) {
            out.print("<br><h3 style='text-align: center; color: #ffffff'>Por favor complete los datos faltantes del paciente y vuelva a realizar el proceso de Órden.</h3><br>");
        } else {
            out.print("<div class='nav-scroller bg-white box-shadow'>"
                    + "    <nav class='nav nav-underline'>"
                    + "        <a class='nav-link' href='#' onclick=mostrarForm('" + request.getContextPath() + "/Menu/Paciente/Registro.jsp');>Nuevo Paciente</a>"
                    + "        <a class='nav-link' href='#' onclick=mostrarForm('ShowPac'); >Ver Pacientes</a>"
                    + "    </nav>"
                    + "</div><hr class='mb-1'>");
        }
        out.println("<div style='color: white' class='table-responsive'>");

        out.println("<div id='nameP'>"
                + "<table style=' text-align: center' class='table table-bordered table-hover table-sm'>"
                + "<tr class='table-info' style='color: black'>"
                + "<th colspan='3' >Datos de Paciente</th>"
                + "<th>Modificar</th></tr><tr>"
                + "<td >" + pac.getNombre() + "</td>"
                + "<td >" + pac.getAp_Paterno() + "</td>"
                + "<td >" + pac.getAp_Materno() + "</td>"
                + "<th><button href=# class='btn btn-warning btn-sm' onclick=FormUpDtsP(" + index + ",'name','form','pacs') ><span><img src='images/pencil.png'></span></button></th>"
                + "</tr></table>");
        out.println("</div>");

        out.println("<div id='contactoP'>"
                + "<table style=' text-align: center' class='table table-bordered table-hover table-sm'>"
                + "<tr class='table-warning' style='color: black'>"
                + "<th colspan='3' >Datos de Contacto</th>"
                + "<th >Modificar</th></tr><tr>");
        if (pac.getMail() != null && !"".equals(pac.getMail())) {
            out.print("<td >" + pac.getMail() + "</td>");
        } else {
            out.println("<td class='bg-danger'></td>");
        }
        if (pac.getTelefono1() != null && !"".equals(pac.getTelefono1())) {
            out.print("<td >" + pac.getTelefono1() + "</td>");
        } else {
            out.println("<td class='bg-danger'></td>");
        }
        if (pac.getTelefono2() != null && !"".equals(pac.getTelefono2())) {
            out.print("<td >" + pac.getTelefono2() + "</td>");
        } else {
            out.println("<td class='bg-danger'></td>");
        }
        out.println("<th><button href=# class='btn btn-warning btn-sm' onclick=FormUpDtsP(" + index + ",'contacto','form','pacs') ><span><img src='images/pencil.png'></span></button></th>"
                + "</tr></table>");
        out.println("</div>");

        out.println("<div id='fcsx'>"
                + "<table style=' text-align: center' class='table table-bordered table-hover table-sm'>"
                + "<tr class='table-warning' style='color: black'>"
                + "<th >Fecha de Nacimiento</th>"
                + "<th >Sexo</th>"
                + "<th >Modificar</th></tr><tr>");
        if (pac.getFecha_Nac() != null && !"".equals(pac.getFecha_Nac())) {
            out.println("<td>" + pac.getFecha_Nac() + "</td>");
        } else {
            out.println("<td class='bg-danger'></td>");
        }
        if (pac.getSexo() != null && !"".equals(pac.getSexo())) {
            out.println("<td>" + pac.getSexo() + "</td>");
        } else {
            out.println("<td class='bg-danger'></td>");
        }
        out.println("<th><button href=# class='btn btn-warning btn-sm' onclick=FormUpDtsP(" + index + ",'fcsx','form','pacs') ><span><img src='images/pencil.png'></span></button></th>"
                + "</tr></table>");
        out.println("</div>");

        out.println("<div id='direccion'>"
                + "<table style=' text-align: center' class='table table-bordered table-hover table-sm'>"
                + "<tr class='table-success' style='color: black'>"
                + "<th colspan='7' >Dirección</th>"
                + "<th >Modificar</th>"
                + "</tr>");
        out.println("<tr>");
        if (pac.getC_p() != null && !"".equals(pac.getC_p())) {
            out.println("<td>CP:'" + pac.getC_p() + "'</td>");
        } else {
            out.println("<td class='bg-danger'></td>");
        }
        if (pac.getNombre_Estado() != null && !"".equals(pac.getNombre_Estado())) {
            out.println("<td>'" + pac.getNombre_Estado() + "'</td>");
        } else {
            out.println("<td class='bg-danger'></td>");
        }
        if (pac.getNombre_Municipio() != null && !"".equals(pac.getNombre_Municipio())) {
            out.println("<td>'" + pac.getNombre_Municipio() + "'</td>");
        } else {
            out.println("<td class='bg-danger'></td>");
        }
        if (pac.getNombre_Colonia() != null && !"".equals(pac.getNombre_Colonia())) {
            out.println("<td>'" + pac.getNombre_Colonia() + "'</td>");
        } else {
            out.println("<td class='bg-danger'></td>");
        }
        if (pac.getCalle() != null && !"".equals(pac.getCalle())) {
            out.println("<td>'" + pac.getCalle() + "'</td>");
        } else {
            out.println("<td class='bg-danger'></td>");
        }
        if (pac.getNo_Int() != null && !"".equals(pac.getNo_Int())) {
            out.println("<td>'" + pac.getNo_Int() + "'</td>");
        } else {
            out.println("<td class='bg-danger'></td>");
        }
        if (pac.getNo_Ext() != null && !"".equals(pac.getNo_Ext())) {
            out.println("<td>'" + pac.getNo_Ext() + "'</td>");
        } else {
            out.println("<td class='bg-danger'></td>");
        }
        out.println("<th><button href=# class='btn btn-warning btn-sm' onclick=FormUpDtsP(" + index + ",'direccion','form','pacs') ><span><img src='images/pencil.png'></span></button></th>"
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
