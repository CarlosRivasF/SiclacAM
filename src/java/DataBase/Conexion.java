package DataBase;

import java.sql.Connection;
import java.sql.SQLException;
import javax.sql.DataSource;
import org.apache.commons.dbcp.BasicDataSource;

/**
 * @author ZionS
 * <session-config>
 * <cookie-config>
 * <secure>true</secure>
 * <http-only>true</http-only>
 * </cookie-config>
 * </session-config>
 */

public class Conexion {

    private static int c=0; 
    public static DataSource dataSource = null;//siclac2 SET GLOBAL max_connections = 300
    private static final String DB = "qasiclac";//node74321-amlab.whelastic.net // localhost //node74321-amlab.whelastic.net:11065
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
                c++;
                //System.out.println("Conexiónes: "+c);
                return Conexion.getCon();                
            } else {
                if (Conexion.dataSource.getConnection() == null) {
                    Conexion co = new Conexion();
                    c++;
                    //System.out.println("Conexiónes: "+c);
                    return Conexion.getCon();
                } else {
                    c++;
                    //System.out.println("Conexiónes: "+c);
                    return Conexion.dataSource.getConnection();
                }
            }
        } catch (SQLException ex) {
            dataSource = null;
            c=0;
            //System.out.println("REINICIO: "+c);
            throw new RuntimeException(ex);
        }
    }
    public static void main(String[]args){
    System.out.println(Conexion.getCon());
    }
}