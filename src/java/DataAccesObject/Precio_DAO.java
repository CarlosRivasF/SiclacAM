package DataAccesObject;

import DataBase.Conexion;
import DataTransferObject.Precio_DTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ZionSystems
 */
public class Precio_DAO {
    public int registrarPrecio(int id_Est_Uni, Precio_DTO dto) {
        int id_Precio = 0;
        String sql = "INSERT INTO precio VALUES(null," + id_Est_Uni + ",'" + dto.getPrecio_N() + "',"
                + "" + dto.getT_Entrega_N() + ",'" + dto.getPrecio_U() + "'," + dto.getT_Entrega_U() + ")";
        
        try (Connection con = Conexion.getCon(); PreparedStatement pstm = con.prepareStatement(sql);) {
            if (pstm.executeUpdate() == 1) {
                pstm.close();
                sql = "SELECT id_Precio from precio WHERE id_Est_Uni=" + id_Est_Uni + " and Precio_N='" + dto.getPrecio_N() + "' AND T_Entrega_N=" + dto.getT_Entrega_N() + " AND Precio_U='" + dto.getPrecio_U() + "' AND T_Entrega_U=" + dto.getT_Entrega_U() + "";
                try (PreparedStatement pstm1 = con.prepareStatement(sql);
                        ResultSet rs = pstm1.executeQuery();) {
                    while (rs.next()) {
                        id_Precio = rs.getInt("id_Precio");
                    }
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(Configuracion_DAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return id_Precio;
    }
}
