package DataAccesObject;

import DataBase.Conexion;
import DataBase.Util;
import DataTransferObject.Nomina_DTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author ZionSystems
 */
public class Nomina_DAO {
    
    public void RegistrarNomina(Nomina_DTO Nom, int id_empleado) {        
        String sql = "INSERT INTO Nomina VALUES(null," + Nom.getPor().getId_Persona() + "," + id_empleado + ","
                + "'" + Util.redondearDecimales(Nom.getMonto()) + "','" + Nom.getFecha() + "')";
        System.out.println(sql);
        try (Connection con = Conexion.getCon(); PreparedStatement pstm = con.prepareStatement(sql);) {
            pstm.executeUpdate();
        } catch (SQLException ex) {
        }
    }
    
}
