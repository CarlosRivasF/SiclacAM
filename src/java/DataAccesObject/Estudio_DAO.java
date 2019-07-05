package DataAccesObject;

import DataBase.Conexion;
import DataTransferObject.Configuracion_DTO;
import DataTransferObject.Est_Mat_DTO;
import DataTransferObject.Estudio_DTO;
import DataTransferObject.Precio_DTO;
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
public class Estudio_DAO {

    public int registrarEstudio(Estudio_DTO dto) {
        //System.out.println("registrarEstudio.....................................");
        int id_Estudio = 1;
        try (Connection con = Conexion.getCon();) {
            String sql = "INSERT INTO estudio (id_Estudio,id_Tipo_Estudio,Nombre_Estudio,Clave_Estudio, Preparacion,Utilidad,metodo,controlEst,porcRef) VALUES(null," + dto.getId_Tipo_Estudio() + ",'" + dto.getNombre_Estudio() + "','" + dto.getClave_Estudio() + "','" + dto.getPreparacion() + "','" + dto.getUtilidad() + "','" + dto.getMetodo() + "','" + dto.getCtrl_est() + "'," + dto.getPorcEst() + ")";
            //System.out.println(sql);
            try (PreparedStatement pstm = con.prepareStatement(sql);) {
                pstm.executeUpdate();
            }
            sql = "SELECT id_Estudio from estudio WHERE id_Tipo_Estudio=" + dto.getId_Tipo_Estudio() + " AND Nombre_Estudio='" + dto.getNombre_Estudio() + "' AND Clave_Estudio='" + dto.getClave_Estudio() + "' AND Preparacion='" + dto.getPreparacion() + "' AND Utilidad='" + dto.getUtilidad() + "'";
            //System.out.println(sql);
            try (PreparedStatement pstm1 = con.prepareStatement(sql);
                    ResultSet rs = pstm1.executeQuery();) {
                while (rs.next()) {
                    id_Estudio = rs.getInt("id_Estudio");
                }
            }
            return id_Estudio;

        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    public int registrarEst_Uni(int Est, int Unid) {
        //System.out.println("registrarEst_Uni..........................................");
        int id_Est_Uni = 0;
        try (Connection con = Conexion.getCon();) {
            String sql = "INSERT INTO est_uni VALUES(NULL," + Est + "," + Unid + ")";
            //System.out.println(sql);
            try (PreparedStatement pstm = con.prepareStatement(sql);) {
                pstm.executeUpdate();
            }
            sql = "SELECT id_Est_Uni FROM est_uni  WHERE id_Estudio=" + Est + " and id_Unidad=" + Unid + "";
            //System.out.println(sql);
            try (PreparedStatement pstm = con.prepareStatement(sql); ResultSet rs = pstm.executeQuery()) {
                while (rs.next()) {
                    id_Est_Uni = rs.getInt("id_Est_Uni");
                }
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return id_Est_Uni;
    }

    public List<Estudio_DTO> getEstudios() {
        List<Estudio_DTO> ests = new ArrayList<>();
        try (Connection con = Conexion.getCon()) {
            String sql = "SELECT id_Estudio,id_Tipo_Estudio,Nombre_Estudio,Clave_Estudio, Preparacion,Utilidad,metodo FROM estudio";
            try (PreparedStatement pstm = con.prepareStatement(sql); ResultSet rs = pstm.executeQuery();) {
                while (rs.next()) {
                    Estudio_DTO est = new Estudio_DTO();
                    est.setId_Estudio(rs.getInt("id_Estudio"));
                    est.setId_Tipo_Estudio(rs.getInt("id_Tipo_Estudio"));
                    est.setNombre_Estudio(rs.getString("Nombre_Estudio"));
                    est.setClave_Estudio(rs.getString("Clave_Estudio"));
                    est.setPreparacion(rs.getString("Preparacion"));
                    est.setUtilidad(rs.getString("Utilidad"));
                    est.setMetodo(rs.getString("metodo"));
                    ests.add(est);
                }
            }
            for (Estudio_DTO est : ests) {
                sql = "SELECT id_Est_Uni FROM est_uni  WHERE id_Estudio=" + est.getId_Estudio() + "";
                try (PreparedStatement pstm = con.prepareStatement(sql); ResultSet rs = pstm.executeQuery();) {
                    while (rs.next()) {
                        est.setId_Est_Uni(rs.getInt("id_Est_Uni"));
                    }
                }
            }

            for (Estudio_DTO est : ests) {
                sql = "SELECT Nombre_Tipo_Estudio FROM tipo_estudio WHERE id_Tipo_Estudio=" + est.getId_Tipo_Estudio() + "";

                try (PreparedStatement pstm = con.prepareStatement(sql); ResultSet rs = pstm.executeQuery();) {
                    while (rs.next()) {
                        est.setNombre_Tipo_Estudio(rs.getString("Nombre_Tipo_Estudio"));
                    }
                }
            }

            for (Estudio_DTO est : ests) {
                List<Configuracion_DTO> confs = new ArrayList<>();
                sql = "SELECT id_Configuracion FROM conf_est WHERE id_Estudio=" + est.getId_Estudio() + "";

                try (PreparedStatement pstm = con.prepareStatement(sql); ResultSet rs = pstm.executeQuery();) {
                    while (rs.next()) {
                        Configuracion_DTO conf = new Configuracion_DTO();
                        conf.setId_Configuración(rs.getInt("id_Configuracion"));
                        confs.add(conf);
                    }
                }
                est.setCnfs(confs);
            }
            for (Estudio_DTO est : ests) {
                for (Configuracion_DTO conf : est.getCnfs()) {
                    sql = "SELECT * FROM configuracion WHERE id_Configuracion=" + conf.getId_Configuración() + "";

                    try (PreparedStatement pstm = con.prepareStatement(sql); ResultSet rs = pstm.executeQuery();) {
                        while (rs.next()) {
                            conf.setDescripcion(rs.getString("Descripcion"));
                            conf.setSexo(rs.getString("sexo"));
                            conf.setValor_min(rs.getString("Valor_min"));
                            conf.setValor_MAX(rs.getString("Valor_MAX"));
                            conf.setUniddes(rs.getString("Unidades"));
                        }
                    }
                }
            }
            for (Estudio_DTO est : ests) {
                List<Est_Mat_DTO> matsE = new ArrayList<>();
                sql = "SELECT * FROM mat_est WHERE id_Est_Uni=" + est.getId_Est_Uni() + "";

                try (PreparedStatement pstm = con.prepareStatement(sql);
                        ResultSet rs = pstm.executeQuery();) {
                    while (rs.next()) {
                        Est_Mat_DTO matE = new Est_Mat_DTO();
                        matE.setId_Unid_Mat(rs.getInt("id_Unid_Mat"));
                        matE.setUnidadE(rs.getString("unidad"));
                        matE.setCantidadE(rs.getInt("cantidad"));
                        matE.setT_Muestra(rs.getString("T_Muestra"));
                        matsE.add(matE);
                    }
                }
                est.setMts(matsE);
            }
            for (Estudio_DTO est : ests) {
                for (Est_Mat_DTO matE : est.getMts()) {
                    sql = "SELECT id_Unidad,id_Empr_Mat,Cantidad FROM unid_mat WHERE id_Unid_Mat=" + matE.getId_Unid_Mat() + "";

                    try (PreparedStatement pstm = con.prepareStatement(sql);
                            ResultSet rs = pstm.executeQuery();) {
                        while (rs.next()) {
                            matE.setId_Unidad(rs.getInt("id_Unidad"));
                            matE.setId_Empr_Mat(rs.getInt("id_Empr_Mat"));
                            matE.setCantidad(rs.getInt("Cantidad"));
                        }
                    }
                    sql = "SELECT id_Empresa,id_Material,Precio FROM empr_mat WHERE id_Empr_Mat=" + matE.getId_Empr_Mat() + "";

                    try (PreparedStatement pstm = con.prepareStatement(sql);
                            ResultSet rs = pstm.executeQuery();) {
                        while (rs.next()) {
                            matE.setId_Empresa(rs.getInt("id_Empresa"));
                            matE.setId_Material(rs.getInt("id_Material"));
                            matE.setPrecio(rs.getFloat("Precio"));
                        }
                    }
                    sql = "SELECT * FROM material WHERE id_Material=" + matE.getId_Material() + "";

                    try (PreparedStatement pstm = con.prepareStatement(sql);
                            ResultSet rs = pstm.executeQuery();) {
                        while (rs.next()) {
                            matE.setNombre_Material(rs.getString("Nombre_Material"));
                            matE.setClave(rs.getString("Clave_Mat"));
                        }
                    }
                }
            }
            for (Estudio_DTO est : ests) {
                sql = "SELECT * FROM precio WHERE id_Est_Uni=" + est.getId_Est_Uni() + "";
                try (PreparedStatement pstm = con.prepareStatement(sql);
                        ResultSet rs = pstm.executeQuery();) {
                    Precio_DTO precio = new Precio_DTO();
                    while (rs.next()) {
                        precio.setId_Precio(rs.getInt("id_Precio"));
                        precio.setPrecio_N(rs.getFloat("Precio_N"));
                        precio.setPrecio_U(rs.getFloat("Precio_U"));
                        precio.setT_Entrega_N(rs.getInt("T_Entrega_N"));
                        precio.setT_Entrega_U(rs.getInt("T_Entrega_U"));
                    }
                    est.setPrecio(precio);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        //System.out.println("enu size: " + ests.size());
        return ests;
    }

    public List<Estudio_DTO> GetEstudiosNoRegisterInUnidad(int id_Unidad) {
        List<Estudio_DTO> eu = getEstudiosByUnidad(id_Unidad);
        List<Estudio_DTO> enu = getEstudios();
        try {
            eu.stream().forEach((eu1) -> {
                for (int j = 0; j < enu.size(); j++) {
                    if (eu1.getId_Estudio() == enu.get(j).getId_Estudio()) {
                        enu.remove(enu.get(j));
                    }
                }
            });
        } catch (Exception e) {
        }
        return enu;
    }

    public List<Estudio_DTO> getEstudiosNotRegUnidad(int id_Unidad) {
        List<Estudio_DTO> ests = new ArrayList<>();
        try (Connection con = Conexion.getCon()) {
            String sql = "SELECT id_Est_Uni,id_Estudio FROM est_uni  WHERE id_Unidad!=" + id_Unidad + "";
            try (PreparedStatement pstm = con.prepareStatement(sql); ResultSet rs = pstm.executeQuery();) {
                ests = new ArrayList<>();
                while (rs.next()) {
                    Estudio_DTO est = new Estudio_DTO();
                    est.setId_Est_Uni(rs.getInt("id_Est_Uni"));
                    est.setId_Estudio(rs.getInt("id_Estudio"));
                    ests.add(est);
                }
            }
            for (Estudio_DTO est : ests) {
                sql = "SELECT id_Estudio,id_Tipo_Estudio,Nombre_Estudio,Clave_Estudio, Preparacion,Utilidad,metodo FROM estudio WHERE id_Estudio=" + est.getId_Estudio() + "";
                try (PreparedStatement pstm = con.prepareStatement(sql); ResultSet rs = pstm.executeQuery();) {
                    while (rs.next()) {
                        est.setId_Tipo_Estudio(rs.getInt("id_Tipo_Estudio"));
                        est.setNombre_Estudio(rs.getString("Nombre_Estudio"));
                        est.setClave_Estudio(rs.getString("Clave_Estudio"));
                        est.setPreparacion(rs.getString("Preparacion"));
                        est.setUtilidad(rs.getString("Utilidad"));
                        est.setMetodo(rs.getString("metodo"));
                    }
                }
            }
            for (Estudio_DTO est : ests) {
                sql = "SELECT Nombre_Tipo_Estudio FROM tipo_estudio WHERE id_Tipo_Estudio=" + est.getId_Tipo_Estudio() + "";
                try (PreparedStatement pstm = con.prepareStatement(sql); ResultSet rs = pstm.executeQuery();) {
                    while (rs.next()) {
                        est.setNombre_Tipo_Estudio(rs.getString("Nombre_Tipo_Estudio"));
                    }
                }
            }

            for (Estudio_DTO est : ests) {
                List<Configuracion_DTO> confs = new ArrayList<>();
                sql = "SELECT id_Configuracion FROM conf_est WHERE id_Estudio=" + est.getId_Estudio() + "";
                try (PreparedStatement pstm = con.prepareStatement(sql); ResultSet rs = pstm.executeQuery();) {
                    while (rs.next()) {
                        Configuracion_DTO conf = new Configuracion_DTO();
                        conf.setId_Configuración(rs.getInt("id_Configuracion"));
                        confs.add(conf);
                    }
                }
                est.setCnfs(confs);
            }
            for (Estudio_DTO est : ests) {
                for (Configuracion_DTO conf : est.getCnfs()) {
                    sql = "SELECT * FROM configuracion WHERE id_Configuracion=" + conf.getId_Configuración() + "";
                    try (PreparedStatement pstm = con.prepareStatement(sql); ResultSet rs = pstm.executeQuery();) {
                        while (rs.next()) {
                            conf.setDescripcion(rs.getString("Descripcion"));
                            conf.setSexo(rs.getString("sexo"));
                            conf.setValor_min(rs.getString("Valor_min"));
                            conf.setValor_MAX(rs.getString("Valor_MAX"));
                            conf.setUniddes(rs.getString("Unidades"));
                        }
                    }
                }
            }
            for (Estudio_DTO est : ests) {
                List<Est_Mat_DTO> matsE = new ArrayList<>();
                sql = "SELECT * FROM mat_est WHERE id_Est_Uni=" + est.getId_Est_Uni() + "";
                try (PreparedStatement pstm = con.prepareStatement(sql);
                        ResultSet rs = pstm.executeQuery();) {
                    while (rs.next()) {
                        Est_Mat_DTO matE = new Est_Mat_DTO();
                        matE.setId_Unid_Mat(rs.getInt("id_Unid_Mat"));
                        matE.setUnidadE(rs.getString("unidad"));
                        matE.setCantidadE(rs.getInt("cantidad"));
                        matE.setT_Muestra(rs.getString("T_Muestra"));
                        matsE.add(matE);
                    }
                }
                est.setMts(matsE);
            }
            for (Estudio_DTO est : ests) {
                for (Est_Mat_DTO matE : est.getMts()) {
                    sql = "SELECT id_Unidad,id_Empr_Mat,Cantidad FROM unid_mat WHERE id_Unid_Mat=" + matE.getId_Unid_Mat() + "";
                    try (PreparedStatement pstm = con.prepareStatement(sql);
                            ResultSet rs = pstm.executeQuery();) {
                        while (rs.next()) {
                            matE.setId_Unidad(rs.getInt("id_Unidad"));
                            matE.setId_Empr_Mat(rs.getInt("id_Empr_Mat"));
                            matE.setCantidad(rs.getInt("Cantidad"));
                        }
                    }
                    sql = "SELECT id_Empresa,id_Material,Precio FROM empr_mat WHERE id_Empr_Mat=" + matE.getId_Empr_Mat() + "";
                    try (PreparedStatement pstm = con.prepareStatement(sql);
                            ResultSet rs = pstm.executeQuery();) {
                        while (rs.next()) {
                            matE.setId_Empresa(rs.getInt("id_Empresa"));
                            matE.setId_Material(rs.getInt("id_Material"));
                            matE.setPrecio(rs.getFloat("Precio"));
                        }
                    }
                    sql = "SELECT * FROM material WHERE id_Material=" + matE.getId_Material() + "";
                    try (PreparedStatement pstm = con.prepareStatement(sql);
                            ResultSet rs = pstm.executeQuery();) {
                        while (rs.next()) {
                            matE.setNombre_Material(rs.getString("Nombre_Material"));
                            matE.setClave(rs.getString("Clave_Mat"));
                        }
                    }
                }
            }
            for (Estudio_DTO est : ests) {
                sql = "SELECT * FROM precio WHERE id_Est_Uni=" + est.getId_Est_Uni() + "";
                try (PreparedStatement pstm = con.prepareStatement(sql);
                        ResultSet rs = pstm.executeQuery();) {
                    Precio_DTO precio = new Precio_DTO();
                    while (rs.next()) {
                        precio.setId_Precio(rs.getInt("id_Precio"));
                        precio.setPrecio_N(rs.getFloat("Precio_N"));
                        precio.setPrecio_U(rs.getFloat("Precio_U"));
                        precio.setT_Entrega_N(rs.getInt("T_Entrega_N"));
                        precio.setT_Entrega_U(rs.getInt("T_Entrega_U"));
                    }
                    est.setPrecio(precio);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return ests;
    }

    public List<Estudio_DTO> getEstudiosByUnidad(int id_Unidad) {
        List<Estudio_DTO> ests = new ArrayList<>();
        try (Connection con = Conexion.getCon()) {
            String sql = "SELECT id_Est_Uni,id_Estudio FROM est_uni  WHERE id_Unidad=" + id_Unidad + "";
            try (PreparedStatement pstm = con.prepareStatement(sql); ResultSet rs = pstm.executeQuery();) {
                ests = new ArrayList<>();
                while (rs.next()) {
                    Estudio_DTO est = new Estudio_DTO();
                    est.setId_Est_Uni(rs.getInt("id_Est_Uni"));
                    est.setId_Estudio(rs.getInt("id_Estudio"));
                    ests.add(est);
                }
            }
            for (Estudio_DTO est : ests) {
                sql = "SELECT id_Estudio,id_Tipo_Estudio,Nombre_Estudio,Clave_Estudio, Preparacion,Utilidad,metodo FROM estudio WHERE id_Estudio=" + est.getId_Estudio() + "";
                try (PreparedStatement pstm = con.prepareStatement(sql); ResultSet rs = pstm.executeQuery();) {
                    while (rs.next()) {
                        est.setId_Tipo_Estudio(rs.getInt("id_Tipo_Estudio"));
                        est.setNombre_Estudio(rs.getString("Nombre_Estudio"));
                        est.setClave_Estudio(rs.getString("Clave_Estudio"));
                        est.setPreparacion(rs.getString("Preparacion"));
                        est.setUtilidad(rs.getString("Utilidad"));
                        est.setMetodo(rs.getString("metodo"));
                    }
                }
            }
            for (Estudio_DTO est : ests) {
                sql = "SELECT Nombre_Tipo_Estudio FROM tipo_estudio WHERE id_Tipo_Estudio=" + est.getId_Tipo_Estudio() + "";
                try (PreparedStatement pstm = con.prepareStatement(sql); ResultSet rs = pstm.executeQuery();) {
                    while (rs.next()) {
                        est.setNombre_Tipo_Estudio(rs.getString("Nombre_Tipo_Estudio"));
                    }
                }
            }
            for (Estudio_DTO est : ests) {
                List<Configuracion_DTO> confs = new ArrayList<>();
                sql = "SELECT id_Configuracion FROM conf_est WHERE id_Estudio=" + est.getId_Estudio() + "";
                try (PreparedStatement pstm = con.prepareStatement(sql); ResultSet rs = pstm.executeQuery();) {
                    while (rs.next()) {
                        Configuracion_DTO conf = new Configuracion_DTO();
                        conf.setId_Configuración(rs.getInt("id_Configuracion"));
                        confs.add(conf);
                    }
                }
                est.setCnfs(confs);
            }
            for (Estudio_DTO est : ests) {
                sql = "SELECT id_Configuracion FROM conf_est WHERE id_Estudio=" + est.getId_Estudio() + "";
//                System.out.println("List<Configuracion_DTO>: " + sql + ";");
                for (Configuracion_DTO conf : est.getCnfs()) {
                    sql = "SELECT * FROM configuracion WHERE id_Configuracion=" + conf.getId_Configuración() + "";
//                    System.out.println("Configuracion_DTO: " + sql + ";");
                    try (PreparedStatement pstm = con.prepareStatement(sql); ResultSet rs = pstm.executeQuery();) {
                        while (rs.next()) {
                            conf.setDescripcion(rs.getString("Descripcion"));
                            conf.setSexo(rs.getString("sexo"));
                            conf.setValor_min(rs.getString("Valor_min"));
                            conf.setValor_MAX(rs.getString("Valor_MAX"));
                            conf.setUniddes(rs.getString("Unidades"));
                        }
                    }
                }
            }
            for (Estudio_DTO est : ests) {
                List<Est_Mat_DTO> matsE = new ArrayList<>();
                sql = "SELECT * FROM mat_est WHERE id_Est_Uni=" + est.getId_Est_Uni() + "";
                try (PreparedStatement pstm = con.prepareStatement(sql);
                        ResultSet rs = pstm.executeQuery();) {
                    while (rs.next()) {
                        Est_Mat_DTO matE = new Est_Mat_DTO();
                        matE.setId_Unid_Mat(rs.getInt("id_Unid_Mat"));
                        matE.setUnidadE(rs.getString("unidad"));
                        matE.setCantidadE(rs.getInt("cantidad"));
                        matE.setT_Muestra(rs.getString("T_Muestra"));
                        matsE.add(matE);
                    }
                }
                est.setMts(matsE);
            }
            for (Estudio_DTO est : ests) {
                for (Est_Mat_DTO matE : est.getMts()) {
                    sql = "SELECT id_Unidad,id_Empr_Mat,Cantidad FROM unid_mat WHERE id_Unid_Mat=" + matE.getId_Unid_Mat() + "";
                    try (PreparedStatement pstm = con.prepareStatement(sql);
                            ResultSet rs = pstm.executeQuery();) {
                        while (rs.next()) {
                            matE.setId_Unidad(rs.getInt("id_Unidad"));
                            matE.setId_Empr_Mat(rs.getInt("id_Empr_Mat"));
                            matE.setCantidad(rs.getInt("Cantidad"));
                        }
                    }
                    sql = "SELECT id_Empresa,id_Material,Precio FROM empr_mat WHERE id_Empr_Mat=" + matE.getId_Empr_Mat() + "";
                    try (PreparedStatement pstm = con.prepareStatement(sql);
                            ResultSet rs = pstm.executeQuery();) {
                        while (rs.next()) {
                            matE.setId_Empresa(rs.getInt("id_Empresa"));
                            matE.setId_Material(rs.getInt("id_Material"));
                            matE.setPrecio(rs.getFloat("Precio"));
                        }
                    }
                    sql = "SELECT * FROM material WHERE id_Material=" + matE.getId_Material() + "";
                    try (PreparedStatement pstm = con.prepareStatement(sql);
                            ResultSet rs = pstm.executeQuery();) {
                        while (rs.next()) {
                            matE.setNombre_Material(rs.getString("Nombre_Material"));
                            matE.setClave(rs.getString("Clave_Mat"));
                        }
                    }
                }
            }
            for (Estudio_DTO est : ests) {
                sql = "SELECT * FROM precio WHERE id_Est_Uni=" + est.getId_Est_Uni() + "";
                try (PreparedStatement pstm = con.prepareStatement(sql);
                        ResultSet rs = pstm.executeQuery();) {
                    Precio_DTO precio = new Precio_DTO();
                    while (rs.next()) {
                        precio.setId_Precio(rs.getInt("id_Precio"));
                        precio.setPrecio_N(rs.getFloat("Precio_N"));
                        precio.setPrecio_U(rs.getFloat("Precio_U"));
                        precio.setT_Entrega_N(rs.getInt("T_Entrega_N"));
                        precio.setT_Entrega_U(rs.getInt("T_Entrega_U"));
                    }
                    est.setPrecio(precio);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return ests;
    }

    public Estudio_DTO getEst_Uni(int id_Est_Uni) {
        Estudio_DTO est = new Estudio_DTO();
        try (Connection con = Conexion.getCon()) {
            String sql = "SELECT id_Est_Uni,id_Estudio FROM est_uni  WHERE id_Est_Uni=" + id_Est_Uni + "";
            try (PreparedStatement pstm = con.prepareStatement(sql); ResultSet rs = pstm.executeQuery();) {
                while (rs.next()) {
                    est.setId_Est_Uni(rs.getInt("id_Est_Uni"));
                    est.setId_Estudio(rs.getInt("id_Estudio"));
                }
            }
            sql = "SELECT id_Estudio,id_Tipo_Estudio,Nombre_Estudio,Clave_Estudio, Preparacion,Utilidad,metodo FROM estudio WHERE id_Estudio=" + est.getId_Estudio() + "";
            try (PreparedStatement pstm = con.prepareStatement(sql); ResultSet rs = pstm.executeQuery();) {
                while (rs.next()) {
                    est.setId_Tipo_Estudio(rs.getInt("id_Tipo_Estudio"));
                    est.setNombre_Estudio(rs.getString("Nombre_Estudio"));
                    est.setClave_Estudio(rs.getString("Clave_Estudio"));
                    est.setPreparacion(rs.getString("Preparacion"));
                    est.setUtilidad(rs.getString("Utilidad"));
                    est.setMetodo(rs.getString("metodo"));
                }
            }
            sql = "SELECT Nombre_Tipo_Estudio FROM tipo_estudio WHERE id_Tipo_Estudio=" + est.getId_Tipo_Estudio() + "";
            try (PreparedStatement pstm = con.prepareStatement(sql); ResultSet rs = pstm.executeQuery();) {
                while (rs.next()) {
                    est.setNombre_Tipo_Estudio(rs.getString("Nombre_Tipo_Estudio"));
                }
            }
            List<Configuracion_DTO> confs = new ArrayList<>();
            sql = "SELECT id_Configuracion FROM conf_est WHERE id_Estudio=" + est.getId_Estudio() + "";
            try (PreparedStatement pstm = con.prepareStatement(sql); ResultSet rs = pstm.executeQuery();) {
                while (rs.next()) {
                    Configuracion_DTO conf = new Configuracion_DTO();
                    conf.setId_Configuración(rs.getInt("id_Configuracion"));
                    confs.add(conf);
                }
            }
            est.setCnfs(confs);
            for (Configuracion_DTO conf : est.getCnfs()) {
                sql = "SELECT * FROM configuracion WHERE id_Configuracion=" + conf.getId_Configuración() + "";
                try (PreparedStatement pstm = con.prepareStatement(sql); ResultSet rs = pstm.executeQuery();) {
                    while (rs.next()) {
                        conf.setDescripcion(rs.getString("Descripcion"));
                        conf.setSexo(rs.getString("sexo"));
                        conf.setValor_min(rs.getString("Valor_min"));
                        conf.setValor_MAX(rs.getString("Valor_MAX"));
                        conf.setUniddes(rs.getString("Unidades"));
                    }
                }
            }
            List<Est_Mat_DTO> matsE = new ArrayList<>();
            sql = "SELECT * FROM mat_est WHERE id_Est_Uni=" + est.getId_Est_Uni() + "";
            try (PreparedStatement pstm = con.prepareStatement(sql);
                    ResultSet rs = pstm.executeQuery();) {
                while (rs.next()) {
                    Est_Mat_DTO matE = new Est_Mat_DTO();
                    matE.setId_Unid_Mat(rs.getInt("id_Unid_Mat"));
                    matE.setUnidadE(rs.getString("unidad"));
                    matE.setCantidadE(rs.getInt("cantidad"));
                    matE.setT_Muestra(rs.getString("T_Muestra"));
                    matsE.add(matE);
                }
            }
            est.setMts(matsE);
            for (Est_Mat_DTO matE : est.getMts()) {
                sql = "SELECT id_Unidad,id_Empr_Mat,Cantidad FROM unid_mat WHERE id_Unid_Mat=" + matE.getId_Unid_Mat() + "";
                try (PreparedStatement pstm = con.prepareStatement(sql);
                        ResultSet rs = pstm.executeQuery();) {
                    while (rs.next()) {
                        matE.setId_Unidad(rs.getInt("id_Unidad"));
                        matE.setId_Empr_Mat(rs.getInt("id_Empr_Mat"));
                        matE.setCantidad(rs.getInt("Cantidad"));
                    }
                }
                sql = "SELECT id_Empresa,id_Material,Precio FROM empr_mat WHERE id_Empr_Mat=" + matE.getId_Empr_Mat() + "";
                try (PreparedStatement pstm = con.prepareStatement(sql);
                        ResultSet rs = pstm.executeQuery();) {
                    while (rs.next()) {
                        matE.setId_Empresa(rs.getInt("id_Empresa"));
                        matE.setId_Material(rs.getInt("id_Material"));
                        matE.setPrecio(rs.getFloat("Precio"));
                    }
                }
                sql = "SELECT * FROM material WHERE id_Material=" + matE.getId_Material() + "";
                try (PreparedStatement pstm = con.prepareStatement(sql);
                        ResultSet rs = pstm.executeQuery();) {
                    while (rs.next()) {
                        matE.setNombre_Material(rs.getString("Nombre_Material"));
                        matE.setClave(rs.getString("Clave_Mat"));
                    }
                }
            }
            sql = "SELECT * FROM precio WHERE id_Est_Uni=" + est.getId_Est_Uni() + "";
            try (PreparedStatement pstm = con.prepareStatement(sql);
                    ResultSet rs = pstm.executeQuery();) {
                Precio_DTO precio = new Precio_DTO();
                while (rs.next()) {
                    precio.setId_Precio(rs.getInt("id_Precio"));
                    precio.setPrecio_N(rs.getFloat("Precio_N"));
                    precio.setPrecio_U(rs.getFloat("Precio_U"));
                    precio.setT_Entrega_N(rs.getInt("T_Entrega_N"));
                    precio.setT_Entrega_U(rs.getInt("T_Entrega_U"));
                }
                est.setPrecio(precio);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return est;
    }

    public int ActualizarMet(int id, int id_Tipo_Estudio, String metodo) {
        int rp;
        try (Connection con = Conexion.getCon();) {
            String sql = "UPDATE estudio SET id_Tipo_Estudio=" + id_Tipo_Estudio + ", metodo='" + metodo + "' WHERE id_Estudio=" + id + "";

            try (PreparedStatement pstm = con.prepareStatement(sql);) {
                rp = pstm.executeUpdate();
            }
            return rp;
        } catch (SQLException ex) {
            throw new RuntimeException(ex.getLocalizedMessage(), ex);
        }
    }

    public int ActualizarPrep(String prep, int id_Estudio) {
        int rp;
        try (Connection con = Conexion.getCon();) {
            String sql = "UPDATE estudio SET Preparacion='" + prep + "' WHERE id_Estudio=" + id_Estudio + "";
            try (PreparedStatement pstm = con.prepareStatement(sql);) {
                rp = pstm.executeUpdate();
            }
            return rp;
        } catch (SQLException ex) {
            throw new RuntimeException(ex.getLocalizedMessage(), ex);
        }
    }

    public int ActualizarUtilidad(int id, String util) {
        int rp;
        try (Connection con = Conexion.getCon();) {
            String sql = "UPDATE estudio SET Utilidad='" + util + "' WHERE id_Estudio=" + id + "";
            try (PreparedStatement pstm = con.prepareStatement(sql);) {
                rp = pstm.executeUpdate();
            }
            return rp;
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    public int ActualizarPrec(int id_Precio, int id_Est_Uni, Float Pn, int En, Float Pu, int Eu) {
        int rp;
        try (Connection con = Conexion.getCon();) {
            String sql = "UPDATE precio SET Precio_N='" + Pn + "',T_Entrega_N='" + En + "',Precio_U='" + Pu + "'"
                    + ",T_Entrega_U='" + Eu + "' WHERE id_Precio=" + id_Precio + " and id_Est_Uni=" + id_Est_Uni + "";
            try (PreparedStatement pstm = con.prepareStatement(sql);) {
                rp = pstm.executeUpdate();
            }
            return rp;
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    public int ActualizarConfig(Configuracion_DTO dto) {
        int rp;
        try (Connection con = Conexion.getCon();) {
            String sql = "UPDATE configuracion SET Descripcion='" + dto.getDescripcion() + "',Valor_min='" + dto.getValor_min() + "',Valor_MAX='" + dto.getValor_MAX() + "'"
                    + ",Unidades='" + dto.getUniddes() + "',sexo='" + dto.getSexo() + "' WHERE id_Configuracion=" + dto.getId_Configuración() + "";

            try (PreparedStatement pstm = con.prepareStatement(sql);) {
                rp = pstm.executeUpdate();
            }
            return rp;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            throw new RuntimeException(ex);
        }
    }

    public int ActualizarMat_Est(int id_Est_Uni, int id_Unid_Mat, Est_Mat_DTO dto) {
        int rp;
        try (Connection con = Conexion.getCon();) {
            String sql = "UPDATE mat_est SET id_Unid_Mat=" + dto.getId_Unid_Mat() + ",cantidad='" + dto.getCantidadE() + "',unidad='" + dto.getUnidadE() + "'"
                    + ",T_Muestra='" + dto.getT_Muestra() + "' WHERE id_Est_Uni=" + id_Est_Uni + " and id_Unid_Mat=" + id_Unid_Mat + "";
            try (PreparedStatement pstm = con.prepareStatement(sql);) {
                rp = pstm.executeUpdate();
            }
            return rp;
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    public int EliminarEst(Estudio_DTO dto) {
        int rp;
        try (Connection con = Conexion.getCon();) {
            String sql = "DELETE from est_uni WHERE id_Est_Uni=" + dto.getId_Est_Uni() + "";
            try (PreparedStatement pstm = con.prepareStatement(sql);) {
                rp = pstm.executeUpdate();
                pstm.close();
            }
            return rp;
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    public int EliminarEst_Conf(int id_Estudio, int id_Configuracion) {
        int rp;
        try (Connection con = Conexion.getCon();) {
            String sql = "DELETE from conf_est WHERE id_Estudio=" + id_Estudio + " and id_Configuracion=" + id_Configuracion + "";
            try (PreparedStatement pstm = con.prepareStatement(sql);) {
                rp = pstm.executeUpdate();
                pstm.close();
            }
            return rp;
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    public int EliminarEst_Mat(int id_Est_Uni, int id_Unid_Mat) {
        int rp;
        try (Connection con = Conexion.getCon();) {
            String sql = "DELETE from mat_est WHERE id_Est_Uni=" + id_Est_Uni + " and id_Unid_Mat=" + id_Unid_Mat + "";
            try (PreparedStatement pstm = con.prepareStatement(sql);) {
                rp = pstm.executeUpdate();
                pstm.close();
            }
            return rp;
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

//    public static void main(String[] args) {
//        Estudio_DAO E = new Estudio_DAO();
//        List<Estudio_DTO> eu = E.getEstudiosByUnidad(1);
//        List<Estudio_DTO> enu = E.getEstudiosNotRegUnidad(1);
//
//        try {
//            for (int i = 0; i < eu.size(); i++) {
//                for (int j = 0; j < enu.size(); j++) {
//                    if (eu.get(i).getId_Estudio() == enu.get(j).getId_Estudio()) {
//                        enu.remove(enu.get(j));
//                    }
//                }
//            }
//        } catch (Exception e) {
//
//        }
//
//        enu.forEach((e) -> {
//            System.out.print(e.getNombre_Estudio());
//            e.getMts().forEach((me) -> {
//                System.out.print(me.getId_Material() + " - " + me.getNombre_Material());
//            });
//        });
//    }
    public void copy(Estudio_DTO estudio, int id_Unidad) {
        Precio_DAO P = new Precio_DAO();
        Configuracion_DAO CN = new Configuracion_DAO();
        Est_Mat_DAO M = new Est_Mat_DAO();
        estudio.setId_Estudio(registrarEstudio(estudio));
        if (estudio.getId_Estudio() != 0) {
            estudio.setId_Est_Uni(registrarEst_Uni(estudio.getId_Estudio(), id_Unidad));
            if (estudio.getId_Est_Uni() != 0) {
                estudio.getPrecio().setId_Precio(P.registrarPrecio(estudio.getId_Est_Uni(), estudio.getPrecio()));
                if (estudio.getPrecio().getId_Precio() != 0) {
                    if (!estudio.getCnfs().isEmpty()) {
                        estudio.getCnfs().forEach((dto) -> {
                            dto.setId_Configuración(CN.registrarConfiguracion(dto));
                            CN.registrarConf_Est(estudio.getId_Estudio(), dto.getId_Configuración());
                        });
                    }
                    if (estudio.getCnfs().get(0).getId_Configuración() != 0) {
                        if (!estudio.getMts().isEmpty()) {
                            estudio.getMts().forEach((dto) -> {
                                M.registrarMat_Est(estudio.getId_Est_Uni(), dto);
                            });
                        }
                    }
                }
            }
        }
    }
}
