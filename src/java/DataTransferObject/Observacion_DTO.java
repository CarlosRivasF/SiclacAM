/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataTransferObject;

/**
 *
 * @author Carlos Rivas
 */
public class Observacion_DTO {

    private int id_Observacion;
    private String Observacion;

    /**
     * @return the id_Observacion
     */
    public int getId_Observacion() {
        return id_Observacion;
    }

    /**
     * @param id_Observacion the id_Observacion to set
     */
    public void setId_Observacion(int id_Observacion) {
        this.id_Observacion = id_Observacion;
    }

    /**
     * @return the Observacion
     */
    public String getObservacion() {
        return Observacion;
    }

    /**
     * @param Observacion the Observacion to set
     */
    public void setObservacion(String Observacion) {
        this.Observacion = Observacion;
    }

}
