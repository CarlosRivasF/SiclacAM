package DataTransferObject;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author Carlos Rivas
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = {
    "id_Configuración",
    "Descripcion",
    "Valor_min",
    "Valor_MAX",
    "Uniddes",
    "sexo",
    "res"
})
@XmlRootElement(name = "Configuracion_DTO")
public class Configuracion_DTO {

    @XmlElement(name = "id_Configuración", required = true)
    protected int id_Configuración;
    @XmlElement(name = "Descripcion", required = true)
    protected String Descripcion;
    @XmlElement(name = "Valor_min", required = true)
    protected String Valor_min;
    @XmlElement(name = "Valor_MAX", required = true)
    protected String Valor_MAX;
    @XmlElement(name = "Uniddes", required = true)
    protected String Uniddes;
    @XmlElement(name = "sexo", required = true)
    protected String sexo;
    @XmlElement(name = "res", required = true)
    protected Resultado_DTO res;

    public int getId_Configuración() {
        return id_Configuración;
    }

    public void setId_Configuración(int id_Configuración) {
        this.id_Configuración = id_Configuración;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String Descripcion) {
        this.Descripcion = Descripcion;
    }

    public String getValor_min() {
        return Valor_min;
    }

    public void setValor_min(String Valor_min) {
        this.Valor_min = Valor_min;
    }

    public String getValor_MAX() {
        return Valor_MAX;
    }

    public void setValor_MAX(String Valor_MAX) {
        this.Valor_MAX = Valor_MAX;
    }

    public String getUniddes() {
        return Uniddes;
    }

    public void setUniddes(String Uniddes) {
        this.Uniddes = Uniddes;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public Resultado_DTO getRes() {
        return res;
    }

    public void setRes(Resultado_DTO res) {
        this.res = res;
    }
}
