/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataAccesObject;

import DataBase.Conexion;
import DataTransferObject.Det_Prom_DTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author ZionSystem id_Det_Prom id_Promocion id_Est_Uni Descuento Tipo_Entrega
 */
public class Det_Prom_DAO {

    public int registrarDetor(int id_Promocion, Det_Prom_DTO detor) {
        int id_Det_Prom = 0;
        try (Connection con = Conexion.getCon();) {
            System.out.println("REGISTRANDO DETALLES DE PROMOCION");
            String sql = "INSERT INTO det_prom VALUES(null,"
                    + "" + id_Promocion + ","
                    + "" + detor.getEstudio().getId_Est_Uni() + ","
                    + "" + detor.getDescuento() + ","
                    + "'" + detor.getT_Entrega() + "')";
            System.out.println(sql);
            try (PreparedStatement pstm = con.prepareStatement(sql);) {
                pstm.executeUpdate();
            }
            sql = "select id_Det_Prom from det_prom where id_Promocion=" + id_Promocion + " and id_Est_Uni=" + detor.getEstudio().getId_Est_Uni() + ""
                    + " and Descuento=" + detor.getDescuento() + ""
                    + " and Tipo_Entrega='" + detor.getT_Entrega() + "'";
            System.out.println(sql);
            try (PreparedStatement pstm = con.prepareStatement(sql); ResultSet rs = pstm.executeQuery()) {
                while (rs.next()) {
                    id_Det_Prom = rs.getInt("id_Det_Prom");
                }
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return id_Det_Prom;
    }

    public int EliminarMaterial(int id_Det_Prom) {
        int rp;
        try (Connection con = Conexion.getCon();) {
            String sql = "DELETE from det_prom WHERE id_Det_Prom=" + id_Det_Prom + "";
            try (PreparedStatement pstm = con.prepareStatement(sql);) {
                rp = pstm.executeUpdate();
                pstm.close();
            }
            return rp;
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

}
