package DataTransferObject;

import java.util.List;

/**
 *
 * @author ZionSystems 
Precio_Total
Estado
 */
public class Cotizacion_DTO {

    protected int id_Cotizacion;
    protected Unidad_DTO Unidad;
    protected Paciente_DTO Paciente;
    protected Medico_DTO Medico;
    protected Persona_DTO Empleado;
    protected String convenio;
    protected String Fecha_Cot;
    protected String Fecha_Exp;
    protected Float Total;    
    protected List<Det_Cot_DTO> Det_Cot;
    protected String Estado;    

    public int getId_Cotizacion() {
        return id_Cotizacion;
    }

    public void setId_Cotizacion(int id_Cotizacion) {
        this.id_Cotizacion = id_Cotizacion;
    }

    public Unidad_DTO getUnidad() {
        return Unidad;
    }

    public void setUnidad(Unidad_DTO Unidad) {
        this.Unidad = Unidad;
    }

    public Paciente_DTO getPaciente() {
        return Paciente;
    }

    public void setPaciente(Paciente_DTO Paciente) {
        this.Paciente = Paciente;
    }

    public Medico_DTO getMedico() {
        return Medico;
    }

    public void setMedico(Medico_DTO Medico) {
        this.Medico = Medico;
    }

    public Persona_DTO getEmpleado() {
        return Empleado;
    }

    public void setEmpleado(Persona_DTO Empleado) {
        this.Empleado = Empleado;
    }

    public String getConvenio() {
        return convenio;
    }

    public void setConvenio(String convenio) {
        this.convenio = convenio;
    }

    public String getFecha_Cot() {
        return Fecha_Cot;
    }

    public void setFecha_Cot(String Fecha_Cot) {
        this.Fecha_Cot = Fecha_Cot;
    }

    public String getFecha_Exp() {
        return Fecha_Exp;
    }

    public void setFecha_Exp(String Fecha_Exp) {
        this.Fecha_Exp = Fecha_Exp;
    }

    public Float getTotal() {
        return Total;
    }

    public void setTotal(Float Total) {
        this.Total = Total;
    }

    public List<Det_Cot_DTO> getDet_Cot() {
        return Det_Cot;
    }

    public void setDet_Cot(List<Det_Cot_DTO> Det_Cot) {
        this.Det_Cot = Det_Cot;
    }

    public String getEstado() {
        return Estado;
    }

    public void setEstado(String Estado) {
        this.Estado = Estado;
    }
}
