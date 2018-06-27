package DataAccesObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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
        Main objetoPrincipal = new Main();

        String fecha1 = "12/05/2018";

        Date fechaActual = new Date();
        SimpleDateFormat formateador = new SimpleDateFormat("dd/MM/yyyy");
        String fechaSistema = formateador.format(fechaActual);

        String resultadoMenor = objetoPrincipal.compararFechasConDate(fecha1, fechaSistema);
        System.out.println(resultadoMenor + "\n");

    }
}
