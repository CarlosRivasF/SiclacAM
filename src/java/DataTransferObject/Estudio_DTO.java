package DataTransferObject;

import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author ZionSystems
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = {
    "id_Estudio",
    "id_Est_Uni",
    "Nombre_Estudio",
    "Clave_Estudio",
    "Preparacion",
    "Utilidad",
    "Metodo",
    "precio",
    "Cnfs",
    "mts",
    "ctrl_est",
    "PorcEst",
    "addRes",
    "Observacion"
})
@XmlRootElement(name = "Estudio_DTO")
public class Estudio_DTO extends Tipo_Estudio_DTO {

    public Estudio_DTO() {
        addRes = false;
    }

    @XmlElement(name = "id_Estudio", required = true)
    private int id_Estudio;
    @XmlElement(name = "id_Est_Uni", required = true)
    private int id_Est_Uni;
    @XmlElement(name = "Nombre_Estudio", required = true)
    private String Nombre_Estudio;
    @XmlElement(name = "Clave_Estudio", required = true)
    private String Clave_Estudio;
    @XmlElement(name = "Preparacion", required = true)
    private String Preparacion;
    @XmlElement(name = "Utilidad", required = true)
    private String Utilidad;
    @XmlElement(name = "Metodo", required = true)
    private String Metodo;
    @XmlElement(name = "precio", required = true)
    private Precio_DTO precio;
    
    @XmlElementWrapper(name = "Cnfs")
    @XmlElement(name = "Configuracion_DTO")
    private List<Configuracion_DTO> Cnfs;
    
    @XmlElementWrapper(name = "mts")
    @XmlElement(name = "Est_Mat_DTO")    
    private List<Est_Mat_DTO> mts;
    
    @XmlElement(name = "ctrl_est", required = true)
    private String ctrl_est;
    @XmlElement(name = "PorcEst", required = true)
    private int PorcEst;
    @XmlElement(name = "addRes", required = true)
    private Boolean addRes;
    @XmlElement(name = "Observacion", required = true)
    private Observacion_DTO Observacion;

    /**
     * @return the id_Estudio
     */
    public int getId_Estudio() {
        return id_Estudio;
    }

    /**
     * @param id_Estudio the id_Estudio to set
     */
    public void setId_Estudio(int id_Estudio) {
        this.id_Estudio = id_Estudio;
    }

    /**
     * @return the id_Est_Uni
     */
    public int getId_Est_Uni() {
        return id_Est_Uni;
    }

    /**
     * @param id_Est_Uni the id_Est_Uni to set
     */
    public void setId_Est_Uni(int id_Est_Uni) {
        this.id_Est_Uni = id_Est_Uni;
    }

    /**
     * @return the Nombre_Estudio
     */
    public String getNombre_Estudio() {
        return Nombre_Estudio;
    }

    /**
     * @param Nombre_Estudio the Nombre_Estudio to set
     */
    public void setNombre_Estudio(String Nombre_Estudio) {
        this.Nombre_Estudio = Nombre_Estudio;
    }

    /**
     * @return the Clave_Estudio
     */
    public String getClave_Estudio() {
        return Clave_Estudio;
    }

    /**
     * @param Clave_Estudio the Clave_Estudio to set
     */
    public void setClave_Estudio(String Clave_Estudio) {
        this.Clave_Estudio = Clave_Estudio;
    }

    /**
     * @return the Preparacion
     */
    public String getPreparacion() {
        return Preparacion;
    }

    /**
     * @param Preparacion the Preparacion to set
     */
    public void setPreparacion(String Preparacion) {
        this.Preparacion = Preparacion;
    }

    /**
     * @return the Utilidad
     */
    public String getUtilidad() {
        return Utilidad;
    }

    /**
     * @param Utilidad the Utilidad to set
     */
    public void setUtilidad(String Utilidad) {
        this.Utilidad = Utilidad;
    }

    /**
     * @return the Metodo
     */
    public String getMetodo() {
        return Metodo;
    }

    /**
     * @param Metodo the Metodo to set
     */
    public void setMetodo(String Metodo) {
        this.Metodo = Metodo;
    }

    /**
     * @return the precio
     */
    public Precio_DTO getPrecio() {
        return precio;
    }

    /**
     * @param precio the precio to set
     */
    public void setPrecio(Precio_DTO precio) {
        this.precio = precio;
    }

    /**
     * @return the Cnfs
     */
    public List<Configuracion_DTO> getCnfs() {
        return Cnfs;
    }

    /**
     * @param Cnfs the Cnfs to set
     */
    public void setCnfs(List<Configuracion_DTO> Cnfs) {
        this.Cnfs = Cnfs;
    }

    /**
     * @return the mts
     */
    public List<Est_Mat_DTO> getMts() {
        return mts;
    }

    /**
     * @param mts the mts to set
     */
    public void setMts(List<Est_Mat_DTO> mts) {
        this.mts = mts;
    }

    /**
     * @return the ctrl_est
     */
    public String getCtrl_est() {
        return ctrl_est;
    }

    /**
     * @param ctrl_est the ctrl_est to set
     */
    public void setCtrl_est(String ctrl_est) {
        this.ctrl_est = ctrl_est;
    }

    /**
     * @return the PorcEst
     */
    public int getPorcEst() {
        return PorcEst;
    }

    /**
     * @param PorcEst the PorcEst to set
     */
    public void setPorcEst(int PorcEst) {
        this.PorcEst = PorcEst;
    }

    /**
     * @return the addRes
     */
    public Boolean getAddRes() {
        return addRes;
    }

    /**
     * @param addRes the addRes to set
     */
    public void setAddRes(Boolean addRes) {
        this.addRes = addRes;
    }

    /**
     * @return the Observacion
     */
    public Observacion_DTO getObservacion() {
        return Observacion;
    }

    /**
     * @param Observacion the Observacion to set
     */
    public void setObservacion(Observacion_DTO Observacion) {
        this.Observacion = Observacion;
    }
}
