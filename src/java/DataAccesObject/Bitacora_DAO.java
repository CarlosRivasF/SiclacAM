/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataAccesObject;

import DataBase.Conexion;
import DataBase.Util;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

/**
 *
 * @author Carlos Rivas
 */
public class Bitacora_DAO {

    public int RegistraBitacora(String query, int id_Reg_Table, int id_Persona) {
        int id_Transaccion=0;
        String Accion = query.substring(0, 6).toUpperCase();
        String Tabla = "";
        switch (Accion) {
            case "INSERT":
                Tabla = query.substring(10, 20).toUpperCase();
                break;
            case "UPDATE":
                Tabla = query.substring(6, 16).toUpperCase();
                break;
            case "DELETE":
                Tabla = query.substring(10, 20).toUpperCase();
                break;
        }
        Date fac = new Date();
        Util f = new Util();
        f.setHora(fac);
        String hora = f.getHoraMas(Util.getHrBD());
        String fecha = f.getFechaActual();

        String sql = "INSERT INTO bitacora values (?,?,?,?,?,?,?,?)";
        try (Connection con = Conexion.getCon();) {
            try (PreparedStatement pstm = con.prepareStatement(sql);) {
                pstm.setString(1, Accion);
                pstm.setString(2, Tabla);
                pstm.setString(3, query);
                pstm.setInt(4, id_Persona);
                pstm.setInt(5, id_Reg_Table);
                pstm.setString(6, hora);
                pstm.setString(7, fecha);
                pstm.setInt(8, 0);
                pstm.executeUpdate();
            }
            sql = "select id_Transaccion from bitacora where Accion=" + Accion + " AND Query=" + query + " and Fecha=" + fecha + "";
            try (PreparedStatement pstm = con.prepareStatement(sql); ResultSet rs = pstm.executeQuery()) {
                while (rs.next()) {
                    id_Transaccion = (rs.getInt("id_Transaccion"));
                }
            }
        } catch (SQLException ex) {
            System.out.println("ERROR DE REGISTRO EN BITACORA Exception:" + ex.getMessage());
            System.out.println("ERROR DE REGISTRO EN BITACORA Query:" + query);
        }
        return id_Transaccion;
    }

    public void UpdateStatusBitacora(int id_Transaccion) {
        String sql = "UPDATE bitacora "
                + "set Status=? "
                + "where id_Transaccion=?";
        try (Connection con = Conexion.getCon();) {
            try (PreparedStatement pstm = con.prepareStatement(sql);) {
                pstm.setInt(1, 1);
                pstm.setInt(2, id_Transaccion);
                pstm.executeUpdate();
            }
        } catch (SQLException ex) {
            System.out.println("ERROR UpdateStatusBitacora Exception:" + ex.getMessage());
            System.out.println("ERROR UpdateStatusBitacora id_Transaccion:" + id_Transaccion);
        }
    }

}
