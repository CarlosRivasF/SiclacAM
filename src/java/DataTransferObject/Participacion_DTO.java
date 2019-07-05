package DataTransferObject;

import DataBase.Util;
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
    "id_participacion",
    "id_Unidad",
    "Orden",
    "Medico",
    "FechaPart",
    "Hora",
    "Monto",
    "convenio"
})
@XmlRootElement(name = "Participacion_DTO")
public class Participacion_DTO {

    @XmlElement(name = "id_participacion", required = true)
    private int id_participacion;
    @XmlElement(name = "id_Unidad", required = true)
    private int id_Unidad;
    @XmlElement(name = "Orden", required = true)
    private Orden_DTO Orden;
    @XmlElement(name = "Medico", required = true)
    private Medico_DTO Medico;
    @XmlElement(name = "FechaPart", required = true)
    private String FechaPart;
    @XmlElement(name = "Hora", required = true)
    private String Hora;
    @XmlElement(name = "Monto", required = true)
    private Float Monto;
    @XmlElement(name = "convenio", required = true)
    private String convenio;

    public int getId_participacion() {
        return id_participacion;
    }

    public void setId_participacion(int id_participacion) {
        this.id_participacion = id_participacion;
    }

    public int getId_Unidad() {
        return id_Unidad;
    }

    public void setId_Unidad(int id_Unidad) {
        this.id_Unidad = id_Unidad;
    }

    public Orden_DTO getOrden() {
        return Orden;
    }

    public void setOrden(Orden_DTO Orden) {
        this.Orden = Orden;
    }

    public Medico_DTO getMedico() {
        return Medico;
    }

    public void setMedico(Medico_DTO Medico) {
        this.Medico = Medico;
    }

    public String getFecha() {
        return FechaPart;
    }

    public void setFecha(String Fecha) {
        this.FechaPart = Fecha;
    }

    public String getHora() {
        return Hora;
    }

    public void setHora(String Hora) {
        this.Hora = Hora;
    }

    public Float getMonto() {
        return Util.redondearDecimales(Monto);
    }

    public void setMonto(Float Monto) {
        this.Monto = Util.redondearDecimales(Monto);
    }

    public String getConvenio() {
        return convenio;
    }

    public void setConvenio(String convenio) {
        this.convenio = convenio;
    }

}
