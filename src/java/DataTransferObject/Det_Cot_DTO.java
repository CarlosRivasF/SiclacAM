package DataTransferObject;

/**
 *
 * @author ZionSystems
 */
public class Det_Cot_DTO {

    protected int id_Det_Cot;
    protected int id_Cotizacion;
    protected Estudio_DTO Estudio;
    protected Float Descuento;
    protected String T_Entrega;
    protected Float Subtotal;

    public int getId_Det_Cot() {
        return id_Det_Cot;
    }

    public void setId_Det_Cot(int id_Det_Cot) {
        this.id_Det_Cot = id_Det_Cot;
    }

    public int getId_Cotizacion() {
        return id_Cotizacion;
    }

    public void setId_Cotizacion(int id_Cotizacion) {
        this.id_Cotizacion = id_Cotizacion;
    }

    public Estudio_DTO getEstudio() {
        return Estudio;
    }

    public void setEstudio(Estudio_DTO Estudio) {
        this.Estudio = Estudio;
    }

    public Float getDescuento() {
        return Descuento;
    }

    public void setDescuento(Float Descuento) {
        this.Descuento = Descuento;
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
