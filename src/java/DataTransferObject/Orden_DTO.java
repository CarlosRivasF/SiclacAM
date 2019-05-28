package DataTransferObject;

import DataBase.Util;
import java.util.List;

/**
 *
 * @author ZionSystems
 */
public class Orden_DTO {

    protected int id_Orden;
    protected Unidad_DTO unidad;
    protected Paciente_DTO Paciente;
    protected Medico_DTO Medico;
    protected Empleado_DTO Empleado;
    protected String FechaOr;
    protected String Hora;
    protected Float montoRestante;
    protected Float montoPagado;
    protected List<Det_Orden_DTO> Det_Orden;
    protected List<Pago_DTO> pagos;
    protected String estado;
    protected String convenio;
    protected int Folio_Unidad;
    private Float montoPartselect;
    

    public int getId_Orden() {
        return id_Orden;
    }

    public void setId_Orden(int id_Orden) {
        this.id_Orden = id_Orden;
    }

    public Unidad_DTO getUnidad() {
        return unidad;
    }

    public void setUnidad(Unidad_DTO unidad) {
        this.unidad = unidad;
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

    public Empleado_DTO getEmpleado() {
        return Empleado;
    }

    public void setEmpleado(Empleado_DTO Empleado) {
        this.Empleado = Empleado;
    }

    public String getFecha() {
        return FechaOr;
    }

    public void setFecha(String Fecha) {
        this.FechaOr = Fecha;
    }

    public String getHora() {
        return Hora;
    }

    public void setHora(String Hora) {
        this.Hora = Hora;
    }

    public Float getMontoRestante() {
        return Util.redondearDecimales(montoRestante);
    }

    public void setMontoRestante(Float montoRestante) {
        this.montoRestante = Util.redondearDecimales(montoRestante);
    }

    public Float getMontoPagado() {
        return Util.redondearDecimales(montoPagado);
    }

    public void setMontoPagado(Float montoPagado) {
        this.montoPagado = Util.redondearDecimales(montoPagado);
    }

    public List<Det_Orden_DTO> getDet_Orden() {
        return Det_Orden;
    }

    public void setDet_Orden(List<Det_Orden_DTO> Det_Orden) {
        this.Det_Orden = Det_Orden;
    }

    public List<Pago_DTO> getPagos() {
        return pagos;
    }

    public void setPagos(List<Pago_DTO> pagos) {
        this.pagos = pagos;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getConvenio() {
        return convenio;
    }

    public void setConvenio(String convenio) {
        this.convenio = convenio;
    }

    public int getFolio_Unidad() {
        return Folio_Unidad;
    }

    public void setFolio_Unidad(int Folio_Unidad) {
        this.Folio_Unidad = Folio_Unidad;
    }

    public Float getMontoPartselect() {
        return montoPartselect;
    }

    public void setMontoPartselect(Float montoPartselect) {
        this.montoPartselect = montoPartselect;
    }
}
