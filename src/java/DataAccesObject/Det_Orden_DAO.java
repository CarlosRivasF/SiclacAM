package DataAccesObject;

import DataBase.Conexion;
import DataTransferObject.Det_Orden_DTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ZionSystems id_Det_Orden id_Orden id_Est_Uni Descuento Fecha_Entrega
 * Tipo_Entrega Subtotal
 *
 */
public class Det_Orden_DAO {

    public int registrarDetor(int id_Orden, Det_Orden_DTO detor) {
        int id_Det_Orden = 0;
        try (Connection con = Conexion.getCon();) {
            //System.out.println("REGISTRANDO DETALLES DE ORDEN");
            String sql = "INSERT INTO det_orden VALUES(null,"
                    + "" + id_Orden + ","
                    + "" + detor.getEstudio().getId_Est_Uni() + ","
                    + "'" + detor.getDescuento() + "',"
                    + "'" + detor.getSobrecargo() + "',"
                    + "'" + detor.getFecha_Entrega() + "',"
                    + "'" + detor.getT_Entrega() + "',"
                    + "'" + detor.getSubtotal() + "')";

            try (PreparedStatement pstm = con.prepareStatement(sql);) {
                pstm.executeUpdate();
            }
            sql = "select id_Det_Orden from det_orden where id_Orden=" + id_Orden + " and id_Est_Uni=" + detor.getEstudio().getId_Est_Uni() + ""
                    + " and Descuento='" + detor.getDescuento() + "' and Sobrecargo='" + detor.getSobrecargo() + "' and Fecha_Entrega='" + detor.getFecha_Entrega() + "'"
                    + " and Tipo_Entrega='" + detor.getT_Entrega() + "'  and montoRe='" + detor.getSubtotal() + "'";

            try (PreparedStatement pstm = con.prepareStatement(sql); ResultSet rs = pstm.executeQuery()) {
                while (rs.next()) {
                    id_Det_Orden = rs.getInt("id_Det_Orden");
                }
            }
        } catch (SQLException ex) {
        }
        return id_Det_Orden;
    }

    public List<Det_Orden_DTO> comparaDetors(List<Det_Orden_DTO> detor, int id_Orden) {
        List<Det_Orden_DTO> lst = new ArrayList<>();
        List<Integer> ixs = new ArrayList<>();
        String sql = "SELECT id_Det_Orden FROM det_orden WHERE  id_Orden=?";
        List<Det_Orden_DTO> lst2 = new ArrayList<>();
        try (Connection con = Conexion.getCon();) {
            try (PreparedStatement pstm = con.prepareStatement(sql);) {
                pstm.setInt(1, id_Orden);
                try (ResultSet rs = pstm.executeQuery()) {
                    while (rs.next()) {
                        int id_Det_Ord = rs.getInt("id_Det_Orden");
                        System.out.println("id_Det_Orden: " + id_Det_Ord);
                        ixs.add(id_Det_Ord);
                    }
                }
            }
            for (Det_Orden_DTO dto : detor) {
                for (Integer i : ixs) {
                    if (dto.getId_det_orden() != i) {
                        registrarDetor(id_Orden, dto);
                        System.out.println("REISTRANDO NUEVO DETALLE DE ORDEN");
                    }
                }
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return lst;
    }

    public void DelteDet_Orden(int id_Det_Orden) {
        String sql = "Delete from det_orden "
                + "where id_Det_Orden=?";
        try (Connection con = Conexion.getCon();) {
            try (PreparedStatement pstm = con.prepareStatement(sql);) {
                pstm.setInt(1, id_Det_Orden);
                pstm.executeUpdate();
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

}
