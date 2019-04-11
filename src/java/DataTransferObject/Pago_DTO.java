package DataTransferObject;

import DataBase.Util;

/**
 *
 * @author ZionSystems
 */
public class Pago_DTO {

    protected int id_pago;
    protected int id_Orden;
    protected String T_Pago;
    protected Float monto;
    protected String fecha;
    protected String hora;

    public int getId_pago() {
        return id_pago;
    }

    public void setId_pago(int id_pago) {
        this.id_pago = id_pago;
    }

    public int getId_Orden() {
        return id_Orden;
    }

    public void setId_Orden(int id_Orden) {
        this.id_Orden = id_Orden;
    }

    public String getT_Pago() {
        return T_Pago;
    }

    public void setT_Pago(String T_Pago) {
        this.T_Pago = T_Pago;
    }

    public Float getMonto() {
        return Util.redondearDecimales(monto);
    }

    public void setMonto(Float monto) {
        this.monto = Util.redondearDecimales(monto);
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }
}
