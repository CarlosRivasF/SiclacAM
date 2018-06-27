package DataAccesObject;

import DataBase.Conexion;
import DataTransferObject.Colonia_DTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Carlos Rivas
 */
public class Colonia_DAO {
    public List<Colonia_DTO> getColonias(int id_CP) {        
        String sql = "SELECT id_Colonia,Nombre_Colonia FROM colonia WHERE id_CP=" + id_CP + "";        
        try (Connection con = Conexion.getCon();
                PreparedStatement pstm = con.prepareStatement(sql);
                ResultSet rs = pstm.executeQuery();) {
            List<Colonia_DTO> lst = new ArrayList<>();
            while (rs.next()) {
                Colonia_DTO colonia = new Colonia_DTO();
                colonia.setId_Colonia(rs.getInt("id_Colonia"));
                colonia.setNombre_Colonia(rs.getString("Nombre_Colonia"));
                lst.add(colonia);
            }
            con.close();
            return lst;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public String getCol(int id_Colonia) {
        String sql = "SELECT Nombre_Colonia from colonia where id_Colonia=" + id_Colonia + "";
        try (Connection con = Conexion.getCon();
                PreparedStatement pstm = con.prepareStatement(sql);
                ResultSet rs = pstm.executeQuery();) {
            String colonia = "";
            while (rs.next()) {
                colonia = (rs.getString("Nombre_Colonia"));
            }
            return colonia;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
