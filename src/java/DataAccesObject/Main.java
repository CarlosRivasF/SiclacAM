package DataAccesObject;

import DataTransferObject.Estudio_DTO;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.poi.ss.usermodel.Sheet;

/**
 *
 * @author ZionSystems
 */
public class Main {

    private String compararFechasConDate(String fecha1, String fechaActual) {
        String resultado = "";
        try {
            SimpleDateFormat formateador = new SimpleDateFormat("dd/MM/yyyy");
            Date fechaDate1 = formateador.parse(fecha1);
            Date fechaDate2 = formateador.parse(fechaActual);

            if (fechaDate1.before(fechaDate2)) {
                resultado = "La Fecha " + fecha1 + " es menor ";
            } else {
                if (fechaDate2.before(fechaDate1)) {
                    resultado = "La Fecha " + fecha1 + " es Mayor ";
                } else {
                    resultado = "Las Fechas Son iguales ";
                }
            }
        } catch (ParseException e) {
            System.out.println("Se Produjo un Error!!!  " + e.getMessage());
        }
        return resultado;
    }

    public static void main(String[] args) {
        int id_Unidad = 4;
        List<Estudio_DTO> Catalogo;
        List<Estudio_DTO> Catalogo2 = new ArrayList<>();

        Estudio_DAO E = new Estudio_DAO();
        Catalogo = E.getEstudiosByUnidad(id_Unidad);
        for (int i = 1; i <= 8; i++) {
            for (Estudio_DTO dto : Catalogo) {
                if (dto.getId_Tipo_Estudio() == i) {
                    Catalogo2.add(dto);
                }
            }
        }
        int id_Tipo_Estudio = 0;
        for (Estudio_DTO dto : Catalogo2) {
            if (id_Tipo_Estudio != dto.getId_Tipo_Estudio()) {
                id_Tipo_Estudio = dto.getId_Tipo_Estudio();
                System.out.println("Nueva Hoja para: " + dto.getNombre_Tipo_Estudio().toUpperCase());
            }
        }
    }
}
