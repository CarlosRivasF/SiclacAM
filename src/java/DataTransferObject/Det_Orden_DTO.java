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
    "id_det_orden",
    "id_orden",
    "estudio",
    "descuento",
    "sobrecargo",
    "Fecha_Entrega",
    "T_Entrega",
    "Subtotal"
})
@XmlRootElement(name = "Det_Orden_DTO")
public class Det_Orden_DTO {

    @XmlElement(name = "id_det_orden", required = true)
    private int id_det_orden;
    @XmlElement(name = "id_orden", required = true)
    private int id_orden;
    @XmlElement(name = "estudio", required = true)
    private Estudio_DTO estudio;
    @XmlElement(name = "descuento", required = true)
    private Float descuento;
    @XmlElement(name = "sobrecargo", required = true)
    private Float sobrecargo;
    @XmlElement(name = "Fecha_Entrega", required = true)
    private String Fecha_Entrega;
    @XmlElement(name = "T_Entrega", required = true)
    private String T_Entrega;
    @XmlElement(name = "Subtotal", required = true)
    private Float Subtotal;

    public int getId_det_orden() {
        return id_det_orden;
    }

    public void setId_det_orden(int id_det_orden) {
        this.id_det_orden = id_det_orden;
    }

    public int getId_orden() {
        return id_orden;
    }

    public void setId_orden(int id_orden) {
        this.id_orden = id_orden;
    }

    public Estudio_DTO getEstudio() {
        return estudio;
    }

    public void setEstudio(Estudio_DTO estudio) {
        this.estudio = estudio;
    }

    public Float getDescuento() {
        return descuento;
    }

    public void setDescuento(Float descuento) {
        this.descuento = descuento;
    }

    public Float getSobrecargo() {
        return sobrecargo;
    }

    public void setSobrecargo(Float sobrecargo) {
        this.sobrecargo = sobrecargo;
    }

    public String getFecha_Entrega() {
        return Fecha_Entrega;
    }

    public void setFecha_Entrega(String Fecha_Entrega) {
        this.Fecha_Entrega = Fecha_Entrega;
    }

    public String getT_Entrega() {
        return T_Entrega;
    }

    public void setT_Entrega(String T_Entrega) {
        this.T_Entrega = T_Entrega;
    }

    public Float getSubtotal() {
        return Subtotal;
    }

    public void setSubtotal(Float Subtotal) {
        this.Subtotal = Subtotal;
    }
}
