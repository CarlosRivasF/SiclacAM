package DataAccesObject;

import DataBase.Conexion;
import DataTransferObject.Configuracion_DTO;
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
public class Configuracion_DAO {

    public int registrarConfiguracion(Configuracion_DTO dto) {
        int id_Configuracion = 0;
        try (Connection con = Conexion.getCon();) {
                String sql = "INSERT INTO configuracion VALUES(null,"
                        + "'" + dto.getDescripcion() + "',"
                        + "'" + dto.getValor_min() + "',"
                        + "'" + dto.getValor_MAX() + "',"
                        + "'" + dto.getUniddes() + "',"
                        + "'" + dto.getSexo() + "')";               
                PreparedStatement pstm = con.prepareStatement(sql);
                if (pstm.executeUpdate() == 1) {
                    pstm.close();
                    sql = "SELECT id_Configuracion from configuracion WHERE "
                            + "Descripcion='" + dto.getDescripcion() + "' AND "
                            + "Valor_min='" + dto.getValor_min() + "' AND "
                            + "Valor_MAX='" + dto.getValor_MAX() + "' AND "
                            + "Unidades='" + dto.getUniddes() + "' AND "
                            + "sexo='" + dto.getSexo() + "'";                   
                    try (PreparedStatement pstm1 = con.prepareStatement(sql);
                            ResultSet rs = pstm1.executeQuery();) {
                        while (rs.next()) {
                            id_Configuracion = rs.getInt("id_Configuracion");
                        }
                    }
                }
                return id_Configuracion;
            
        } catch (SQLException ex) {
            Logger.getLogger(Configuracion_DAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return id_Configuracion;
    }

    public int registrarConf_Est(int Est, int Conf) {
        int id_Configuracion = 0;
        String sql = "INSERT INTO conf_est VALUES(" + Est + "," + Conf + ")";
        try (Connection con = Conexion.getCon(); PreparedStatement pstm = con.prepareStatement(sql);) {
            pstm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(Configuracion_DAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return id_Configuracion;
    }    
}
