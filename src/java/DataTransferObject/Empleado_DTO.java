package DataTransferObject;

import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author Carlos Rivas
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = {
    "id_Empleado",
    "id_Unidad",
    "curp",
    "nss",
    "Fecha_Ing",
    "Salario_Bto",
    "Dias_Trabajo",
    "Permisos",
    "usuario",
    "Hora_Ent",
    "Hora_Com",
    "Hora_Reg",
    "Hora_Sal",
    "Ordenes"
})
@XmlRootElement(name = "Empleado_DTO")
public class Empleado_DTO extends Persona_DTO {

    @XmlElement(name = "id_Empleado", required = true)
    protected int id_Empleado;
    @XmlElement(name = "id_Unidad", required = true)
    protected int id_Unidad;
    @XmlElement(name = "curp", required = true)
    protected String curp;
    @XmlElement(name = "nss", required = true)
    protected String nss;
    @XmlElement(name = "Fecha_Ing", required = true)
    protected String Fecha_Ing;
    @XmlElement(name = "Salario_Bto", required = true)
    protected Float Salario_Bto;

    @XmlElement(name = "Dias_Trabajo", required = true)
    protected List<String> Dias_Trabajo;

    @XmlElement(name = "Permisos", required = true)
    protected List<String> Permisos;

    @XmlElement(name = "usuario", required = true)
    protected Usuario_DTO usuario;
    @XmlElement(name = "Hora_Ent", required = true)
    protected String Hora_Ent;
    @XmlElement(name = "Hora_Com", required = true)
    protected String Hora_Com;
    @XmlElement(name = "Hora_Reg", required = true)
    protected String Hora_Reg;
    @XmlElement(name = "Hora_Sal", required = true)
    protected String Hora_Sal;

    @XmlElementWrapper(name = "Ordenes")
    @XmlElement(name = "Orden_DTO")
    protected List<Orden_DTO> Ordenes;

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

    public List<Orden_DTO> getOrdenes() {
        return Ordenes;
    }

    public void setOrdenes(List<Orden_DTO> Ordenes) {
        this.Ordenes = Ordenes;
    }
}
