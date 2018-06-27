package DataTransferObject;

import java.io.Serializable;

/**
 *
 * @author Carlos Rivas
 */
public class Direccion_DTO extends Colonia_DTO implements Serializable{

    protected int id_Direccion;
    protected String Calle;
    protected String No_Int;
    protected String No_Ext;

    public int getId_Direccion() {
        return id_Direccion;
    }

    public void setId_Direccion(int id_Direccion) {
        this.id_Direccion = id_Direccion;
    }

    public String getCalle() {
        return Calle;
    }

    public void setCalle(String Calle) {
        this.Calle = Calle;
    }

    public String getNo_Int() {
        return No_Int;
    }

    public void setNo_Int(String No_Int) {
        this.No_Int = No_Int;
    }

    public String getNo_Ext() {
        return No_Ext;
    }

    public void setNo_Ext(String No_Ext) {
        this.No_Ext = No_Ext;
    }
}
