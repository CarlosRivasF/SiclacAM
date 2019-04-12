package DataTransferObject;

/**
 *
 * @author ZionSystems
 */
public class Det_Orden_DTO {

    protected int id_det_orden;
    protected int id_orden;
    protected Estudio_DTO estudio;
    protected Float descuento;
    protected String Fecha_Entrega;
    protected String T_Entrega;
    protected Float Subtotal;

    public int getId_det_orden() {
        return id_det_orden;
    }

    public void setId_det_orden(int id_det_orden) {
        this.id_det_orden = id_det_orden;
    }

    public int getId_orden() {
        return id_orden;
    }

    public void setId_orden(int id_orden) {
        this.id_orden = id_orden;
    }

    public Estudio_DTO getEstudio() {
        return estudio;
    }

    public void setEstudio(Estudio_DTO estudio) {
        this.estudio = estudio;
    }

    public Float getDescuento() {
        return descuento;
    }

    public void setDescuento(Float descuento) {
        if (descuento < 0) {
            this.descuento = Float.parseFloat("0");
        } else {
            this.descuento = descuento;
        }
    }

    public String getFecha_Entrega() {
        return Fecha_Entrega;
    }

    public void setFecha_Entrega(String Fecha_Entrega) {
        this.Fecha_Entrega = Fecha_Entrega;
    }

    public String getT_Entrega() {
        return T_Entrega;
    }

    public void setT_Entrega(String T_Entrega) {
        this.T_Entrega = T_Entrega;
    }

    public Float getSubtotal() {
        return Subtotal;
    }

    public void setSubtotal(Float Subtotal) {
        if (Subtotal < 0) {
            this.Subtotal = Float.parseFloat("0");
        } else {
            this.Subtotal = Subtotal;
        }
    }
}
