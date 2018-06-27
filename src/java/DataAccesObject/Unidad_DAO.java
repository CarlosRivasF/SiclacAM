package DataAccesObject;

import DataBase.Conexion;
import DataTransferObject.Persona_DTO;
import DataTransferObject.Unidad_DTO;
import DataTransferObject.Usuario_DTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Carlos Rivas
 */
public class Unidad_DAO {

    public int RegistrarUnidad(Unidad_DTO dto) {
        int id_Unidad = 0;
        String sql = "INSERT INTO unidad (id_Unidad,id_Empresa,Nombre_Unidad,Clave,id_Persona) VALUES(null," + dto.getId_Empresa() + ",'" + dto.getNombre_Unidad() + "','" + dto.getClave() + "'," + dto.getEncargado().getId_Persona() + ")";
        try (Connection con = Conexion.getCon();
                PreparedStatement pstm = con.prepareStatement(sql);) {
            if (pstm.executeUpdate() == 1) {
                pstm.close();
                sql = "SELECT id_Unidad from unidad WHERE id_Empresa=" + dto.getId_Empresa() + " AND Nombre_Unidad='" + dto.getNombre_Unidad() + "' AND Clave='" + dto.getClave() + "' and id_Persona=" + dto.getEncargado().getId_Persona() + "";
                try (PreparedStatement pstm1 = con.prepareStatement(sql);
                        ResultSet rs = pstm1.executeQuery();) {
                    while (rs.next()) {
                        id_Unidad = rs.getInt("id_Unidad");
                    }
                }
            }
            return id_Unidad;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<Unidad_DTO> getUnidadesByEmpresa(int id_Empresa) {
        List<Unidad_DTO> uns;
        String sql = "SELECT id_Unidad,id_Empresa,Nombre_Unidad,Clave,id_Persona FROM unidad where id_Empresa=" + id_Empresa + "";
        try (Connection con = Conexion.getCon();) {
            PreparedStatement pstm = con.prepareStatement(sql);
            try (ResultSet rs = pstm.executeQuery();) {
                uns = new ArrayList<>();
                while (rs.next()) {
                    Unidad_DTO unidad = new Unidad_DTO();
                    Persona_DTO encargado = new Persona_DTO();
                    Usuario_DTO usuario = new Usuario_DTO();
                    unidad.setId_Unidad(rs.getInt("id_Unidad"));
                    unidad.setNombre_Unidad(rs.getString("Nombre_Unidad"));
                    unidad.setClave(rs.getString("Clave"));
                    encargado.setId_Persona(rs.getInt("id_Persona"));
                    usuario.setId_Persona(encargado.getId_Persona());
                    usuario.setId_Unidad(unidad.getId_Unidad());
                    unidad.setEncargado(encargado);
                    unidad.setUsuario(usuario);
                    uns.add(unidad);
                }
            }
            pstm.close();
            for (Unidad_DTO dto : uns) {
                sql = "SELECT * FROM persona where id_Persona=" + dto.getEncargado().getId_Persona() + "";
                pstm = con.prepareStatement(sql);
                try (ResultSet rs = pstm.executeQuery();) {
                    while (rs.next()) {
                        dto.getEncargado().setNombre(rs.getString("Nombre"));
                        dto.getEncargado().setAp_Paterno(rs.getString("Ap_Paterno"));
                        dto.getEncargado().setAp_Materno(rs.getString("Ap_Materno"));
                        dto.getEncargado().setFecha_Nac(rs.getString("Fecha_Nac"));
                        dto.getEncargado().setSexo(rs.getString("Sexo"));
                        dto.getEncargado().setMail(rs.getString("Mail"));
                        dto.getEncargado().setTelefono1(rs.getString("Telefono1"));
                        dto.getEncargado().setTelefono2(rs.getString("Telefono2"));
                        dto.getEncargado().setId_Direccion(rs.getInt("id_Direccion"));
                    }
                }
                pstm.close();
            }
            for (Unidad_DTO dto : uns) {
                sql = "SELECT * FROM direccion where id_Direccion=" + dto.getEncargado().getId_Direccion() + "";
                pstm = con.prepareStatement(sql);
                try (ResultSet rs = pstm.executeQuery();) {
                    while (rs.next()) {
                        dto.getEncargado().setId_Colonia(rs.getInt("id_Colonia"));
                        dto.getEncargado().setCalle(rs.getString("Calle"));
                        dto.getEncargado().setNo_Int(rs.getString("No_Int"));
                        dto.getEncargado().setNo_Ext(rs.getString("No_Ext"));
                    }
                }
                pstm.close();
            }
            for (Unidad_DTO dto : uns) {
                sql = "SELECT * FROM colonia WHERE id_Colonia=" + dto.getEncargado().getId_Colonia() + "";
                pstm = con.prepareStatement(sql);
                try (ResultSet rs = pstm.executeQuery();) {
                    while (rs.next()) {
                        dto.getEncargado().setId_CP(rs.getInt("id_CP"));
                        dto.getEncargado().setNombre_Colonia(rs.getString("Nombre_Colonia"));
                    }
                }
                pstm.close();
            }
            for (Unidad_DTO dto : uns) {
                sql = "SELECT * FROM cp WHERE id_CP='" + dto.getEncargado().getId_CP() + "'";
                pstm = con.prepareStatement(sql);
                try (ResultSet rs = pstm.executeQuery();) {
                    while (rs.next()) {
                        dto.getEncargado().setId_CP(rs.getInt("id_CP"));
                        dto.getEncargado().setId_Municipio(rs.getInt("id_Municipio"));
                        dto.getEncargado().setC_p(rs.getString("c_p"));
                    }
                }
                pstm.close();
            }
            for (Unidad_DTO dto : uns) {
                sql = "SELECT id_Estado,Nombre_Municipio FROM municipio WHERE id_Municipio=" + dto.getEncargado().getId_Municipio() + "";
                pstm = con.prepareStatement(sql);
                try (ResultSet rs = pstm.executeQuery();) {
                    while (rs.next()) {
                        dto.getEncargado().setId_Estado(rs.getInt("id_Estado"));
                        dto.getEncargado().setNombre_Municipio(rs.getString("Nombre_Municipio"));
                    }
                }
                pstm.close();
            }
            for (Unidad_DTO dto : uns) {
                sql = "SELECT Nombre_Estado FROM estado WHERE id_Estado=" + dto.getEncargado().getId_Estado() + "";
                pstm = con.prepareStatement(sql);
                try (ResultSet rs = pstm.executeQuery();) {
                    while (rs.next()) {
                        dto.getEncargado().setNombre_Estado(rs.getString("Nombre_Estado"));
                    }
                }
                pstm.close();
            }
            for (Unidad_DTO dto : uns) {
                sql = "SELECT * FROM usuario WHERE id_Persona=" + dto.getUsuario().getId_Persona() + "";
                pstm = con.prepareStatement(sql);
                try (ResultSet rs = pstm.executeQuery();) {
                    while (rs.next()) {
                        dto.getUsuario().setId_Usuario(rs.getInt("id_Usuario"));
                        dto.getUsuario().setNombre_Usuario(rs.getString("Nombre_Usuario"));
                        dto.getUsuario().setContraseña(rs.getString("Contrasena"));
                        dto.getUsuario().setRol(rs.getString("Rol"));
                        dto.getUsuario().setEstado(rs.getString("Estado"));
                    }
                }
                pstm.close();
            }
            return uns;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Unidad_DTO getUnidadAll(int id_Unidad) {
        Unidad_DTO unidad = new Unidad_DTO();
        String sql = "SELECT id_Unidad,id_Empresa,Nombre_Unidad,Clave,id_Persona FROM unidad where id_Unidad=" + id_Unidad + "";
        try (Connection con = Conexion.getCon();) {
            PreparedStatement pstm = con.prepareStatement(sql);
            try (ResultSet rs = pstm.executeQuery();) {
                while (rs.next()) {
                    Persona_DTO encargado = new Persona_DTO();
                    Usuario_DTO usuario = new Usuario_DTO();
                    unidad.setId_Unidad(rs.getInt("id_Unidad"));
                    unidad.setNombre_Unidad(rs.getString("Nombre_Unidad"));
                    unidad.setClave(rs.getString("Clave"));
                    encargado.setId_Persona(rs.getInt("id_Persona"));
                    usuario.setId_Persona(encargado.getId_Persona());
                    usuario.setId_Unidad(unidad.getId_Unidad());
                    unidad.setEncargado(encargado);
                    unidad.setUsuario(usuario);
                }
            }
            pstm.close();
            sql = "SELECT * FROM persona where id_Persona=" + unidad.getEncargado().getId_Persona() + "";
            pstm = con.prepareStatement(sql);
            try (ResultSet rs = pstm.executeQuery();) {
                while (rs.next()) {
                    unidad.getEncargado().setNombre(rs.getString("Nombre"));
                    unidad.getEncargado().setAp_Paterno(rs.getString("Ap_Paterno"));
                    unidad.getEncargado().setAp_Materno(rs.getString("Ap_Materno"));
                    unidad.getEncargado().setFecha_Nac(rs.getString("Fecha_Nac"));
                    unidad.getEncargado().setSexo(rs.getString("Sexo"));
                    unidad.getEncargado().setMail(rs.getString("Mail"));
                    unidad.getEncargado().setTelefono1(rs.getString("Telefono1"));
                    unidad.getEncargado().setTelefono2(rs.getString("Telefono2"));
                    unidad.getEncargado().setId_Direccion(rs.getInt("id_Direccion"));
                }
            }
            pstm.close();

            sql = "SELECT * FROM direccion where id_Direccion=" + unidad.getEncargado().getId_Direccion() + "";
            pstm = con.prepareStatement(sql);
            try (ResultSet rs = pstm.executeQuery();) {
                while (rs.next()) {
                    unidad.getEncargado().setId_Colonia(rs.getInt("id_Colonia"));
                    unidad.getEncargado().setCalle(rs.getString("Calle"));
                    unidad.getEncargado().setNo_Int(rs.getString("No_Int"));
                    unidad.getEncargado().setNo_Ext(rs.getString("No_Ext"));
                }
            }
            pstm.close();
            sql = "SELECT * FROM colonia WHERE id_Colonia=" + unidad.getEncargado().getId_Colonia() + "";
            pstm = con.prepareStatement(sql);
            try (ResultSet rs = pstm.executeQuery();) {
                while (rs.next()) {
                    unidad.getEncargado().setId_CP(rs.getInt("id_CP"));
                    unidad.getEncargado().setNombre_Colonia(rs.getString("Nombre_Colonia"));
                }
            }
            pstm.close();

            sql = "SELECT * FROM cp WHERE id_CP='" + unidad.getEncargado().getId_CP() + "'";
            pstm = con.prepareStatement(sql);
            try (ResultSet rs = pstm.executeQuery();) {
                while (rs.next()) {
                    unidad.getEncargado().setId_CP(rs.getInt("id_CP"));
                    unidad.getEncargado().setId_Municipio(rs.getInt("id_Municipio"));
                    unidad.getEncargado().setC_p(rs.getString("c_p"));
                }
            }
            pstm.close();
            sql = "SELECT id_Estado,Nombre_Municipio FROM municipio WHERE id_Municipio=" + unidad.getEncargado().getId_Municipio() + "";
            pstm = con.prepareStatement(sql);
            try (ResultSet rs = pstm.executeQuery();) {
                while (rs.next()) {
                    unidad.getEncargado().setId_Estado(rs.getInt("id_Estado"));
                    unidad.getEncargado().setNombre_Municipio(rs.getString("Nombre_Municipio"));
                }
            }
            pstm.close();
            sql = "SELECT Nombre_Estado FROM estado WHERE id_Estado=" + unidad.getEncargado().getId_Estado() + "";
            pstm = con.prepareStatement(sql);
            try (ResultSet rs = pstm.executeQuery();) {
                while (rs.next()) {
                    unidad.getEncargado().setNombre_Estado(rs.getString("Nombre_Estado"));
                }
            }
            pstm.close();
            sql = "SELECT * FROM usuario WHERE id_Persona=" + unidad.getUsuario().getId_Persona() + "";
            pstm = con.prepareStatement(sql);
            try (ResultSet rs = pstm.executeQuery();) {
                while (rs.next()) {
                    unidad.getUsuario().setId_Usuario(rs.getInt("id_Usuario"));
                    unidad.getUsuario().setNombre_Usuario(rs.getString("Nombre_Usuario"));
                    unidad.getUsuario().setContraseña(rs.getString("Contrasena"));
                    unidad.getUsuario().setRol(rs.getString("Rol"));
                    unidad.getUsuario().setEstado(rs.getString("Estado"));
                }
            }
            pstm.close();
            return unidad;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Unidad_DTO getUnidad(int id_Unidad) {
        String sql = "SELECT id_Unidad,id_Empresa,Nombre_Unidad,Clave,id_Persona FROM unidad where id_Unidad=" + id_Unidad + "";
        try (Connection con = Conexion.getCon();
                PreparedStatement pstm = con.prepareStatement(sql);
                ResultSet rs = pstm.executeQuery();) {
            Unidad_DTO unidad = new Unidad_DTO();
            while (rs.next()) {
                unidad.setId_Unidad(rs.getInt("id_Unidad"));
                unidad.setId_Empresa(rs.getInt("id_Empresa"));
                unidad.setNombre_Unidad(rs.getString("Nombre_Unidad"));
                unidad.setClave(rs.getString("Clave"));
            }
            if (unidad.getId_Empresa() != 0 && unidad.getNombre_Unidad() != null) {
                return unidad;
            } else {
                return null;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public int getIdEmpresa(int id_unidad) {
        String sql = "SELECT id_Empresa FROM unidad where id_Unidad=" + id_unidad + "";
        try (Connection con = Conexion.getCon();
                PreparedStatement pstm = con.prepareStatement(sql);
                ResultSet rs = pstm.executeQuery();) {
            int empresa = 0;
            while (rs.next()) {
                empresa = (rs.getInt("id_Empresa"));
            }
            return empresa;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
