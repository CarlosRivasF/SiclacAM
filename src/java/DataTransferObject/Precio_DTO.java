package DataTransferObject;

/**
 *
 * @author ZionSystems
 */
public class Precio_DTO {

    protected int id_Precio;
    protected int id_Estudio;
    protected Float Precio_N;
    protected int T_Entrega_N;
    protected Float Precio_U;
    protected int T_Entrega_U;

    public int getId_Precio() {
        return id_Precio;
    }

    public void setId_Precio(int id_Precio) {
        this.id_Precio = id_Precio;
    }

    public Float getPrecio_N() {
        return Precio_N;
    }

    public void setPrecio_N(Float Precio_N) {
        this.Precio_N = Precio_N;
    }

    public int getT_Entrega_N() {
        return T_Entrega_N;
    }

    public void setT_Entrega_N(int T_Entrega_N) {
        this.T_Entrega_N = T_Entrega_N;
    }

    public Float getPrecio_U() {
        return Precio_U;
    }

    public void setPrecio_U(Float Precio_U) {
        this.Precio_U = Precio_U;
    }

    public int getT_Entrega_U() {
        return T_Entrega_U;
    }

    public void setT_Entrega_U(int T_Entrega_U) {
        this.T_Entrega_U = T_Entrega_U;
    }

    public int getId_Estudio() {
        return id_Estudio;
    }

    public void setId_Estudio(int id_Estudio) {
        this.id_Estudio = id_Estudio;
    }
}
