package DataAccesObject;

import DataBase.Conexion;
import DataBase.Util;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

/**
 *
 * @author ZionSystems
 */
public class Salida_DAO {

    public String registrarHrSal(int empleado) {
        Date fac = new Date();
        Util f = new Util();
        f.setHora(fac);
        String hora = f.getHoraMas(Util.getHrBD());
        String sql = "INSERT INTO salida VALUES(null," + empleado + ",'" + f.getFechaActual() + "','" + hora + "')";
        try (Connection con = Conexion.getCon(); PreparedStatement pstm = con.prepareStatement(sql);) {
            pstm.executeUpdate();
        } catch (SQLException ex) {
        }
        return hora;
    }

    public String getHrSal(int empleado) {
        Date fac = new Date();
        Util f = new Util();
        f.setHora(fac);
        String id_hr = null;
        String sql = "SELECT hora from salida WHERE id_Empleado=" + empleado + " and fecha='" + f.getFechaActual() + "'";
        try (Connection con = Conexion.getCon(); PreparedStatement pstm = con.prepareStatement(sql); ResultSet rs = pstm.executeQuery();) {
            while (rs.next()) {
                id_hr = rs.getString("hora");
            }
        } catch (SQLException ex) {
        }
        return id_hr;
    }
}
