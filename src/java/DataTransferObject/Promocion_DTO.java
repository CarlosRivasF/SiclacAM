package DataTransferObject;

import DataBase.Util;
import java.util.List;

/**
 *
 * @author ZionSystem
 */
public class Promocion_DTO {

    private int id_Promocion;
    private int id_Unidad;
    private Persona_DTO Empleado;
    private String Nombre_Promocion;
    private String Descripcion;
    private String Fecha_I;
    private String Fecha_F;
    private Float Precio_Total;
    private List<Det_Prom_DTO> Det_Prom;
    private String Estado;    

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
     * @return the id_Unidad
     */
    public int getId_Unidad() {
        return id_Unidad;
    }

    /**
     * @param id_Unidad the id_Unidad to set
     */
    public void setId_Unidad(int id_Unidad) {
        this.id_Unidad = id_Unidad;
    }

    /**
     * @return the Empleado
     */
    public Persona_DTO getEmpleado() {
        return Empleado;
    }

    /**
     * @param Empleado the Empleado to set
     */
    public void setEmpleado(Persona_DTO Empleado) {
        this.Empleado = Empleado;
    }

    /**
     * @return the Nombre_Promocion
     */
    public String getNombre_Promocion() {
        return Nombre_Promocion;
    }

    /**
     * @param Nombre_Promocion the Nombre_Promocion to set
     */
    public void setNombre_Promocion(String Nombre_Promocion) {
        this.Nombre_Promocion = Nombre_Promocion;
    }

    /**
     * @return the Descripcion
     */
    public String getDescripcion() {
        return Descripcion;
    }

    /**
     * @param Descripcion the Descripcion to set
     */
    public void setDescripcion(String Descripcion) {
        this.Descripcion = Descripcion;
    }

    /**
     * @return the Fecha_I
     */
    public String getFecha_I() {
        return Fecha_I;
    }

    /**
     * @param Fecha_I the Fecha_I to set
     */
    public void setFecha_I(String Fecha_I) {
        this.Fecha_I = Fecha_I;
    }

    /**
     * @return the Fecha_F
     */
    public String getFecha_F() {
        return Fecha_F;
    }

    /**
     * @param Fecha_F the Fecha_F to set
     */
    public void setFecha_F(String Fecha_F) {
        this.Fecha_F = Fecha_F;
    }

    /**
     * @return the Precio_Total
     */
    public Float getPrecio_Total() {
        return Util.redondearDecimales(Precio_Total);
    }

    /**
     * @param Precio_Total the Precio_Total to set
     */
    public void setPrecio_Total(Float Precio_Total) {
        this.Precio_Total = Util.redondearDecimales(Precio_Total);
    }

    /**
     * @return the Det_Prom
     */
    public List<Det_Prom_DTO> getDet_Prom() {
        return Det_Prom;
    }

    /**
     * @param Det_Prom the Det_Prom to set
     */
    public void setDet_Prom(List<Det_Prom_DTO> Det_Prom) {
        this.Det_Prom = Det_Prom;
    }

    /**
     * @return the Estado
     */
    public String getEstado() {
        return Estado;
    }

    /**
     * @param Estado the Estado to set
     */
    public void setEstado(String Estado) {
        this.Estado = Estado;
    }

}
