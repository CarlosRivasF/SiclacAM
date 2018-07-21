package DataTransferObject;

/**
 *
 * @author ZionSystem
 */
public class Det_Prom_DTO {
    
    private int id_Det_Prom;
    private int id_Promocion;
    private Estudio_DTO estudio;
    private int descuento;
    private String Fecha_Entrega;
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
     * @return the id_Promocion
     */
    public int getId_Promocion() {
        return id_Promocion;
    }

    /**
     * @param id_Promocion the id_Promocion to set
     */
    public void setId_Promocion(int id_Promocion) {
        this.id_Promocion = id_Promocion;
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
    public int getDescuento() {
        return descuento;
    }

    /**
     * @param descuento the descuento to set
     */
    public void setDescuento(int descuento) {
        this.descuento = descuento;
    }

    /**
     * @return the Fecha_Entrega
     */
    public String getFecha_Entrega() {
        return Fecha_Entrega;
    }

    /**
     * @param Fecha_Entrega the Fecha_Entrega to set
     */
    public void setFecha_Entrega(String Fecha_Entrega) {
        this.Fecha_Entrega = Fecha_Entrega;
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
