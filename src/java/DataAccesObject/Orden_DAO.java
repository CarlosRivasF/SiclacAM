package DataAccesObject;

import DataBase.Conexion;
import DataTransferObject.Orden_DTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author ZionSystems
 */
public class Orden_DAO {

    public int registrarOrden(Orden_DTO orden) {
        try (Connection con = Conexion.getCon();) {
            String sql = "INSERT INTO orden VALUES(null,"
                    + "" + orden.getUnidad().getId_Unidad() + ","
                    + "" + orden.getPaciente().getId_Paciente() + ","
                    + "" + orden.getMedico().getId_Medico() + ","
                    + "" + orden.getEmpleado().getId_Persona() + ","
                    + "'" + orden.getFecha() + "',"
                    + "'" + orden.getHora() + "',"
                    + "'" + (orden.getTotal() + orden.getRestante()) + "',"
                    + "'" + orden.getTotal() + "',"
                    + "'" + orden.getEstado() + "',"
                    + "'" + orden.getConvenio() + "',"
                    + "" + orden.getFolio_Unidad() + ")";

            System.out.println(sql);
            try (PreparedStatement pstm = con.prepareStatement(sql);) {
                pstm.executeUpdate();
            }
            sql = "select id_Orden from orden where id_unidad=" + orden.getUnidad().getId_Unidad() + " and id_paciente=" + orden.getPaciente().getId_Paciente() + ""
                    + " and id_medico=" + orden.getMedico().getId_Medico() + " and id_empleado=" + orden.getEmpleado().getId_Persona() + ""
                    + " and Fecha_Orden='" + orden.getFecha() + "'  and Hora_Orden='" + orden.getHora() + "' and Precio_Total='" + (orden.getTotal() + orden.getRestante()) + "' and montoRes='" + orden.getTotal() + "' and convenio='" + orden.getConvenio() + "' and folio_unidad=" + orden.getFolio_Unidad() + "";
            System.out.println(sql);
            try (PreparedStatement pstm = con.prepareStatement(sql); ResultSet rs = pstm.executeQuery()) {
                while (rs.next()) {
                    orden.setId_Orden(rs.getInt("id_Orden"));
                }
            }
            System.out.println("Id_Orden: " + orden.getId_Orden());
            if (orden.getId_Orden() != 0) {
                System.out.println("ORDEN REGISTRADA");
                Det_Orden_DAO DET = new Det_Orden_DAO();
                orden.getDet_Orden().forEach((detor) -> {
                    detor.setId_det_orden(DET.registrarDetor(orden.getId_Orden(), detor));
                });
                if (orden.getPagos() != null && !orden.getPagos().isEmpty()) {
                    Pago_DAO P = new Pago_DAO();
                    orden.getPagos().forEach((pago) -> {
                        pago.setId_pago(P.registrarPago(orden.getId_Orden(), pago));
                    });
                }
            }

        } catch (SQLException ex) {
        }
        return orden.getId_Orden();
    }

    public int getNoOrdenByUnidad(int id_Unidad) {
        String sql = "Select count(id_Orden) from orden where id_Unidad=" + id_Unidad + "";
        int NoOrds = 0;
        try (Connection con = Conexion.getCon()) {
            PreparedStatement pstm = con.prepareStatement(sql);
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                NoOrds = rs.getInt(1);
            }
        } catch (SQLException ex) {

        }
        return NoOrds;
    }

    public static void main(String[] args) {
        Orden_DAO O = new Orden_DAO();
        System.out.println(O.getNoOrdenByUnidad(1));
    }
}
