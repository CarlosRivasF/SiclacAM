package DataAccesObject;

import DataBase.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import DataTransferObject.Cotizacion_DTO;

/**
 *
 * @author ZionSystems
 */
public class Cotizacion_DAO {

    public int RegistrarCotizacion(Cotizacion_DTO dto) {
        String sql = "INSERT INTO cotizacion VALUES(NULL,"
                + "" + dto.getUnidad().getId_Unidad() + ","
                + "" + dto.getPaciente().getId_Paciente() + ","
                + "" + dto.getMedico().getId_Medico() + ","
                + "" + dto.getEmpleado().getId_Persona()+ ","
                + "'" + dto.getConvenio() + "',"
                + "'" + dto.getFecha_Cot() + "',"
                + "'" + dto.getFecha_Exp() + "',"
                + "'" + dto.getTotal() + "',"
                + "'" + dto.getEstado() + "')";
        System.out.println(sql);
        try (Connection con = Conexion.getCon();
                PreparedStatement pstm = con.prepareStatement(sql);) {
            if (pstm.executeUpdate() == 1) {
                sql = "SELECT id_Cotizacion from cotizacion WHERE id_Unidad=" + dto.getUnidad().getId_Unidad() + " AND id_Paciente=" + dto.getPaciente().getId_Paciente() + ""
                        + " AND id_Medico=" + dto.getMedico().getId_Medico() + " AND id_Persona=" + dto.getEmpleado().getId_Persona()+ " AND convenio='" + dto.getConvenio() + "' "
                        + "AND Fecha_Cot='" + dto.getFecha_Cot() + "' AND Fecha_Exp='" + dto.getFecha_Exp() + "' AND Precio_Total='" + dto.getTotal() + "'";
                System.out.println(sql);
                try (PreparedStatement pstm1 = con.prepareStatement(sql);
                        ResultSet rs = pstm1.executeQuery();) {
                    while (rs.next()) {
                        dto.setId_Cotizacion(rs.getInt("id_Cotizacion"));
                    }
                }
                if (dto.getId_Cotizacion() != 0) {
                    Det_Cot_DAO DC = new Det_Cot_DAO();
                    dto.getDet_Cot().stream().map((dtc) -> {
                        dtc.setId_Cotizacion(dto.getId_Cotizacion()); 
                        return dtc;
                    }).forEachOrdered((dtc) -> {
                        dtc.setId_Det_Cot(DC.RegistrarCotizacion(dtc));
                    });
                }
            }
            con.close();
            return dto.getId_Cotizacion();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
