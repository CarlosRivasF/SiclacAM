package Servlets.Orden;

import DataAccesObject.Cotizacion_DAO;
import DataAccesObject.Paciente_DAO;
import DataAccesObject.Persona_DAO;
import DataBase.Fecha;
import DataTransferObject.Cotizacion_DTO;
import DataTransferObject.Det_Orden_DTO;
import DataTransferObject.Orden_DTO;
import DataTransferObject.Paciente_DTO;
import DataTransferObject.Persona_DTO;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
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
@WebServlet(name = "CastToOrden", urlPatterns = {"/CastToOrden"})
public class CastToOrden extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        HttpSession sesion = request.getSession();

        Date fac = new Date();
        Fecha f = new Fecha();
        f.setHora(fac);
        String codeBar = request.getParameter("Id_Cot").trim();
        String[] bar = codeBar.split("-");

        int id_Cotizacion = Integer.parseInt(bar[0]);
        Cotizacion_DAO C = new Cotizacion_DAO();
        Cotizacion_DTO cot = C.getCotizacion(id_Cotizacion);

        if (f.IsValid(cot.getFecha_Exp().trim())) {

            Orden_DTO Orden = new Orden_DTO();

            Orden.setUnidad(cot.getUnidad());
            Orden.setPaciente(cot.getPaciente());
            Orden.setMedico(cot.getMedico());
            Persona_DAO P = new Persona_DAO();
            Persona_DTO por = P.getPersona(Integer.parseInt(sesion.getAttribute("persona").toString().trim()));
            Orden.setEmpleado(por);
            Orden.setFecha(f.getFechaActual());
            Orden.setHora(f.getHoraMas(6));
            Orden.setMontoRestante(Float.parseFloat("0"));
            Orden.setMontoPagado(Float.parseFloat("0"));
            Orden.setEstado("Pendiente");
            Orden.setConvenio(cot.getConvenio());
            List<Det_Orden_DTO> Det_Orden = new ArrayList<>();
            cot.getDet_Cot().stream().map((dtc) -> {
                Det_Orden_DTO detor = new Det_Orden_DTO();
                detor.setEstudio(dtc.getEstudio());
                detor.setDescuento(dtc.getDescuento());
                Float p = Float.parseFloat("0");
                detor.setT_Entrega(dtc.getT_Entrega());
                if (detor.getT_Entrega().equals("Normal")) {
                    detor.setFecha_Entrega(f.SumarDias(detor.getEstudio().getPrecio().getT_Entrega_N()));
                    p = detor.getEstudio().getPrecio().getPrecio_N();
                } else if (detor.getT_Entrega().equals("Urgente")) {
                    detor.setFecha_Entrega(f.SumarDias(detor.getEstudio().getPrecio().getT_Entrega_U()));
                    p = detor.getEstudio().getPrecio().getPrecio_U();
                }
                detor.setSubtotal(p - ((detor.getDescuento() * p) / 100));
                return detor;
            }).forEachOrdered((detor) -> {
                Det_Orden.add(detor);
            });
            Orden.setDet_Orden(Det_Orden);
            sesion.setAttribute("Orden", Orden);
            try {
                request.getRequestDispatcher("Menu/Orden/AddEsts.jsp").forward(request, response);
            } catch (ServletException | IOException ex) {
                //si existe una excepcion es porque faltan datos del  paciente.
                Paciente_DAO Pa = new Paciente_DAO();
                List<Paciente_DTO> pacs = Pa.getPacientes();
                sesion.setAttribute("pacs", pacs);
                int index = 0;
                for (Paciente_DTO pac : pacs) {
                    if (pac.getId_Paciente() == cot.getPaciente().getId_Paciente()) {
                        index=pacs.indexOf(pac);
                    }
                }
                request.getRequestDispatcher("ShDetPac?index=" + index + " &modeP=rellenar").forward(request, response);
            }
        } else {
            String msg = "La cotización expiró";
            sesion.setAttribute("msg", msg);
            response.sendRedirect("Ordenes.jsp");
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
