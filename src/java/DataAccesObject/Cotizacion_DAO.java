package DataAccesObject;

import DataBase.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import DataTransferObject.Cotizacion_DTO;
import DataTransferObject.Det_Cot_DTO;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ZionSystems
 */
public class Cotizacion_DAO {

    public int RegistrarCotizacion(Cotizacion_DTO dto) {
        String sql = "INSERT INTO cotizacion VALUES(NULL,"
                + "" + dto.getUnidad().getId_Unidad() + ","
                + "" + dto.getPaciente().getId_Paciente() + ","
                + "" + dto.getMedico().getId_Medico() + ","
                + "" + dto.getEmpleado().getId_Persona() + ","
                + "'" + dto.getConvenio() + "',"
                + "'" + dto.getFecha_Cot() + "',"
                + "'" + dto.getFecha_Exp() + "',"
                + "'" + dto.getTotal() + "',"
                + "'" + dto.getEstado() + "')";
        try (Connection con = Conexion.getCon();
                PreparedStatement pstm = con.prepareStatement(sql);) {
            if (pstm.executeUpdate() == 1) {
                sql = "SELECT id_Cotizacion from cotizacion WHERE id_Unidad=" + dto.getUnidad().getId_Unidad() + " AND id_Paciente=" + dto.getPaciente().getId_Paciente() + ""
                        + " AND id_Medico=" + dto.getMedico().getId_Medico() + " AND id_Persona=" + dto.getEmpleado().getId_Persona() + " AND convenio='" + dto.getConvenio() + "' "
                        + "AND Fecha_Cot='" + dto.getFecha_Cot() + "' AND Fecha_Exp='" + dto.getFecha_Exp() + "' AND Precio_Total='" + dto.getTotal() + "'";
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

    public Cotizacion_DTO getCotizacion(int id_Cotizacion) {        
        Cotizacion_DTO cot = new Cotizacion_DTO();
        Estudio_DAO E = new Estudio_DAO();
        Unidad_DAO U = new Unidad_DAO();
        Paciente_DAO P = new Paciente_DAO();
        Medico_DAO M = new Medico_DAO();
        Persona_DAO Pr = new Persona_DAO();
        try (Connection con = Conexion.getCon()) {
            String sql = "SELECT * FROM cotizacion  WHERE id_Cotizacion=" + id_Cotizacion + "";
            try (PreparedStatement pstm = con.prepareStatement(sql); ResultSet rs = pstm.executeQuery();) {
                while (rs.next()) {
                    cot.setId_Cotizacion(rs.getInt("id_Cotizacion"));
                    cot.setUnidad(U.getUnidadAll(rs.getInt("id_Unidad")));
                    cot.setPaciente(P.getPaciente(rs.getInt("id_Paciente")));
                    cot.setMedico(M.getMedico(rs.getInt("id_Medico")));
                    cot.setEmpleado(Pr.getPersona(rs.getInt("id_Persona")));
                    cot.setConvenio(rs.getString("convenio"));
                    cot.setFecha_Cot(rs.getString("Fecha_Cot"));
                    cot.setFecha_Exp(rs.getString("Fecha_Exp"));
                    cot.setTotal(rs.getFloat("Precio_Total"));
                    cot.setEstado(rs.getString("Estado"));
                }
            }
            List<Det_Cot_DTO> dets = new ArrayList<>();
            sql = "SELECT * FROM det_cot WHERE  id_Cotizacion=" + cot.getId_Cotizacion() + "";
            try (PreparedStatement pstm = con.prepareStatement(sql); ResultSet rs = pstm.executeQuery();) {
                while (rs.next()) {
                    Det_Cot_DTO det = new Det_Cot_DTO();
                    det.setId_Cotizacion(rs.getInt("id_Cotizacion"));
                    det.setEstudio(E.getEst_Uni(rs.getInt("id_Est_Uni")));
                    det.setDescuento(rs.getFloat("Descuento"));
                    det.setT_Entrega(rs.getString("Tipo_Entrega"));
                    dets.add(det);
                }
            }
            cot.setDet_Cot(dets);
        }catch (SQLException e) {
         throw new RuntimeException(e);
        }
        return cot;
    }
//
//    public static void main(String[] args) {
//        Cotizacion_DAO C = new Cotizacion_DAO();
//        
//        Cotizacion_DTO cot = C.getCotizacion(1);
//        System.out.println(cot.getPaciente().getNombre());
//        System.out.println(cot.getMedico().getNombre());
//        System.out.println(cot.getEmpleado().getNombre());
//        cot.getDet_Cot().forEach((det) -> {
//            System.out.println(det.getEstudio().getNombre_Estudio());
//        });
//    }
}