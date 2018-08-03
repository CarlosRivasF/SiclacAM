package DataTransferObject;

import java.util.List;

/**
 *
 * @author ZionSystems
 */
public class Estudio_DTO extends Tipo_Estudio_DTO {
    
    public Estudio_DTO(){
    addRes=false;
    }

    protected int id_Estudio;
    protected int id_Est_Uni;
    protected String Nombre_Estudio;
    protected String Clave_Estudio;
    protected String Preparacion;
    protected String Utilidad;
    protected String Metodo;
    protected Precio_DTO precio;
    protected List<Configuracion_DTO> Cnfs;
    protected List<Est_Mat_DTO> mts;
    protected String ctrl_est;
    protected int PorcEst;
    protected Boolean addRes;

    public int getId_Estudio() {
        return id_Estudio;
    }

    public void setId_Estudio(int id_Estudio) {
        this.id_Estudio = id_Estudio;
    }

    public int getId_Est_Uni() {
        return id_Est_Uni;
    }

    public void setId_Est_Uni(int id_Est_Uni) {
        this.id_Est_Uni = id_Est_Uni;
    }

    public String getNombre_Estudio() {
        return Nombre_Estudio;
    }

    public void setNombre_Estudio(String Nombre_Estudio) {
        this.Nombre_Estudio = Nombre_Estudio;
    }

    public String getClave_Estudio() {
        return Clave_Estudio;
    }

    public void setClave_Estudio(String Clave_Estudio) {
        this.Clave_Estudio = Clave_Estudio;
    }

    public String getPreparacion() {
        return Preparacion;
    }

    public void setPreparacion(String Preparacion) {
        this.Preparacion = Preparacion;
    }

    public String getUtilidad() {
        return Utilidad;
    }

    public void setUtilidad(String Utilidad) {
        this.Utilidad = Utilidad;
    }

    public String getMetodo() {
        return Metodo;
    }

    public void setMetodo(String Metodo) {
        this.Metodo = Metodo;
    }

    public Precio_DTO getPrecio() {
        return precio;
    }

    public void setPrecio(Precio_DTO precio) {
        this.precio = precio;
    }

    public List<Configuracion_DTO> getCnfs() {
        return Cnfs;
    }

    public void setCnfs(List<Configuracion_DTO> Cnfs) {
        this.Cnfs = Cnfs;
    }

    public List<Est_Mat_DTO> getMts() {
        return mts;
    }

    public void setMts(List<Est_Mat_DTO> mts) {
        this.mts = mts;
    }

    public String getCtrl_est() {
        return ctrl_est;
    }

    public void setCtrl_est(String ctrl_est) {
        this.ctrl_est = ctrl_est;
    }

    public int getPorcEst() {
        return PorcEst;
    }

    public void setPorcEst(int PorcEst) {
        this.PorcEst = PorcEst;
    }

    public Boolean getAddRes() {
        return addRes;
    }

    public void setAddRes(Boolean addRes) {
        this.addRes = addRes;
    }
}
