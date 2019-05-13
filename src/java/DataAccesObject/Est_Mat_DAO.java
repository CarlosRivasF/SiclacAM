package DataAccesObject;

import DataBase.Conexion;
import DataTransferObject.Est_Mat_DTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author ZionSystems
 */
public class Est_Mat_DAO {

    public int registrarMat_Est(int id_Est_Uni, Est_Mat_DTO dto) {
        int r = 0;
        String sql = "INSERT INTO mat_est VALUES(" + id_Est_Uni + "," + dto.getId_Unid_Mat() + ","
                + "" + dto.getCantidadE() + ",'" + dto.getUnidadE()+ "','" + dto.getT_Muestra() + "')";
        
        try (Connection con = Conexion.getCon(); PreparedStatement pstm = con.prepareStatement(sql);) {
            r = pstm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(Configuracion_DAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return r;
    }

}
