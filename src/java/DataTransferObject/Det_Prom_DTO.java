package DataTransferObject;

/**
 *
 * @author ZionSystem
 */
public class Det_Prom_DTO {
    
    private int id_Det_Prom;    
    private Estudio_DTO estudio;
    private Float descuento;    
    private Float sobrecargo;    
    private String T_Entrega;
    private Float Subtotal;

    public int getId_Det_Prom() {
        return id_Det_Prom;
    }

    public void setId_Det_Prom(int id_Det_Prom) {
        this.id_Det_Prom = id_Det_Prom;
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
