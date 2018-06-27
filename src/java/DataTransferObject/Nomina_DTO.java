package DataTransferObject;

/**
 *
 * @author ZionSystems
 */
public class Nomina_DTO {
    
     protected int id_Sancion;
    protected Persona_DTO por;
    protected Float Monto;    
    protected String Fecha;

    public int getId_Sancion() {
        return id_Sancion;
    }

    public void setId_Sancion(int id_Sancion) {
        this.id_Sancion = id_Sancion;
    }

    public Persona_DTO getPor() {
        return por;
    }

    public void setPor(Persona_DTO por) {
        this.por = por;
    }

    public Float getMonto() {
        return Monto;
    }

    public void setMonto(Float Monto) {
        this.Monto = Monto;
    }

    public String getFecha() {
        return Fecha;
    }

    public void setFecha(String Fecha) {
        this.Fecha = Fecha;
    }
    
}
