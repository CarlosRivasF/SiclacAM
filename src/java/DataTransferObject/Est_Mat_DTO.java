package DataTransferObject;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author ZionSystems
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = {
    "unidadE",
    "cantidadE",
    "T_Muestra"
})
@XmlRootElement(name = "Est_Mat_DTO")
public class Est_Mat_DTO extends Material_DTO {

    @XmlElement(name = "unidadE", required = true)
    protected String unidadE;
    @XmlElement(name = "cantidadE", required = true)
    protected int cantidadE;
    @XmlElement(name = "T_Muestra", required = true)
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
