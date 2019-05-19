package Servlets.Usuario;

import DataAccesObject.Usuario_DAO;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author ZionS
 */
@WebServlet(name = "LogOut", urlPatterns = {"/LogOut"})
public class LogOut extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession sesion = request.getSession();
        sesion.removeAttribute("user");
        sesion.removeAttribute("persona");
        sesion.removeAttribute("unidad");
        sesion.invalidate();
        Usuario_DAO.nSesion--;
//        System.out.println("*-*-*-*-* SESION: " + Usuario_DAO.nSesion + " de Usuario:" + Usuario + ", Password:" + Contraseña);
        response.sendRedirect("" + request.getContextPath() + "");
    }
}
