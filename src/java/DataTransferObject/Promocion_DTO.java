package DataTransferObject;

import java.util.List;

/**
 *
 * @author ZionSystem
 */
public class Promocion_DTO {
    
int    id_Promocion ;
int id_Unidad;
protected Persona_DTO Empleado;
String Nombre_Promocion;
String Descripcion;
String Fecha_I;
String Fecha_F;
Float Precio_Total;
protected List<Det_Prom_DTO> Det_Prom;    
String Estado;
String Tipo_Prom ;

}
