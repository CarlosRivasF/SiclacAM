package DataAccesObject;

import DataBase.Conexion;
import DataTransferObject.Medico_DTO;
import DataTransferObject.Paciente_DTO;
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
public class Paciente_DAO {    

    public int RegistrarPaciente(Paciente_DTO dto) {
        int id_Paciente = 0;
        try (Connection con = Conexion.getCon();) {
            String sql = "SELECT id_Paciente from  paciente WHERE id_Persona=" + dto.getId_Persona() + "";            
            try (PreparedStatement pstm1 = con.prepareStatement(sql);
                    ResultSet rs = pstm1.executeQuery();) {
                while (rs.next()) {
                    id_Paciente = rs.getInt("id_Paciente");
                }
            }
            if (id_Paciente != 0) {
                return id_Paciente;
            } else {
                sql = "INSERT INTO  paciente VALUES(NULL," + dto.getId_Unidad() + "," + dto.getId_Persona() + ",'" + dto.getCodPac() + "','" + dto.getSendMail() + "')";                
                PreparedStatement pstm = con.prepareStatement(sql);
                if (pstm.executeUpdate() == 1) {
                    pstm.close();
                    sql = "SELECT id_Paciente from  paciente WHERE id_Unidad=" + dto.getId_Unidad() + " AND id_Persona=" + dto.getId_Persona() + " AND CodPac='" + dto.getCodPac() + "'";                    
                    try (PreparedStatement pstm1 = con.prepareStatement(sql);
                            ResultSet rs = pstm1.executeQuery();) {
                        while (rs.next()) {
                            id_Paciente = rs.getInt("id_Paciente");
                        }
                    }
                }
                con.close();
                return id_Paciente;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<Paciente_DTO> getPacientes() {
        try {
            List<Paciente_DTO> lst;
            try (Connection con = Conexion.getCon()) {
                String sql = "SELECT * FROM paciente";
                PreparedStatement pstm = con.prepareStatement(sql);
                ResultSet rs = pstm.executeQuery();
                lst = new ArrayList<>();
                while (rs.next()) {
                    Paciente_DTO dto = new Paciente_DTO();
                    dto.setId_Paciente(rs.getInt("id_Paciente"));
                    dto.setId_Unidad(rs.getInt("id_Unidad"));
                    dto.setId_Persona(rs.getInt("id_Persona"));
                    dto.setCodPac(rs.getString("CodPac"));
                    dto.setSenMail(Boolean.valueOf(rs.getString("SendMail")));
                    lst.add(dto);
                }
                rs.close();
                pstm.close();
                for (Paciente_DTO dto : lst) {
                    sql = "SELECT * FROM persona WHERE id_Persona=" + dto.getId_Persona() + "";
                    pstm = con.prepareStatement(sql);
                    rs = pstm.executeQuery();
                    while (rs.next()) {
                        dto.setNombre(rs.getString("Nombre"));
                        dto.setAp_Paterno(rs.getString("Ap_Paterno"));
                        dto.setAp_Materno(rs.getString("Ap_Materno"));
                        dto.setFecha_Nac(rs.getString("Fecha_Nac"));
                        dto.setSexo(rs.getString("Sexo"));
                        dto.setMail(rs.getString("Mail"));
                        dto.setTelefono1(rs.getString("Telefono1"));
                        dto.setTelefono2(rs.getString("Telefono2"));
                        dto.setId_Direccion(rs.getInt("id_Direccion"));
                    }
                    rs.close();
                    pstm.close();
                }
                for (Paciente_DTO dto : lst) {
                    sql = "SELECT * FROM direccion WHERE id_Direccion=" + dto.getId_Direccion() + "";
                    pstm = con.prepareStatement(sql);
                    rs = pstm.executeQuery();
                    while (rs.next()) {
                        dto.setId_Colonia(rs.getInt("id_Colonia"));
                        dto.setCalle(rs.getString("Calle"));
                        dto.setNo_Int(rs.getString("No_Int"));
                        dto.setNo_Ext(rs.getString("No_Ext"));
                    }
                    rs.close();
                    pstm.close();
                }
                for (Paciente_DTO dto : lst) {
                    sql = "SELECT * FROM colonia WHERE id_Colonia=" + dto.getId_Colonia() + "";
                    pstm = con.prepareStatement(sql);
                    rs = pstm.executeQuery();
                    while (rs.next()) {
                        dto.setId_CP(rs.getInt("id_CP"));
                        dto.setNombre_Colonia(rs.getString("Nombre_Colonia"));
                    }
                    rs.close();
                    pstm.close();
                }
                for (Paciente_DTO dto : lst) {
                    sql = "SELECT * FROM cp WHERE id_CP='" + dto.getId_CP() + "'";
                    pstm = con.prepareStatement(sql);
                    rs = pstm.executeQuery();
                    while (rs.next()) {
                        dto.setId_CP(rs.getInt("id_CP"));
                        dto.setId_Municipio(rs.getInt("id_Municipio"));
                        dto.setC_p(rs.getString("c_p"));
                    }
                    rs.close();
                    pstm.close();
                }
                for (Paciente_DTO dto : lst) {
                    sql = "SELECT id_Estado,Nombre_Municipio FROM municipio WHERE id_Municipio=" + dto.getId_Municipio() + "";
                    pstm = con.prepareStatement(sql);
                    rs = pstm.executeQuery();
                    while (rs.next()) {
                        dto.setId_Estado(rs.getInt("id_Estado"));
                        dto.setNombre_Municipio(rs.getString("Nombre_Municipio"));
                    }
                    rs.close();
                    pstm.close();
                }
                for (Paciente_DTO dto : lst) {
                    sql = "SELECT Nombre_Estado FROM estado WHERE id_Estado=" + dto.getId_Estado() + "";
                    pstm = con.prepareStatement(sql);
                    rs = pstm.executeQuery();
                    while (rs.next()) {
                        dto.setNombre_Estado(rs.getString("Nombre_Estado"));
                    }
                    rs.close();
                    pstm.close();
                }
            }
            return lst;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Paciente_DTO> getPacientesByUnidad(int id_Unidad) {
        try {
            List<Paciente_DTO> lst;
            try (Connection con = Conexion.getCon()) {
                String sql = "SELECT * FROM paciente WHERE id_Unidad=" + id_Unidad + "";
                PreparedStatement pstm = con.prepareStatement(sql);
                ResultSet rs = pstm.executeQuery();
                lst = new ArrayList<>();
                while (rs.next()) {
                    Paciente_DTO dto = new Paciente_DTO();
                    dto.setId_Paciente(rs.getInt("id_Paciente"));
                    dto.setId_Unidad(rs.getInt("id_Unidad"));
                    dto.setId_Persona(rs.getInt("id_Persona"));
                    dto.setCodPac(rs.getString("CodPac"));
                    dto.setSenMail(Boolean.valueOf(rs.getString("SendMail")));
                    lst.add(dto);
                }
                rs.close();
                pstm.close();
                for (Paciente_DTO dto : lst) {
                    sql = "SELECT * FROM persona WHERE id_Persona=" + dto.getId_Persona() + "";
                    pstm = con.prepareStatement(sql);
                    rs = pstm.executeQuery();
                    while (rs.next()) {
                        dto.setNombre(rs.getString("Nombre"));
                        dto.setAp_Paterno(rs.getString("Ap_Paterno"));
                        dto.setAp_Materno(rs.getString("Ap_Materno"));
                        dto.setFecha_Nac(rs.getString("Fecha_Nac"));
                        dto.setSexo(rs.getString("Sexo"));
                        dto.setMail(rs.getString("Mail"));
                        dto.setTelefono1(rs.getString("Telefono1"));
                        dto.setTelefono2(rs.getString("Telefono2"));
                        dto.setId_Direccion(rs.getInt("id_Direccion"));
                    }
                    rs.close();
                    pstm.close();
                }
                for (Paciente_DTO dto : lst) {
                    sql = "SELECT * FROM direccion WHERE id_Direccion=" + dto.getId_Direccion() + "";
                    pstm = con.prepareStatement(sql);
                    rs = pstm.executeQuery();
                    while (rs.next()) {
                        dto.setId_Colonia(rs.getInt("id_Colonia"));
                        dto.setCalle(rs.getString("Calle"));
                        dto.setNo_Int(rs.getString("No_Int"));
                        dto.setNo_Ext(rs.getString("No_Ext"));
                    }
                    rs.close();
                    pstm.close();
                }
                for (Paciente_DTO dto : lst) {
                    sql = "SELECT * FROM colonia WHERE id_Colonia=" + dto.getId_Colonia() + "";
                    pstm = con.prepareStatement(sql);
                    rs = pstm.executeQuery();
                    while (rs.next()) {
                        dto.setId_CP(rs.getInt("id_CP"));
                        dto.setNombre_Colonia(rs.getString("Nombre_Colonia"));
                    }
                    rs.close();
                    pstm.close();
                }
                for (Paciente_DTO dto : lst) {
                    sql = "SELECT * FROM cp WHERE id_CP='" + dto.getId_CP() + "'";
                    pstm = con.prepareStatement(sql);
                    rs = pstm.executeQuery();
                    while (rs.next()) {
                        dto.setId_CP(rs.getInt("id_CP"));
                        dto.setId_Municipio(rs.getInt("id_Municipio"));
                        dto.setC_p(rs.getString("c_p"));
                    }
                    rs.close();
                    pstm.close();
                }
                for (Paciente_DTO dto : lst) {
                    sql = "SELECT id_Estado,Nombre_Municipio FROM municipio WHERE id_Municipio=" + dto.getId_Municipio() + "";
                    pstm = con.prepareStatement(sql);
                    rs = pstm.executeQuery();
                    while (rs.next()) {
                        dto.setId_Estado(rs.getInt("id_Estado"));
                        dto.setNombre_Municipio(rs.getString("Nombre_Municipio"));
                    }
                    rs.close();
                    pstm.close();
                }
                for (Paciente_DTO dto : lst) {
                    sql = "SELECT Nombre_Estado FROM estado WHERE id_Estado=" + dto.getId_Estado() + "";
                    pstm = con.prepareStatement(sql);
                    rs = pstm.executeQuery();
                    while (rs.next()) {
                        dto.setNombre_Estado(rs.getString("Nombre_Estado"));
                    }
                    rs.close();
                    pstm.close();
                }
            }
            return lst;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Paciente_DTO getPaciente(int id_Paciente) {
        try {
            Paciente_DTO dto = new Paciente_DTO();
            try (Connection con = Conexion.getCon()) {
                String sql = "SELECT * FROM paciente WHERE id_Paciente=" + id_Paciente + "";
                PreparedStatement pstm = con.prepareStatement(sql);
                ResultSet rs = pstm.executeQuery();
                while (rs.next()) {
                    dto.setId_Unidad(rs.getInt("id_Unidad"));
                    dto.setId_Persona(rs.getInt("id_Persona"));
                    dto.setCodPac(rs.getString("CodPac"));
                    dto.setSenMail(Boolean.valueOf(rs.getString("SendMail")));
                }
                rs.close();
                pstm.close();
                sql = "SELECT * FROM persona WHERE id_Persona=" + dto.getId_Persona() + "";
                pstm = con.prepareStatement(sql);
                rs = pstm.executeQuery();
                while (rs.next()) {
                    dto.setNombre(rs.getString("Nombre"));
                    dto.setAp_Paterno(rs.getString("Ap_Paterno"));
                    dto.setAp_Materno(rs.getString("Ap_Materno"));
                    dto.setFecha_Nac(rs.getString("Fecha_Nac"));
                    dto.setSexo(rs.getString("Sexo"));
                    dto.setMail(rs.getString("Mail"));
                    dto.setTelefono1(rs.getString("Telefono1"));
                    dto.setTelefono2(rs.getString("Telefono2"));
                    dto.setId_Direccion(rs.getInt("id_Direccion"));
                }
                rs.close();
                pstm.close();
                sql = "SELECT * FROM direccion WHERE id_Direccion=" + dto.getId_Direccion() + "";
                pstm = con.prepareStatement(sql);
                rs = pstm.executeQuery();
                while (rs.next()) {
                    dto.setId_Colonia(rs.getInt("id_Colonia"));
                    dto.setCalle(rs.getString("Calle"));
                    dto.setNo_Int(rs.getString("No_Int"));
                    dto.setNo_Ext(rs.getString("No_Ext"));
                }
                rs.close();
                pstm.close();
                sql = "SELECT * FROM colonia WHERE id_Colonia=" + dto.getId_Colonia() + "";
                pstm = con.prepareStatement(sql);
                rs = pstm.executeQuery();
                while (rs.next()) {
                    dto.setId_CP(rs.getInt("id_CP"));
                    dto.setNombre_Colonia(rs.getString("Nombre_Colonia"));
                }
                rs.close();
                pstm.close();
                sql = "SELECT * FROM cp WHERE id_CP='" + dto.getId_CP() + "'";
                pstm = con.prepareStatement(sql);
                rs = pstm.executeQuery();
                while (rs.next()) {
                    dto.setId_CP(rs.getInt("id_CP"));
                    dto.setId_Municipio(rs.getInt("id_Municipio"));
                    dto.setC_p(rs.getString("c_p"));
                }
                rs.close();
                pstm.close();
                sql = "SELECT id_Estado,Nombre_Municipio FROM municipio WHERE id_Municipio=" + dto.getId_Municipio() + "";
                pstm = con.prepareStatement(sql);
                rs = pstm.executeQuery();
                while (rs.next()) {
                    dto.setId_Estado(rs.getInt("id_Estado"));
                    dto.setNombre_Municipio(rs.getString("Nombre_Municipio"));
                }
                rs.close();
                pstm.close();
                sql = "SELECT Nombre_Estado FROM estado WHERE id_Estado=" + dto.getId_Estado() + "";
                pstm = con.prepareStatement(sql);
                rs = pstm.executeQuery();
                while (rs.next()) {
                    dto.setNombre_Estado(rs.getString("Nombre_Estado"));
                }
                rs.close();
                pstm.close();

            }
            return dto;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int EliminarPac(Paciente_DTO dto) {
        int rp = 0;
        try (Connection con = Conexion.getCon();) {
            String sql = "DELETE from paciente WHERE id_Paciente=" + dto.getId_Paciente()+ "";
            try (PreparedStatement pstm = con.prepareStatement(sql);) {
                if (pstm.executeUpdate() != 1) {
                    String sql1 = "DELETE from persona WHERE id_Persona=" + dto.getId_Persona() + "";
                    try (PreparedStatement pstm1 = con.prepareStatement(sql1);) {
                        rp = pstm1.executeUpdate();
                        pstm.close();
                    }
                }
            }
            return rp;
        } catch (SQLException ex) {
        }
        return rp;
    }
}
