package DataAccesObject;

import DataBase.Conexion;
import DataBase.Util;
import DataTransferObject.Observacion_DTO;
import DataTransferObject.Resultado_DTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

/**
 *
 * @author ZionSystem id_resultado id_Det_Orden id_Configuracion Valor_Obtenido
 */
public class Resultado_DAO {

    public int RegistrarResultado(int id_Det_Ord, int id_Configuracion, String Valor_Obtenido) {
        int id_resultado = 0;
        Date fac = new Date();
        Util f = new Util();
        f.setHora(fac);
        String sql = "INSERT INTO resultado VALUES(null,"
                + "" + id_Det_Ord + ","
                + "" + id_Configuracion + ","
                + "'" + Valor_Obtenido + "')";
        System.out.println(sql);
        try (Connection con = Conexion.getCon();) {
            try (PreparedStatement pstm = con.prepareStatement(sql);) {
                pstm.executeUpdate();
            }
            sql = "SELECT id_resultado from resultado WHERE id_Det_Orden=" + id_Det_Ord + " and id_Configuracion=" + id_Configuracion + " AND Valor_Obtenido='" + Valor_Obtenido + "' ";
            System.out.println(sql);
            try (PreparedStatement pstm1 = con.prepareStatement(sql);
                    ResultSet rs = pstm1.executeQuery();) {
                while (rs.next()) {
                    id_resultado = rs.getInt("id_resultado");
                }
            }
        } catch (SQLException ex) {

        }
        return id_resultado;
    }

    public void updateRes(Resultado_DTO res) {
        String sql = "UPDATE resultado SET"
                + " Valor_Obtenido='" + res.getValor_Obtenido() + "' WHERE "
                + "id_resultado=" + res.getId_resultado() + "";
        try (Connection con = Conexion.getCon();) {
            try (PreparedStatement pstm = con.prepareStatement(sql);) {
                pstm.executeUpdate();
            }
        } catch (SQLException ex) {
        }
    }

    public int RegistrarObserv(int id_Det_Ord, String Observacion) {
        int id_Observacion = 0;
        String sql = "INSERT INTO observacion VALUES(null,"
                + "" + id_Det_Ord + ","
                + "'" + Observacion + "')";
        System.out.println(sql);
        try (Connection con = Conexion.getCon();) {
            try (PreparedStatement pstm = con.prepareStatement(sql);) {
                pstm.executeUpdate();
            }
            sql = "SELECT id_Observacion from Observacion WHERE id_Det_Orden=" + id_Det_Ord + "";
            System.out.println(sql);
            try (PreparedStatement pstm1 = con.prepareStatement(sql);
                    ResultSet rs = pstm1.executeQuery();) {
                while (rs.next()) {
                    id_Observacion = rs.getInt("id_Observacion");
                }
            }
        } catch (SQLException ex) {

        }
        return id_Observacion;
    }

    public void updateObs(int id_Det_Ord,Observacion_DTO obs) {
        String sql = "UPDATE observacion SET"
                + " observacion='" + obs.getObservacion()+ "' WHERE "
                + "id_Det_Orden=" + id_Det_Ord + "";
        try (Connection con = Conexion.getCon();) {
            try (PreparedStatement pstm = con.prepareStatement(sql);) {
                pstm.executeUpdate();
            }
        } catch (SQLException ex) {
        }
    }

}
