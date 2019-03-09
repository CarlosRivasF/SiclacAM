package DataTransferObject;

public class Estadistica_DTO {

    private int id_Est_Uni;
    private String Clave_Estudio;
    private String Nombre_Estudio;
    private int Cantidad;

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

    public int getCantidad() {
        return Cantidad;
    }

    public void setCantidad(int Cantidad) {
        this.Cantidad = Cantidad;
    }

    public String getClave_Estudio() {
        return Clave_Estudio;
    }

    public void setClave_Estudio(String Clave_Estudio) {
        this.Clave_Estudio = Clave_Estudio;
    }
}
