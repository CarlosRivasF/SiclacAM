/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets.Estudio;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author ZionSystem
 */
@WebServlet(name = "FormAddNWConf", urlPatterns = {"/FormAddNWConf"})
public class FormAddNWConf extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        int index=Integer.parseInt(request.getParameter("index").trim());
        try (PrintWriter out = response.getWriter()) {
            out.println("<div class='form-row' >"
                    + "<div class='col-md-4 mb-3'>"
                    + "<label class='sr-only' >Descripción</label>"
                    + "<input style='text-align: center' type='text' class='form-control form-control-sm' name='desc' id='desc' placeholder='Descripción' required/>"
                    + "<div class='invalid-feedback' style='width: 100%;'>"
                    + "Descripción requerida."
                    + "</div>"
                    + "</div>"
                    + "<div class='col-4 col-md-2 mb-3'>"
                    + "<label for='sexo' class='sr-only'>Sexo</label>"
                    + "<select class='custom-select d-block w-100 form-control-sm' id='sexo' name='sexo' required>"
                    + "<option value=''>Sexo</option> "
                    + "<option value='all-Femenino'>TODOS Femenino</option> "
                    + "<option value='all-Masculino'>TODOS Masculino</option> "
                    + "<option value='all-Ambos'>TODOS Ambos</option>"
                    + "<option value='3E-Femenino'>3ra Edad Femenino</option> "
                    + "<option value='3E-Masculino'>3ra Edad Masculino</option> "
                    + "<option value='3E-Ambos'>3ra Edad Ambos</option> "
                    + "<option value='A-Femenino'>Adulto Femenino</option> "
                    + "<option value='A-Masculino'>Adulto Masculino</option> "
                    + "<option value='A-Ambos'>Adulto Ambos</option> "
                    + "<option value='N-Masculino'>Niño Femenino</option> "
                    + "<option value='N-Masculino'>Niño Masculino</option> "
                    + "<option value='N-Ambos'>Niño Ambos</option> "
                    + "<option value='RN-Masculino'>Rec Nac Femenino</option> "
                    + "<option value='RN-Masculino'>Rec Nac Masculino</option> "
                    + "<option value='RN-Ambos'>Rec Nac Ambos</option>"
                    + "</select>"
                    + "<div class='invalid-feedback' style='width: 100%;'>"
                    + "Por favor seleccione un Sexo."
                    + "</div>"
                    + "</div>"
                    + "<div class='col-4 col-md-2 mb-3'>"
                    + "<label class='sr-only' >Valor 1</label>"
                    + "<input style='text-align: center' type='text' class='form-control form-control-sm' name='min' id='min' placeholder='Valor 1' />"
                    + "<div class='invalid-feedback'>"
                    + "Valor minimo obligatorio."
                    + "</div>"
                    + "</div>"
                    + "<div class='col-4 col-md-2 mb-3'>"
                    + "<label class='sr-only' >Valor 2</label>"
                    + "<input style='text-align: center' type='text' class='form-control form-control-sm' name='max' id='max' placeholder='Valor 2' />"
                    + "<div class='invalid-feedback'>"
                    + "Valor máximo obligatorio."
                    + "</div>"
                    + "</div>"
                    + "<div class='col-10 col-md-1 mb-3'>"
                    + "<label class='sr-only' >Unidades</label>"
                    + "<input style='text-align: center' type='text' class='form-control form-control-sm' name='unidades' id='unidades' placeholder='Unidades'>"
                    + "<div class='invalid-feedback'>"
                    + "Unidades no validas"
                    + "</div>"
                    + "</div>"
                    + "<div class='col-2 col-md-1 mb-3'>"
                    + "<input type='button' class='btn btn-success btn-sm col-md-12' onclick='AddNWConf("+index+");' value='Guardar'>"
                    + "</div>"
                    + "</div>");
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
