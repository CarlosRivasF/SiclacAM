package DataTransferObject;

import java.io.Serializable;

/**
 *
 * @author Carlos Rivas
 */
public class Permiso_DTO implements Serializable{

    public String getInput() {
        return Input;
    }

    public void setInput(String Input) {
        this.Input = Input;
    }

    protected String ruta;
    protected String nombre;
    protected String Input;

    public String getRuta() {
        return ruta;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    
}
