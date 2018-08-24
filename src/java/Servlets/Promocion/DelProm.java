package Servlets.Promocion;

import DataAccesObject.Promocion_DAO;
import DataTransferObject.Promocion_DTO;
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
 * @author ZionSystem
 */
@WebServlet(name = "DelProm", urlPatterns = {"/DelProm"})
public class DelProm extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        try {
            response.setContentType("text/html;charset=UTF-8");
            HttpSession sesion = request.getSession();
            int id_unidad = Integer.parseInt(sesion.getAttribute("unidad").toString().trim());
            Promocion_DAO P = new Promocion_DAO();
            List<Promocion_DTO> proms = P.getPromociones(id_unidad);
            String i = request.getParameter("index");
            int index = Integer.parseInt(i.trim());
            if (request.getParameter("ADelm") != null) {
                String act = request.getParameter("ADelm").trim();
                switch (act) {
                    case "show":
                        out.print("<button href=# class='btn btn-light' onclick=FormDelProm(" + index + ",'SI')>SI</button> "
                                + "<button href=# class='btn btn-info' onclick=FormDelProm(" + index + ",'NO')>NO</button>");
                        request.getRequestDispatcher("ShowProms#pac-" + i);
                        break;
                    case "SI":
                        Promocion_DTO prom = proms.get(index);
                        P.EliminarPromocion(prom.getId_Promocion());                        
                        response.sendRedirect("ShowProms");
                        break;
                    case "NO":
                        out.print("<button href=# class='btn btn-danger' onclick=FormDelProm(" + index + ",'show')><span><img src='images/trash.png'></span></button>");
                        request.getRequestDispatcher("ShowProms#pac-" + i);
                        break;
                }
            } else {
                out.print("null");
                request.getRequestDispatcher("ShowProms#pac-" + i);
            }
        } catch (IOException ex) {
            out.println("<br>'DelProm'<br><h1 style='color: white'>" + ex.getMessage() + "...<br>Por favor capture una imagen del error y comuniquelo de inmediato a ZionSystems</h1>");
        }
    }
}
