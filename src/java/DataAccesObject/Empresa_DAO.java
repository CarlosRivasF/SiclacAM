package DataAccesObject;

import DataBase.Conexion;
import DataTransferObject.Empresa_DTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author ZionSystems
 */
public class Empresa_DAO {
    public Empresa_DTO getEmpresa(int id_Empresa) {
        String sql = "SELECT * FROM empresa where id_Empresa=" + id_Empresa + "";
        try (Connection con = Conexion.getCon();
                PreparedStatement pstm = con.prepareStatement(sql);
                ResultSet rs = pstm.executeQuery();) {
            Empresa_DTO empresa = new Empresa_DTO();
            while (rs.next()) {
                empresa.setId_Empresa(rs.getInt("id_Empresa"));
                empresa.setNombre_Empresa(rs.getString("Nombre_Empresa"));
            }
            if (empresa.getId_Empresa() != 0 && empresa.getNombre_Empresa() != null) {
                return empresa;
            } else {
                return null;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public Empresa_DTO getEmpresaByUnidad(int id_Unidad) {
        Unidad_DAO U = new Unidad_DAO();
        String sql = "SELECT * FROM empresa where id_Empresa=" + U.getIdEmpresa(id_Unidad) + "";
        try (Connection con = Conexion.getCon();
                PreparedStatement pstm = con.prepareStatement(sql);
                ResultSet rs = pstm.executeQuery();) {
            Empresa_DTO empresa = new Empresa_DTO();
            while (rs.next()) {
                empresa.setId_Empresa(rs.getInt("id_Empresa"));
                empresa.setNombre_Empresa(rs.getString("Nombre_Empresa"));
            }
            if (empresa.getId_Empresa() != 0 && empresa.getNombre_Empresa() != null) {
                return empresa;
            } else {
                return null;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
