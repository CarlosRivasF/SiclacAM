package DataTransferObject;

/**
 *
 * @author ZionSystems
 */
public class Det_Orden_DTO {

    private int id_det_orden;
    private int id_orden;
    private Estudio_DTO estudio;
    private Float descuento;
    private Float sobrecargo;
    private String Fecha_Entrega;
    private String T_Entrega;
    private Float Subtotal;

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
        this.descuento = descuento;
    }

    public Float getSobrecargo() {
        return sobrecargo;
    }

    public void setSobrecargo(Float sobrecargo) {
        this.sobrecargo = sobrecargo;
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
        this.Subtotal = Subtotal;
    }
}
