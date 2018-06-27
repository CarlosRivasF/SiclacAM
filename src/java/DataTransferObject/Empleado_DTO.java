package DataTransferObject;

import java.util.List;

/**
 *
 * @author Carlos Rivas
 */
public class Empleado_DTO extends Persona_DTO {

    protected int id_Empleado;
    protected int id_Unidad;
    protected String curp;
    protected String nss;
    protected String Fecha_Ing;
    protected Float Salario_Bto;
    protected List<String> Dias_Trabajo;
    protected List<String> Permisos;
    protected Usuario_DTO usuario;
    protected String Hora_Ent;
    protected String Hora_Com;
    protected String Hora_Reg;
    protected String Hora_Sal;

    public int getId_Empleado() {
        return id_Empleado;
    }

    public void setId_Empleado(int id_Empleado) {
        this.id_Empleado = id_Empleado;
    }

    public int getId_Unidad() {
        return id_Unidad;
    }

    public void setId_Unidad(int id_Unidad) {
        this.id_Unidad = id_Unidad;
    }

    public String getCurp() {
        return curp;
    }

    public void setCurp(String curp) {
        this.curp = curp;
    }

    public String getNss() {
        return nss;
    }

    public void setNss(String nss) {
        this.nss = nss;
    }

    public String getFecha_Ing() {
        return Fecha_Ing;
    }

    public void setFecha_Ing(String Fecha_Ing) {
        this.Fecha_Ing = Fecha_Ing;
    }

    public Float getSalario_Bto() {
        return Salario_Bto;
    }

    public void setSalario_Bto(Float Salario_Bto) {
        this.Salario_Bto = Salario_Bto;
    }

    public List<String> getDias_Trabajo() {
        return Dias_Trabajo;
    }

    public void setDias_Trabajo(List<String> Dias_Trabajo) {
        this.Dias_Trabajo = Dias_Trabajo;
    }

    public String getHora_Ent() {
        return Hora_Ent;
    }

    public void setHora_Ent(String Hora_Ent) {
        this.Hora_Ent = Hora_Ent;
    }

    public String getHora_Com() {
        return Hora_Com;
    }

    public void setHora_Com(String Hora_Com) {
        this.Hora_Com = Hora_Com;
    }

    public String getHora_Reg() {
        return Hora_Reg;
    }

    public void setHora_Reg(String Hora_Reg) {
        this.Hora_Reg = Hora_Reg;
    }

    public String getHora_Sal() {
        return Hora_Sal;
    }

    public void setHora_Sal(String Hora_Sal) {
        this.Hora_Sal = Hora_Sal;
    }

    public List<String> getPermisos() {
        return Permisos;
    }

    public void setPermisos(List<String> Permisos) {
        this.Permisos = Permisos;
    }

    public Usuario_DTO getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario_DTO usuario) {
        this.usuario = usuario;
    }
}
