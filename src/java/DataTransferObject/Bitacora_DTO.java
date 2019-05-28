package DataTransferObject;

/**
 *
 * @author Carlos Rivas
 */
public class Bitacora_DTO {

    protected int id_Transaccion;
    protected String Accion;
    protected String Table;
    protected String Query;
    protected int id_Reg_Table;
    protected int id_Persona;
    protected String Fecha;
    protected String Hora;
    protected int Status;

    public int getId_Transaccion() {
        return id_Transaccion;
    }

    public void setId_Transaccion(int id_Transaccion) {
        this.id_Transaccion = id_Transaccion;
    }

    public String getAccion() {
        return Accion;
    }

    public void setAccion(String Accion) {
        this.Accion = Accion;
    }

    public String getTable() {
        return Table;
    }

    public void setTable(String Table) {
        this.Table = Table;
    }

    public String getQuery() {
        return Query;
    }

    public void setQuery(String Query) {
        this.Query = Query;
    }

    public int getId_Reg_Table() {
        return id_Reg_Table;
    }

    public void setId_Reg_Table(int id_Reg_Table) {
        this.id_Reg_Table = id_Reg_Table;
    }

    public int getId_Persona() {
        return id_Persona;
    }

    public void setId_Persona(int id_Persona) {
        this.id_Persona = id_Persona;
    }

    public String getFecha() {
        return Fecha;
    }

    public void setFecha(String Fecha) {
        this.Fecha = Fecha;
    }

    public String getHora() {
        return Hora;
    }

    public void setHora(String Hora) {
        this.Hora = Hora;
    }

    public int getStatus() {
        return Status;
    }

    public void setStatus(int Status) {
        this.Status = Status;
    }

}
