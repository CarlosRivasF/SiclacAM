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

/**
 *
 * @author ZionSystems
 */
@WebServlet(name = "DelPac", urlPatterns = {"/DelPac"})
public class DelPac extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        try {
            response.setContentType("text/html;charset=UTF-8");
            HttpSession sesion = request.getSession();            
            List<Paciente_DTO> pacs = (List<Paciente_DTO>) sesion.getAttribute("pacs");
            String i = request.getParameter("index");
            int index = Integer.parseInt(i.trim());
            if (request.getParameter("ADelm") != null) {
                String act = request.getParameter("ADelm");
                switch (act) {
                    case "show":
                        out.print("<button href=# class='btn btn-light' onclick=FormDelPac(" + index + ",'SI')>SI</button> "
                                + "<button href=# class='btn btn-info' onclick=FormDelPac(" + index + ",'NO')>NO</button>");
                        request.getRequestDispatcher("ShowPac#pac-" + i);
                        break;
                    case "SI":
                        Paciente_DAO P = new Paciente_DAO();
                        Paciente_DTO pac = pacs.get(index);
                        P.EliminarPac(pac);
                        pacs.remove(index);
                        response.sendRedirect("ShowPac");
                        break;
                    case "NO":
                        out.print("<button href=# class='btn btn-danger' onclick=FormDelPac(" + index + ",'show')><span><img src='images/trash.png'></span></button>");
                        request.getRequestDispatcher("ShowPac#pac-" + i);
                        break;
                }
            } else {
                out.print("null");
                request.getRequestDispatcher("ShowPac#pac-" + i);
            }
        } catch (IOException ex) {
            out.println("<br>'DelPac'<br><h1 style='color: white'>" + ex.getMessage() + "...<br>Por favor capture una imagen del error y comuniquelo de inmediato a ZionSystems</h1>");
        }
    }
}
