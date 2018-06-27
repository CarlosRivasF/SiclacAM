package DataAccesObject;

import DataBase.Conexion;
import DataBase.Fecha;
import DataTransferObject.Pago_DTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

/**
 *
 * @author ZionSystems id_Pago id_Det_Orden T_Pago monto fecha hora
 */
public class Pago_DAO {

    public int registrarPago(int id_Orden, Pago_DTO pago) {
        int id_Pago = 0;
        Date fac = new Date();
        Fecha f = new Fecha();
        f.setHora(fac);
        String sql = "INSERT INTO pago VALUES(null,"
                + "" + id_Orden + ","
                + "'" + pago.getT_Pago() + "',"
                + "'" + pago.getMonto() + "',"
                + "'" + pago.getFecha() + "',"
                + "'" + pago.getHora() + "')";
        System.out.println(sql);
        try (Connection con = Conexion.getCon();) {
            try (PreparedStatement pstm = con.prepareStatement(sql);) {
                pstm.executeUpdate();
            }
            sql = "SELECT id_Pago from Pago WHERE id_Det_Orden=" + id_Orden + " and T_Pago='" + pago.getT_Pago() + "' AND monto='" + pago.getMonto() + "' AND fecha='" + pago.getFecha() + "' AND hora='" + pago.getHora() + "'";
            System.out.println(sql);
            try (PreparedStatement pstm1 = con.prepareStatement(sql);
                    ResultSet rs = pstm1.executeQuery();) {
                while (rs.next()) {
                    id_Pago = rs.getInt("id_Pago");
                }
            }
        } catch (SQLException ex) {
        }
        return id_Pago;
    }
}
