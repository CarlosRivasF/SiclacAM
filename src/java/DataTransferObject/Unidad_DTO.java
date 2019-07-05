package DataTransferObject;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author Carlos Rivas
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = {
    "id_Unidad",
    "Nombre_Unidad",
    "clave",
    "encargado",
    "usuario" 
})
@XmlRootElement(name = "cliente")
public class Unidad_DTO extends Empresa_DTO implements Serializable {

    @XmlElement(name = "id_Unidad", required = true)
    protected int id_Unidad;
    @XmlElement(name = "Nombre_Unidad", required = true)
    protected String Nombre_Unidad;
    @XmlElement(name = "clave", required = true)
    protected String clave;
    @XmlElement(name = "encargado", required = true)
    protected Persona_DTO encargado;
    @XmlElement(name = "usuario", required = true)
    protected Usuario_DTO usuario;

    public int getId_Unidad() {
        return id_Unidad;
    }

    public void setId_Unidad(int id_Unidad) {
        this.id_Unidad = id_Unidad;
    }

    public String getNombre_Unidad() {
        return Nombre_Unidad;
    }

    public void setNombre_Unidad(String Nobre_Unidad) {
        this.Nombre_Unidad = Nobre_Unidad;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public Persona_DTO getEncargado() {
        return encargado;
    }

    public void setEncargado(Persona_DTO encargado) {
        this.encargado = encargado;
    }

    public Usuario_DTO getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario_DTO usuario) {
        this.usuario = usuario;
    }
}
