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
public class Entrada_DAO {

    public String registrarHrEnt(int empleado) {
        Date fac = new Date();
        Fecha f = new Fecha();
        f.setHora(fac);
        String hora = f.getHoraMas(5);
        String sql = "INSERT INTO entrada VALUES(null," + empleado + ",'" + f.getFechaActual() + "','" + hora + "')";
        try (Connection con = Conexion.getCon(); PreparedStatement pstm = con.prepareStatement(sql);) {
            pstm.executeUpdate();
        } catch (SQLException ex) {
        }
        return hora;
    }

    public String getHrEnt(int empleado) {
        Date fac = new Date();
        Fecha f = new Fecha();
        f.setHora(fac);
        String hora = null;
        String sql = "SELECT hora from entrada WHERE id_Empleado=" + empleado + " and fecha='" + f.getFechaActual() + "'";
        try (Connection con = Conexion.getCon(); PreparedStatement pstm = con.prepareStatement(sql); ResultSet rs = pstm.executeQuery();) {
            while (rs.next()) {
                hora = rs.getString("hora");
            }
        } catch (SQLException ex) {
        }        
        return hora;
    }
}
