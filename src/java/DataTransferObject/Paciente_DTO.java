package DataTransferObject;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author ZionSystems
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = {
    "id_Paciente",
    "id_Unidad",
    "CodPac",
    "SendMail"
})
@XmlRootElement(name = "Paciente_DTO")
public class Paciente_DTO extends Persona_DTO {

    @XmlElement(name = "id_Paciente", required = true)
    protected int id_Paciente;
    @XmlElement(name = "id_Unidad", required = true)
    protected int id_Unidad;
    @XmlElement(name = "CodPac", required = true)
    protected String CodPac;
    @XmlElement(name = "SendMail", required = true)
    protected boolean SendMail;

    public int getId_Paciente() {
        return id_Paciente;
    }

    public void setId_Paciente(int id_Paciente) {
        this.id_Paciente = id_Paciente;
    }

    public int getId_Unidad() {
        return id_Unidad;
    }

    public void setId_Unidad(int id_Unidad) {
        this.id_Unidad = id_Unidad;
    }

    public String getCodPac() {
        return CodPac;
    }

    public void setCodPac(String CodPac) {
        this.CodPac = CodPac;
    }

    public boolean getSendMail() {
        return SendMail;
    }

    public void setSenMail(boolean SendMail) {
        this.SendMail = SendMail;
    }
}
