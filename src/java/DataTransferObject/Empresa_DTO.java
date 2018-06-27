package DataTransferObject;

import java.io.Serializable;

/**
 *
 * @author Carlos Rivas
 */
public class Empresa_DTO  implements Serializable{

    protected int id_Empresa;
    protected String Nombre_Empresa;
    protected int id_Persona;

    public int getId_Empresa() {
        return id_Empresa;
    }

    public void setId_Empresa(int id_Empresa) {
        this.id_Empresa = id_Empresa;
    }

    public String getNombre_Empresa() {
        return Nombre_Empresa;
    }

    public void setNombre_Empresa(String Nombre_Empresa) {
        this.Nombre_Empresa = Nombre_Empresa;
    }

    public int getId_Persona() {
        return id_Persona;
    }

    public void setId_Persona(int id_Persona) {
        this.id_Persona = id_Persona;
    }

}
