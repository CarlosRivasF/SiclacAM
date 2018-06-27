package DataTransferObject;

/**
 *
 * @author ZionSystems
 */
public class Paciente_DTO extends Persona_DTO {

    protected int id_Paciente;
    protected int id_Unidad;
    protected String CodPac;
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
