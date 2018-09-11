package DataBase;

import java.sql.Connection;
import java.sql.SQLException;
import javax.sql.DataSource;
import org.apache.commons.dbcp.BasicDataSource;

/**
 *
 * @author ZionS
 */
public class Conexion {

    public static DataSource dataSource = null;//siclac2
    private static final String DB = "siclac2";//node74321-amlab.whelastic.net // localhost
    private static final String URL = "jdbc:mysql://localhost/" + DB + "?useServerPrepStmts=true&autoReconnect=true&useSSL=false";
    private static final String USER = "root";
    private static final String PASS = "";//NAVngv51153

    private Conexion() {
        BasicDataSource basicDataSource = new BasicDataSource();
        basicDataSource.setDriverClassName("org.gjt.mm.mysql.Driver");
        basicDataSource.setUsername(USER);
        basicDataSource.setPassword(PASS);
        basicDataSource.setUrl(URL);
        basicDataSource.setPoolPreparedStatements(true);
        basicDataSource.setMaxOpenPreparedStatements(-1);
        basicDataSource.setMaxActive(-1);
        basicDataSource.setMinIdle(50);
        basicDataSource.setMaxIdle(100);
        basicDataSource.setTimeBetweenEvictionRunsMillis(2000);
        basicDataSource.setMinEvictableIdleTimeMillis(1500);
        basicDataSource.setNumTestsPerEvictionRun(100);
        basicDataSource.setMaxWait(500);
        dataSource = basicDataSource;
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
            dataSource = null;
            throw new RuntimeException(ex);
        }
    }
}