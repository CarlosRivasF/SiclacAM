package DataTransferObject;

/**
 *
 * @author ZionSystem
 */
public class Det_Prom_DTO {
    
    private int id_Det_Prom;    
    private Estudio_DTO estudio;
    private Float descuento;    
    private String T_Entrega;
    private Float Subtotal;

    /**
     * @return the id_Det_Prom
     */
    public int getId_Det_Prom() {
        return id_Det_Prom;
    }

    /**
     * @param id_Det_Prom the id_Det_Prom to set
     */
    public void setId_Det_Prom(int id_Det_Prom) {
        this.id_Det_Prom = id_Det_Prom;
    }

    /**
     * @return the estudio
     */
    public Estudio_DTO getEstudio() {
        return estudio;
    }

    /**
     * @param estudio the estudio to set
     */
    public void setEstudio(Estudio_DTO estudio) {
        this.estudio = estudio;
    }

    /**
     * @return the descuento
     */
    public Float getDescuento() {
        return descuento;
    }

    /**
     * @param descuento the descuento to set
     */
    public void setDescuento(Float descuento) {
        this.descuento = descuento;
    }

    /**
     * @return the T_Entrega
     */
    public String getT_Entrega() {
        return T_Entrega;
    }

    /**
     * @param T_Entrega the T_Entrega to set
     */
    public void setT_Entrega(String T_Entrega) {
        this.T_Entrega = T_Entrega;
    }

    /**
     * @return the Subtotal
     */
    public Float getSubtotal() {
        return Subtotal;
    }

    /**
     * @param Subtotal the Subtotal to set
     */
    public void setSubtotal(Float Subtotal) {
        this.Subtotal = Subtotal;
    }
    
}
