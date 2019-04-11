package DataTransferObject;

import DataBase.Util;

/**
 *
 * @author Carlos Rivas
 */
public class Participacion_DTO {

    private int id_participacion;
    private int id_Unidad;
    private int id_Orden;
    private int id_Medico;
    private String FechaPart;
    private String Hora;
    private Float Monto;
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

    public int getId_Orden() {
        return id_Orden;
    }

    public void setId_Orden(int id_Orden) {
        this.id_Orden = id_Orden;
    }

    public int getId_Medico() {
        return id_Medico;
    }

    public void setId_Medico(int id_Medico) {
        this.id_Medico = id_Medico;
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
        this.Monto = Util.redondearDecimales(Monto );
    }

    public String getConvenio() {
        return convenio;
    }

    public void setConvenio(String convenio) {
        this.convenio = convenio;
    }

}
