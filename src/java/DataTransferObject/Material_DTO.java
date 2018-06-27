package DataTransferObject;

import java.io.Serializable;

/**
 *
 * @author ZionSystems
 */
public class Material_DTO implements Serializable{

    public String getClave() {
        return Clave;
    }

    public void setClave(String Clave) {
        this.Clave = Clave;
    }

    protected int id_Material;
    protected int id_Empresa;
    protected int id_Unidad;
    protected int id_Empr_Mat;
    protected int id_Unid_Mat;
    protected String Nombre_Material;
    protected String Clave;
    protected Float Precio;
    protected int Cantidad;

    public int getId_Material() {
        return id_Material;
    }

    public void setId_Material(int id_Material) {
        this.id_Material = id_Material;
    }

    public int getId_Empresa() {
        return id_Empresa;
    }

    public void setId_Empresa(int id_Empresa) {
        this.id_Empresa = id_Empresa;
    }

    public int getId_Unidad() {
        return id_Unidad;
    }

    public void setId_Unidad(int id_Unidad) {
        this.id_Unidad = id_Unidad;
    }

    public int getId_Empr_Mat() {
        return id_Empr_Mat;
    }

    public void setId_Empr_Mat(int id_Empr_Mat) {
        this.id_Empr_Mat = id_Empr_Mat;
    }

    public int getId_Unid_Mat() {
        return id_Unid_Mat;
    }

    public void setId_Unid_Mat(int id_Unid_Mat) {
        this.id_Unid_Mat = id_Unid_Mat;
    }

    public String getNombre_Material() {
        return Nombre_Material;
    }

    public void setNombre_Material(String Nombre_Material) {
        this.Nombre_Material = Nombre_Material;
    }

    public Float getPrecio() {
        return Precio;
    }

    public void setPrecio(Float Precio) {
        this.Precio = Precio;
    }

    public int getCantidad() {
        return Cantidad;
    }

    public void setCantidad(int Cantidad) {
        this.Cantidad = Cantidad;
    }
}
