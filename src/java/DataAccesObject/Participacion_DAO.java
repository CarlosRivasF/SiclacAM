
package DataAccesObject;

import DataBase.Conexion;
import DataBase.Util;
import DataTransferObject.Participacion_DTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

/**
 *
 * @author Carlos Rivas
 */
public class Participacion_DAO {
    
    /*	id_participacion	id_Unidad	id_Orden	id_Medico	Fecha	Hora	Monto	convenio */
    
    public int registrarPago(Participacion_DTO participacion) {
        int id_participacion = 0;
        Date fac = new Date();
        Util f = new Util();
        f.setHora(fac);
        String sql = "INSERT INTO participacion VALUES(null,"
                + "" + participacion.getId_Unidad()+ ","
                + "" + participacion.getId_Orden() + ","
                + "" + participacion.getId_Medico()+ ","
                + "'" + participacion.getFecha()+ "',"
                + "'" + participacion.getHora()+ "',"
                + "'" + participacion.getMonto()+ "',"
                + "'" + participacion.getConvenio()+ "')";
        int rp;
        try (Connection con = Conexion.getCon();) {
            try (PreparedStatement pstm = con.prepareStatement(sql);) {
                System.out.println(sql);
                rp = pstm.executeUpdate();
                System.out.println(rp);
            }
            if (rp == 1) {
                sql = "SELECT id_participacion from participacion WHERE id_Orden=" + participacion.getId_Orden() + " "
                        + "and id_Medico='" + participacion.getId_Medico()+ "'  AND fecha='" + participacion.getFecha() + "' AND hora='" + participacion.getHora() + "'";
                System.out.println("\t--"+sql);
                try (PreparedStatement pstm1 = con.prepareStatement(sql);
                        ResultSet rs = pstm1.executeQuery();) {
                    while (rs.next()) {
                        id_participacion = rs.getInt("id_participacion");
                    }
                }
            }
            return id_participacion;
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }
    
}
