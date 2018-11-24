package DataAccesObject;

import DataBase.Conexion;
import DataTransferObject.Det_Prom_DTO;
import DataTransferObject.Promocion_DTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ZionSystem
 *
 */
public class Promocion_DAO {

    public int RegistrarPromocion(Promocion_DTO Prom) {
        try (Connection con = Conexion.getCon();) {
            String sql = "INSERT INTO promocion (id_Promocion,id_Unidad,id_Persona,Nombre_Promocion,Descripcion,Fecha_I,Fecha_F,Precio_Total,Estado) "
                    + "VALUES(null,"
                    + "" + Prom.getId_Unidad() + ","
                    + "" + Prom.getEmpleado().getId_Persona() + ","
                    + "'" + Prom.getNombre_Promocion() + "',"
                    + "'" + Prom.getDescripcion() + "',"
                    + "'" + Prom.getFecha_I() + "',"
                    + "'" + Prom.getFecha_F() + "',"
                    + "'" + Prom.getPrecio_Total() + "',"
                    + "'" + Prom.getEstado() + "')";
            System.out.println(sql);
            try (PreparedStatement pstm = con.prepareStatement(sql);) {
                pstm.executeUpdate();
            }
            sql = "select id_Promocion from promocion where id_unidad=" + Prom.getId_Unidad() + " and id_Persona=" + Prom.getEmpleado().getId_Persona() + ""
                    + " and Nombre_Promocion='" + Prom.getNombre_Promocion() + "' and Descripcion='" + Prom.getDescripcion() + "'"
                    + " and Fecha_I='" + Prom.getFecha_I() + "'  and Fecha_F='" + Prom.getFecha_F() + "' and Precio_Total='" + Prom.getPrecio_Total() + "'";
            System.out.println(sql);
            try (PreparedStatement pstm = con.prepareStatement(sql); ResultSet rs = pstm.executeQuery()) {
                while (rs.next()) {
                    Prom.setId_Promocion(rs.getInt("id_Promocion"));
                }
            }
            if (Prom.getId_Promocion() != 0) {
                Det_Prom_DAO D = new Det_Prom_DAO();
                Prom.getDet_Prom().forEach((det) -> {
                    D.registrarDetor(Prom.getId_Promocion(), det);
                });
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return 0;
    }

    public List<Promocion_DTO> getPromociones(int id_Unidad) {
        List<Promocion_DTO> proms = new ArrayList<>();
        Estudio_DAO E = new Estudio_DAO();
        Persona_DAO Pr = new Persona_DAO();
        try (Connection con = Conexion.getCon()) {
            String sql = "SELECT * FROM promocion  WHERE id_Unidad=" + id_Unidad + "";
            try (PreparedStatement pstm = con.prepareStatement(sql); ResultSet rs = pstm.executeQuery();) {
                while (rs.next()) {
                    Promocion_DTO prom = new Promocion_DTO();
                    prom.setId_Promocion(rs.getInt("id_Promocion"));
                    prom.setId_Unidad(rs.getInt("id_Unidad"));
                    prom.setEmpleado(Pr.getPersona(rs.getInt("id_Persona")));
                    prom.setNombre_Promocion(rs.getString("Nombre_Promocion"));
                    prom.setDescripcion(rs.getString("Descripcion"));
                    prom.setFecha_I(rs.getString("Fecha_I"));
                    prom.setFecha_F(rs.getString("Fecha_F"));
                    prom.setPrecio_Total(rs.getFloat("Precio_Total"));
                    prom.setEstado(rs.getString("Estado"));
                    proms.add(prom);
                }
            }
            for (Promocion_DTO prom : proms) {
                List<Det_Prom_DTO> dets = new ArrayList<>();
                sql = "SELECT * FROM det_prom WHERE  id_Promocion=" + prom.getId_Promocion() + "";
                try (PreparedStatement pstm = con.prepareStatement(sql); ResultSet rs = pstm.executeQuery();) {
                    while (rs.next()) {
                        Det_Prom_DTO det = new Det_Prom_DTO();
                        det.setId_Det_Prom(rs.getInt("id_Det_Prom"));
                        det.setEstudio(E.getEst_Uni(rs.getInt("id_Est_Uni")));
                        det.setDescuento(rs.getInt("Descuento"));
                        det.setT_Entrega(rs.getString("Tipo_Entrega"));
                        dets.add(det);
                    }
                    prom.setDet_Prom(dets);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return proms;
    }

    public int ActualizarPrecProm(Float Precio_Total, int id_Promocion) {
        int rp;
        try (Connection con = Conexion.getCon();) {
            String sql = "UPDATE promocion SET Precio_Total='" + Precio_Total + "' WHERE id_Promocion=" + id_Promocion + "";
            try (PreparedStatement pstm = con.prepareStatement(sql);) {
                rp = pstm.executeUpdate();
                pstm.close();
            }
            return rp;
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    public int ActualizarNameProm(String Nombre_Promocion, int id_Promocion) {
        int rp;
        try (Connection con = Conexion.getCon();) {
            String sql = "UPDATE promocion SET Nombre_Promocion='" + Nombre_Promocion + "' WHERE id_Promocion=" + id_Promocion + "";
            try (PreparedStatement pstm = con.prepareStatement(sql);) {
                rp = pstm.executeUpdate();
                pstm.close();
            }
            return rp;
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    public int ActualizarDescProm(String Descripcion, int id_Promocion) {
        int rp;
        try (Connection con = Conexion.getCon();) {
            String sql = "UPDATE promocion SET Descripcion='" + Descripcion + "' WHERE id_Promocion=" + id_Promocion + "";
            try (PreparedStatement pstm = con.prepareStatement(sql);) {
                rp = pstm.executeUpdate();
                pstm.close();
            }
            return rp;
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    public int ActualizarFchsProm(String Fecha_I, String Fecha_F, int id_Promocion) {
        int rp;
        try (Connection con = Conexion.getCon();) {
            String sql = "UPDATE promocion SET Fecha_I='" + Fecha_I + "' ,Fecha_F='" + Fecha_F + "' WHERE id_Promocion=" + id_Promocion + "";
            try (PreparedStatement pstm = con.prepareStatement(sql);) {
                rp = pstm.executeUpdate();
                pstm.close();
            }
            return rp;
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    public int EliminarPromocion(int id_Promocion) {
        int rp;
        try (Connection con = Conexion.getCon();) {
            String sql = "DELETE from det_prom WHERE id_Promocion=" + id_Promocion + "";
            try (PreparedStatement pstm = con.prepareStatement(sql);) {
                rp = pstm.executeUpdate();
                pstm.close();
            }
            if (rp != 0) {
                sql = "DELETE from promocion WHERE id_Promocion=" + id_Promocion + "";
                try (PreparedStatement pstm = con.prepareStatement(sql);) {
                    rp = pstm.executeUpdate();
                    pstm.close();
                }
            }
            return rp;
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

//    public static void main(String[] args) {
//        String Nombre_Promocion = "NameProm";
//        int id_Promocion = 1;
//        String sql = "UPDATE promocion SET Nombre_Promocion='" + Nombre_Promocion + "' WHERE id_Promocion=" + id_Promocion + "";
//        System.out.println(sql);
//    }
}
