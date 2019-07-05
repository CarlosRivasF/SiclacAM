package DataTransferObject;

import DataBase.Util;
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
    "id_pago",
    "id_Orden",
    "T_Pago",
    "monto",
    "fecha",
    "hora"
})
@XmlRootElement(name = "Pago_DTO")
public class Pago_DTO {
    
    @XmlElement(name = "id_pago", required = true)
    protected int id_pago;
    @XmlElement(name = "id_Orden", required = true)
    protected int id_Orden;
    @XmlElement(name = "T_Pago", required = true)
    protected String T_Pago;
    @XmlElement(name = "monto", required = true)
    protected Float monto;
    @XmlElement(name = "fecha", required = true)
    protected String fecha;
    @XmlElement(name = "hora", required = true)
    protected String hora;

    public int getId_pago() {
        return id_pago;
    }

    public void setId_pago(int id_pago) {
        this.id_pago = id_pago;
    }

    public int getId_Orden() {
        return id_Orden;
    }

    public void setId_Orden(int id_Orden) {
        this.id_Orden = id_Orden;
    }

    public String getT_Pago() {
        return T_Pago;
    }

    public void setT_Pago(String T_Pago) {
        this.T_Pago = T_Pago;
    }

    public Float getMonto() {
        return Util.redondearDecimales(monto);
    }

    public void setMonto(Float monto) {
        this.monto = Util.redondearDecimales(monto);
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }
}
