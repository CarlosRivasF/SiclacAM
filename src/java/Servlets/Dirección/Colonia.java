package Servlets.Dirección;

import DataAccesObject.CP_DAO;
import DataAccesObject.Colonia_DAO;
import DataTransferObject.Colonia_DTO;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author ZionSystems
 */
@WebServlet(name = "Col", urlPatterns = {"/Col"})
public class Colonia extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        CP_DAO CP = new CP_DAO();
        Colonia_DAO C = new Colonia_DAO();
        String cp = request.getParameter("c_p");
        int Idc_p = CP.getIdCP(cp);
        List<Colonia_DTO> lst = C.getColonias(Idc_p);
        out.print("<label for='colonia' class='sr-only'>Colonia</label>"
                + "<select class='custom-select d-block w-100' id='colonia' name='colonia' required>");
        lst.forEach((colonia) -> {
            out.print("<option value=" + colonia.getId_Colonia() + ">"
                    + colonia.getNombre_Colonia()
                    + "</option>");
        });
        out.print("</select>"
                + "<div class='invalid-feedback' style='width: 100%;'>"
                + "Por favor seleccione un país válido."
                + "</div>");
    }
}
