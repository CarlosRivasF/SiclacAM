package DataTransferObject;

import java.io.Serializable;

/**
 *
 * @author Carlos Rivas
 */
public class Estado_DTO implements Serializable{
    
protected int id_Estado;
protected String Nombre_Estado;

    public int getId_Estado() {
        return id_Estado;
    }

    public void setId_Estado(int id_Estado) {
        this.id_Estado = id_Estado;
    }

    public String getNombre_Estado() {
        return Nombre_Estado;
    }

    public void setNombre_Estado(String Nombre_Estado) {
        this.Nombre_Estado = Nombre_Estado;
    }
    
}
