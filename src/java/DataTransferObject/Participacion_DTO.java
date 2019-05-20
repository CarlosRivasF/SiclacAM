/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataTransferObject;

import DataBase.Util;

/**
 *
 * @author Carlos Rivas
 */
public class Participacion_DTO {

    private int id_participacion;
    private int id_Unidad;
    private Orden_DTO Orden;
    private Medico_DTO Medico;
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
        this.Monto = Util.redondearDecimales(Monto );
    }

    public String getConvenio() {
        return convenio;
    }

    public void setConvenio(String convenio) {
        this.convenio = convenio;
    }

}
