package DataTransferObject;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author ZionSystem
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = {
    "id_resultado",
    "Valor_Obtenido"
})
@XmlRootElement(name = "Resultado_DTO")
public class Resultado_DTO {

    @XmlElement(name = "id_resultado", required = true)
    protected int id_resultado;
    @XmlElement(name = "Valor_Obtenido", required = true)
    protected String Valor_Obtenido;

    public int getId_resultado() {
        return id_resultado;
    }

    public void setId_resultado(int id_resultado) {
        this.id_resultado = id_resultado;
    }

    public String getValor_Obtenido() {
        return Valor_Obtenido;
    }

    public void setValor_Obtenido(String Valor_Obtenido) {
        this.Valor_Obtenido = Valor_Obtenido;
    }
}
