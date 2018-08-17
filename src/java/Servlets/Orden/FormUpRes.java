package Servlets.Orden;

import DataAccesObject.Resultado_DAO;
import DataTransferObject.Configuracion_DTO;
import DataTransferObject.Det_Orden_DTO;
import DataTransferObject.Orden_DTO;
import java.io.IOException;
import java.io.PrintWriter;
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
@WebServlet(name = "FormUpRes", urlPatterns = {"/FormUpRes"})
public class FormUpRes extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String acc = request.getParameter("acc").trim();
        if (request.getParameter("index") != null) {
            HttpSession sesion = request.getSession();
            Orden_DTO Orden = (Orden_DTO) sesion.getAttribute("OrdenSh");
            int index = Integer.parseInt(request.getParameter("index").trim());
            int ixconf = Integer.parseInt(request.getParameter("ixconf").trim());
            Det_Orden_DTO det = Orden.getDet_Orden().get(index);
            Configuracion_DTO cnf = det.getEstudio().getCnfs().get(ixconf);
            Resultado_DAO R=new Resultado_DAO();
            if ("upd".equals(acc)) {
                cnf.getRes().setValor_Obtenido(request.getParameter("Resultado"));
                det.getEstudio().getCnfs().set(ixconf, cnf);                
                Orden.getDet_Orden().set(index, det);
                sesion.setAttribute("OrdenSh", Orden);
                R.updateRes(cnf.getRes());
                out.print(cnf.getRes().getValor_Obtenido());
            } else {
                out.println("<input style='text-align: center' type='text' class='form-control form-control-sm' name='valRes-" + ixconf + "' value='" + cnf.getRes().getValor_Obtenido() + "' id='valRes-" + ixconf + "' placeholder='Resultado'>");
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
