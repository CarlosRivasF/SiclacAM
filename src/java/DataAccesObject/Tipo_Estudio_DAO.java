package DataAccesObject;

import DataBase.Conexion;
import DataTransferObject.Tipo_Estudio_DTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ZionSystems
 */
public class Tipo_Estudio_DAO {

    public List<Tipo_Estudio_DTO> getTipo_Estudios() {
        List<Tipo_Estudio_DTO> tipos;
        try (Connection con = Conexion.getCon()) {
                        String sql = "SELECT id_Tipo_Estudio,Nombre_Tipo_Estudio FROM tipo_estudio";
            try (PreparedStatement pstm = con.prepareStatement(sql);
                    ResultSet rs = pstm.executeQuery();) {
                tipos = new ArrayList<>();
                while (rs.next()) {
                    Tipo_Estudio_DTO tipo = new Tipo_Estudio_DTO();
                    tipo.setId_Tipo_Estudio(rs.getInt("id_Tipo_Estudio"));
                    tipo.setNombre_Tipo_Estudio(rs.getString("Nombre_Tipo_Estudio"));
                    tipos.add(tipo);
                }
            }
            return tipos;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
