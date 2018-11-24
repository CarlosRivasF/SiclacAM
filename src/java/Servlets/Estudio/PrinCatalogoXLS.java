/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets.Estudio;

import DataAccesObject.Estudio_DAO;
import DataBase.Conexion;
import DataTransferObject.Estudio_DTO;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

/**
 *
 * @author KODE
 */
@WebServlet(name = "PrinCatalogoXLS", urlPatterns = {"/PrinCatXLS"})
public class PrinCatalogoXLS extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-Disposition", "attachment; filename=sampleName.xls");
        HttpSession sesion = request.getSession();
        int id_Unidad;

        id_Unidad = Integer.parseInt(request.getParameter("Id_Unidad"));
        List<Estudio_DTO> Catalogo;

        Estudio_DAO E = new Estudio_DAO();
        Catalogo = E.getEstudiosByUnidad(id_Unidad);//recupera lista de estudios por unidad
        try {
            Workbook libro = new HSSFWorkbook();
            int id_Tipo_Estudio = 0;
            for (Estudio_DTO dto : Catalogo) {
                try {
                    Sheet hoja = null;
                    if (id_Tipo_Estudio != dto.getId_Tipo_Estudio()) {
                        hoja = libro.createSheet(dto.getNombre_Tipo_Estudio());
                    }
                    int f = 0;
                    System.out.println("Imprimiendo Columnas");
                    try (Connection con = Conexion.getCon();
                            PreparedStatement pstm = con.prepareStatement("select Nombre,Ap_Paterno,Ap_Materno from persona");
                            ResultSet rs = pstm.executeQuery();) {
                        if (rs.first()) { //filas                                   
                            Row fila = hoja.createRow(f);
                            for (int c = 1; c <= rs.getMetaData().getColumnCount(); c++) { //columnas                    
                                Cell celda = fila.createCell(c - 1);
                                celda.setCellValue(rs.getMetaData().getColumnName(c).toUpperCase());
                            }
                        }
                    } catch (SQLException ex) {
                        System.out.println("SQLException: " + ex.getMessage());
                    }
                    System.out.println("Imprimiendo datos");
                    try (Connection con = Conexion.getCon();
                            PreparedStatement pstm = con.prepareStatement("select Nombre,Ap_Paterno,Ap_Materno from persona");
                            ResultSet rc = pstm.executeQuery();) {
                        while (rc.next()) { //filas               
                            f++;
                            Row fila = hoja.createRow(f);
                            for (int c = 1; c <= rc.getMetaData().getColumnCount(); c++) { //columnas                    
                                Cell celda = fila.createCell(c - 1);
                                celda.setCellValue(rc.getString(c).toLowerCase());
                            }
                        }
                    } catch (SQLException ex) {
                        System.out.println("SQLException: " + ex.getMessage());
                    }
                } catch (Exception e) {
                }

            }
            libro.write(response.getOutputStream());
            response.getOutputStream().close();
        } catch (IOException ex) {
            System.out.println("IOException: " + ex.getMessage());
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
