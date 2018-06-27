package DataTransferObject;

import java.io.Serializable;

/**
 *
 * @author Carlos Rivas
 */
public class CP_DTO extends Municipio_DTO implements Serializable{

    protected int id_CP;
    protected String c_p;

    public int getId_CP() {
        return id_CP;
    }

    public void setId_CP(int id_CP) {
        this.id_CP = id_CP;
    }

    public String getC_p() {
        return c_p;
    }

    public void setC_p(String c_p) {
        this.c_p = c_p;
    }
}
