package DataAccesObject;

import DataBase.Conexion;
import DataBase.Util;
import DataTransferObject.Sancion_DTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author ZionSystems
 *
 * por para Monto Motivo Fecha
 */
public class Sancion_DAO {

    public void RegistrarSancion(Sancion_DTO sanc, int id_empleado) {        
        String sql = "INSERT INTO sancion VALUES(null," + sanc.getPor().getId_Persona() + "," + id_empleado + ","
                + "'" + Util.redondearDecimales(sanc.getMonto()) + "','" + sanc.getMotivo() + "','" + sanc.getFecha() + "')";        
        try (Connection con = Conexion.getCon(); PreparedStatement pstm = con.prepareStatement(sql);) {
            pstm.executeUpdate();
        } catch (SQLException ex) {
        }
    }

}
