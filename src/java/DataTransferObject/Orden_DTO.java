  package DataTransferObject;

import java.util.List;

/**
 *
 * @author ZionSystems
 */
public class Orden_DTO {

    private int id_Orden;
    private Unidad_DTO unidad;
    private Paciente_DTO Paciente;
    private Medico_DTO Medico;
    private Persona_DTO Empleado;
    private String Fecha;
    private String Hora;
    private Float Total;
    private Float Restante;
    private List<Det_Orden_DTO> Det_Orden;
    private List<Pago_DTO> pagos;
    private String estado;
    private String convenio;
    private int Folio_Unidad;

    /**
     * @return the id_Orden
     */
    public int getId_Orden() {
        return id_Orden;
    }

    /**
     * @param id_Orden the id_Orden to set
     */
    public void setId_Orden(int id_Orden) {
        this.id_Orden = id_Orden;
    }

    /**
     * @return the unidad
     */
    public Unidad_DTO getUnidad() {
        return unidad;
    }

    /**
     * @param unidad the unidad to set
     */
    public void setUnidad(Unidad_DTO unidad) {
        this.unidad = unidad;
    }

    /**
     * @return the Paciente
     */
    public Paciente_DTO getPaciente() {
        return Paciente;
    }

    /**
     * @param Paciente the Paciente to set
     */
    public void setPaciente(Paciente_DTO Paciente) {
        this.Paciente = Paciente;
    }

    /**
     * @return the Medico
     */
    public Medico_DTO getMedico() {
        return Medico;
    }

    /**
     * @param Medico the Medico to set
     */
    public void setMedico(Medico_DTO Medico) {
        this.Medico = Medico;
    }

    /**
     * @return the Empleado
     */
    public Persona_DTO getEmpleado() {
        return Empleado;
    }

    /**
     * @param Empleado the Empleado to set
     */
    public void setEmpleado(Persona_DTO Empleado) {
        this.Empleado = Empleado;
    }

    /**
     * @return the Fecha
     */
    public String getFecha() {
        return Fecha;
    }

    /**
     * @param Fecha the Fecha to set
     */
    public void setFecha(String Fecha) {
        this.Fecha = Fecha;
    }

    /**
     * @return the Hora
     */
    public String getHora() {
        return Hora;
    }

    /**
     * @param Hora the Hora to set
     */
    public void setHora(String Hora) {
        this.Hora = Hora;
    }

    /**
     * @return the Total
     */
    public Float getTotal() {
        return Total;
    }

    /**
     * @param Total the Total to set
     */
    public void setTotal(Float Total) {
        this.Total = Total;
    }

    /**
     * @return the Restante
     */
    public Float getRestante() {
        return Restante;
    }

    /**
     * @param Restante the Restante to set
     */
    public void setRestante(Float Restante) {
        this.Restante = Restante;
    }

    /**
     * @return the Det_Orden
     */
    public List<Det_Orden_DTO> getDet_Orden() {
        return Det_Orden;
    }

    /**
     * @param Det_Orden the Det_Orden to set
     */
    public void setDet_Orden(List<Det_Orden_DTO> Det_Orden) {
        this.Det_Orden = Det_Orden;
    }

    /**
     * @return the pagos
     */
    public List<Pago_DTO> getPagos() {
        return pagos;
    }

    /**
     * @param pagos the pagos to set
     */
    public void setPagos(List<Pago_DTO> pagos) {
        this.pagos = pagos;
    }

    /**
     * @return the estado
     */
    public String getEstado() {
        return estado;
    }

    /**
     * @param estado the estado to set
     */
    public void setEstado(String estado) {
        this.estado = estado;
    }

    /**
     * @return the convenio
     */
    public String getConvenio() {
        return convenio;
    }

    /**
     * @param convenio the convenio to set
     */
    public void setConvenio(String convenio) {
        this.convenio = convenio;
    }

    /**
     * @return the Folio_Unidad
     */
    public int getFolio_Unidad() {
        return Folio_Unidad;
    }

    /**
     * @param Folio_Unidad the Folio_Unidad to set
     */
    public void setFolio_Unidad(int Folio_Unidad) {
        this.Folio_Unidad = Folio_Unidad;
    }
    }
