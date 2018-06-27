package DataAccesObject;

import DataBase.Conexion;
import DataTransferObject.Direccion_DTO;
import DataTransferObject.Persona_DTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ZionSystems
 */
public class Persona_DAO {

    public int RegistrarPersona(Persona_DTO dto) {
        int id_Persona = 0;
        try (Connection con = Conexion.getCon();) {
            String sql = "SELECT id_Persona from persona WHERE Nombre='" + dto.getNombre() + "' AND Ap_Paterno='" + dto.getAp_Paterno()
                    + "' AND Ap_Materno='" + dto.getAp_Materno() + "' AND Telefono1='" + dto.getTelefono1() + "'";
            try (PreparedStatement pstm1 = con.prepareStatement(sql);
                    ResultSet rs = pstm1.executeQuery();) {
                while (rs.next()) {
                    id_Persona = rs.getInt("id_Persona");
                }
            }
            if (id_Persona != 0) {
                return id_Persona;
            } else {
                sql = "INSERT INTO persona VALUES(NULL,'" + dto.getNombre() + "','" + dto.getAp_Paterno() + "',"
                        + "'" + dto.getAp_Materno() + "','" + dto.getFecha_Nac() + "','" + dto.getSexo() + "','" + dto.getMail() + "',"
                        + "'" + dto.getTelefono1() + "','" + dto.getTelefono2() + "'," + dto.getId_Direccion() + ")";
                PreparedStatement pstm = con.prepareStatement(sql);
                if (pstm.executeUpdate() == 1) {
                    sql = "SELECT id_Persona from persona WHERE Nombre='" + dto.getNombre() + "' AND Ap_Paterno='" + dto.getAp_Paterno()
                            + "' AND Ap_Materno='" + dto.getAp_Materno() + "' AND Fecha_Nac='" + dto.getFecha_Nac() + "' AND Sexo='" + dto.getSexo() + "' "
                            + "AND Mail='" + dto.getMail() + "' AND Telefono1='" + dto.getTelefono1() + "'";
                    try (PreparedStatement pstm1 = con.prepareStatement(sql);
                            ResultSet rs = pstm1.executeQuery();) {
                        while (rs.next()) {
                            id_Persona = rs.getInt("id_Persona");
                        }
                    }
                }
                con.close();
                return id_Persona;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public int RegistrarPersona2(Persona_DTO dto) {
        int id_Persona = 0;
        try (Connection con = Conexion.getCon();) {
            String sql = "SELECT id_Persona from persona WHERE Nombre='" + dto.getNombre() + "' AND Ap_Paterno='" + dto.getAp_Paterno()
                    + "' AND Ap_Materno='" + dto.getAp_Materno() + "'";
            try (PreparedStatement pstm1 = con.prepareStatement(sql);
                    ResultSet rs = pstm1.executeQuery();) {
                while (rs.next()) {
                    id_Persona = rs.getInt("id_Persona");
                }
            }
            if (id_Persona != 0) {
                return id_Persona;
            } else {
                sql = "INSERT INTO persona (id_Persona,Nombre,Ap_Paterno,Ap_Materno) VALUES(NULL,'" + dto.getNombre() + "','" + dto.getAp_Paterno() + "',"
                        + "'" + dto.getAp_Materno() + "')";
                PreparedStatement pstm = con.prepareStatement(sql);
                if (pstm.executeUpdate() == 1) {
                    sql = "SELECT id_Persona from persona WHERE Nombre='" + dto.getNombre() + "' AND Ap_Paterno='" + dto.getAp_Paterno()
                            + "' AND Ap_Materno='" + dto.getAp_Materno() + "'";
                    try (PreparedStatement pstm1 = con.prepareStatement(sql);
                            ResultSet rs = pstm1.executeQuery();) {
                        while (rs.next()) {
                            id_Persona = rs.getInt("id_Persona");
                        }
                    }
                }
                con.close();
                return id_Persona;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public int ActualizarNombre(Persona_DTO dto) {
        int rp = 0;
        try (Connection con = Conexion.getCon();) {
            String sql = "UPDATE persona SET Nombre='" + dto.getNombre() + "',Ap_Paterno='" + dto.getAp_Paterno() + "',Ap_Materno='" + dto.getAp_Materno() + "'"
                    + " WHERE id_Persona=" + dto.getId_Persona() + "";
            try (PreparedStatement pstm = con.prepareStatement(sql);) {
                rp = pstm.executeUpdate();
            }
            return rp;
        } catch (SQLException ex) {
            Logger.getLogger(Material_DAO.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        return rp;
    }

    public int ActualizarContacto(Persona_DTO dto) {
        int rp = 0;
        try (Connection con = Conexion.getCon();) {
            String sql = "UPDATE persona SET Mail='" + dto.getMail() + "',Telefono1='" + dto.getTelefono1() + "',Telefono2='" + dto.getTelefono2() + "'"
                    + " WHERE id_Persona=" + dto.getId_Persona() + "";
            try (PreparedStatement pstm = con.prepareStatement(sql);) {
                rp = pstm.executeUpdate();
            }
            return rp;
        } catch (SQLException ex) {
            Logger.getLogger(Material_DAO.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        return rp;
    }
    
    public int ActualizarFxSx(Persona_DTO dto) {
        int rp = 0;
        try (Connection con = Conexion.getCon();) {
            String sql = "UPDATE persona SET fecha_nac='" + dto.getFecha_Nac()+ "',sexo='" + dto.getSexo()+ "'"
                    + " WHERE id_Persona=" + dto.getId_Persona() + "";
            try (PreparedStatement pstm = con.prepareStatement(sql);) {
                rp = pstm.executeUpdate();
            }
            return rp;
        } catch (SQLException ex) {
            Logger.getLogger(Material_DAO.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        return rp;
    }

    public int ActualizarDireccion(Persona_DTO dto) {
        if (dto.getId_Direccion() == 0 || dto.getC_p() == null) {
            Direccion_DTO dir = (Direccion_DTO) dto;
            Direccion_DAO D = new Direccion_DAO();
            int ID = D.RegistrarDirección(dir);
            try (Connection con = Conexion.getCon();) {
                String sql = "UPDATE persona SET id_Direccion=" + ID + " WHERE id_Persona=" + dto.getId_Persona() + "";
                try (PreparedStatement pstm = con.prepareStatement(sql);) {
                    pstm.executeUpdate();
                }
            } catch (SQLException ex) {
                Logger.getLogger(Material_DAO.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
            return ID;
        } else {
            int rp = 0;
            try (Connection con = Conexion.getCon();) {
                String sql = "UPDATE direccion SET id_Colonia='" + dto.getId_Colonia() + "',Calle='" + dto.getCalle() + "',No_Int='" + dto.getNo_Int() + "',No_Ext='" + dto.getNo_Ext() + "' "
                        + " WHERE id_Direccion=" + dto.getId_Direccion() + "";
                try (PreparedStatement pstm = con.prepareStatement(sql);) {
                    rp = pstm.executeUpdate();
                }
                return rp;
            } catch (SQLException ex) {
                Logger.getLogger(Material_DAO.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
            return rp;
        }
    }

    public Persona_DTO getPersona(int id_Persona) {
        Persona_DTO empleado;
        try (Connection con = Conexion.getCon()) {
            String sql = "SELECT * FROM persona WHERE id_Persona=" + id_Persona + "";
            PreparedStatement pstm = con.prepareStatement(sql);
            ResultSet rs = pstm.executeQuery();
            empleado = new Persona_DTO();
            empleado.setId_Persona(id_Persona);
            while (rs.next()) {
                empleado.setNombre(rs.getString("Nombre"));
                empleado.setAp_Paterno(rs.getString("Ap_Paterno"));
                empleado.setAp_Materno(rs.getString("Ap_Materno"));
                empleado.setFecha_Nac(rs.getString("Fecha_Nac"));
                empleado.setSexo(rs.getString("Sexo"));
                empleado.setMail(rs.getString("Mail"));
                empleado.setTelefono1(rs.getString("Telefono1"));
                empleado.setTelefono2(rs.getString("Telefono2"));
                empleado.setId_Direccion(rs.getInt("id_Direccion"));
            }
            rs.close();
            pstm.close();
            sql = "SELECT * FROM direccion WHERE id_Direccion=" + empleado.getId_Direccion() + "";
            pstm = con.prepareStatement(sql);
            rs = pstm.executeQuery();
            while (rs.next()) {
                empleado.setId_Colonia(rs.getInt("id_Colonia"));
                empleado.setCalle(rs.getString("Calle"));
                empleado.setNo_Int(rs.getString("No_Int"));
                empleado.setNo_Ext(rs.getString("No_Ext"));
            }
            rs.close();
            pstm.close();

            sql = "SELECT * FROM colonia WHERE id_Colonia=" + empleado.getId_Colonia() + "";
            pstm = con.prepareStatement(sql);
            rs = pstm.executeQuery();
            while (rs.next()) {
                empleado.setId_CP(rs.getInt("id_CP"));
                empleado.setNombre_Colonia(rs.getString("Nombre_Colonia"));
            }
            rs.close();
            pstm.close();
            sql = "SELECT * FROM cp WHERE id_CP='" + empleado.getId_CP() + "'";
            pstm = con.prepareStatement(sql);
            rs = pstm.executeQuery();
            while (rs.next()) {
                empleado.setId_CP(rs.getInt("id_CP"));
                empleado.setId_Municipio(rs.getInt("id_Municipio"));
                empleado.setC_p(rs.getString("c_p"));
            }
            rs.close();
            pstm.close();
            sql = "SELECT id_Estado,Nombre_Municipio FROM municipio WHERE id_Municipio=" + empleado.getId_Municipio() + "";
            pstm = con.prepareStatement(sql);
            rs = pstm.executeQuery();
            while (rs.next()) {
                empleado.setId_Estado(rs.getInt("id_Estado"));
                empleado.setNombre_Municipio(rs.getString("Nombre_Municipio"));
            }
            rs.close();
            pstm.close();

            sql = "SELECT Nombre_Estado FROM estado WHERE id_Estado=" + empleado.getId_Estado() + "";
            pstm = con.prepareStatement(sql);
            rs = pstm.executeQuery();
            while (rs.next()) {
                empleado.setNombre_Estado(rs.getString("Nombre_Estado"));
            }
            rs.close();
            pstm.close();

            return empleado;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
