package DataTransferObject;

import java.io.Serializable;

/**
 *
 * @author Carlos Rivas
 */
public class Persona_DTO extends Direccion_DTO implements Serializable{

    protected int id_Persona;
    protected String Nombre;
    protected String Ap_Paterno;
    protected String Ap_Materno;
    protected String Fecha_Nac;
    protected String Sexo;
    protected String Mail;
    protected String Telefono1;
    protected String Telefono2;

    public int getId_Persona() {
        return id_Persona;
    }

    public void setId_Persona(int id_Persona) {
        this.id_Persona = id_Persona;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String Nombre) {
        this.Nombre = Nombre;
    }

    public String getAp_Paterno() {
        return Ap_Paterno;
    }

    public void setAp_Paterno(String Ap_Paterno) {
        this.Ap_Paterno = Ap_Paterno;
    }

    public String getAp_Materno() {
        return Ap_Materno;
    }

    public void setAp_Materno(String Ap_Materno) {
        this.Ap_Materno = Ap_Materno;
    }

    public String getFecha_Nac() {
        return Fecha_Nac;
    }

    public void setFecha_Nac(String Fecha_Nac) {
        this.Fecha_Nac = Fecha_Nac;
    }

    public String getSexo() {
        return Sexo;
    }

    public void setSexo(String Sexo) {
        this.Sexo = Sexo;
    }

    public String getMail() {
        return Mail;
    }

    public void setMail(String Mail) {
        this.Mail = Mail;
    }

    public String getTelefono1() {
        return Telefono1;
    }

    public void setTelefono1(String Telefono1) {
        this.Telefono1 = Telefono1;
    }

    public String getTelefono2() {
        return Telefono2;
    }

    public void setTelefono2(String Telefono2) {
        this.Telefono2 = Telefono2;
    }

}
