package DataTransferObject;

/**
 *
 * @author ZionSystems
 */
public class Est_Mat_DTO extends Material_DTO {

    protected String unidadE;
    protected int cantidadE;
    protected String T_Muestra;

    public String getUnidadE() {
        return unidadE;
    }

    public void setUnidadE(String unidadE) {
        this.unidadE = unidadE;
    }

    public int getCantidadE() {
        return cantidadE;
    }

    public void setCantidadE(int cantidadE) {
        this.cantidadE = cantidadE;
    }

    public String getT_Muestra() {
        return T_Muestra;
    }

    public void setT_Muestra(String T_Muestra) {
        this.T_Muestra = T_Muestra;
    }
}
