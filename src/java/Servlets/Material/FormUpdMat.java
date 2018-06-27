package Servlets.Material;

import DataTransferObject.Material_DTO;
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
@WebServlet(name = "FormUpdMat", urlPatterns = {"/FormUpdMat"})
public class FormUpdMat extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession sesion = request.getSession();
        List<Material_DTO> matsU = (List<Material_DTO>) sesion.getAttribute("matsU");
        int index = Integer.parseInt(request.getParameter("index").trim());
        Material_DTO mat = matsU.get(index);
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.print("<div class='nav-scroller bg-white box-shadow'>"
                + "<nav class='nav nav-underline'>"
                + "<a class='nav-link' href='#' onclick=mostrarForm('" + request.getContextPath() + "/Menu/Material/Registro.jsp');>Nueva Material</a>"
                + "<a class='nav-link active' href='#' onclick=mostrarForm('ShowMats'); style='color: blue'><ins>Ver Materiales</ins></a>"
                + "</nav>"
                + "</div>"
                + "<div>");
        out.print("<div class='container-fluid' style='color: white'><br>"
                + "<form class='needs-validation' novalidate name='fors' method='POST' action='UpdMat'>"
                + "<input type='hidden' name='index' id='index' value='" + index + "'>"
                + "<h6 style='text-align: center'>Datos del Material</h6>"
                + "<hr class='mb-4'>"
                + "<div class='form-row'>"
                + "<div class='col-md-6 mb-3'>"
                + "<label >Nombre del Material</label>"
                + "<input style='text-align: center' type='text' class='form-control' name='nombre_material' value='" + mat.getNombre_Material() + "' id='nombre_material' placeholder='Nombre del Material' required readonly>"
                + "<div class='invalid-feedback'>"
                + "El nombre del material es obligatorio."
                + "</div>"
                + "</div>"
                + "<div class='col mb-3'>"
                + "<label  >Cantidad</label>"
                + "<input style='text-align: center' type='number' onkeypress='return soloNumeros(event)' min='1' class='form-control' value='" + mat.getCantidad() + "' name='cantidad' id='cantidad' placeholder='Cantidad' required>"
                + "<div class='invalid-feedback'>"
                + "Se requiere una cantidad mayor a 0."
                + "</div>"
                + "</div>"
                + "<div class='col mb-3'>"
                + "<label  >Precio</label>"
                + "<input style='text-align: center' type='number'step='any' onkeypress='return soloNumeros(event)' class='form-control' name='precio' value='" + mat.getPrecio() + "' id='precio' placeholder='Precio' required>"
                + "<div class='invalid-feedback'>"
                + "Se requiere un precio valido."
                + "</div>"
                + "</div>"
                + "</div>"
                + "<hr class='mb-4'>"
                + "<input class='btn btn-primary btn-lg btn-block' type='button' id='sendForm' onclick='x();' name='sendForm' value='Actualizar Material'>"
                + "<br>"
                + "</form>"
                + "</div>");
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
