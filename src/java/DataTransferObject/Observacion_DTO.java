package DataTransferObject;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author Carlos Rivas
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = {
    "id_Observacion",
    "Observacion"
})
@XmlRootElement(name = "Observacion_DTO")
public class Observacion_DTO {

    @XmlElement(name = "id_Observacion", required = true)
    private int id_Observacion;
    @XmlElement(name = "Observacion", required = true)
    private String Observacion;

    /**
     * @return the id_Observacion
     */
    public int getId_Observacion() {
        return id_Observacion;
    }

    /**
     * @param id_Observacion the id_Observacion to set
     */
    public void setId_Observacion(int id_Observacion) {
        this.id_Observacion = id_Observacion;
    }

    /**
     * @return the Observacion
     */
    public String getObservacion() {
        return Observacion;
    }

    /**
     * @param Observacion the Observacion to set
     */
    public void setObservacion(String Observacion) {
        this.Observacion = Observacion;
    }

}
