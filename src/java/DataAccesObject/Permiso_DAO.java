package DataAccesObject;

import DataBase.Conexion;
import DataTransferObject.Permiso_DTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Permiso_DAO {

    public int registrarPermisos(int usuario, List<String> ps) {
        int r = 0;
        try (Connection con = Conexion.getCon();) {
            for (String p : ps) {
                String sql = "INSERT INTO use_per VALUES(" + usuario + "," + p + ")";
                try (PreparedStatement pstm = con.prepareStatement(sql);) {
                    r = pstm.executeUpdate();
                }
            }
            return r;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public int ActualizarPermisos(int id_Usuario, List<String> ps) {
        int r;
        try (Connection con = Conexion.getCon();) {
            String sql = "DELETE FROM use_per WHERE id_Usuario=" + id_Usuario + "";
            try (PreparedStatement pstm = con.prepareStatement(sql);) {
                r = pstm.executeUpdate();
            }
            if (r != 0) {
                for (String p : ps) {
                    sql = "INSERT INTO use_per VALUES(" + id_Usuario + "," + p + ")";
                    try (PreparedStatement pstm = con.prepareStatement(sql);) {
                        r = pstm.executeUpdate();
                    }
                }
            }
            return r;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<String> getPermisoString(int id_Usuario) {
        List<String> lst = new ArrayList<>();
        try (Connection con = Conexion.getCon()) {
            String sql = "SELECT id_Permiso FROM  use_per WHERE id_Usuario=" + id_Usuario + " order by id_Permiso";
            
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

    public List<Permiso_DTO> getPermisos(int id_Usuario) {
        int[] permisos = null;
        List<Permiso_DTO> lst;
        try (Connection con = Conexion.getCon()) {
            String sql = "SELECT count(id_Permiso) FROM  use_per WHERE id_Usuario=" + id_Usuario + "";
            PreparedStatement pstm = con.prepareStatement(sql);
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                permisos = new int[rs.getInt(1)];
            }
            rs.close();
            pstm.close();
            sql = "SELECT id_Permiso FROM  use_per WHERE id_Usuario=" + id_Usuario + " order by id_Permiso";
            pstm = con.prepareStatement(sql);
            rs = pstm.executeQuery();
            int p = 0;
            while (rs.next()) {
                permisos[p] = rs.getInt("id_Permiso");
                p++;
            }
            rs.close();
            pstm.close();
            lst = new ArrayList<>();
            for (int i = 0; i < permisos.length; i++) {
                sql = "SELECT ruta,nombre FROM permiso WHERE id_Permiso=" + permisos[i] + "";
                pstm = con.prepareStatement(sql);
                rs = pstm.executeQuery();
                Permiso_DTO permiso = new Permiso_DTO();
                while (rs.next()) {
                    permiso.setRuta(rs.getString("ruta"));
                    permiso.setNombre(rs.getString("nombre"));
                }
                lst.add(permiso);
                rs.close();
                pstm.close();
            }
            return lst;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Permiso_DTO> getPermisos() {
        List<Permiso_DTO> pms = new ArrayList<>();
        String Reportes = "<div class='col custom-control custom-checkbox form-check-inline'>"
                + "<input class='custom-control-input' type='checkbox' name='permisos' id='reportes' value='2'>"
                + "<label class='col custom-control-label' for='reportes'>Reportes</label>"
                + "</div>";
        Permiso_DTO p1 = new Permiso_DTO();
        p1.setNombre("Reportes");
        p1.setInput(Reportes);
        pms.add(p1);
        String Materiales = "<div class='col custom-control custom-checkbox form-check-inline'>"
                + "<input class='custom-control-input' type='checkbox' name='permisos' id='material' value='3'>"
                + "<label class='col custom-control-label' for='material'>Materiales</label>"
                + "</div>";
        Permiso_DTO p2 = new Permiso_DTO();
        p2.setNombre("Materiales");
        p2.setInput(Materiales);
        pms.add(p2);
        String Estudios = "<div class='col custom-control custom-checkbox form-check-inline'>"
                + "<input class='custom-control-input' type='checkbox' name='permisos' id='estudios' value='4'>"
                + "<label class='col custom-control-label' for='estudios'>&emsp13;Estudios</label>"
                + "</div> ";
        Permiso_DTO p3 = new Permiso_DTO();
        p3.setNombre("Estudios");
        p3.setInput(Estudios);
        pms.add(p3);
        String Empleados = "<div class='col custom-control custom-checkbox form-check-inline'>"
                + "<input class='custom-control-input' type='checkbox' name='permisos' id='empleados' value='5'>"
                + "<label class='col custom-control-label' for='empleados'>Empleados</label>"
                + "</div>";
        Permiso_DTO p4 = new Permiso_DTO();
        p4.setNombre("Empleados");
        p4.setInput(Empleados);
        pms.add(p4);
        String Médicos = "<div class='col custom-control custom-checkbox form-check-inline'>"
                + "<input class='custom-control-input' type='checkbox' name='permisos' id='medicos' value='6'>"
                + "<label class='col custom-control-label' for='medicos'>&emsp13;Médicos</label>"
                + "</div>";
        Permiso_DTO p5 = new Permiso_DTO();
        p5.setNombre("Médicos");
        p5.setInput(Médicos);
        pms.add(p5);
        String Pacientes = "<div class='col custom-control custom-checkbox form-check-inline'>"
                + "<input class='custom-control-input' type='checkbox' name='permisos' id='pacientes' value='7'>"
                + "<label class='col custom-control-label' for='pacientes'>&emsp13;Pacientes</label>"
                + "</div>";
        Permiso_DTO p6 = new Permiso_DTO();
        p6.setNombre("Pacientes");
        p6.setInput(Pacientes);
        pms.add(p6);
        String Órdenes = "<div class='col custom-control custom-checkbox form-check-inline'>"
                + "<input class='custom-control-input' type='checkbox' name='permisos' id='ordenes' value='8'>"
                + "<label class='col custom-control-label' for='ordenes'>&emsp13;Órdenes</label>"
                + "</div>";
        Permiso_DTO p7 = new Permiso_DTO();
        p7.setNombre("Órdenes");
        p7.setInput(Órdenes);
        pms.add(p7);
        return pms;
    }

    public List<Permiso_DTO> getPermisosChecked() {
        List<Permiso_DTO> pms = new ArrayList<>();
        String Reportes = "<div class='col custom-control custom-checkbox form-check-inline'>"
                + "<input class='custom-control-input' checked type='checkbox' name='permisos' id='reportes' value='2'>"
                + "<label class='col custom-control-label' for='reportes'>Reportes</label>"
                + "</div>";
        Permiso_DTO p1 = new Permiso_DTO();
        p1.setNombre("Reportes");
        p1.setInput(Reportes);
        pms.add(p1);
        String Materiales = "<div class='col custom-control custom-checkbox form-check-inline'>"
                + "<input class='custom-control-input' checked type='checkbox' name='permisos' id='material' value='3'>"
                + "<label class='col custom-control-label' for='material'>Materiales</label>"
                + "</div>";
        Permiso_DTO p2 = new Permiso_DTO();
        p2.setNombre("Materiales");
        p2.setInput(Materiales);
        pms.add(p2);
        String Estudios = "<div class='col custom-control custom-checkbox form-check-inline'>"
                + "<input class='custom-control-input' checked type='checkbox' name='permisos' id='estudios' value='4'>"
                + "<label class='col custom-control-label' for='estudios'>&emsp13;Estudios</label>"
                + "</div> ";
        Permiso_DTO p3 = new Permiso_DTO();
        p3.setNombre("Estudios");
        p3.setInput(Estudios);
        pms.add(p3);
        String Empleados = "<div class='col custom-control custom-checkbox form-check-inline'>"
                + "<input class='custom-control-input' checked type='checkbox' name='permisos' id='empleados' value='5'>"
                + "<label class='col custom-control-label' for='empleados'>Empleados</label>"
                + "</div>";
        Permiso_DTO p4 = new Permiso_DTO();
        p4.setNombre("Empleados");
        p4.setInput(Empleados);
        pms.add(p4);
        String Médicos = "<div class='col custom-control custom-checkbox form-check-inline'>"
                + "<input class='custom-control-input' checked type='checkbox' name='permisos' id='medicos' value='6'>"
                + "<label class='col custom-control-label' for='medicos'>&emsp13;Médicos</label>"
                + "</div>";
        Permiso_DTO p5 = new Permiso_DTO();
        p5.setNombre("Médicos");
        p5.setInput(Médicos);
        pms.add(p5);
        String Pacientes = "<div class='col custom-control custom-checkbox form-check-inline'>"
                + "<input class='custom-control-input' checked type='checkbox' name='permisos' id='pacientes' value='7'>"
                + "<label class='col custom-control-label' for='pacientes'>&emsp13;Pacientes</label>"
                + "</div>";
        Permiso_DTO p6 = new Permiso_DTO();
        p6.setNombre("Pacientes");
        p6.setInput(Pacientes);
        pms.add(p6);
        String Órdenes = "<div class='col custom-control custom-checkbox form-check-inline'>"
                + "<input class='custom-control-input' checked type='checkbox' name='permisos' id='ordenes' value='8'>"
                + "<label class='col custom-control-label' for='ordenes'>&emsp13;Órdenes</label>"
                + "</div>";
        Permiso_DTO p7 = new Permiso_DTO();
        p7.setNombre("Órdenes");
        p7.setInput(Órdenes);
        pms.add(p7);
        return pms;
    }

    public List<Permiso_DTO> getPermisosCheckedDisabled() {
        List<Permiso_DTO> pms = new ArrayList<>();
        String Reportes = "<div class='col custom-control custom-checkbox form-check-inline'>"
                + "<input class='custom-control-input' checked type='checkbox' name='permisos' id='reportes' value='2' disabled>"
                + "<label style=' color: white' class='col custom-control-label' for='reportes'>Reportes</label>"
                + "</div>";
        Permiso_DTO p1 = new Permiso_DTO();
        p1.setNombre("Reportes");
        p1.setInput(Reportes);
        pms.add(p1);
        String Materiales = "<div class='col custom-control custom-checkbox form-check-inline'>"
                + "<input class='custom-control-input' checked type='checkbox' name='permisos' id='material' value='3' disabled>"
                + "<label style=' color: white' class='col custom-control-label' for='material'>Materiales</label>"
                + "</div>";
        Permiso_DTO p2 = new Permiso_DTO();
        p2.setNombre("Materiales");
        p2.setInput(Materiales);
        pms.add(p2);
        String Estudios = "<div class='col custom-control custom-checkbox form-check-inline'>"
                + "<input class='custom-control-input' checked type='checkbox' name='permisos' id='estudios' value='4' disabled>"
                + "<label style=' color: white' class='col custom-control-label' for='estudios'>&emsp13;Estudios</label>"
                + "</div> ";
        Permiso_DTO p3 = new Permiso_DTO();
        p3.setNombre("Estudios");
        p3.setInput(Estudios);
        pms.add(p3);
        String Empleados = "<div class='col custom-control custom-checkbox form-check-inline'>"
                + "<input class='custom-control-input' checked type='checkbox' name='permisos' id='empleados' value='5' disabled>"
                + "<label style=' color: white' class='col custom-control-label' for='empleados'>Empleados</label>"
                + "</div>";
        Permiso_DTO p4 = new Permiso_DTO();
        p4.setNombre("Empleados");
        p4.setInput(Empleados);
        pms.add(p4);
        String Médicos = "<div class='col custom-control custom-checkbox form-check-inline'>"
                + "<input class='custom-control-input' checked type='checkbox' name='permisos' id='medicos' value='6' disabled>"
                + "<label style=' color: white' class='col custom-control-label' for='medicos'>&emsp13;Médicos</label>"
                + "</div>";
        Permiso_DTO p5 = new Permiso_DTO();
        p5.setNombre("Médicos");
        p5.setInput(Médicos);
        pms.add(p5);
        String Pacientes = "<div class='col custom-control custom-checkbox form-check-inline'>"
                + "<input class='custom-control-input' checked type='checkbox' name='permisos' id='pacientes' value='7' disabled>"
                + "<label style=' color: white' class='col custom-control-label' for='pacientes'>&emsp13;Pacientes</label>"
                + "</div>";
        Permiso_DTO p6 = new Permiso_DTO();
        p6.setNombre("Pacientes");
        p6.setInput(Pacientes);
        pms.add(p6);
        String Órdenes = "<div class='col custom-control custom-checkbox form-check-inline'>"
                + "<input class='custom-control-input' checked type='checkbox' name='permisos' id='ordenes' value='8' disabled>"
                + "<label style=' color: white' class='col custom-control-label' for='ordenes'>&emsp13;Órdenes</label>"
                + "</div>";
        Permiso_DTO p7 = new Permiso_DTO();
        p7.setNombre("Órdenes");
        p7.setInput(Órdenes);
        pms.add(p7);
        return pms;
    }
}
