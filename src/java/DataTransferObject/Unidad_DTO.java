package DataTransferObject;

import java.io.Serializable;

/**
 *
 * @author Carlos Rivas
 */
public class Unidad_DTO extends Empresa_DTO implements Serializable{

    protected int id_Unidad;
    protected String Nombre_Unidad;
    protected String clave;
    protected Persona_DTO encargado;
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
