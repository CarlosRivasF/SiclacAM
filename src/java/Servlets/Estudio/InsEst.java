package Servlets.Estudio;

import DataAccesObject.Configuracion_DAO;
import DataAccesObject.Est_Mat_DAO;
import DataAccesObject.Estudio_DAO;
import DataAccesObject.Precio_DAO;
import DataTransferObject.Configuracion_DTO;
import DataTransferObject.Est_Mat_DTO;
import DataTransferObject.Estudio_DTO;
import DataTransferObject.Precio_DTO;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Carlos Rivas
 */
@WebServlet(name = "InsEst", urlPatterns = {"/InsEst"})
public class InsEst extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession sesion = request.getSession();
        if (sesion.getAttribute("user") != null && sesion.getAttribute("unidad") != null) {
            Estudio_DAO E = new Estudio_DAO();
            List<Estudio_DTO> ests;
            int id_unidad = Integer.parseInt(sesion.getAttribute("unidad").toString().trim());
            if (sesion.getAttribute("ests") != null) {
                ests = (List<Estudio_DTO>) sesion.getAttribute("ests");
            } else {
                ests = E.getEstudiosByUnidad(id_unidad);
                sesion.setAttribute("ests", ests);
            }
            response.setContentType("text/html;charset=UTF-8");

            Estudio_DTO estudio = new Estudio_DTO();
            Precio_DTO precio = new Precio_DTO();
            Precio_DAO P = new Precio_DAO();

            List<Configuracion_DTO> cnfs = new ArrayList<>();
            Configuracion_DTO conf = new Configuracion_DTO();
            Configuracion_DAO CN = new Configuracion_DAO();

            List<Est_Mat_DTO> mts = new ArrayList<>();
            Est_Mat_DTO mt = new Est_Mat_DTO();
            Est_Mat_DAO M = new Est_Mat_DAO();

            try {
                estudio.setNombre_Estudio(request.getParameter("nombre_estudio"));
                estudio.setClave_Estudio(request.getParameter("clave_estudio"));
                estudio.setMetodo(request.getParameter("Metodo"));
                System.out.println(estudio.getMetodo());
                estudio.setPreparacion(request.getParameter("preparacion"));
                estudio.setUtilidad(request.getParameter("utilidad"));
                estudio.setId_Tipo_Estudio(Integer.parseInt(request.getParameter("Tipo_Estudio").trim()));

                precio.setT_Entrega_N(Integer.parseInt(request.getParameter("dias_n").trim()));
                precio.setPrecio_N(Float.parseFloat(request.getParameter("precio_n")));
                if (request.getParameter("dias_u") != null && request.getParameter("precio_u") != null
                        || !"".equals(request.getParameter("dias_u")) && !"".equals(request.getParameter("precio_u"))) {
                    precio.setT_Entrega_U(Integer.parseInt(request.getParameter("dias_u").trim()));
                    precio.setPrecio_U(Float.parseFloat(request.getParameter("precio_u")));
                }
                
                String ctrl_est =request.getParameter("ctrl_est");
                estudio.setCtrl_est(ctrl_est.trim());
                if("Referenciado".equals(estudio.getCtrl_est())){                
                int p=Integer.parseInt(request.getParameter("porc").trim());
                estudio.setPorcEst(p);
                }   

                if (sesion.getAttribute("nconf") != null) {
                    conf.setDescripcion(request.getParameter("desc"));
                    conf.setSexo(request.getParameter("sexo"));
                    conf.setValor_min(request.getParameter("min"));
                    conf.setValor_MAX(request.getParameter("max"));
                    conf.setUniddes(request.getParameter("unidades"));
                    cnfs.add(conf);
                    int nc = Integer.parseInt(sesion.getAttribute("nconf").toString().trim());
                    for (int i = 1; i <= nc; i++) {
                        if (request.getParameter("desc" + i) == null) {
                            continue;
                        }
                        Configuracion_DTO dto = new Configuracion_DTO();
                        dto.setDescripcion(request.getParameter("desc" + i));
                        dto.setSexo(request.getParameter("sexo" + i));
                        dto.setValor_min(request.getParameter("min" + i));
                        dto.setValor_MAX(request.getParameter("max" + i));
                        dto.setUniddes(request.getParameter("unidades" + i));
                        cnfs.add(dto);
                    }
                } else {
                    conf.setDescripcion(request.getParameter("desc"));
                    conf.setSexo(request.getParameter("sexo"));
                    conf.setValor_min(request.getParameter("min"));
                    conf.setValor_MAX(request.getParameter("max"));
                    conf.setUniddes(request.getParameter("unidades"));
                }

                if (sesion.getAttribute("nmat") != null) {
                    mt.setId_Unid_Mat(Integer.parseInt(request.getParameter("mat").trim()));
                    mt.setUnidadE(request.getParameter("unidad"));
                    mt.setCantidadE(Integer.parseInt(request.getParameter("cant").trim()));
                    mt.setT_Muestra(request.getParameter("muestra"));
                    mts.add(mt);
                    int nm = Integer.parseInt(sesion.getAttribute("nmat").toString().trim());
                    for (int i = 1; i <= nm; i++) {
                        if (request.getParameter("mat" + i) == null) {
                            continue;
                        }
                        Est_Mat_DTO dto = new Est_Mat_DTO();
                        dto.setId_Unid_Mat(Integer.parseInt(request.getParameter("mat" + i).trim()));
                        dto.setUnidadE(request.getParameter("unidad" + i).trim());
                        dto.setCantidadE(Integer.parseInt(request.getParameter("cant" + i).trim()));
                        dto.setT_Muestra(request.getParameter("muestra" + i));
                        mts.add(dto);
                    }
                } else {
                    mt.setId_Unid_Mat(Integer.parseInt(request.getParameter("mat").trim()));
                    mt.setUnidadE(request.getParameter("unidad"));
                    mt.setCantidadE(Integer.parseInt(request.getParameter("cant").trim()));
                    mt.setT_Muestra(request.getParameter("muestra"));
                }

                try {
                    estudio.setId_Estudio(E.registrarEstudio(estudio));
                    if (estudio.getId_Estudio() != 0) {
                        estudio.setId_Est_Uni(E.registrarEst_Uni(estudio.getId_Estudio(), id_unidad));
                        if (estudio.getId_Est_Uni() != 0) {
                            precio.setId_Precio(P.registrarPrecio(estudio.getId_Est_Uni(), precio));
                            if (precio.getId_Precio() != 0) {
                                estudio.setPrecio(precio);
                                if (!cnfs.isEmpty()) {
                                    cnfs.forEach((dto) -> {
                                        dto.setId_Configuración(CN.registrarConfiguracion(dto));
                                        CN.registrarConf_Est(estudio.getId_Estudio(), dto.getId_Configuración());
                                    });
                                    estudio.setCnfs(cnfs);
                                } else {
                                    conf.setId_Configuración(CN.registrarConfiguracion(conf));
                                    CN.registrarConf_Est(estudio.getId_Estudio(), conf.getId_Configuración());
                                    cnfs.add(conf);
                                    estudio.setCnfs(cnfs);
                                }
                                if (cnfs.get(0).getId_Configuración() != 0) {
                                    if (!mts.isEmpty()) {
                                        mts.forEach((dto) -> {
                                            M.registrarMat_Est(estudio.getId_Est_Uni(), dto);
                                        });
                                        estudio.setMts(mts);
                                    } else {
                                        M.registrarMat_Est(estudio.getId_Est_Uni(), mt);
                                        mts.add(mt);
                                        estudio.setMts(mts);
                                    }
                                    String msg = "Estudio " + estudio.getNombre_Estudio() + " registrado correctamente";
                                    sesion.setAttribute("msg", msg);
                                    ests.add(estudio);
                                    response.sendRedirect("Estudios.jsp");
                                }
                            }
                        }
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            } catch (NumberFormatException e) {
                throw new RuntimeException(e);
            }
        } else {
            response.sendRedirect("" + request.getContextPath() + "");
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
