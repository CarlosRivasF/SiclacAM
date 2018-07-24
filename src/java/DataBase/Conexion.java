package DataBase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.sql.DataSource;
import org.apache.commons.dbcp.BasicDataSource;

/**
 *
 * @author ZionS
 */
public class Conexion {

    public static DataSource dataSource = null;//siclac2
    private static final String DB = "siclac2";  //node74321-amlab.whelastic.net
    private static final String URL = "jdbc:mysql://localhost/" + DB + "?useServerPrepStmts=true&autoReconnect=true";
    private static final String USER = "root";
    private static final String PASS = "";//NAVngv51153

    public Conexion() {
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
        basicDataSource.setTimeBetweenEvictionRunsMillis(4500);
        basicDataSource.setMinEvictableIdleTimeMillis(3000);
        basicDataSource.setNumTestsPerEvictionRun(10);
        basicDataSource.setMaxWait(5000);
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

    public static Connection getCon() {              
        try {
            if (Conexion.dataSource == null) {
                Conexion co = new Conexion();
                return Conexion.getCon();
            } else {
                if (Conexion.dataSource.getConnection() == null) {
                    Conexion co = new Conexion();
                    return Conexion.getCon();
                } else {
                    return Conexion.dataSource.getConnection();
                }                
            }
        } catch (SQLException ex) {            
            System.out.println(ex.getCause());
            System.out.println(ex.getMessage());
            return null;
        }
    } 
    
}