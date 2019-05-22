package DataAccesObject;

import DataBase.Conexion;
import DataBase.Util;
import DataTransferObject.Medico_DTO;
import DataTransferObject.Participacion_DTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Carlos Rivas
 */
public class Participacion_DAO {

    public int registrarParticipacion(Participacion_DTO part) {
        String sql = "INSERT INTO participacion VALUES(null," + part.getId_Unidad() + "," + part.getOrden().getId_Orden() + "," + part.getMedico().getId_Medico() + ",'" + part.getFecha() + "','" + part.getHora() + "','" + part.getMonto() + "','" + part.getConvenio() + "')";
        try (Connection con = Conexion.getCon(); PreparedStatement pstm = con.prepareStatement(sql);) {
            pstm.executeUpdate();
            part.setId_participacion(GetIDParticipacion(part));;
        } catch (SQLException ex) {
            System.out.println("* INSERT *PARTICIPACION: " + ex.getMessage());
        }
        return part.getId_participacion();
    }

    public int GetIDParticipacion(Participacion_DTO part) {
        int id_participacion = 0;
        String sql = "SELECT id_participacion from participacion WHERE id_Unidad=" + part.getId_Unidad() + " "
                + "                                             and id_Orden=" + part.getOrden().getId_Orden()  + " "
                + "                                             and id_Medico=" + part.getMedico().getId_Medico() + " "
                + "                                             and Fecha='" + part.getFecha() + "' and convenio='" + part.getConvenio() + "'";
        try (Connection con = Conexion.getCon(); PreparedStatement pstm = con.prepareStatement(sql); ResultSet rs = pstm.executeQuery();) {
            while (rs.next()) {
                id_participacion = rs.getInt("id_participacion");
            }
        } catch (SQLException ex) {
            System.out.println("* SELECT ID * PARTICIPACION: " + ex.getMessage());
        }
        return id_participacion;
    }
    
    public Float Participacion(Participacion_DTO part) {
        Date fac = new Date();
        Util f = new Util();
        f.setHora(fac);
        Float Monto = Float.parseFloat("0");
        String sql = "SELECT Monto from participacion WHERE id_Orden=" + part.getOrden().getId_Orden() + "";
        try (Connection con = Conexion.getCon(); PreparedStatement pstm = con.prepareStatement(sql); ResultSet rs = pstm.executeQuery();) {
            while (rs.next()) {
                Monto = rs.getFloat("Monto");
            }
        } catch (SQLException ex) {
            System.out.println("* SELECT Participacion [MONTO] : " + ex.getMessage());
        }
        return Monto;
    }

    public List<Participacion_DTO> GetPartsByUnidad(int id_Unidad, String fechaInicio, String fechaFinal) {
        List<Participacion_DTO> parts = new ArrayList<>();
        Orden_DAO O=new Orden_DAO();
        String sql = "SELECT * from participacion WHERE id_Unidad=" + id_Unidad + " AND Monto>0 AND  Fecha BETWEEN '" + fechaInicio + "' AND '" + fechaFinal + "' order by id_Medico";
        try (Connection con = Conexion.getCon(); PreparedStatement pstm = con.prepareStatement(sql); ResultSet rs = pstm.executeQuery();) {
            while (rs.next()) {
                Participacion_DTO part = new Participacion_DTO();
                part.setId_participacion(rs.getInt("id_participacion"));
                part.setOrden(O.getOrden(rs.getInt("id_Orden")));                
                part.setMonto(rs.getFloat("Monto"));
                part.setConvenio(rs.getString("convenio"));
                parts.add(part);
            }
        } catch (SQLException ex) {
            System.out.println("* SELECT ID * PARTICIPACION: " + ex.getMessage());
        }
        return parts;
    }

    public Medico_DTO GetPartsByUnddAndMdco(int id_Unidad, int id_Medico) {
        Medico_DTO Medico;
        List<Participacion_DTO> parts = new ArrayList<>();
        Medico_DAO M = new Medico_DAO();
        Medico = M.getMedico(id_Medico);
        String sql = "SELECT * from participacion WHERE id_Unidad=" + id_Unidad + " and id_Medico=" + id_Medico + "";
        try (Connection con = Conexion.getCon(); PreparedStatement pstm = con.prepareStatement(sql); ResultSet rs = pstm.executeQuery();) {
            while (rs.next()) {
                Participacion_DTO part = new Participacion_DTO();
                part.setId_participacion(rs.getInt("id_participacion"));
                part.setId_Unidad(rs.getInt("id_Unidad"));
                part.setFecha(rs.getString("Fecha"));
                part.setHora(rs.getString("Hora"));
                part.setMonto(rs.getFloat("Monto"));
                part.setConvenio(rs.getString("convenio"));
                parts.add(part);
            }
        } catch (SQLException ex) {
            System.out.println("* GePartsByUnddAndMdco " + ex.getMessage());
        }
        Medico.setParticipaciones(parts);
        return Medico;
    }

}
