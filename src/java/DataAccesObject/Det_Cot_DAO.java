package DataAccesObject;

import DataBase.Conexion;
import DataTransferObject.Det_Cot_DTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author ZionSystems
 */
public class Det_Cot_DAO {

    public int RegistrarCotizacion(Det_Cot_DTO dto) {
        int id_Det_Cot = 0;
        String sql = "INSERT INTO det_cot VALUES(NULL," + dto.getId_Cotizacion() + "," + dto.getEstudio().getId_Est_Uni() + ","
                + "'" + dto.getDescuento() + "','" + dto.getT_Entrega() + "')";
        System.out.println(sql);
        try (Connection con = Conexion.getCon();
                PreparedStatement pstm = con.prepareStatement(sql);) {
            if (pstm.executeUpdate() == 1) {
                sql = "SELECT id_Det_Cot from det_cot WHERE id_Cotizacion=" + dto.getId_Cotizacion() + " AND id_Est_Uni=" + dto.getEstudio().getId_Est_Uni() + ""
                        + " AND Descuento='" + dto.getDescuento() + "' AND Tipo_Entrega='" + dto.getT_Entrega() + "'";
                System.out.println(sql);
                try (PreparedStatement pstm1 = con.prepareStatement(sql);
                        ResultSet rs = pstm1.executeQuery();) {
                    while (rs.next()) {
                        id_Det_Cot = rs.getInt("id_Det_Cot");
                    }
                }
            }
            con.close();
            return id_Det_Cot;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
