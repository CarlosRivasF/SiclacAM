package DataAccesObject;

import DataBase.Conexion;
import DataTransferObject.Det_Orden_DTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author ZionSystems id_Det_Orden
id_Orden
id_Est_Uni
Descuento
Fecha_Entrega
Tipo_Entrega
Subtotal
 *
 */
public class Det_Orden_DAO {

    public int registrarDetor(int id_Orden, Det_Orden_DTO detor) {
        int id_Det_Orden = 0;
        try (Connection con = Conexion.getCon();) {
            System.out.println("REGISTRANDO DETALLES DE ORDEN");
            String sql = "INSERT INTO det_orden VALUES(null,"
                    + "" + id_Orden + ","
                    + "" + detor.getEstudio().getId_Est_Uni() + ","
                    + "'" + detor.getDescuento() + "',"
                    + "'" + detor.getSobrecargo()+ "',"
                    + "'" + detor.getFecha_Entrega() + "',"
                    + "'" + detor.getT_Entrega() + "',"
                    + "'" + detor.getSubtotal() + "')";
            System.out.println(sql);
            try (PreparedStatement pstm = con.prepareStatement(sql);) {
                pstm.executeUpdate();
            }
            sql = "select id_Det_Orden from det_orden where id_Orden=" + id_Orden + " and id_Est_Uni=" + detor.getEstudio().getId_Est_Uni() + ""
                    + " and Descuento='" + detor.getDescuento() + "' and Sobrecargo='" + detor.getSobrecargo() + "' and Fecha_Entrega='" + detor.getFecha_Entrega() + "'"
                    + " and Tipo_Entrega='" + detor.getT_Entrega() + "'  and montoRe='" + detor.getSubtotal() + "'";
            System.out.println(sql);
            try (PreparedStatement pstm = con.prepareStatement(sql); ResultSet rs = pstm.executeQuery()) {
                while (rs.next()) {
                    id_Det_Orden = rs.getInt("id_Det_Orden");
                }
            }
        } catch (SQLException ex) {
        }
        return id_Det_Orden;
    }

}
