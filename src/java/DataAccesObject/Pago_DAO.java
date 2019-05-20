package DataAccesObject;

import DataBase.Conexion;
import DataBase.Util;
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

    public int registrarPago(Pago_DTO pago) {
        int id_Pago = 0;
        Date fac = new Date();
        Util f = new Util();
        f.setHora(fac);
        String sql = "INSERT INTO pago VALUES(null,"
                + "" + pago.getId_Orden() + ","
                + "'" + pago.getT_Pago() + "',"
                + "'" + Util.redondearDecimales(pago.getMonto()) + "',"
                + "'" + pago.getFecha() + "',"
                + "'" + pago.getHora() + "')";
        int rp;
        try (Connection con = Conexion.getCon();) {
            try (PreparedStatement pstm = con.prepareStatement(sql);) {
                
                rp = pstm.executeUpdate();
            }
            if (rp == 1) {
                sql = "SELECT id_Pago from pago WHERE id_Orden=" + pago.getId_Orden() + " "
                        + "and T_Pago='" + pago.getT_Pago() + "' AND monto='" + Util.redondearDecimales(pago.getMonto()) + "' AND fecha='" + pago.getFecha() + "' AND hora='" + pago.getHora() + "'";
                
                try (PreparedStatement pstm1 = con.prepareStatement(sql);
                        ResultSet rs = pstm1.executeQuery();) {
                    while (rs.next()) {
                        id_Pago = rs.getInt("id_Pago");
                    }
                }
            }
            return id_Pago;
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

//    public static void main(String[] args) {
//        Pago_DAO P = new Pago_DAO();
//        Pago_DTO pago = new Pago_DTO();
//
//        pago.setFecha("2018-08-09");
//        pago.setHora("20:16");
//        pago.setId_Orden(7);
//        pago.setMonto(Float.parseFloat("1"));
//        pago.setT_Pago("Efectivo");
//
//        P.registrarPago(pago);
//        Orden_DAO O = new Orden_DAO();
//        O.updateSaldo(Float.parseFloat("8"), Float.parseFloat("3"), pago);
//    }
}
