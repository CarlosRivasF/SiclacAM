package DataTransferObject;

import java.io.Serializable;

/**
 *
 * @author Carlos Rivas
 */
public class Municipio_DTO extends Estado_DTO implements Serializable{

    protected int id_Municipio;
    protected String Nombre_Municipio;

    public int getId_Municipio() {
        return id_Municipio;
    }

    public void setId_Municipio(int id_Municipio) {
        this.id_Municipio = id_Municipio;
    }

    public String getNombre_Municipio() {
        return Nombre_Municipio;
    }

    public void setNombre_Municipio(String Nombre_Municipio) {
        this.Nombre_Municipio = Nombre_Municipio;
    }
}
