package DataAccesObject;

import DataBase.Conexion;
import DataTransferObject.CP_DTO;
import DataTransferObject.Colonia_DTO;
import DataTransferObject.Estado_DTO;
import DataTransferObject.Municipio_DTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CP_DAO {

    public CP_DTO getDatosMex(String c_p) {
        try {
            CP_DTO cp;
            try (Connection con = Conexion.getCon()) {
                String sql = "SELECT * FROM cp WHERE c_p=?";
                PreparedStatement pstm = con.prepareStatement(sql);
                pstm.setString(1, c_p);
                ResultSet rs = pstm.executeQuery();
                cp = new CP_DTO();
                while (rs.next()) {
                    cp.setId_CP(rs.getInt("id_CP"));
                    cp.setId_Municipio(rs.getInt("id_Municipio"));
                    cp.setC_p(rs.getString("c_p"));
                }
                rs.close();
                pstm.close();
                sql = "SELECT id_Estado,Nombre_Municipio FROM municipio WHERE id_Municipio=?";
                pstm = con.prepareStatement(sql);
                pstm.setInt(1, cp.getId_Municipio());
                rs = pstm.executeQuery();
                while (rs.next()) {
                    cp.setId_Estado(rs.getInt("id_Estado"));
                    cp.setNombre_Municipio(rs.getString("Nombre_Municipio"));
                }
                rs.close();
                pstm.close();
                sql = "SELECT Nombre_Estado FROM estado WHERE id_Estado=?";
                pstm = con.prepareStatement(sql);
                pstm.setInt(1, cp.getId_Estado());
                rs = pstm.executeQuery();
                while (rs.next()) {
                    cp.setNombre_Estado(rs.getString("Nombre_Estado"));
                }
                rs.close();
                pstm.close();
            }
            return cp;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Estado_DTO> getEdosMex() {
        List<Estado_DTO> edos = new ArrayList<>();
        try (Connection con = Conexion.getCon()) {
            String sql = "SELECT * FROM estado";
            try (PreparedStatement pstm = con.prepareStatement(sql); ResultSet rs = pstm.executeQuery()) {
                while (rs.next()) {
                    Estado_DTO edo = new Estado_DTO();
                    //oke
                    edo.setId_Estado(rs.getInt("id_Estado"));
                    edo.setNombre_Estado(rs.getString("Nombre_Estado"));
                    edos.add(edo);
                }
            }
            return edos;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Municipio_DTO> getMposByEdo(int edo) throws SQLException {
        List<Municipio_DTO> mpos = new ArrayList<>();
        try (Connection con = Conexion.getCon()) {
            String sql = "SELECT * FROM municipio where id_Estado=?";
            try (PreparedStatement pstm = con.prepareStatement(sql)) {
                pstm.setInt(1, edo);
                try (ResultSet rs = pstm.executeQuery()) {
                    while (rs.next()) {
                        Municipio_DTO mpo = new Municipio_DTO();
                        mpo.setId_Municipio(rs.getInt("id_Municipio"));
                        mpo.setNombre_Municipio(rs.getString("Nombre_Municipio"));
                        mpos.add(mpo);
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }

            return mpos;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<CP_DTO> getCpsByMpo(int mpo) {
        List<CP_DTO> cps = new ArrayList<>();
        try (Connection con = Conexion.getCon()) {
            String sql = "SELECT * FROM cp where id_Municipio=?";
            try (PreparedStatement pstm = con.prepareStatement(sql);) {
                pstm.setInt(1, mpo);
                try (ResultSet rs = pstm.executeQuery();) {
                    while (rs.next()) {
                        CP_DTO cp = new CP_DTO();
                        cp.setId_CP(rs.getInt("id_CP"));
                        cp.setC_p(rs.getString("c_p"));
                        cps.add(cp);
                    }
                }
            } catch (SQLException e) {
            }
            return cps;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Colonia_DTO> getColsByCp(int cp) {
        List<Colonia_DTO> cols = new ArrayList<>();
        try (Connection con = Conexion.getCon()) {
            String sql = "SELECT * FROM colonia where id_CP=?";
            try (PreparedStatement pstm = con.prepareStatement(sql);) {
                pstm.setInt(1, cp);
                try (ResultSet rs = pstm.executeQuery();) {
                    while (rs.next()) {
                        Colonia_DTO col = new Colonia_DTO();
                        col.setId_Colonia(rs.getInt("id_CP"));
                        col.setNombre_Colonia(rs.getString("Nombre_Colonia"));
                        cols.add(col);
                    }
                }
            } catch (SQLException e) {
            }
            return cols;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int getIdCP(String cp) {
        String sql = "SELECT id_CP FROM cp where c_p=?";
        try (Connection con = Conexion.getCon();
                PreparedStatement pstm = con.prepareStatement(sql);) {
            pstm.setString(1, cp);
            try (ResultSet rs = pstm.executeQuery();) {
                int Idc_p = 0;
                while (rs.next()) {
                    Idc_p = (rs.getInt("id_CP"));
                }
                return Idc_p;
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

//    public static void main(String[] args) {
//        CP_DAO cp = new CP_DAO();
//        for (Colonia_DTO col : cp.getColsByCp(1060)) {
//            System.out.println(col.getNombre_Colonia());
//            System.out.println(col.getNombre_Municipio());
//            System.out.println(col.getNombre_Estado());
//        }
//    }
}
