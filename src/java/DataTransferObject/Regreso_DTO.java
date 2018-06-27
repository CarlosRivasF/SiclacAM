package DataTransferObject;

/**
 *
 * @author ZionSystems
 */
public class Regreso_DTO {
    protected int id_regreso;
    protected int id_empleado;
    protected String fecha;
    protected String hora;  

    public int getId_regreso() {
        return id_regreso;
    }

    public void setId_regreso(int id_regreso) {
        this.id_regreso = id_regreso;
    }

    public int getId_empleado() {
        return id_empleado;
    }

    public void setId_empleado(int id_empleado) {
        this.id_empleado = id_empleado;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }
}
