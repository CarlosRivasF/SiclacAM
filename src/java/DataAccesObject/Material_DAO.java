package DataAccesObject;

import DataBase.Conexion;
import DataTransferObject.Material_DTO;
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
 * @author ZionSystems
 */
public class Material_DAO {
    public int RegistrarMaterial(int unidad, Material_DTO dto) {
        Unidad_DAO U = new Unidad_DAO();
        int rp;
        String sql = "INSERT INTO material VALUES(NULL,'" + dto.getClave() + "','" + dto.getNombre_Material() + "')";
        try (Connection con = Conexion.getCon();) {
            try (PreparedStatement pstm = con.prepareStatement(sql);) {
                rp = pstm.executeUpdate();
                pstm.close();
            }
            if (rp == 1) {
                sql = "SELECT id_Material from material WHERE Nombre_Material='" + dto.getNombre_Material() + "' and Clave_Mat='" + dto.getClave() + "'";
                try (PreparedStatement pstm = con.prepareStatement(sql); ResultSet rs = pstm.executeQuery();) {
                    while (rs.next()) {
                        dto.setId_Material(rs.getInt("id_Material"));
                    }
                    rs.close();
                    pstm.close();
                }
                sql = "INSERT INTO empr_mat VALUES(NULL," + U.getIdEmpresa(unidad) + "," + dto.getId_Material() + ",'" + dto.getPrecio() + "')";
                try (PreparedStatement pstm = con.prepareStatement(sql);) {
                    rp = pstm.executeUpdate();
                    pstm.close();
                }
                if (rp == 1) {
                    sql = "SELECT id_Empr_Mat from empr_mat WHERE id_Material=" + dto.getId_Material() + "";
                    try (PreparedStatement pstm = con.prepareStatement(sql); ResultSet rs = pstm.executeQuery();) {
                        while (rs.next()) {
                            dto.setId_Empr_Mat(rs.getInt("id_Empr_Mat"));
                        }
                        rs.close();
                        pstm.close();
                    }
                    sql = "INSERT INTO unid_mat VALUES(NULL," + unidad + "," + dto.getId_Empr_Mat() + "," + dto.getCantidad() + ")";
                    try (PreparedStatement pstm = con.prepareStatement(sql);) {
                        rp = pstm.executeUpdate();
                        pstm.close();
                    }
                    if (rp == 1) {
                        sql = "SELECT id_Unid_Mat from unid_mat WHERE id_Empr_Mat=" + dto.getId_Empr_Mat() + "";
                        try (PreparedStatement pstm = con.prepareStatement(sql); ResultSet rs = pstm.executeQuery();) {
                            while (rs.next()) {
                                dto.setId_Unid_Mat(rs.getInt("id_Unid_Mat"));
                            }
                            rs.close();
                            pstm.close();
                        }
                    }
                }
            }
            con.close();
            return dto.getId_Unid_Mat();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }    
    public Material_DTO addMaterialByU(int unidad, Material_DTO dto) {
        Unidad_DAO U = new Unidad_DAO();
        int id_Empresa = U.getIdEmpresa(unidad);
        int rp;
        try (Connection con = Conexion.getCon();) {
            String sql = "INSERT INTO unid_mat VALUES(NULL," + unidad + "," + dto.getId_Empr_Mat() + "," + dto.getCantidad() + ")";
            try (PreparedStatement pstm = con.prepareStatement(sql);) {
                rp = pstm.executeUpdate();
            }
            if (rp == 1) {
                sql = "SELECT id_Unid_Mat from unid_mat WHERE id_Empr_Mat=" + dto.getId_Empr_Mat() + " and id_Unidad=" + unidad + "";
                try (PreparedStatement pstm = con.prepareStatement(sql); ResultSet rs = pstm.executeQuery();) {
                    while (rs.next()) {
                        dto.setId_Unid_Mat(rs.getInt("id_Unid_Mat"));
                    }
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(Material_DAO.class.getName()).log(Level.SEVERE, null, ex);

        }
        return dto;
    }
    public Material_DTO addMaterial(int unidad, Material_DTO dto) {
        Unidad_DAO U = new Unidad_DAO();
        int id_Empresa = U.getIdEmpresa(unidad);
        int rp, id_Em_Mat = 0, id_Un_Mat;
        try (Connection con = Conexion.getCon();) {
            String sql = "SELECT id_Empr_Mat from empr_mat WHERE id_Material=" + dto.getId_Material() + " and id_Empresa=" + id_Empresa + "";
            try (PreparedStatement pstm = con.prepareStatement(sql); ResultSet rs = pstm.executeQuery();) {
                while (rs.next()) {
                    id_Em_Mat = rs.getInt("id_Empr_Mat");
                }
            }
            if (id_Em_Mat != 0) {
                dto.setId_Empr_Mat(id_Em_Mat);
                sql = "INSERT INTO unid_mat VALUES(NULL," + unidad + "," + id_Em_Mat + "," + dto.getCantidad() + ")";
                try (PreparedStatement pstm = con.prepareStatement(sql);) {
                    rp = pstm.executeUpdate();
                }
                if (rp == 1) {
                    sql = "SELECT id_Unid_Mat from unid_mat WHERE id_Empr_Mat=" + id_Em_Mat + " and id_Unidad=" + unidad + "";
                    try (PreparedStatement pstm = con.prepareStatement(sql); ResultSet rs = pstm.executeQuery();) {
                        while (rs.next()) {
                            id_Un_Mat = rs.getInt("id_Unid_Mat");
                            dto.setId_Unid_Mat(id_Un_Mat);
                        }
                    }
                }
                return dto;
            } else {
                sql = "INSERT INTO empr_mat VALUES(NULL," + id_Empresa + "," + dto.getId_Material() + ",'" + dto.getPrecio() + "')";
                try (PreparedStatement pstm = con.prepareStatement(sql);) {
                    rp = pstm.executeUpdate();
                }
                if (rp == 1) {
                    sql = "SELECT id_Empr_Mat from empr_mat WHERE id_Material=" + dto.getId_Material() + " and id_Empresa=" + id_Empresa + "";
                    try (PreparedStatement pstm = con.prepareStatement(sql); ResultSet rs = pstm.executeQuery();) {
                        while (rs.next()) {
                            id_Em_Mat = rs.getInt("id_Empr_Mat");
                            dto.setId_Empr_Mat(id_Em_Mat);
                        }
                    }
                    sql = "INSERT INTO unid_mat VALUES(NULL," + unidad + "," + id_Em_Mat + "," + dto.getCantidad() + ")";
                    try (PreparedStatement pstm = con.prepareStatement(sql);) {
                        rp = pstm.executeUpdate();
                    }
                    if (rp == 1) {
                        sql = "SELECT id_Unid_Mat from unid_mat WHERE id_Empr_Mat=" + id_Em_Mat + " and id_Unidad=" + unidad + "";
                        try (PreparedStatement pstm = con.prepareStatement(sql); ResultSet rs = pstm.executeQuery();) {
                            while (rs.next()) {
                                id_Un_Mat = rs.getInt("id_Unid_Mat");
                                dto.setId_Unid_Mat(id_Un_Mat);
                            }
                        }
                    }
                }
                return dto;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public int ActualizarMaterial(Material_DTO dto) {
        int rp;
        try (Connection con = Conexion.getCon();) {
            String sql = "UPDATE unid_mat SET cantidad=" + dto.getCantidad() + " WHERE id_Unid_Mat=" + dto.getId_Unid_Mat() + "";
            try (PreparedStatement pstm = con.prepareStatement(sql);) {
                rp = pstm.executeUpdate();
                pstm.close();
                if (rp != 0) {
                    sql = "UPDATE empr_mat SET precio=" + dto.getPrecio() + " WHERE id_Empr_Mat=" + dto.getId_Empr_Mat() + "";
                    try (PreparedStatement pstm1 = con.prepareStatement(sql);) {
                        rp = pstm1.executeUpdate();
                        pstm1.close();
                    }
                }
            }
            return rp;
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }
    public int EliminarMaterial(Material_DTO dto) {
        int rp = 0;
        try (Connection con = Conexion.getCon();) {
            String sql = "DELETE from unid_mat WHERE id_Unid_Mat=" + dto.getId_Unid_Mat() + "";            
            try (PreparedStatement pstm = con.prepareStatement(sql);) {
                rp = pstm.executeUpdate();
                pstm.close();
            }
            return rp;
        } catch (SQLException ex) {
            Logger.getLogger(Material_DAO.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        return rp;
    }
    public List<Material_DTO> getMateriales() {
        List<Material_DTO> mats;
        try (Connection con = Conexion.getCon()) {
            String sql = "SELECT * FROM material";
            try (PreparedStatement pstm = con.prepareStatement(sql);
                    ResultSet rs = pstm.executeQuery();) {
                mats = new ArrayList<>();
                while (rs.next()) {
                    Material_DTO mat = new Material_DTO();
                    mat.setId_Material(rs.getInt("id_Material"));
                    mat.setNombre_Material(rs.getString("Nombre_Material"));
                    mats.add(mat);
                }
            }
            return mats;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public List<Material_DTO> getMaterialesByUnidad(int id_Unidad) {
        List<Material_DTO> mats;
        try (Connection con = Conexion.getCon()) {
            String sql = "SELECT id_Unidad,id_Unid_Mat,id_Empr_Mat,Cantidad FROM unid_mat WHERE id_Unidad=" + id_Unidad + "";
            try (PreparedStatement pstm = con.prepareStatement(sql);
                    ResultSet rs = pstm.executeQuery();) {
                mats = new ArrayList<>();
                while (rs.next()) {
                    Material_DTO mat = new Material_DTO();
                    mat.setId_Unid_Mat(rs.getInt("id_Unid_Mat"));
                    mat.setId_Empr_Mat(rs.getInt("id_Empr_Mat"));
                    mat.setCantidad(rs.getInt("Cantidad"));
                    mats.add(mat);
                }
            }
            for (Material_DTO mat : mats) {
                sql = "SELECT id_Material,Precio FROM empr_mat WHERE id_Empr_Mat=" + mat.getId_Empr_Mat() + "";
                try (PreparedStatement pstm = con.prepareStatement(sql);
                        ResultSet rs = pstm.executeQuery();) {
                    while (rs.next()) {
                        mat.setId_Material(rs.getInt("id_Material"));
                        mat.setPrecio(rs.getFloat("Precio"));
                    }
                }
            }
            for (Material_DTO mat : mats) {
                sql = "SELECT * FROM material WHERE id_Material=" + mat.getId_Material() + "";
                try (PreparedStatement pstm = con.prepareStatement(sql);
                        ResultSet rs = pstm.executeQuery();) {
                    while (rs.next()) {
                        mat.setNombre_Material(rs.getString("Nombre_Material"));
                        mat.setClave(rs.getString("Clave_Mat"));
                    }
                }
            }
            return mats;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public List<Material_DTO> getMatsNotRegistedUnid(int id_Unidad) {
        List<Material_DTO> mats;
        Unidad_DAO U = new Unidad_DAO();
        int id_Empresa = U.getIdEmpresa(id_Unidad);
        try (Connection con = Conexion.getCon()) {
            String sql = "SELECT id_Unidad,id_Empr_Mat FROM unid_mat WHERE id_Unidad!=" + id_Unidad + "";
            try (PreparedStatement pstm = con.prepareStatement(sql);
                    ResultSet rs = pstm.executeQuery();) {
                mats = new ArrayList<>();
                while (rs.next()) {
                    Material_DTO mat = new Material_DTO();
                    mat.setId_Unidad(rs.getInt("id_Unidad"));
                    mat.setId_Empr_Mat(rs.getInt("id_Empr_Mat"));
                    mats.add(mat);
                }
            }
            for (Material_DTO mat : mats) {
                sql = "SELECT id_Empresa,id_Material FROM empr_mat WHERE id_Empr_Mat=" + mat.getId_Empr_Mat() + "";
                try (PreparedStatement pstm = con.prepareStatement(sql);
                        ResultSet rs = pstm.executeQuery();) {
                    while (rs.next()) {
                        mat.setId_Empresa(rs.getInt("id_Empresa"));
                        mat.setId_Material(rs.getInt("id_Material"));
                    }
                }
            }
            for (Material_DTO mat : mats) {
                sql = "SELECT * FROM material WHERE id_Material=" + mat.getId_Material() + "";
                try (PreparedStatement pstm = con.prepareStatement(sql);
                        ResultSet rs = pstm.executeQuery();) {
                    while (rs.next()) {
                        mat.setNombre_Material(rs.getString("Nombre_Material"));
                        mat.setClave(rs.getString("Clave_Mat"));
                    }
                }
            }
            return mats;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public List<Material_DTO> getMatsNotRegistedEmpr(int id_Empresa) {
        List<Material_DTO> mats;
        try (Connection con = Conexion.getCon()) {
            String sql = "SELECT id_Empr_Mat,id_Material,Precio FROM empr_mat WHERE id_Empresa!=" + id_Empresa + "";
            try (PreparedStatement pstm = con.prepareStatement(sql);
                    ResultSet rs = pstm.executeQuery();) {
                mats = new ArrayList<>();
                while (rs.next()) {
                    Material_DTO mat = new Material_DTO();
                    mat.setId_Material(rs.getInt("id_Material"));
                    mats.add(mat);
                }
            }
            for (Material_DTO mat : mats) {
                sql = "SELECT * FROM material WHERE id_Material=" + mat.getId_Material() + "";
                try (PreparedStatement pstm = con.prepareStatement(sql);
                        ResultSet rs = pstm.executeQuery();) {
                    while (rs.next()) {
                        mat.setNombre_Material(rs.getString("Nombre_Material"));
                        mat.setClave(rs.getString("Clave_Mat"));
                    }
                }
            }
            return mats;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public List<Material_DTO> getMaterialesByEmpresa(int id_Empresa) {
        List<Material_DTO> mats;
        try (Connection con = Conexion.getCon()) {
            String sql = "SELECT id_Empr_Mat,id_Material,Precio FROM empr_mat WHERE id_Empresa=" + id_Empresa + "";
            try (PreparedStatement pstm = con.prepareStatement(sql);
                    ResultSet rs = pstm.executeQuery();) {
                mats = new ArrayList<>();
                while (rs.next()) {
                    Material_DTO mat = new Material_DTO();
                    mat.setId_Empr_Mat(rs.getInt("id_Empr_Mat"));
                    mat.setId_Material(rs.getInt("id_Material"));
                    mat.setPrecio(rs.getFloat("Precio"));
                    mats.add(mat);
                }
            }
            for (Material_DTO mat : mats) {
                sql = "SELECT * FROM material WHERE id_Material=" + mat.getId_Material() + "";
                try (PreparedStatement pstm = con.prepareStatement(sql);
                        ResultSet rs = pstm.executeQuery();) {
                    while (rs.next()) {
                        mat.setNombre_Material(rs.getString("Nombre_Material"));
                        mat.setClave(rs.getString("Clave_Mat"));
                    }
                }
            }
            return mats;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    
    public int Copy(int unidad, Material_DTO dto) {
        Unidad_DAO U = new Unidad_DAO();
        int rp;
        String sql = "INSERT INTO material VALUES(NULL,'" + dto.getClave() + "','" + dto.getNombre_Material() + "')";
        try (Connection con = Conexion.getCon();) {
            try (PreparedStatement pstm = con.prepareStatement(sql);) {
                rp = pstm.executeUpdate();
                pstm.close();
            }
            if (rp == 1) {
                sql = "SELECT id_Material from material WHERE Nombre_Material='" + dto.getNombre_Material() + "' and Clave_Mat='" + dto.getClave() + "'";
                try (PreparedStatement pstm = con.prepareStatement(sql); ResultSet rs = pstm.executeQuery();) {
                    while (rs.next()) {
                        dto.setId_Material(rs.getInt("id_Material"));
                    }
                    rs.close();
                    pstm.close();
                }
                sql = "INSERT INTO empr_mat VALUES(NULL," + U.getIdEmpresa(unidad) + "," + dto.getId_Material() + ",'" + dto.getPrecio() + "')";
                try (PreparedStatement pstm = con.prepareStatement(sql);) {
                    rp = pstm.executeUpdate();
                    pstm.close();
                }
                if (rp == 1) {
                    sql = "SELECT id_Empr_Mat from empr_mat WHERE id_Material=" + dto.getId_Material() + "";
                    try (PreparedStatement pstm = con.prepareStatement(sql); ResultSet rs = pstm.executeQuery();) {
                        while (rs.next()) {
                            dto.setId_Empr_Mat(rs.getInt("id_Empr_Mat"));
                        }
                        rs.close();
                        pstm.close();
                    }
                    sql = "INSERT INTO unid_mat VALUES(NULL," + unidad + "," + dto.getId_Empr_Mat() + "," + dto.getCantidad() + ")";
                    try (PreparedStatement pstm = con.prepareStatement(sql);) {
                        rp = pstm.executeUpdate();
                        pstm.close();
                    }
                    if (rp == 1) {
                        sql = "SELECT id_Unid_Mat from unid_mat WHERE id_Empr_Mat=" + dto.getId_Empr_Mat() + "";
                        try (PreparedStatement pstm = con.prepareStatement(sql); ResultSet rs = pstm.executeQuery();) {
                            while (rs.next()) {
                                dto.setId_Unid_Mat(rs.getInt("id_Unid_Mat"));
                            }
                            rs.close();
                            pstm.close();
                        }
                    }
                }
            }
            con.close();
            return dto.getId_Unid_Mat();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
}
