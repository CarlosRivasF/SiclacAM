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
 * @author ZionSystem id_resultado id_Det_Orden id_Configuracion Valor_Obtenido
 */
public class Resultado_DAO {

    public int RegistrarResultado(int id_Det_Ord, int id_Configuracion, String Valor_Obtenido) {
        int id_resultado = 0;
        Date fac = new Date();
        Fecha f = new Fecha();
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

}
