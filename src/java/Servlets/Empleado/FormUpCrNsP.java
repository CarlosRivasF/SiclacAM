package Servlets.Empleado;

import DataAccesObject.Empleado_DAO;
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
@WebServlet(name = "FormUpCrNsP", urlPatterns = {"/FormUpCrNsP"})
public class FormUpCrNsP extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        HttpSession sesion = request.getSession();
        int index = Integer.parseInt(request.getParameter("index").trim());
        List<Empleado_DTO> emps;
        emps = (List<Empleado_DTO>) sesion.getAttribute("emps");
        Empleado_DTO empleado = emps.get(index);
        String acc = request.getParameter("acc").trim();
        String part = request.getParameter("part").trim();
        switch (part) {
            case "datosEmp":
                if ("upd".equals(acc)) {
                    Empleado_DAO E = new Empleado_DAO();
                    empleado.setCurp(request.getParameter("curpEm"));
                    empleado.setNss(request.getParameter("NssEm"));
                    E.ActualizarCrNsEmp(empleado);
                    out.println("<table style=' text-align: center' class='table table-bordered table-hover table-sm'>"
                            + "<tr class='table-info' style='color: black'>"
                            + "<th >CURP</th>"
                            + "<th >NSS</th>"
                            + "<th >Modificar</th></tr><tr>"
                            + "<td >" + empleado.getCurp() + "</td>"
                            + "<td >" + empleado.getNss() + "</td>"
                            + "<th><button href=# class='btn btn-warning btn-sm' onclick=FormUpCrNsP(" + index + ",'form','datosEmp') ><span><img src='images/pencil.png'></span></button></th>"
                            + "</tr></table>");
                    emps.set(index, empleado);
                } else {
                    out.println("<table style=' text-align: center' class='table table-bordered table-hover table-sm'>"
                            + "<tr class='table-info' style='color: black'>"
                            + "<th >CURP</th>"
                            + "<th >NSS</th>"
                            + "<th >Modificar</th></tr><tr>");
                    out.println("<td><input style='text-align: center' type='text' class='form-control form-control-sm' name='curpEm' value='" + empleado.getCurp() + "' id='curpEm' placeholder='CURP' required></td>"
                            + "<td><input style='text-align: center' type='text' class='form-control form-control-sm' name='NssEm' value='" + empleado.getNss() + "' id='NssEm' placeholder='NSS' required></td>"
                            + "<th><button href=# class='btn btn-success btn-sm' onclick=FormUpCrNsP(" + index + ",'upd','datosEmp') ><span><img src='images/save.png'></span></button></th>"
                            + "</tr></table>");
                }
                break;
            case "datoslab":
                if ("upd".equals(acc)) {
                    Empleado_DAO E = new Empleado_DAO();
                    empleado.setFecha_Ing(request.getParameter("fechIngr").trim());
                    empleado.setSalario_Bto(Float.parseFloat(request.getParameter("salBr").trim()));
                    E.ActualizarDatosLab(empleado);
                    out.println("<table style=' text-align: center' class='table table-bordered table-hover table-sm'>"
                            + "<tr class='table-warning' style='color: black'>"
                            + "<th colspan='3' >Datos Laborales</th></tr><tr>"
                            + "<th >Fecha Ingreso</th>"
                            + "<th >Salario</th>"
                            + "<th >Guardar</th></tr><tr>"
                            + "<td >" + empleado.getFecha_Ing() + "</td>"
                            + "<td >" + empleado.getSalario_Bto() + "</td>"
                            + "<th><button href=# class='btn btn-warning btn-sm' onclick=FormUpCrNsP(" + index + ",'form','datoslab') ><span><img src='images/pencil.png'></span></button></th>"
                            + "</tr></table>");
                    emps.set(index, empleado);
                } else {
                    out.println("<table style=' text-align: center' class='table table-bordered table-hover table-sm'>"
                            + "<tr class='table-warning' style='color: black'>"
                            + "<th colspan='3' >Datos Laborales</th></tr><tr>"
                            + "<th >Fecha Ingreso</th>"
                            + "<th >Salario</th>"
                            + "<th >Guardar</th></tr><tr>"
                            + "<td><input style='text-align: center' type='date' class='form-control form-control-sm' name='fechIngr' value='" + empleado.getFecha_Ing().trim() + "' id='fechIngr' required></td>"
                            + "<td><input style='text-align: center' type='number' step='any' class='form-control form-control-sm' name='salBr' value='" + empleado.getSalario_Bto() + "' id='salBr' required></td>"
                            + "<th><button href=# class='btn btn-success btn-sm' onclick=FormUpCrNsP(" + index + ",'upd','datoslab') ><span><img src='images/save.png'></span></button></th>"
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
