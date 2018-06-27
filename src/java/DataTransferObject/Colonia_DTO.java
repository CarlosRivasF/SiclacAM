package DataTransferObject;

import java.io.Serializable;

/**
 *
 * @author Carlos Rivas
 */
public class Colonia_DTO extends CP_DTO implements Serializable{

    protected int id_Colonia;
    protected String Nombre_Colonia;

    public int getId_Colonia() {
        return id_Colonia;
    }

    public void setId_Colonia(int id_Colonia) {
        this.id_Colonia = id_Colonia;
    }

    public String getNombre_Colonia() {
        return Nombre_Colonia;
    }

    public void setNombre_Colonia(String Nombre_Colonia) {
        this.Nombre_Colonia = Nombre_Colonia;
    }
}
