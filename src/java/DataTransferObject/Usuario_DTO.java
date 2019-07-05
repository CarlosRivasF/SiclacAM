package DataTransferObject;

import java.io.Serializable;
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
    "id_Usuario",
    "id_Unidad",
    "id_Persona",
    "Nombre_Usuario",
    "Contraseña",
    "Rol",
    "Estado",
    "lst"
}
)
@XmlRootElement(name = "Usuario_DTO")
public class Usuario_DTO implements Serializable {

    @XmlElement(name = "id_Usuario", required = true)
    protected int id_Usuario;
    @XmlElement(name = "id_Unidad", required = true)
    protected int id_Unidad;
    @XmlElement(name = "id_Persona", required = true)
    protected int id_Persona;
    @XmlElement(name = "Nombre_Usuario", required = true)
    protected String Nombre_Usuario;
    @XmlElement(name = "Contraseña", required = true)
    protected String Contraseña;
    @XmlElement(name = "Rol", required = true)
    protected String Rol;
    @XmlElement(name = "Estado", required = true)
    protected String Estado;
    @XmlElement(name = "lst", required = true)
    protected List<String> lst;

    public int getId_Usuario() {
        return id_Usuario;
    }

    public void setId_Usuario(int id_Usuario) {
        this.id_Usuario = id_Usuario;
    }

    public int getId_Unidad() {
        return id_Unidad;
    }

    public void setId_Unidad(int id_Unidad) {
        this.id_Unidad = id_Unidad;
    }

    public int getId_Persona() {
        return id_Persona;
    }

    public void setId_Persona(int id_Persona) {
        this.id_Persona = id_Persona;
    }

    public String getNombre_Usuario() {
        return Nombre_Usuario;
    }

    public void setNombre_Usuario(String Nombre_Usuario) {
        this.Nombre_Usuario = Nombre_Usuario;
    }

    public String getContraseña() {
        return Contraseña;
    }

    public void setContraseña(String Contraseña) {
        this.Contraseña = Contraseña;
    }

    public String getRol() {
        return Rol;
    }

    public void setRol(String Rol) {
        this.Rol = Rol;
    }

    public String getEstado() {
        return Estado;
    }

    public void setEstado(String Estado) {
        this.Estado = Estado;
    }

    public List<String> getLst() {
        return lst;
    }

    public void setLst(List<String> lst) {
        this.lst = lst;
    }
}
