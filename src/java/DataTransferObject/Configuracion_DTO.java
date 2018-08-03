package DataTransferObject;

/**
 *
 * @author Carlos Rivas
 */
public class Configuracion_DTO {

    protected int id_Configuración;
    protected String Descripcion;
    protected String Valor_min;
    protected String Valor_MAX;
    protected String Uniddes;
    protected String sexo;      
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
