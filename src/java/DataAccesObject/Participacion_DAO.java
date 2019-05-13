
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
    
    public Participacion_DTO registrarParticipacion(Participacion_DTO part) {
        Date fac = new Date();
        Util f = new Util();
        f.setHora(fac);
        String hora = f.getHoraMas(Util.getHrBD());
        part.setFecha(f.getFechaActual());
        part.setHora(hora);
        String sql = "INSERT INTO participacion VALUES(null," + part.getId_Unidad() + "," + part.getId_Orden()+ "," + part.getId_Medico()+ ",'" + part.getFecha() + "','" + part.getHora() + "','" + part.getMonto() + "','" + part.getConvenio() + "')";   
        try (Connection con = Conexion.getCon(); PreparedStatement pstm = con.prepareStatement(sql);) {
            pstm.executeUpdate();
            part.setId_participacion(GetIDParticipacion(part));;
        } catch (SQLException ex) {
            System.out.println("* INSERT *PARTICIPACION: "+ex.getMessage());
        }
        return part;
    }

    public int GetIDParticipacion(Participacion_DTO part) {
        Date fac = new Date();
        Util f = new Util();
        f.setHora(fac);
        int id_participacion = 0;
        String sql = "SELECT id_participacion from participacion WHERE id_Unidad=" + part.getId_Unidad() + " "
                + "                                             and id_Orden=" + part.getId_Orden() + " "
                + "                                             and id_Medico=" + part.getId_Medico() + " "                
                + "                                             and Fecha='" + part.getFecha() + "' and convenio='" + part.getConvenio()+ "'";
        try (Connection con = Conexion.getCon(); PreparedStatement pstm = con.prepareStatement(sql); ResultSet rs = pstm.executeQuery();) {
            while (rs.next()) {
                id_participacion = rs.getInt("id_participacion");
            }
        } catch (SQLException ex) {
            System.out.println("* SELECT ID * PARTICIPACION: "+ex.getMessage());
        }
        return id_participacion;
    }
}