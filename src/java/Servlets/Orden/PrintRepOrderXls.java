/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets.Orden;

import DataAccesObject.Estudio_DAO;
import DataTransferObject.Est_Mat_DTO;
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
 * @author Carlos Rivas
 */
@WebServlet(name = "PrintRepOrderXls", urlPatterns = {"/PrintRepOrderXls"})
public class PrintRepOrderXls extends HttpServlet {

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
        response.setHeader("Content-Disposition", "attachment; filename=CatExcel.xls");
        HttpSession sesion = request.getSession();
        int id_Unidad;
        int Tipo_Estudio = 0;
        if (request.getParameter("ITpoEto") != null) {
            Tipo_Estudio = Integer.parseInt(request.getParameter("ITpoEto").trim());
        }

        Boolean Det = false;

        id_Unidad = Integer.parseInt(sesion.getAttribute("unidad").toString().trim());
        List<Estudio_DTO> Catalogo;
        List<Estudio_DTO> Catalogo2 = new ArrayList<>();
        if (request.getParameter("DetCat") != null) {
            Det = true;
        }

        if (request.getParameter("ITpoEto") != null) {
            Tipo_Estudio = Integer.parseInt(request.getParameter("ITpoEto").trim());
        }

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
        if (Tipo_Estudio != 0) {
            for (Estudio_DTO dto : Catalogo2) {
                if (Tipo_Estudio != dto.getId_Tipo_Estudio()) {
                    Catalogo2.remove(dto);
                }
            }
        }
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
                        f = 0;
                        Row Head = hoja.createRow(f);//Fila de Cabecera
                        //columnas                    
                        Cell cellHClave = Head.createCell(0);
                        cellHClave.setCellValue("Clave");
                        Cell cellHnombre = Head.createCell(1);
                        cellHnombre.setCellValue("Nombre de Estudio");
                        Cell cellHDiasN = Head.createCell(2);
                        cellHDiasN.setCellValue("Dias Normal");
                        Cell cellHPrecioN = Head.createCell(3);
                        cellHPrecioN.setCellValue("Precio Normal");
                        if (Det) {
                            Cell cellHDescripcion = Head.createCell(4);
                            cellHDescripcion.setCellValue("Descripcion");
                        }
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
                    cellCPrecioN.setCellValue(dto.getPrecio().getT_Entrega_N());
                    Cell cellCPrecioU = fila.createCell(3);
                    cellCPrecioU.setCellValue(dto.getPrecio().getPrecio_N());
                    if (Det) {
                        Cell cellCDescripcion = fila.createCell(3);
                        cellCDescripcion.setCellValue(dto.getPreparacion());
                        f++;
                        Row fila2 = hoja.createRow(f);
                        Cell HMateriales = fila2.createCell(0);
                        HMateriales.setCellValue("Material");
                        Cell HPrecio = fila2.createCell(1);
                        HPrecio.setCellValue("Clave");
                        Cell HCantidad = fila2.createCell(2);
                        HCantidad.setCellValue("Cantidad");
                        for (Est_Mat_DTO mt : dto.getMts()) {
                            f++;
                            Row fila3 = hoja.createRow(f);
                            Cell Material = fila3.createCell(0);
                            Material.setCellValue(mt.getNombre_Material());
                            Cell Precio = fila3.createCell(1);
                            Precio.setCellValue(mt.getClave());
                            Cell Cantidad = fila3.createCell(2);
                            Cantidad.setCellValue(mt.getCantidadE());
                        }
                        f += 2;
                    }
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
