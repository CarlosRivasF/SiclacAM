package DataAccesObject;

import DataBase.Conexion;
import DataTransferObject.Usuario_DTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Carlos Rivas
 */
public class Usuario_DAO {

    public int RegistrarUsuario(Usuario_DTO dto) {
        int id_Usuario = 0;
        String sql = "INSERT INTO usuario VALUES(NULL,'" + dto.getId_Unidad() + "','" + dto.getId_Persona() + "','" + dto.getNombre_Usuario() + "','" + dto.getContraseña() + "','" + dto.getRol() + "','" + dto.getEstado() + "')";
        try (Connection con = Conexion.getCon();
                PreparedStatement pstm = con.prepareStatement(sql);) {
            if (pstm.executeUpdate() == 1) {
                sql = "SELECT id_Usuario from usuario WHERE id_Persona=" + dto.getId_Persona() + " AND Nombre_Usuario='" + dto.getNombre_Usuario() + "' AND Contrasena='" + dto.getContraseña() + "' AND Rol='" + dto.getRol() + "'";
                try (PreparedStatement pstm1 = con.prepareStatement(sql);
                        ResultSet rs = pstm1.executeQuery();) {
                    while (rs.next()) {
                        id_Usuario = rs.getInt("id_Usuario");
                    }
                }
            }
            return id_Usuario;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Usuario_DTO Login(String Usuario, String Contraseña) {
        Usuario_DTO usuario = new Usuario_DTO();
        String sql = "SELECT * FROM usuario WHERE Nombre_Usuario=? AND contrasena=?";

        try (Connection con = Conexion.getCon(); PreparedStatement pstm = con.prepareStatement(sql);) {
            pstm.setString(1, Usuario);
            pstm.setString(2, Contraseña);
            System.out.println("Usuario:" + Usuario + ", Password:" + Contraseña);
            try (ResultSet rs = pstm.executeQuery()) {
                while (rs.next()) {
                    usuario.setId_Usuario(rs.getInt("id_Usuario"));
                    usuario.setId_Unidad(rs.getInt("id_Unidad"));
                    usuario.setId_Persona(rs.getInt("id_Persona"));
                    usuario.setNombre_Usuario(rs.getString("Nombre_Usuario"));
                    usuario.setContraseña(rs.getString("Contrasena"));
                    usuario.setRol(rs.getString("Rol"));
                    usuario.setEstado(rs.getString("Estado"));
                }
            }
            con.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        if (usuario.getNombre_Usuario() != null & usuario.getContraseña() != null) {
            if (usuario.getNombre_Usuario().trim().equals(Usuario.trim()) & usuario.getContraseña().trim().equals(Contraseña.trim())) {
                if (usuario.getId_Unidad() != 0 & usuario.getRol() != null & usuario.getEstado() != null) {
                    return usuario;
                } else {
                    return null;
                }
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    public int ActualizarUsuario(Usuario_DTO dto) {
        int rp = 0;
        try (Connection con = Conexion.getCon();) {
            String sql = "UPDATE usuario SET Nombre_Usuario='" + dto.getNombre_Usuario() + "',Contrasena='" + dto.getContraseña() + "',Estado='" + dto.getEstado() + "'"
                    + " WHERE id_Usuario=" + dto.getId_Usuario() + " and id_Unidad=" + dto.getId_Unidad() + "";
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

    public int DeshabilitarUsuario(int id_Usuario) {
        int rp = 0;
        try (Connection con = Conexion.getCon();) {
            String sql = "UPDATE usuario SET Estado='Inactivo' WHERE id_Usuario=" + id_Usuario + "";
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

    public int HabilitarUsuario(int id_Usuario) {
        int rp = 0;
        try (Connection con = Conexion.getCon();) {
            String sql = "UPDATE usuario SET Estado='Activo' WHERE id_Usuario=" + id_Usuario + "";
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
