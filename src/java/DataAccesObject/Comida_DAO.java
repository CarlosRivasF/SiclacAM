package DataAccesObject;

import DataBase.Conexion;
import DataBase.Fecha;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

/**
 *
 * @author ZionSystems
 */
public class Comida_DAO {

    public String registrarHrCom(int empleado) {
        Date fac = new Date();
        Fecha f = new Fecha();
        f.setHora(fac);
        String hora = f.getHoraMas(6);
        String sql = "INSERT INTO comida VALUES(null,?,?,?)";
        try (Connection con = Conexion.getCon(); PreparedStatement pstm = con.prepareStatement(sql);) {
            pstm.setInt(1, empleado);
            pstm.setString(2, f.getFechaActual());
            pstm.setString(3, hora);
            pstm.executeUpdate();
        } catch (SQLException ex) {
        }
        return hora;
    }

    public String getHrCom(int empleado) {
        Date fac = new Date();
        Fecha f = new Fecha();
        f.setHora(fac);
        String id_hr = null;
        String sql = "SELECT hora from comida WHERE id_Empleado=" + empleado + " and fecha='" + f.getFechaActual() + "'";
        try (Connection con = Conexion.getCon(); PreparedStatement pstm = con.prepareStatement(sql); ResultSet rs = pstm.executeQuery();) {
            while (rs.next()) {
                id_hr = rs.getString("hora");
            }
        } catch (SQLException ex) {
        }
        return id_hr;
    }
}
