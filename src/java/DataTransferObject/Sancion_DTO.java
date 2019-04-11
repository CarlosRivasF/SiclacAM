package DataTransferObject;

import DataBase.Util;

/**
 *
 * @author ZionSystems
 */
public class Sancion_DTO {

    protected int id_Sancion;
    protected Persona_DTO por;
    protected Float Monto;
    protected String Motivo;
    protected String FechaSanc;

    public int getId_Sancion() {
        return id_Sancion;
    }

    public void setId_Sancion(int id_Sancion) {
        this.id_Sancion = id_Sancion;
    }

    public Persona_DTO getPor() {
        return por;
    }

    public void setPor(Persona_DTO por) {
        this.por = por;
    }

    public Float getMonto() {
        return Util.redondearDecimales(Monto);
    }

    public void setMonto(Float Monto) {
        this.Monto = Util.redondearDecimales(Monto);
    }

    public String getMotivo() {
        return Motivo;
    }

    public void setMotivo(String Motivo) {
        this.Motivo = Motivo;
    }

    public String getFecha() {
        return FechaSanc;
    }

    public void setFecha(String Fecha) {
        this.FechaSanc = Fecha;
    }
}
