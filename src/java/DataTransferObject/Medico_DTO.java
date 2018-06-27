package DataTransferObject;

/**
 *
 * @author Carlos Rivas
 */
public class Medico_DTO extends Persona_DTO {

    public String getCodMed() {
        return CodMed;
    }

    public void setCodMed(String CodMed) {
        this.CodMed = CodMed;
    }

    protected int id_Medico;
    protected int id_Unidad;
    protected String Empresa;
    protected Float participacion;
    protected Float Descuento;
    protected String CodMed;

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
}
