package DataAccesObject;

import DataBase.Conexion;
import DataTransferObject.Empleado_DTO;
import DataTransferObject.Usuario_DTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Carlos Rivas
 */
public class Empleado_DAO {

    public int RegistrarEmpleado(Empleado_DTO dto) {
        int id_Empleado = 0;
        String sql = "INSERT INTO empleado VALUES(NULL," + dto.getId_Unidad() + "," + dto.getId_Persona() + ","
                + "'" + dto.getCurp() + "','" + dto.getNss() + "','" + dto.getFecha_Ing() + "','" + dto.getSalario_Bto() + "',"
                + "'" + dto.getHora_Ent() + "','" + dto.getHora_Com() + "','" + dto.getHora_Reg() + "','" + dto.getHora_Sal() + "')";        
        try (Connection con = Conexion.getCon();
                PreparedStatement pstm = con.prepareStatement(sql);) {
            if (pstm.executeUpdate() == 1) {
                sql = "SELECT id_Empleado from empleado WHERE id_Unidad=" + dto.getId_Unidad() + " AND id_Persona=" + dto.getId_Persona() + ""
                        + " AND curp='" + dto.getCurp() + "' AND nss='" + dto.getNss() + "' AND Fecha_Ing='" + dto.getFecha_Ing() + "'";                
                try (PreparedStatement pstm1 = con.prepareStatement(sql);
                        ResultSet rs = pstm1.executeQuery();) {
                    while (rs.next()) {
                        id_Empleado = rs.getInt("id_Empleado");
                    }
                }
                if (id_Empleado != 0) {
                    for (String dia : dto.getDias_Trabajo()) {
                        sql = "INSERT INTO dias_trab VALUES(" + id_Empleado + "," + dia + ")";                        
                        try (PreparedStatement pstm2 = con.prepareStatement(sql);) {
                            pstm2.executeUpdate();
                        }
                    }
                }
            }
            con.close();
            return id_Empleado;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<Empleado_DTO> getEmpleadosByUnidad(int id_Unidad) {
        try {
            List<Empleado_DTO> lst;
            try (Connection con = Conexion.getCon()) {
                String sql = "SELECT * FROM empleado WHERE id_Unidad=" + id_Unidad + "";
                PreparedStatement pstm = con.prepareStatement(sql);
                ResultSet rs = pstm.executeQuery();
                lst = new ArrayList<>();
                while (rs.next()) {
                    Empleado_DTO empleado = new Empleado_DTO();
                    empleado.setId_Empleado(rs.getInt("id_Empleado"));
                    empleado.setId_Unidad(rs.getInt("id_Unidad"));
                    empleado.setId_Persona(rs.getInt("id_Persona"));
                    empleado.setCurp(rs.getString("curp"));
                    empleado.setNss(rs.getString("nss"));
                    empleado.setFecha_Ing(rs.getString("Fecha_Ing"));
                    empleado.setSalario_Bto(rs.getFloat("Salario_Bto"));
                    empleado.setHora_Ent(rs.getString("Hora_Ent"));
                    empleado.setHora_Com(rs.getString("Hora_Com"));
                    empleado.setHora_Reg(rs.getString("Hora_Reg"));
                    empleado.setHora_Sal(rs.getString("Hora_Sal"));
                    lst.add(empleado);
                }
                rs.close();
                pstm.close();
                for (Empleado_DTO dto : lst) {
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
                for (Empleado_DTO dto : lst) {
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
                for (Empleado_DTO dto : lst) {
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
                for (Empleado_DTO dto : lst) {
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
                for (Empleado_DTO dto : lst) {
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
                for (Empleado_DTO dto : lst) {
                    sql = "SELECT Nombre_Estado FROM estado WHERE id_Estado=" + dto.getId_Estado() + "";
                    pstm = con.prepareStatement(sql);
                    rs = pstm.executeQuery();
                    while (rs.next()) {
                        dto.setNombre_Estado(rs.getString("Nombre_Estado"));
                    }
                    rs.close();
                    pstm.close();
                }
                for (Empleado_DTO dto : lst) {
                    Usuario_DTO usuario = new Usuario_DTO();
                    sql = "SELECT * FROM usuario WHERE id_Persona=" + dto.getId_Persona() + "";
                    pstm = con.prepareStatement(sql);
                    rs = pstm.executeQuery();
                    while (rs.next()) {
                        usuario.setId_Usuario(rs.getInt("id_Usuario"));
                        usuario.setId_Unidad(rs.getInt("id_Unidad"));
                        usuario.setNombre_Usuario(rs.getString("Nombre_Usuario"));
                        usuario.setContraseña(rs.getString("Contrasena"));
                        usuario.setRol(rs.getString("Rol"));
                        usuario.setEstado(rs.getString("Estado"));
                    }
                    dto.setUsuario(usuario);
                    rs.close();
                    pstm.close();
                }
                for (Empleado_DTO dto : lst) {
                    List<String> pers = new ArrayList<>();
                    sql = "SELECT id_Permiso FROM use_per WHERE id_Usuario=" + dto.getUsuario().getId_Usuario() + "";
                    pstm = con.prepareStatement(sql);
                    rs = pstm.executeQuery();
                    while (rs.next()) {
                        String sql1 = "select nombre from permiso where id_Permiso=" + rs.getInt("id_Permiso") + "";
                        PreparedStatement pstm1 = con.prepareStatement(sql1);
                        try (ResultSet rs1 = pstm1.executeQuery()) {
                            while (rs1.next()) {
                                pers.add(rs1.getString("nombre"));
                            }
                        }
                    }
                    dto.setPermisos(pers);
                    rs.close();
                    pstm.close();
                }
                for (Empleado_DTO dto : lst) {
                    List<String> diasL = new ArrayList<>();
                    sql = "SELECT id_Dia FROM dias_trab WHERE id_Empleado=" + dto.getId_Empleado() + "";
                    pstm = con.prepareStatement(sql);
                    rs = pstm.executeQuery();
                    while (rs.next()) {
                        String sql1 = "select Dia from semana where id_Dia=" + rs.getInt("id_Dia") + "";
                        PreparedStatement pstm1 = con.prepareStatement(sql1);
                        try (ResultSet rs1 = pstm1.executeQuery()) {
                            while (rs1.next()) {
                                diasL.add(rs1.getString("Dia"));
                            }
                        }
                    }
                    dto.setDias_Trabajo(diasL);
                    rs.close();
                    pstm.close();
                }
            }
            return lst;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Empleado_DTO getEmpleado(int id_Persona) {
        try {
            Empleado_DTO empleado;
            try (Connection con = Conexion.getCon()) {
                String sql = "SELECT * FROM empleado WHERE id_Persona=" + id_Persona + "";
                PreparedStatement pstm = con.prepareStatement(sql);
                ResultSet rs = pstm.executeQuery();
                empleado = new Empleado_DTO();
                while (rs.next()) {
                    empleado.setId_Empleado(rs.getInt("id_Empleado"));
                    empleado.setId_Unidad(rs.getInt("id_Unidad"));
                    empleado.setId_Persona(rs.getInt("id_Persona"));
                    empleado.setCurp(rs.getString("curp"));
                    empleado.setNss(rs.getString("nss"));
                    empleado.setFecha_Ing(rs.getString("Fecha_Ing"));
                    empleado.setSalario_Bto(rs.getFloat("Salario_Bto"));
                    empleado.setHora_Ent(rs.getString("Hora_Ent"));
                    empleado.setHora_Com(rs.getString("Hora_Com"));
                    empleado.setHora_Reg(rs.getString("Hora_Reg"));
                    empleado.setHora_Sal(rs.getString("Hora_Sal"));
                }
                rs.close();
                pstm.close();
                sql = "SELECT * FROM persona WHERE id_Persona=" + empleado.getId_Persona() + "";
                pstm = con.prepareStatement(sql);
                rs = pstm.executeQuery();
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
                Usuario_DTO usuario = new Usuario_DTO();
                sql = "SELECT * FROM usuario WHERE id_Persona=" + empleado.getId_Persona() + "";
                pstm = con.prepareStatement(sql);
                rs = pstm.executeQuery();
                while (rs.next()) {
                    usuario.setId_Usuario(rs.getInt("id_Usuario"));
                    usuario.setId_Unidad(rs.getInt("id_Unidad"));
                    usuario.setNombre_Usuario(rs.getString("Nombre_Usuario"));
                    usuario.setContraseña(rs.getString("Contrasena"));
                    usuario.setRol(rs.getString("Rol"));
                    usuario.setEstado(rs.getString("Estado"));
                }
                empleado.setUsuario(usuario);
                rs.close();
                pstm.close();
                List<String> pers = new ArrayList<>();
                sql = "SELECT id_Permiso FROM use_per WHERE id_Usuario=" + empleado.getUsuario().getId_Usuario() + "";
                pstm = con.prepareStatement(sql);
                rs = pstm.executeQuery();
                while (rs.next()) {
                    String sql1 = "select nombre from permiso where id_Permiso=" + rs.getInt("id_Permiso") + "";
                    PreparedStatement pstm1 = con.prepareStatement(sql1);
                    try (ResultSet rs1 = pstm1.executeQuery()) {
                        while (rs1.next()) {
                            pers.add(rs1.getString("nombre"));
                        }
                    }
                }
                empleado.setPermisos(pers);
                rs.close();
                pstm.close();
                List<String> diasL = new ArrayList<>();
                sql = "SELECT id_Dia FROM dias_trab WHERE id_Empleado=" + empleado.getId_Empleado() + "";
                pstm = con.prepareStatement(sql);
                rs = pstm.executeQuery();
                while (rs.next()) {
                    String sql1 = "select Dia from semana where id_Dia=" + rs.getInt("id_Dia") + "";
                    PreparedStatement pstm1 = con.prepareStatement(sql1);
                    try (ResultSet rs1 = pstm1.executeQuery()) {
                        while (rs1.next()) {
                            diasL.add(rs1.getString("Dia"));
                        }
                    }
                }
                empleado.setDias_Trabajo(diasL);
                rs.close();
                pstm.close();
            }
            if (empleado.getId_Empleado() != 0) {
                return empleado;
            } else {
                return null;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    
    public Empleado_DTO getEmpleadoE(int id_Empleado) {
        try {
            Empleado_DTO empleado;
            try (Connection con = Conexion.getCon()) {
                String sql = "SELECT * FROM empleado WHERE id_Empleado=" + id_Empleado + "";
                PreparedStatement pstm = con.prepareStatement(sql);
                ResultSet rs = pstm.executeQuery();
                empleado = new Empleado_DTO();
                while (rs.next()) {
                    empleado.setId_Empleado(rs.getInt("id_Empleado"));
                    empleado.setId_Unidad(rs.getInt("id_Unidad"));
                    empleado.setId_Persona(rs.getInt("id_Persona"));
                    empleado.setCurp(rs.getString("curp"));
                    empleado.setNss(rs.getString("nss"));
                    empleado.setFecha_Ing(rs.getString("Fecha_Ing"));
                    empleado.setSalario_Bto(rs.getFloat("Salario_Bto"));
                    empleado.setHora_Ent(rs.getString("Hora_Ent"));
                    empleado.setHora_Com(rs.getString("Hora_Com"));
                    empleado.setHora_Reg(rs.getString("Hora_Reg"));
                    empleado.setHora_Sal(rs.getString("Hora_Sal"));
                }
                rs.close();
                pstm.close();
                sql = "SELECT * FROM persona WHERE id_Persona=" + empleado.getId_Persona() + "";
                pstm = con.prepareStatement(sql);
                rs = pstm.executeQuery();
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
                Usuario_DTO usuario = new Usuario_DTO();
                sql = "SELECT * FROM usuario WHERE id_Persona=" + empleado.getId_Persona() + "";
                pstm = con.prepareStatement(sql);
                rs = pstm.executeQuery();
                while (rs.next()) {
                    usuario.setId_Usuario(rs.getInt("id_Usuario"));
                    usuario.setId_Unidad(rs.getInt("id_Unidad"));
                    usuario.setNombre_Usuario(rs.getString("Nombre_Usuario"));
                    usuario.setContraseña(rs.getString("Contrasena"));
                    usuario.setRol(rs.getString("Rol"));
                    usuario.setEstado(rs.getString("Estado"));
                }
                empleado.setUsuario(usuario);
                rs.close();
                pstm.close();
                List<String> pers = new ArrayList<>();
                sql = "SELECT id_Permiso FROM use_per WHERE id_Usuario=" + empleado.getUsuario().getId_Usuario() + "";
                pstm = con.prepareStatement(sql);
                rs = pstm.executeQuery();
                while (rs.next()) {
                    String sql1 = "select nombre from permiso where id_Permiso=" + rs.getInt("id_Permiso") + "";
                    PreparedStatement pstm1 = con.prepareStatement(sql1);
                    try (ResultSet rs1 = pstm1.executeQuery()) {
                        while (rs1.next()) {
                            pers.add(rs1.getString("nombre"));
                        }
                    }
                }
                empleado.setPermisos(pers);
                rs.close();
                pstm.close();
                List<String> diasL = new ArrayList<>();
                sql = "SELECT id_Dia FROM dias_trab WHERE id_Empleado=" + empleado.getId_Empleado() + "";
                pstm = con.prepareStatement(sql);
                rs = pstm.executeQuery();
                while (rs.next()) {
                    String sql1 = "select Dia from semana where id_Dia=" + rs.getInt("id_Dia") + "";
                    PreparedStatement pstm1 = con.prepareStatement(sql1);
                    try (ResultSet rs1 = pstm1.executeQuery()) {
                        while (rs1.next()) {
                            diasL.add(rs1.getString("Dia"));
                        }
                    }
                }
                empleado.setDias_Trabajo(diasL);
                rs.close();
                pstm.close();
            }
            if (empleado.getId_Empleado() != 0) {
                return empleado;
            } else {
                return null;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int ActualizarCrNsEmp(Empleado_DTO dto) {
        int rp = 0;
        try (Connection con = Conexion.getCon();) {
            String sql = "UPDATE empleado SET curp='" + dto.getCurp() + "',nss='" + dto.getNss() + "'"
                    + " WHERE id_Empleado=" + dto.getId_Empleado() + " and id_Persona=" + dto.getId_Persona() + "";
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

    public int ActualizarDatosLab(Empleado_DTO dto) {
        int rp = 0;
        try (Connection con = Conexion.getCon();) {
            String sql = "UPDATE empleado SET Fecha_Ing='" + dto.getFecha_Ing() + "',Salario_Bto='" + dto.getSalario_Bto() + "'"
                    + " WHERE id_Empleado=" + dto.getId_Empleado() + " and id_Persona=" + dto.getId_Persona() + "";
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

    public int ActualizarDias(int id_Empleado, List<String> dias) {
        int r = 0;
        try (Connection con = Conexion.getCon();) {
            String sql = "DELETE FROM dias_trab WHERE id_Empleado=" + id_Empleado + "";            
            try (PreparedStatement pstm = con.prepareStatement(sql);) {
                pstm.executeUpdate();
            }
            for (String dia : dias) {
                sql = "INSERT INTO dias_trab VALUES(" + id_Empleado + "," + dia.trim() + ")";                
                try (PreparedStatement pstm2 = con.prepareStatement(sql);) {
                    pstm2.executeUpdate();
                }
            }
            return r;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<String> getDias(int id_Empleado) {
        try (Connection con = Conexion.getCon()) {
            String sql = "SELECT id_Dia FROM dias_trab WHERE id_Empleado=" + id_Empleado + "";
            try (PreparedStatement pstm = con.prepareStatement(sql); ResultSet rs = pstm.executeQuery()) {
                List<String> diasL = new ArrayList<>();
                while (rs.next()) {
                    String sql1 = "select Dia from semana where id_Dia=" + rs.getInt("id_Dia") + "";
                    PreparedStatement pstm1 = con.prepareStatement(sql1);
                    try (ResultSet rs1 = pstm1.executeQuery()) {
                        while (rs1.next()) {
                            diasL.add(rs1.getString("Dia"));
                        }
                    }
                }
                return diasL;
            }
        } catch (SQLException ex) {
            return null;
        }
    }

    public List<String> getPers(int id_Usuario) {
        try (Connection con = Conexion.getCon()) {
            String sql = "SELECT id_Permiso FROM use_per WHERE id_Usuario=" + id_Usuario + "";
            try (PreparedStatement pstm = con.prepareStatement(sql); ResultSet rs = pstm.executeQuery()) {
                List<String> pers = new ArrayList<>();
                while (rs.next()) {
                    String sql1 = "select nombre from permiso where id_Permiso=" + rs.getInt("id_Permiso") + "";
                    PreparedStatement pstm1 = con.prepareStatement(sql1);
                    try (ResultSet rs1 = pstm1.executeQuery()) {
                        while (rs1.next()) {
                            pers.add(rs1.getString("nombre"));
                        }
                    }
                }
                return pers;
            }
        } catch (SQLException ex) {
            return null;
        }
    }

    public int ActualizarHorario(Empleado_DTO dto) {
        int rp = 0;
        try (Connection con = Conexion.getCon();) {
            String sql = "UPDATE empleado SET Hora_Ent='" + dto.getHora_Ent() + "',Hora_Com='" + dto.getHora_Com() + "',"
                    + "Hora_Reg='" + dto.getHora_Reg() + "',Hora_Sal='" + dto.getHora_Sal() + "'"
                    + " WHERE id_Empleado=" + dto.getId_Empleado() + " and id_Persona=" + dto.getId_Persona() + "";
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
