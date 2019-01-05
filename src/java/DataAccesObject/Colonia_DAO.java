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
        String sql = "SELECT id_Colonia,Nombre_Colonia FROM colonia WHERE id_CP=?";
        try (Connection con = Conexion.getCon();
                PreparedStatement pstm = con.prepareStatement(sql);) {
            pstm.setInt(1, id_CP);
            List<Colonia_DTO> lst = new ArrayList<>();
            try (ResultSet rs = pstm.executeQuery();) {
                while (rs.next()) {
                    Colonia_DTO colonia = new Colonia_DTO();
                    colonia.setId_Colonia(rs.getInt("id_Colonia"));
                    colonia.setNombre_Colonia(rs.getString("Nombre_Colonia"));
                    lst.add(colonia);
                }
                con.close();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            return lst;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String getCol(int id_Colonia) {
        String sql = "SELECT Nombre_Colonia from colonia where id_Colonia=?";
        try (Connection con = Conexion.getCon();
                PreparedStatement pstm = con.prepareStatement(sql);) {
            pstm.setInt(1, id_Colonia);
            String colonia = "";
            try (ResultSet rs = pstm.executeQuery();) {
                while (rs.next()) {
                    colonia = (rs.getString("Nombre_Colonia"));
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            return colonia;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
