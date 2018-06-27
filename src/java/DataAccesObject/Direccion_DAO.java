package DataAccesObject;

import DataBase.Conexion;
import DataTransferObject.Direccion_DTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author Carlos Rivas
 */
public class Direccion_DAO {    
    public int RegistrarDirección(Direccion_DTO dto) {
        int id_Direccion = 0;
        try (Connection con = Conexion.getCon();) {            
            String sql = "SELECT id_Direccion from direccion WHERE id_Colonia=" + dto.getId_Colonia() + " AND Calle='" + dto.getCalle() + "' AND No_Int='" + dto.getNo_Int() + "' AND No_Ext='" + dto.getNo_Ext() + "'";
            try (PreparedStatement pstm1 = con.prepareStatement(sql);) {
                ResultSet rs = pstm1.executeQuery();
                while (rs.next()) {
                    id_Direccion = rs.getInt("id_Direccion");
                }
            }
            if (id_Direccion != 0) {
                return id_Direccion;
            } else {                
                sql = "INSERT INTO direccion VALUES(NULL,'" + dto.getId_Colonia() + "','" + dto.getCalle() + "','" + dto.getNo_Int() + "','" + dto.getNo_Ext() + "')";                
                PreparedStatement pstm = con.prepareStatement(sql);
                if (pstm.executeUpdate() == 1) {
                    pstm.close();
                    sql = "SELECT id_Direccion from direccion WHERE id_Colonia=" + dto.getId_Colonia() + " AND Calle='" + dto.getCalle() + "' AND No_Int='" + dto.getNo_Int() + "' AND No_Ext='" + dto.getNo_Ext() + "'";
                    try (PreparedStatement pstm1 = con.prepareStatement(sql);) {
                        ResultSet rs = pstm1.executeQuery();
                        while (rs.next()) {
                            id_Direccion = rs.getInt("id_Direccion");
                        }
                    }
                }                
                return id_Direccion;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }   
}
