package DataBase;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

/**
 *
 * @author ZionSystems
 */
@WebServlet(name = "Sample", urlPatterns = {"/Sample"})
public class Sample extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-Disposition", "attachment; filename=sampleName.xls");
        try {
            Workbook libro = new HSSFWorkbook();
            for (int i = 1; i <= 10; i++) {
                try {
                    Sheet hoja = libro.createSheet("Mi hoja de trabajo " + i);
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
