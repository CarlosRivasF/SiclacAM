package DataTransferObject;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author Carlos Rivas
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = {
    "id_Medico",
    "id_Unidad",
    "Empresa",
    "participacion",
    "Descuento",
    "CodMed",
    "Participaciones"
})
@XmlRootElement(name = "Medico_DTO")
public class Medico_DTO extends Persona_DTO {

    public Medico_DTO() {
        List<Participacion_DTO> Parts = new ArrayList<>();
        this.Participaciones = Parts;
    }

    @XmlElement(name = "id_Medico", required = true)
    private int id_Medico;
    @XmlElement(name = "id_Unidad", required = true)
    private int id_Unidad;
    @XmlElement(name = "Empresa", required = true)
    private String Empresa;
    @XmlElement(name = "participacion", required = true)
    private Float participacion;
    @XmlElement(name = "Descuento", required = true)
    private Float Descuento;
    @XmlElement(name = "CodMed", required = true)
    private String CodMed;
    @XmlElementWrapper(name = "Participaciones")
    @XmlElement(name = "Participacion_DTO")
    private List<Participacion_DTO> Participaciones;

    public int getId_Medico() {
        return id_Medico;
    }

    public void setId_Medico(int id_Medico) {
        this.id_Medico = id_Medico;
    }

    public int getId_Unidad() {
        return id_Unidad;
    }

    public void setId_Unidad(int id_Unidad) {
        this.id_Unidad = id_Unidad;
    }

    public String getEmpresa() {
        return Empresa;
    }

    public void setEmpresa(String Empresa) {
        this.Empresa = Empresa;
    }

    public Float getParticipacion() {
        return participacion;
    }

    public void setParticipacion(Float participacion) {
        this.participacion = participacion;
    }

    public Float getDescuento() {
        return Descuento;
    }

    public void setDescuento(Float Descuento) {
        this.Descuento = Descuento;
    }

    public List<Participacion_DTO> getParticipaciones() {
        return Participaciones;
    }

    public void setParticipaciones(List<Participacion_DTO> Participaciones) {
        this.Participaciones = Participaciones;
    }

    public String getCodMed() {
        return CodMed;
    }

    public void setCodMed(String CodMed) {
        this.CodMed = CodMed;
    }
}
