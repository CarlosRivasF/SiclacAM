package DataAccesObject;

import DataBase.Conexion;
import DataTransferObject.Configuracion_DTO;
import DataTransferObject.Det_Orden_DTO;
import DataTransferObject.Observacion_DTO;
import DataTransferObject.Orden_DTO;
import DataTransferObject.Pago_DTO;
import DataTransferObject.Resultado_DTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
                    + "'" + orden.getMontoPagado() + "',"
                    + "'" + orden.getMontoRestante() + "',"
                    + "'" + orden.getEstado() + "',"
                    + "'" + orden.getConvenio() + "',"
                    + "" + orden.getFolio_Unidad() + ")";
            System.out.println(sql);
            try (PreparedStatement pstm = con.prepareStatement(sql);) {
                pstm.executeUpdate();
            }
            sql = "select id_Orden from orden where id_unidad=" + orden.getUnidad().getId_Unidad() + " and id_paciente=" + orden.getPaciente().getId_Paciente() + ""
                    + " and id_medico=" + orden.getMedico().getId_Medico() + " and id_Persona=" + orden.getEmpleado().getId_Persona() + ""
                    + " and Fecha_Orden='" + orden.getFecha() + "'  and Hora_Orden='" + orden.getHora() + "' and Precio_Total='" + orden.getMontoPagado() + "' and montoRes='" + orden.getMontoRestante() + "' and convenio='" + orden.getConvenio() + "' and folio_unidad=" + orden.getFolio_Unidad() + "";
            System.out.println(sql);
            try (PreparedStatement pstm = con.prepareStatement(sql); ResultSet rs = pstm.executeQuery()) {
                while (rs.next()) {
                    orden.setId_Orden(rs.getInt("id_Orden"));
                }
            }
            if (orden.getId_Orden() != 0) {
                System.out.println("ORDEN REGISTRADA");
                Det_Orden_DAO DET = new Det_Orden_DAO();
                orden.getDet_Orden().forEach((detor) -> {
                    detor.setId_det_orden(DET.registrarDetor(orden.getId_Orden(), detor));
                });
                if (orden.getPagos() != null && !orden.getPagos().isEmpty()) {
                    Pago_DAO P = new Pago_DAO();
                    orden.getPagos().forEach((pago) -> {
                        pago.setId_Orden(orden.getId_Orden());
                        pago.setId_pago(P.registrarPago(pago));
                    });
                }
            }

        } catch (SQLException ex) {
            throw new RuntimeException(ex);
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
            throw new RuntimeException(ex);
        }
        return NoOrds;
    }

    public Orden_DTO getOrden(int id_Orden) {
        Orden_DTO ord = new Orden_DTO();
        Estudio_DAO E = new Estudio_DAO();
        Unidad_DAO U = new Unidad_DAO();
        Paciente_DAO P = new Paciente_DAO();
        Medico_DAO M = new Medico_DAO();
        Persona_DAO Pr = new Persona_DAO();
        try (Connection con = Conexion.getCon()) {
            String sql = "SELECT * FROM orden  WHERE id_Orden=" + id_Orden + "";
            try (PreparedStatement pstm = con.prepareStatement(sql); ResultSet rs = pstm.executeQuery();) {
                while (rs.next()) {
                    ord.setId_Orden(rs.getInt("id_Orden"));
                    ord.setUnidad(U.getUnidadAll(rs.getInt("id_Unidad")));
                    ord.setPaciente(P.getPaciente(rs.getInt("id_Paciente")));
                    ord.setMedico(M.getMedico(rs.getInt("id_Medico")));
                    ord.setEmpleado(Pr.getPersona(rs.getInt("id_Persona")));
                    ord.setFecha(rs.getString("Fecha_Orden"));
                    ord.setHora(rs.getString("Hora_Orden"));
                    ord.setMontoPagado(rs.getFloat("Precio_Total"));
                    ord.setMontoRestante(rs.getFloat("montoRes"));
                    ord.setEstado(rs.getString("Estado"));
                    ord.setConvenio(rs.getString("convenio"));
                    ord.setFolio_Unidad(rs.getInt("folio_unidad"));
                }
            }
            List<Det_Orden_DTO> dets = new ArrayList<>();
            sql = "SELECT * FROM det_orden WHERE  id_Orden=" + ord.getId_Orden() + "";
            try (PreparedStatement pstm = con.prepareStatement(sql); ResultSet rs = pstm.executeQuery();) {
                Boolean r = false;
                while (rs.next()) {
                    Det_Orden_DTO det = new Det_Orden_DTO();
                    det.setId_det_orden(rs.getInt("id_Det_Orden"));
                    det.setEstudio(E.getEst_Uni(rs.getInt("id_Est_Uni")));
                    det.setDescuento(rs.getFloat("Descuento"));
                    det.setFecha_Entrega(rs.getString("Fecha_Entrega"));
                    det.setT_Entrega(rs.getString("Tipo_Entrega"));
                    det.setSubtotal(rs.getFloat("Subtotal"));
                    String sql1 = "SELECT * FROM resultado WHERE  id_Det_Orden=" + det.getId_det_orden() + "";
                    List<Configuracion_DTO> confs = new ArrayList<>();
                    try (PreparedStatement pstm1 = con.prepareStatement(sql1); ResultSet rs1 = pstm1.executeQuery();) {
                        while (rs1.next()) {
                            Configuracion_DTO cnf = new Configuracion_DTO();
                            Resultado_DTO res = new Resultado_DTO();
                            res.setId_resultado(rs1.getInt("id_resultado"));
                            if (res.getId_resultado() != 0) {
                                r = true;
                                cnf.setId_Configuración(rs1.getInt("id_Configuracion"));
                                res.setValor_Obtenido(rs1.getString("Valor_Obtenido"));
                                det.getEstudio().setAddRes(true);
                                String sql2 = "SELECT * FROM configuracion WHERE id_Configuracion=" + cnf.getId_Configuración() + "";
                                try (PreparedStatement pstm2 = con.prepareStatement(sql2); ResultSet rs2 = pstm2.executeQuery();) {
                                    while (rs2.next()) {
                                        cnf.setDescripcion(rs2.getString("Descripcion"));
                                        cnf.setSexo(rs2.getString("sexo"));
                                        cnf.setValor_min(rs2.getString("Valor_min"));
                                        cnf.setValor_MAX(rs2.getString("Valor_MAX"));
                                        cnf.setUniddes(rs2.getString("Unidades"));
                                    }

                                }
                                cnf.setRes(res);
                                confs.add(cnf);
                            }
                        }
                        det.getEstudio().getCnfs().forEach((Configuracion_DTO confE) -> {
                            confs.stream().filter((confR) -> (confE.getId_Configuración() == confR.getId_Configuración())).forEachOrdered((confR) -> {
                                det.getEstudio().getCnfs().set(det.getEstudio().getCnfs().indexOf(confE), confR);
                            });
                        });
                        dets.add(det);
                    }
                    ord.setDet_Orden(dets);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return ord;
    }

    public Orden_DTO getOrdenByFolio(int folio_unidad, int id_Unidad) {
        Orden_DTO ord = new Orden_DTO();
        Estudio_DAO E = new Estudio_DAO();
        Unidad_DAO U = new Unidad_DAO();
        Paciente_DAO P = new Paciente_DAO();
        Medico_DAO M = new Medico_DAO();
        Persona_DAO Pr = new Persona_DAO();
        try (Connection con = Conexion.getCon()) {
            String sql = "SELECT * FROM orden  WHERE id_Unidad=" + id_Unidad + " AND folio_unidad=" + folio_unidad + "";
            try (PreparedStatement pstm = con.prepareStatement(sql); ResultSet rs = pstm.executeQuery();) {
                while (rs.next()) {
                    ord.setId_Orden(rs.getInt("id_Orden"));
                    ord.setUnidad(U.getUnidadAll(rs.getInt("id_Unidad")));
                    ord.setPaciente(P.getPaciente(rs.getInt("id_Paciente")));
                    ord.setMedico(M.getMedico(rs.getInt("id_Medico")));
                    ord.setEmpleado(Pr.getPersona(rs.getInt("id_Persona")));
                    ord.setFecha(rs.getString("Fecha_Orden"));
                    ord.setHora(rs.getString("Hora_Orden"));
                    ord.setMontoPagado(rs.getFloat("Precio_Total"));
                    ord.setMontoRestante(rs.getFloat("montoRes"));
                    ord.setEstado(rs.getString("Estado"));
                    ord.setConvenio(rs.getString("convenio"));
                    ord.setFolio_Unidad(rs.getInt("folio_unidad"));
                }
            }
            List<Det_Orden_DTO> dets = new ArrayList<>();
            sql = "SELECT * FROM det_orden WHERE  id_Orden=" + ord.getId_Orden() + "";
            try (PreparedStatement pstm = con.prepareStatement(sql); ResultSet rs = pstm.executeQuery();) {
                Boolean r = false;
                while (rs.next()) {
                    Det_Orden_DTO det = new Det_Orden_DTO();
                    det.setId_orden(ord.getId_Orden());
                    det.setId_det_orden(rs.getInt("id_Det_Orden"));
                    det.setEstudio(E.getEst_Uni(rs.getInt("id_Est_Uni")));
                    det.setDescuento(rs.getFloat("Descuento"));
                    det.setFecha_Entrega(rs.getString("Fecha_Entrega"));
                    det.setT_Entrega(rs.getString("Tipo_Entrega"));
                    det.setSubtotal(rs.getFloat("Subtotal"));
                    String sql1 = "SELECT * FROM resultado WHERE  id_Det_Orden=" + det.getId_det_orden() + "";
                    List<Configuracion_DTO> confs = new ArrayList<>();
                    try (PreparedStatement pstm1 = con.prepareStatement(sql1); ResultSet rs1 = pstm1.executeQuery();) {
                        while (rs1.next()) {
                            Configuracion_DTO cnf = new Configuracion_DTO();
                            Resultado_DTO res = new Resultado_DTO();
                            res.setId_resultado(rs1.getInt("id_resultado"));
                            if (res.getId_resultado() != 0) {
                                r = true;
                                cnf.setId_Configuración(rs1.getInt("id_Configuracion"));
                                res.setValor_Obtenido(rs1.getString("Valor_Obtenido"));
                                det.getEstudio().setAddRes(true);
                                String sql2 = "SELECT * FROM configuracion WHERE id_Configuracion=" + cnf.getId_Configuración() + "";
                                try (PreparedStatement pstm2 = con.prepareStatement(sql2); ResultSet rs2 = pstm2.executeQuery();) {
                                    while (rs2.next()) {
                                        cnf.setDescripcion(rs2.getString("Descripcion"));
                                        cnf.setSexo(rs2.getString("sexo"));
                                        cnf.setValor_min(rs2.getString("Valor_min"));
                                        cnf.setValor_MAX(rs2.getString("Valor_MAX"));
                                        cnf.setUniddes(rs2.getString("Unidades"));
                                    }

                                }
                                cnf.setRes(res);
                                confs.add(cnf);
                            }
                        }
                        det.getEstudio().getCnfs().forEach((Configuracion_DTO confE) -> {
                            confs.stream().filter((confR) -> (confE.getId_Configuración() == confR.getId_Configuración())).forEachOrdered((confR) -> {
                                det.getEstudio().getCnfs().set(det.getEstudio().getCnfs().indexOf(confE), confR);
                            });
                        });
                        dets.add(det);
                    }
                    ord.setDet_Orden(dets);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return ord;
    }

    public List<Orden_DTO> getOrdenes(int id_Unidad) {
        List<Orden_DTO> ords = new ArrayList<>();
        Estudio_DAO E = new Estudio_DAO();
        Unidad_DAO U = new Unidad_DAO();
        Paciente_DAO P = new Paciente_DAO();
        Medico_DAO M = new Medico_DAO();
        Persona_DAO Pr = new Persona_DAO();
        try (Connection con = Conexion.getCon()) {
            String sql = "SELECT * FROM orden  WHERE id_Unidad=" + id_Unidad + "";
            try (PreparedStatement pstm = con.prepareStatement(sql); ResultSet rs = pstm.executeQuery();) {
                while (rs.next()) {
                    Orden_DTO ord = new Orden_DTO();
                    ord.setId_Orden(rs.getInt("id_Orden"));
                    ord.setUnidad(U.getUnidadAll(rs.getInt("id_Unidad")));
                    ord.setPaciente(P.getPaciente(rs.getInt("id_Paciente")));
                    ord.setMedico(M.getMedico(rs.getInt("id_Medico")));
                    ord.setEmpleado(Pr.getPersona(rs.getInt("id_Persona")));
                    ord.setFecha(rs.getString("Fecha_Orden"));
                    ord.setHora(rs.getString("Hora_Orden"));
                    ord.setMontoPagado(rs.getFloat("Precio_Total"));
                    ord.setMontoRestante(rs.getFloat("montoRes"));
                    ord.setEstado(rs.getString("Estado"));
                    ord.setConvenio(rs.getString("convenio"));
                    ord.setFolio_Unidad(rs.getInt("folio_unidad"));
                    ords.add(ord);
                }
            }

            for (Orden_DTO ord : ords) {
                List<Det_Orden_DTO> dets = new ArrayList<>();
                sql = "SELECT * FROM det_orden WHERE  id_Orden=" + ord.getId_Orden() + "";
                try (PreparedStatement pstm = con.prepareStatement(sql); ResultSet rs = pstm.executeQuery();) {
                    Boolean r = false;
                    while (rs.next()) {
                        Det_Orden_DTO det = new Det_Orden_DTO();
                        det.setId_det_orden(rs.getInt("id_Det_Orden"));
                        det.setEstudio(E.getEst_Uni(rs.getInt("id_Est_Uni")));
                        det.setDescuento(rs.getFloat("Descuento"));
                        det.setFecha_Entrega(rs.getString("Fecha_Entrega"));
                        det.setT_Entrega(rs.getString("Tipo_Entrega"));
                        det.setSubtotal(rs.getFloat("Subtotal"));
                        String sql1 = "SELECT * FROM resultado WHERE  id_Det_Orden=" + det.getId_det_orden() + "";
                        List<Configuracion_DTO> confs = new ArrayList<>();
                        try (PreparedStatement pstm1 = con.prepareStatement(sql1); ResultSet rs1 = pstm1.executeQuery();) {
                            while (rs1.next()) {
                                Configuracion_DTO cnf = new Configuracion_DTO();
                                Resultado_DTO res = new Resultado_DTO();
                                res.setId_resultado(rs1.getInt("id_resultado"));
                                if (res.getId_resultado() != 0) {
                                    r = true;
                                    cnf.setId_Configuración(rs1.getInt("id_Configuracion"));
                                    res.setValor_Obtenido(rs1.getString("Valor_Obtenido"));
                                    det.getEstudio().setAddRes(true);
                                    String sql2 = "SELECT * FROM configuracion WHERE id_Configuracion=" + cnf.getId_Configuración() + "";
                                    try (PreparedStatement pstm2 = con.prepareStatement(sql2); ResultSet rs2 = pstm2.executeQuery();) {
                                        while (rs2.next()) {
                                            cnf.setDescripcion(rs2.getString("Descripcion"));
                                            cnf.setSexo(rs2.getString("sexo"));
                                            cnf.setValor_min(rs2.getString("Valor_min"));
                                            cnf.setValor_MAX(rs2.getString("Valor_MAX"));
                                            cnf.setUniddes(rs2.getString("Unidades"));
                                        }

                                    }
                                    cnf.setRes(res);
                                    confs.add(cnf);
                                }
                            }
                        }
                        det.getEstudio().getCnfs().forEach((Configuracion_DTO confE) -> {
                            confs.stream().filter((confR) -> (confE.getId_Configuración() == confR.getId_Configuración())).forEachOrdered((confR) -> {
                                det.getEstudio().getCnfs().set(det.getEstudio().getCnfs().indexOf(confE), confR);
                            });
                        });
                        String sql3 = "SELECT * FROM observacion WHERE  id_Det_Orden=" + det.getId_det_orden() + "";
                        try (PreparedStatement pstm3 = con.prepareStatement(sql3); ResultSet rs3 = pstm3.executeQuery();) {
                            while (rs3.next()) {
                                Observacion_DTO Obs = new Observacion_DTO();
                                Obs.setId_Observacion(rs3.getInt("id_Observacion"));
                                Obs.setObservacion(rs3.getString("observacion"));
                                det.getEstudio().setObservacion(Obs);
                            }
                        }
                        dets.add(det);
                    }
                    ord.setDet_Orden(dets);

                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return ords;
    }

    public List<Orden_DTO> getOrdenesPendientes(int id_Unidad) {
        /*las Ordenes son 'PENDIENTES' cuando aún no se completan los resultados
        [*puede estar liquidada(montoRes==0) pero SIN resultados]*/
        List<Orden_DTO> ords = new ArrayList<>();
        Estudio_DAO E = new Estudio_DAO();
        Unidad_DAO U = new Unidad_DAO();
        Paciente_DAO P = new Paciente_DAO();
        Medico_DAO M = new Medico_DAO();
        Persona_DAO Pr = new Persona_DAO();
        try (Connection con = Conexion.getCon()) {
            String sql = "SELECT * FROM orden  WHERE Estado='Pendiente' AND id_Unidad=" + id_Unidad + "";
            try (PreparedStatement pstm = con.prepareStatement(sql); ResultSet rs = pstm.executeQuery();) {
                while (rs.next()) {
                    Orden_DTO ord = new Orden_DTO();
                    ord.setId_Orden(rs.getInt("id_Orden"));
                    ord.setUnidad(U.getUnidadAll(rs.getInt("id_Unidad")));
                    ord.setPaciente(P.getPaciente(rs.getInt("id_Paciente")));
                    ord.setMedico(M.getMedico(rs.getInt("id_Medico")));
                    ord.setEmpleado(Pr.getPersona(rs.getInt("id_Persona")));
                    ord.setFecha(rs.getString("Fecha_Orden"));
                    ord.setHora(rs.getString("Hora_Orden"));
                    ord.setMontoPagado(rs.getFloat("Precio_Total"));
                    ord.setMontoRestante(rs.getFloat("montoRes"));
                    ord.setEstado(rs.getString("Estado"));
                    ord.setConvenio(rs.getString("convenio"));
                    ord.setFolio_Unidad(rs.getInt("folio_unidad"));
                    ords.add(ord);
                }
            }

            for (Orden_DTO ord : ords) {
                List<Det_Orden_DTO> dets = new ArrayList<>();
                sql = "SELECT * FROM det_orden WHERE  id_Orden=" + ord.getId_Orden() + "";
                try (PreparedStatement pstm = con.prepareStatement(sql); ResultSet rs = pstm.executeQuery();) {
                    Boolean r = false;
                    while (rs.next()) {
                        Det_Orden_DTO det = new Det_Orden_DTO();
                        det.setId_det_orden(rs.getInt("id_Det_Orden"));
                        det.setEstudio(E.getEst_Uni(rs.getInt("id_Est_Uni")));
                        det.setDescuento(rs.getFloat("Descuento"));
                        det.setFecha_Entrega(rs.getString("Fecha_Entrega"));
                        det.setT_Entrega(rs.getString("Tipo_Entrega"));
                        det.setSubtotal(rs.getFloat("Subtotal"));
                        String sql1 = "SELECT * FROM resultado WHERE  id_Det_Orden=" + det.getId_det_orden() + "";
                        List<Configuracion_DTO> confs = new ArrayList<>();
                        try (PreparedStatement pstm1 = con.prepareStatement(sql1); ResultSet rs1 = pstm1.executeQuery();) {
                            while (rs1.next()) {
                                Configuracion_DTO cnf = new Configuracion_DTO();
                                Resultado_DTO res = new Resultado_DTO();
                                res.setId_resultado(rs1.getInt("id_resultado"));
                                if (res.getId_resultado() != 0) {
                                    r = true;
                                    cnf.setId_Configuración(rs1.getInt("id_Configuracion"));
                                    res.setValor_Obtenido(rs1.getString("Valor_Obtenido"));
                                    det.getEstudio().setAddRes(true);
                                    String sql2 = "SELECT * FROM configuracion WHERE id_Configuracion=" + cnf.getId_Configuración() + "";
                                    try (PreparedStatement pstm2 = con.prepareStatement(sql2); ResultSet rs2 = pstm2.executeQuery();) {
                                        while (rs2.next()) {
                                            cnf.setDescripcion(rs2.getString("Descripcion"));
                                            cnf.setSexo(rs2.getString("sexo"));
                                            cnf.setValor_min(rs2.getString("Valor_min"));
                                            cnf.setValor_MAX(rs2.getString("Valor_MAX"));
                                            cnf.setUniddes(rs2.getString("Unidades"));
                                        }

                                    }
                                    cnf.setRes(res);
                                    confs.add(cnf);
                                }
                            }
                        }
                        det.getEstudio().getCnfs().forEach((Configuracion_DTO confE) -> {
                            confs.stream().filter((confR) -> (confE.getId_Configuración() == confR.getId_Configuración())).forEachOrdered((confR) -> {
                                det.getEstudio().getCnfs().set(det.getEstudio().getCnfs().indexOf(confE), confR);
                            });
                        });
                        String sql3 = "SELECT * FROM observacion WHERE  id_Det_Orden=" + det.getId_det_orden() + "";
                        try (PreparedStatement pstm3 = con.prepareStatement(sql3); ResultSet rs3 = pstm3.executeQuery();) {
                            while (rs3.next()) {
                                Observacion_DTO Obs = new Observacion_DTO();
                                Obs.setId_Observacion(rs3.getInt("id_Observacion"));
                                Obs.setObservacion(rs3.getString("observacion"));
                                det.getEstudio().setObservacion(Obs);
                            }
                        }
                        dets.add(det);
                    }
                    ord.setDet_Orden(dets);

                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return ords;
    }

    public Orden_DTO getOrdenPendiente(int folio_unidad, int id_Unidad) {
        Orden_DTO ord = new Orden_DTO();
        Estudio_DAO E = new Estudio_DAO();
        Unidad_DAO U = new Unidad_DAO();
        Paciente_DAO P = new Paciente_DAO();
        Medico_DAO M = new Medico_DAO();
        Persona_DAO Pr = new Persona_DAO();
        try (Connection con = Conexion.getCon()) {
            String sql = "SELECT * FROM orden  WHERE Estado='Pendiente' AND id_Unidad=" + id_Unidad + " AND folio_unidad=" + folio_unidad + "";
            try (PreparedStatement pstm = con.prepareStatement(sql); ResultSet rs = pstm.executeQuery();) {
                while (rs.next()) {
                    ord.setId_Orden(rs.getInt("id_Orden"));
                    ord.setUnidad(U.getUnidadAll(rs.getInt("id_Unidad")));
                    ord.setPaciente(P.getPaciente(rs.getInt("id_Paciente")));
                    ord.setMedico(M.getMedico(rs.getInt("id_Medico")));
                    ord.setEmpleado(Pr.getPersona(rs.getInt("id_Persona")));
                    ord.setFecha(rs.getString("Fecha_Orden"));
                    ord.setHora(rs.getString("Hora_Orden"));
                    ord.setMontoPagado(rs.getFloat("Precio_Total"));
                    ord.setMontoRestante(rs.getFloat("montoRes"));
                    ord.setEstado(rs.getString("Estado"));
                    ord.setConvenio(rs.getString("convenio"));
                    ord.setFolio_Unidad(rs.getInt("folio_unidad"));
                }
            }

            List<Det_Orden_DTO> dets = new ArrayList<>();
            sql = "SELECT * FROM det_orden WHERE  id_Orden=" + ord.getId_Orden() + "";
            try (PreparedStatement pstm = con.prepareStatement(sql); ResultSet rs = pstm.executeQuery();) {
                Boolean r = false;
                while (rs.next()) {
                    Det_Orden_DTO det = new Det_Orden_DTO();
                    det.setId_det_orden(rs.getInt("id_Det_Orden"));
                    det.setEstudio(E.getEst_Uni(rs.getInt("id_Est_Uni")));
                    det.setDescuento(rs.getFloat("Descuento"));
                    det.setFecha_Entrega(rs.getString("Fecha_Entrega"));
                    det.setT_Entrega(rs.getString("Tipo_Entrega"));
                    det.setSubtotal(rs.getFloat("Subtotal"));
                    String sql1 = "SELECT * FROM resultado WHERE  id_Det_Orden=" + det.getId_det_orden() + "";
                    List<Configuracion_DTO> confs = new ArrayList<>();
                    try (PreparedStatement pstm1 = con.prepareStatement(sql1); ResultSet rs1 = pstm1.executeQuery();) {
                        while (rs1.next()) {
                            Configuracion_DTO cnf = new Configuracion_DTO();
                            Resultado_DTO res = new Resultado_DTO();
                            res.setId_resultado(rs1.getInt("id_resultado"));
                            if (res.getId_resultado() != 0) {
                                r = true;
                                cnf.setId_Configuración(rs1.getInt("id_Configuracion"));
                                res.setValor_Obtenido(rs1.getString("Valor_Obtenido"));
                                det.getEstudio().setAddRes(true);
                                String sql2 = "SELECT * FROM configuracion WHERE id_Configuracion=" + cnf.getId_Configuración() + "";
                                try (PreparedStatement pstm2 = con.prepareStatement(sql2); ResultSet rs2 = pstm2.executeQuery();) {
                                    while (rs2.next()) {
                                        cnf.setDescripcion(rs2.getString("Descripcion"));
                                        cnf.setSexo(rs2.getString("sexo"));
                                        cnf.setValor_min(rs2.getString("Valor_min"));
                                        cnf.setValor_MAX(rs2.getString("Valor_MAX"));
                                        cnf.setUniddes(rs2.getString("Unidades"));
                                    }

                                }
                                cnf.setRes(res);
                                confs.add(cnf);
                            }
                        }
                    }
                    det.getEstudio().getCnfs().forEach((Configuracion_DTO confE) -> {
                        confs.stream().filter((confR) -> (confE.getId_Configuración() == confR.getId_Configuración())).forEachOrdered((confR) -> {
                            det.getEstudio().getCnfs().set(det.getEstudio().getCnfs().indexOf(confE), confR);
                        });
                    });
                    String sql3 = "SELECT * FROM observacion WHERE  id_Det_Orden=" + det.getId_det_orden() + "";
                    try (PreparedStatement pstm3 = con.prepareStatement(sql3); ResultSet rs3 = pstm3.executeQuery();) {
                        while (rs3.next()) {
                            Observacion_DTO Obs = new Observacion_DTO();
                            Obs.setId_Observacion(rs3.getInt("id_Observacion"));
                            Obs.setObservacion(rs3.getString("observacion"));
                            det.getEstudio().setObservacion(Obs);
                        }
                    }
                    dets.add(det);
                }
                ord.setDet_Orden(dets);

            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return ord;
    }

    public List<Orden_DTO> getOrdenesSaldo(int id_Unidad) {
        /*las Ordenes con 'SALDO' son cuando aún no se termina de pagar aún cuando ya tenga resltados*/
        List<Orden_DTO> ords = new ArrayList<>();
        Estudio_DAO E = new Estudio_DAO();
        Unidad_DAO U = new Unidad_DAO();
        Paciente_DAO P = new Paciente_DAO();
        Medico_DAO M = new Medico_DAO();
        Persona_DAO Pr = new Persona_DAO();
        try (Connection con = Conexion.getCon()) {
            String sql = "SELECT * FROM orden  WHERE id_Unidad=" + id_Unidad + " AND montoRes>0";
            try (PreparedStatement pstm = con.prepareStatement(sql); ResultSet rs = pstm.executeQuery();) {
                while (rs.next()) {
                    Orden_DTO ord = new Orden_DTO();
                    ord.setId_Orden(rs.getInt("id_Orden"));
                    ord.setUnidad(U.getUnidadAll(rs.getInt("id_Unidad")));
                    ord.setPaciente(P.getPaciente(rs.getInt("id_Paciente")));
                    ord.setMedico(M.getMedico(rs.getInt("id_Medico")));
                    ord.setEmpleado(Pr.getPersona(rs.getInt("id_Persona")));
                    ord.setFecha(rs.getString("Fecha_Orden"));
                    ord.setHora(rs.getString("Hora_Orden"));
                    ord.setMontoPagado(rs.getFloat("Precio_Total"));
                    ord.setMontoRestante(rs.getFloat("montoRes"));
                    ord.setEstado(rs.getString("Estado"));
                    ord.setConvenio(rs.getString("convenio"));
                    ord.setFolio_Unidad(rs.getInt("folio_unidad"));
                    ords.add(ord);
                }
            }

            for (Orden_DTO ord : ords) {
                List<Det_Orden_DTO> dets = new ArrayList<>();
                sql = "SELECT * FROM det_orden WHERE  id_Orden=" + ord.getId_Orden() + "";
                try (PreparedStatement pstm = con.prepareStatement(sql); ResultSet rs = pstm.executeQuery();) {
                    Boolean r = false;
                    while (rs.next()) {
                        Det_Orden_DTO det = new Det_Orden_DTO();
                        det.setId_det_orden(rs.getInt("id_Det_Orden"));
                        det.setEstudio(E.getEst_Uni(rs.getInt("id_Est_Uni")));
                        det.setDescuento(rs.getFloat("Descuento"));
                        det.setFecha_Entrega(rs.getString("Fecha_Entrega"));
                        det.setT_Entrega(rs.getString("Tipo_Entrega"));
                        det.setSubtotal(rs.getFloat("Subtotal"));
                        String sql1 = "SELECT * FROM resultado WHERE  id_Det_Orden=" + det.getId_det_orden() + "";
                        List<Configuracion_DTO> confs = new ArrayList<>();
                        try (PreparedStatement pstm1 = con.prepareStatement(sql1); ResultSet rs1 = pstm1.executeQuery();) {
                            while (rs1.next()) {
                                Configuracion_DTO cnf = new Configuracion_DTO();
                                Resultado_DTO res = new Resultado_DTO();
                                res.setId_resultado(rs1.getInt("id_resultado"));
                                if (res.getId_resultado() != 0) {
                                    r = true;
                                    cnf.setId_Configuración(rs1.getInt("id_Configuracion"));
                                    res.setValor_Obtenido(rs1.getString("Valor_Obtenido"));
                                    det.getEstudio().setAddRes(true);
                                    String sql2 = "SELECT * FROM configuracion WHERE id_Configuracion=" + cnf.getId_Configuración() + "";
                                    try (PreparedStatement pstm2 = con.prepareStatement(sql2); ResultSet rs2 = pstm2.executeQuery();) {
                                        while (rs2.next()) {
                                            cnf.setDescripcion(rs2.getString("Descripcion"));
                                            cnf.setSexo(rs2.getString("sexo"));
                                            cnf.setValor_min(rs2.getString("Valor_min"));
                                            cnf.setValor_MAX(rs2.getString("Valor_MAX"));
                                            cnf.setUniddes(rs2.getString("Unidades"));
                                        }

                                    }
                                    cnf.setRes(res);
                                    confs.add(cnf);
                                }
                            }
                        }
                        det.getEstudio().getCnfs().forEach((Configuracion_DTO confE) -> {
                            confs.stream().filter((confR) -> (confE.getId_Configuración() == confR.getId_Configuración())).forEachOrdered((confR) -> {
                                det.getEstudio().getCnfs().set(det.getEstudio().getCnfs().indexOf(confE), confR);
                            });
                        });
                        dets.add(det);
                    }
                    ord.setDet_Orden(dets);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return ords;
    }

    public Orden_DTO getOrdenSaldo(int folio_unidad, int id_Unidad) {
        Orden_DTO ord = new Orden_DTO();
        Estudio_DAO E = new Estudio_DAO();
        Unidad_DAO U = new Unidad_DAO();
        Paciente_DAO P = new Paciente_DAO();
        Medico_DAO M = new Medico_DAO();
        Persona_DAO Pr = new Persona_DAO();
        try (Connection con = Conexion.getCon()) {
            String sql = "SELECT * FROM orden  WHERE id_Unidad=" + id_Unidad + " AND folio_unidad=" + folio_unidad + " AND montoRes>0";
            try (PreparedStatement pstm = con.prepareStatement(sql); ResultSet rs = pstm.executeQuery();) {
                while (rs.next()) {
                    ord.setId_Orden(rs.getInt("id_Orden"));
                    ord.setUnidad(U.getUnidadAll(rs.getInt("id_Unidad")));
                    ord.setPaciente(P.getPaciente(rs.getInt("id_Paciente")));
                    ord.setMedico(M.getMedico(rs.getInt("id_Medico")));
                    ord.setEmpleado(Pr.getPersona(rs.getInt("id_Persona")));
                    ord.setFecha(rs.getString("Fecha_Orden"));
                    ord.setHora(rs.getString("Hora_Orden"));
                    ord.setMontoPagado(rs.getFloat("Precio_Total"));
                    ord.setMontoRestante(rs.getFloat("montoRes"));
                    ord.setEstado(rs.getString("Estado"));
                    ord.setConvenio(rs.getString("convenio"));
                    ord.setFolio_Unidad(rs.getInt("folio_unidad"));
                }
            }

            List<Det_Orden_DTO> dets = new ArrayList<>();
            sql = "SELECT * FROM det_orden WHERE  id_Orden=" + ord.getId_Orden() + "";
            try (PreparedStatement pstm = con.prepareStatement(sql); ResultSet rs = pstm.executeQuery();) {
                Boolean r = false;
                while (rs.next()) {
                    Det_Orden_DTO det = new Det_Orden_DTO();
                    det.setId_det_orden(rs.getInt("id_Det_Orden"));
                    det.setEstudio(E.getEst_Uni(rs.getInt("id_Est_Uni")));
                    det.setDescuento(rs.getFloat("Descuento"));
                    det.setFecha_Entrega(rs.getString("Fecha_Entrega"));
                    det.setT_Entrega(rs.getString("Tipo_Entrega"));
                    det.setSubtotal(rs.getFloat("Subtotal"));
                    String sql1 = "SELECT * FROM resultado WHERE  id_Det_Orden=" + det.getId_det_orden() + "";
                    List<Configuracion_DTO> confs = new ArrayList<>();
                    try (PreparedStatement pstm1 = con.prepareStatement(sql1); ResultSet rs1 = pstm1.executeQuery();) {
                        while (rs1.next()) {
                            Configuracion_DTO cnf = new Configuracion_DTO();
                            Resultado_DTO res = new Resultado_DTO();
                            res.setId_resultado(rs1.getInt("id_resultado"));
                            if (res.getId_resultado() != 0) {
                                r = true;
                                cnf.setId_Configuración(rs1.getInt("id_Configuracion"));
                                res.setValor_Obtenido(rs1.getString("Valor_Obtenido"));
                                det.getEstudio().setAddRes(true);
                                String sql2 = "SELECT * FROM configuracion WHERE id_Configuracion=" + cnf.getId_Configuración() + "";
                                try (PreparedStatement pstm2 = con.prepareStatement(sql2); ResultSet rs2 = pstm2.executeQuery();) {
                                    while (rs2.next()) {
                                        cnf.setDescripcion(rs2.getString("Descripcion"));
                                        cnf.setSexo(rs2.getString("sexo"));
                                        cnf.setValor_min(rs2.getString("Valor_min"));
                                        cnf.setValor_MAX(rs2.getString("Valor_MAX"));
                                        cnf.setUniddes(rs2.getString("Unidades"));
                                    }

                                }
                                cnf.setRes(res);
                                confs.add(cnf);
                            }
                        }
                    }
                    det.getEstudio().getCnfs().forEach((Configuracion_DTO confE) -> {
                        confs.stream().filter((confR) -> (confE.getId_Configuración() == confR.getId_Configuración())).forEachOrdered((confR) -> {
                            det.getEstudio().getCnfs().set(det.getEstudio().getCnfs().indexOf(confE), confR);
                        });
                    });
                    dets.add(det);
                }
                ord.setDet_Orden(dets);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return ord;
    }

    public List<Orden_DTO> getOrdenesTerminadas(int id_Unidad) {
        List<Orden_DTO> ords = new ArrayList<>();
        Estudio_DAO E = new Estudio_DAO();
        Unidad_DAO U = new Unidad_DAO();
        Paciente_DAO P = new Paciente_DAO();
        Medico_DAO M = new Medico_DAO();
        Persona_DAO Pr = new Persona_DAO();
        try (Connection con = Conexion.getCon()) {
            String sql = "SELECT * FROM orden  WHERE Estado='Finalizado' AND id_Unidad=" + id_Unidad + "  AND montoRes=0";
            try (PreparedStatement pstm = con.prepareStatement(sql); ResultSet rs = pstm.executeQuery();) {
                while (rs.next()) {
                    Orden_DTO ord = new Orden_DTO();
                    ord.setId_Orden(rs.getInt("id_Orden"));
                    ord.setUnidad(U.getUnidadAll(rs.getInt("id_Unidad")));
                    ord.setPaciente(P.getPaciente(rs.getInt("id_Paciente")));
                    ord.setMedico(M.getMedico(rs.getInt("id_Medico")));
                    ord.setEmpleado(Pr.getPersona(rs.getInt("id_Persona")));
                    ord.setFecha(rs.getString("Fecha_Orden"));
                    ord.setHora(rs.getString("Hora_Orden"));
                    ord.setMontoPagado(rs.getFloat("Precio_Total"));
                    ord.setMontoRestante(rs.getFloat("montoRes"));
                    ord.setEstado(rs.getString("Estado"));
                    ord.setConvenio(rs.getString("convenio"));
                    ord.setFolio_Unidad(rs.getInt("folio_unidad"));
                    ords.add(ord);
                }
            }

            for (Orden_DTO ord : ords) {
                List<Det_Orden_DTO> dets = new ArrayList<>();
                sql = "SELECT * FROM det_orden WHERE  id_Orden=" + ord.getId_Orden() + "";
                try (PreparedStatement pstm = con.prepareStatement(sql); ResultSet rs = pstm.executeQuery();) {
                    Boolean r = false;
                    while (rs.next()) {
                        Det_Orden_DTO det = new Det_Orden_DTO();
                        det.setId_det_orden(rs.getInt("id_Det_Orden"));
                        det.setEstudio(E.getEst_Uni(rs.getInt("id_Est_Uni")));
                        det.setDescuento(rs.getFloat("Descuento"));
                        det.setFecha_Entrega(rs.getString("Fecha_Entrega"));
                        det.setT_Entrega(rs.getString("Tipo_Entrega"));
                        det.setSubtotal(rs.getFloat("Subtotal"));
                        String sql1 = "SELECT * FROM resultado WHERE  id_Det_Orden=" + det.getId_det_orden() + "";
                        List<Configuracion_DTO> confs = new ArrayList<>();
                        try (PreparedStatement pstm1 = con.prepareStatement(sql1); ResultSet rs1 = pstm1.executeQuery();) {
                            while (rs1.next()) {
                                Configuracion_DTO cnf = new Configuracion_DTO();
                                Resultado_DTO res = new Resultado_DTO();
                                res.setId_resultado(rs1.getInt("id_resultado"));
                                if (res.getId_resultado() != 0) {
                                    r = true;
                                    cnf.setId_Configuración(rs1.getInt("id_Configuracion"));
                                    res.setValor_Obtenido(rs1.getString("Valor_Obtenido"));
                                    det.getEstudio().setAddRes(true);
                                    String sql2 = "SELECT * FROM configuracion WHERE id_Configuracion=" + cnf.getId_Configuración() + "";
                                    try (PreparedStatement pstm2 = con.prepareStatement(sql2); ResultSet rs2 = pstm2.executeQuery();) {
                                        while (rs2.next()) {
                                            cnf.setDescripcion(rs2.getString("Descripcion"));
                                            cnf.setSexo(rs2.getString("sexo"));
                                            cnf.setValor_min(rs2.getString("Valor_min"));
                                            cnf.setValor_MAX(rs2.getString("Valor_MAX"));
                                            cnf.setUniddes(rs2.getString("Unidades"));
                                        }

                                    }
                                    cnf.setRes(res);
                                    confs.add(cnf);
                                }
                            }
                        }
                        det.getEstudio().getCnfs().forEach((Configuracion_DTO confE) -> {
                            confs.stream().filter((confR) -> (confE.getId_Configuración() == confR.getId_Configuración())).forEachOrdered((confR) -> {
                                det.getEstudio().getCnfs().set(det.getEstudio().getCnfs().indexOf(confE), confR);
                            });
                        });
                        dets.add(det);
                    }
                    ord.setDet_Orden(dets);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return ords;
    }

    public Orden_DTO getOrdenTerminada(int folio_unidad, int id_Unidad) {
        Orden_DTO ord = new Orden_DTO();
        Estudio_DAO E = new Estudio_DAO();
        Unidad_DAO U = new Unidad_DAO();
        Paciente_DAO P = new Paciente_DAO();
        Medico_DAO M = new Medico_DAO();
        Persona_DAO Pr = new Persona_DAO();
        try (Connection con = Conexion.getCon()) {
            String sql = "SELECT * FROM orden  WHERE Estado='Finalizado' AND id_Unidad=" + id_Unidad + " AND folio_unidad=" + folio_unidad + " AND montoRes=0";
            try (PreparedStatement pstm = con.prepareStatement(sql); ResultSet rs = pstm.executeQuery();) {
                while (rs.next()) {
                    ord.setId_Orden(rs.getInt("id_Orden"));
                    ord.setUnidad(U.getUnidadAll(rs.getInt("id_Unidad")));
                    ord.setPaciente(P.getPaciente(rs.getInt("id_Paciente")));
                    ord.setMedico(M.getMedico(rs.getInt("id_Medico")));
                    ord.setEmpleado(Pr.getPersona(rs.getInt("id_Persona")));
                    ord.setFecha(rs.getString("Fecha_Orden"));
                    ord.setHora(rs.getString("Hora_Orden"));
                    ord.setMontoPagado(rs.getFloat("Precio_Total"));
                    ord.setMontoRestante(rs.getFloat("montoRes"));
                    ord.setEstado(rs.getString("Estado"));
                    ord.setConvenio(rs.getString("convenio"));
                    ord.setFolio_Unidad(rs.getInt("folio_unidad"));
                }
            }

            List<Det_Orden_DTO> dets = new ArrayList<>();
            sql = "SELECT * FROM det_orden WHERE  id_Orden=" + ord.getId_Orden() + "";
            try (PreparedStatement pstm = con.prepareStatement(sql); ResultSet rs = pstm.executeQuery();) {
                Boolean r = false;
                while (rs.next()) {
                    Det_Orden_DTO det = new Det_Orden_DTO();
                    det.setId_det_orden(rs.getInt("id_Det_Orden"));
                    det.setEstudio(E.getEst_Uni(rs.getInt("id_Est_Uni")));
                    det.setDescuento(rs.getFloat("Descuento"));
                    det.setFecha_Entrega(rs.getString("Fecha_Entrega"));
                    det.setT_Entrega(rs.getString("Tipo_Entrega"));
                    det.setSubtotal(rs.getFloat("Subtotal"));
                    String sql1 = "SELECT * FROM resultado WHERE  id_Det_Orden=" + det.getId_det_orden() + "";
                    List<Configuracion_DTO> confs = new ArrayList<>();
                    try (PreparedStatement pstm1 = con.prepareStatement(sql1); ResultSet rs1 = pstm1.executeQuery();) {
                        while (rs1.next()) {
                            Configuracion_DTO cnf = new Configuracion_DTO();
                            Resultado_DTO res = new Resultado_DTO();
                            res.setId_resultado(rs1.getInt("id_resultado"));
                            if (res.getId_resultado() != 0) {
                                r = true;
                                cnf.setId_Configuración(rs1.getInt("id_Configuracion"));
                                res.setValor_Obtenido(rs1.getString("Valor_Obtenido"));
                                det.getEstudio().setAddRes(true);
                                String sql2 = "SELECT * FROM configuracion WHERE id_Configuracion=" + cnf.getId_Configuración() + "";
                                try (PreparedStatement pstm2 = con.prepareStatement(sql2); ResultSet rs2 = pstm2.executeQuery();) {
                                    while (rs2.next()) {
                                        cnf.setDescripcion(rs2.getString("Descripcion"));
                                        cnf.setSexo(rs2.getString("sexo"));
                                        cnf.setValor_min(rs2.getString("Valor_min"));
                                        cnf.setValor_MAX(rs2.getString("Valor_MAX"));
                                        cnf.setUniddes(rs2.getString("Unidades"));
                                    }

                                }
                                cnf.setRes(res);
                                confs.add(cnf);
                            }
                        }
                    }
                    det.getEstudio().getCnfs().forEach((Configuracion_DTO confE) -> {
                        confs.stream().filter((confR) -> (confE.getId_Configuración() == confR.getId_Configuración())).forEachOrdered((confR) -> {
                            det.getEstudio().getCnfs().set(det.getEstudio().getCnfs().indexOf(confE), confR);
                        });
                    });
                    dets.add(det);
                }
                ord.setDet_Orden(dets);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return ord;
    }

    public void updateSaldo(Float MontoPagado, Float MontoRestante, Pago_DTO pago) {
        String sql = "UPDATE orden "
                + "set Precio_Total='" + (MontoPagado + pago.getMonto()) + "',"
                + " montoRes='" + (MontoRestante - pago.getMonto()) + "' "
                + "where id_Orden=" + pago.getId_Orden() + "";
        System.out.println(sql);
        try (Connection con = Conexion.getCon();) {
            try (PreparedStatement pstm = con.prepareStatement(sql);) {
                pstm.executeUpdate();
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    public void TerminarOrden(Orden_DTO Orden) {
        String sql = "UPDATE orden "
                + "set Estado=? "
                + "where id_Orden=?";
        System.out.println(sql);
        try (Connection con = Conexion.getCon();) {
            try (PreparedStatement pstm = con.prepareStatement(sql);) {
                pstm.setString(1, Orden.getEstado());
                pstm.setInt(2,Orden.getId_Orden());
                pstm.executeUpdate();
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

//    public static void main(String[] args) {
//        Orden_DAO O = new Orden_DAO();
//        Orden_DTO Orden = O.getOrdenByFolio(4, 1);
//        int r = 0;
//        for (Det_Orden_DTO d : Orden.getDet_Orden()) {
//            if (d.getEstudio().getAddRes()) {
//                r = r + 2;
//                r = d.getEstudio().getCnfs().stream().filter((c) -> (c.getRes() != null)).map((_item) -> 1).reduce(r, Integer::sum);
//            }
//        }
//        String Source = "";
//        if (r < 35) {
//            Source = "M/MembreteRes1.pdf";
//        } else if (r > 35 & r < 70) {
//            Source = "M/MembreteRes2.pdf";
//        } else if (r > 70 & r < 105) {
//            Source = "M/MembreteRes3.pdf";
//        } else if (r > 140 & r < 175) {
//            Source = "M/MembreteRes4.pdf";
//        }
//        System.out.println("Id_Orden=" + Orden.getId_Orden());
//        for (Det_Orden_DTO dto : Orden.getDet_Orden()) {
//            System.out.println("Orden:" + dto.getId_orden() + " Id_Det_Orden=" + dto.getId_det_orden());
//            if (dto.getEstudio().getAddRes()) {
//                for (Configuracion_DTO cnf : dto.getEstudio().getCnfs()) {
//                    if (cnf.getRes() != null) {
//                        System.out.println("Id_Resultado=" + cnf.getRes().getId_resultado());
//                    }
//                }
//            }
//        }
//
//    }
}
