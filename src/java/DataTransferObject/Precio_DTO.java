package DataTransferObject;

import DataBase.Util;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author ZionSystems
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = {
    "id_Precio",
    "id_Estudio",
    "Precio_N",
    "T_Entrega_N",
    "Precio_U",
    "T_Entrega_U"
})
@XmlRootElement(name = "Precio_DTO")
public class Precio_DTO {

    @XmlElement(name = "id_Precio", required = true)
    protected int id_Precio;
    @XmlElement(name = "id_Estudio", required = true)
    protected int id_Estudio;
    @XmlElement(name = "Precio_N", required = true)
    protected Float Precio_N;
    @XmlElement(name = "T_Entrega_N", required = true)
    protected int T_Entrega_N;
    @XmlElement(name = "Precio_U", required = true)
    protected Float Precio_U;
    @XmlElement(name = "T_Entrega_U", required = true)
    protected int T_Entrega_U;

    public int getId_Precio() {
        return id_Precio;
    }

    public void setId_Precio(int id_Precio) {
        this.id_Precio = id_Precio;
    }

    public Float getPrecio_N() {
        return Util.redondearDecimales(Precio_N);
    }

    public void setPrecio_N(Float Precio_N) {
        this.Precio_N = Util.redondearDecimales(Precio_N);
    }

    public int getT_Entrega_N() {
        return T_Entrega_N;
    }

    public void setT_Entrega_N(int T_Entrega_N) {
        this.T_Entrega_N = T_Entrega_N;
    }

    public Float getPrecio_U() {
        return Util.redondearDecimales(Precio_U);
    }

    public void setPrecio_U(Float Precio_U) {
        this.Precio_U = Util.redondearDecimales(Precio_U);
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
