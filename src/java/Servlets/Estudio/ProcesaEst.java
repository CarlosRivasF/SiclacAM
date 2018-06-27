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
 * @author ZionSystems
 */
@WebServlet(name = "ProcesaEst", urlPatterns = {"/ProcesaEst"})
public class ProcesaEst extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter out = response.getWriter();        
        out.println("<h1 style='color: white'>Registrando...</h1>");        
       request.getRequestDispatcher("InsEst").forward(request, response);
    }
}
