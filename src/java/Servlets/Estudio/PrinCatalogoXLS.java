/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets.Estudio;

import DataAccesObject.Estudio_DAO;
import DataTransferObject.Estudio_DTO;
import java.io.IOException;
import java.util.ArrayList;
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

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-Disposition", "attachment; filename=CatExcel.xls");
        HttpSession sesion = request.getSession();
        int id_Unidad;

        id_Unidad = Integer.parseInt(request.getParameter("Id_Unidad"));
        List<Estudio_DTO> Catalogo;
        List<Estudio_DTO> Catalogo2 = new ArrayList<>();

        Estudio_DAO E = new Estudio_DAO();
        Catalogo = E.getEstudiosByUnidad(id_Unidad);
        for (int i = 1; i <= 8; i++) {
            for (Estudio_DTO dto : Catalogo) {
                if (dto.getId_Tipo_Estudio() == i) {
                    Catalogo2.add(dto);
                }
            }
        }
        Catalogo.clear();
        System.out.println("SIZE: " + Catalogo2.size());
        try {
            Sheet hoja = null;
            int f = 0;
            Workbook libro = new HSSFWorkbook();
            int id_Tipo_Estudio = 0;
            for (Estudio_DTO dto : Catalogo2) {
                try {
                    if (id_Tipo_Estudio != dto.getId_Tipo_Estudio()) {
                        id_Tipo_Estudio = dto.getId_Tipo_Estudio();
                        hoja = libro.createSheet(dto.getNombre_Tipo_Estudio());
                        System.out.println("Nueva Hoja para: " + dto.getNombre_Tipo_Estudio().toLowerCase());
                        f = 0;
                        Row Head = hoja.createRow(f);//Fila de Cabecera
                        //columnas                    
                        Cell cellHClave = Head.createCell(0);
                        cellHClave.setCellValue("Clave");
                        Cell cellHnombre = Head.createCell(1);
                        cellHnombre.setCellValue("Nombre de Estudio");
                        Cell cellHPrecioN = Head.createCell(2);
                        cellHPrecioN.setCellValue("Precio Normal");
                        Cell cellHPrecioU = Head.createCell(3);
                        cellHPrecioU.setCellValue("Precio Urgente");
                    }
                    //filas               
                    f++;
                    Row fila = hoja.createRow(f);
                    //columnas                    
                    Cell cellCClave = fila.createCell(0);
                    cellCClave.setCellValue(dto.getClave_Estudio());
                    Cell cellCnombre = fila.createCell(1);
                    cellCnombre.setCellValue(dto.getNombre_Estudio());
                    Cell cellCPrecioN = fila.createCell(2);
                    cellCPrecioN.setCellValue(dto.getPrecio().getPrecio_N());
                    Cell cellCPrecioU = fila.createCell(3);
                    cellCPrecioU.setCellValue(dto.getPrecio().getPrecio_U());
                } catch (Exception e) {
                    e.getMessage();
                }

            }
            libro.write(response.getOutputStream());
            response.getOutputStream().close();
        } catch (IOException ex) {
            Catalogo2.clear();
            System.out.println("IOException: " + ex.getMessage());
        }
        Catalogo2.clear();
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
