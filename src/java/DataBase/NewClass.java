
package DataBase;

import DataAccesObject.Estudio_DAO;
import DataAccesObject.Material_DAO;
import DataAccesObject.Unidad_DAO;
import DataTransferObject.Configuracion_DTO;
import DataTransferObject.Est_Mat_DTO;
import DataTransferObject.Estudio_DTO;
import DataTransferObject.Material_DTO;
import DataTransferObject.Persona_DTO;
import DataTransferObject.Precio_DTO;
import DataTransferObject.Unidad_DTO;
import DataTransferObject.Usuario_DTO;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import javax.sql.DataSource;
import org.apache.commons.dbcp.BasicDataSource;

/**
 *
 * @author ZionSystem
 */


public class NewClass {

    public static DataSource dataSource = null;//siclac2
    private static final String DB = "siclac2";  //node74321-amlab.whelastic.net
    private static final String URL = "jdbc:mysql://node74321-amlab.whelastic.net/" + DB + "?useServerPrepStmts=true&autoReconnect=true";
    private static final String USER = "root";
    private static final String PASS = "NAVngv51153";//NAVngv51153

    public NewClass() {
        inicializaDataSource();
    }

    private void inicializaDataSource() {
        BasicDataSource basicDataSource = new BasicDataSource();
        basicDataSource.setDriverClassName("org.gjt.mm.mysql.Driver");
        basicDataSource.setUsername(USER);
        basicDataSource.setPassword(PASS);
        basicDataSource.setUrl(URL);
        basicDataSource.setMaxActive(-1);
        basicDataSource.setMinIdle(50);
        basicDataSource.setMaxIdle(100);
        basicDataSource.setTimeBetweenEvictionRunsMillis(3000);
        basicDataSource.setMinEvictableIdleTimeMillis(2500);
        basicDataSource.setNumTestsPerEvictionRun(100);
        basicDataSource.setMaxWait(3500);
        dataSource = basicDataSource;
    }

    public static Connection conexion;

    public static Connection conectar() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conexion = DriverManager.getConnection(URL, USER, PASS);
        } catch (ClassNotFoundException | SQLException ex) {
            throw new RuntimeException(ex);
        }
        return conexion;
    }

    public static Connection conectar2() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conexion = DriverManager.getConnection("jdbc:mysql://localhost/siclacweb?useServerPrepStmts=true&autoReconnect=true", USER, PASS);
        } catch (ClassNotFoundException | SQLException ex) {
            throw new RuntimeException(ex);
        }
        return conexion;
    }

    public List<Unidad_DTO> getUnidades() {
        List<Unidad_DTO> uns;
        String sql = "SELECT id_Unidad,id_Empresa,Nombre_Unidad,Clave,id_Persona FROM unidad";
        try (Connection con = conectar2()) {
            PreparedStatement pstm = con.prepareStatement(sql);
            try (ResultSet rs = pstm.executeQuery();) {
                uns = new ArrayList<>();
                while (rs.next()) {
                    Unidad_DTO unidad = new Unidad_DTO();
                    Persona_DTO encargado = new Persona_DTO();
                    Usuario_DTO usuario = new Usuario_DTO();
                    unidad.setId_Unidad(rs.getInt("id_Unidad"));
                    unidad.setId_Empresa(rs.getInt("id_Empresa"));
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
                List<String> lst = getPermisoString(dto.getUsuario().getId_Usuario());
                dto.getUsuario().setLst(lst);
                pstm.close();
            }
            return uns;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<String> getPermisoString(int id_Usuario) {
        List<String> lst = new ArrayList<>();
        try (Connection con = conectar2()) {
            String sql = "SELECT id_Permiso FROM  use_per WHERE id_Usuario=" + id_Usuario + " order by id_Permiso";
            System.out.println(sql);
            try (PreparedStatement pstm = con.prepareStatement(sql); ResultSet rs = pstm.executeQuery()) {
                while (rs.next()) {
                    System.out.println(rs.getString("id_Permiso"));
                    lst.add(rs.getString("id_Permiso"));
                }
            }
            return lst;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Material_DTO> getMateriales() {
        List<Material_DTO> mats;
        try (Connection con = conectar2()) {
            String sql = "SELECT * FROM material";
            try (PreparedStatement pstm = con.prepareStatement(sql);
                    ResultSet rs = pstm.executeQuery();) {
                mats = new ArrayList<>();
                while (rs.next()) {
                    Material_DTO mat = new Material_DTO();
                    mat.setId_Material(rs.getInt("id_Material"));
                    mat.setClave(rs.getString("Clave_Mat"));
                    mat.setNombre_Material(rs.getString("Nombre_Material"));
                    mats.add(mat);
                }
            }
            return mats;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Estudio_DTO> getEstudios() {
        List<Estudio_DTO> ests = new ArrayList<>();
        try (Connection con = conectar2()) {
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

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        Double riz = Math.sqrt(Double.parseDouble(String.valueOf(n)));
        String str = String.valueOf(riz);
        int X = Integer.parseInt(str.substring(0, str.indexOf('.')));
        int c = 1;
        for (int i = X; i > 0; i--) {
            String esp = " ";
            for (int e = 0; e < (i - 1); e++) {
                System.out.print(esp);
            }
            String ch = "*";
            for (int j = 0; j < c; j++) {
                System.out.print(ch);
            }
            System.out.println();
            c = c + 2;
        }

    }

}
