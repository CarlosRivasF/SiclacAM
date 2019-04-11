package DataAccesObject;

import DataBase.Util;
import DataTransferObject.Estudio_DTO;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author ZionSystems
 */
public class Main {

    private static String compararFechasConDate(String Solicita, String Termina) {
        String resultado = "";
        try {
            SimpleDateFormat formateador = new SimpleDateFormat("YYYY-MM-dd");
            Date FechaTerm = formateador.parse(Termina);
            Date FechaSol = formateador.parse(Solicita);

            System.out.println("FS: " + FechaSol.getTime() + "," + FechaSol.getDay());
            System.out.println("FT: " + FechaTerm.getTime() + "," + FechaTerm.getDay());
//            if (FechaSol.compareTo(FechaTerm) > 0) {
//                resultado = "1";
//                System.out.println("'a' Solicita " + Solicita + " Caduca " + Termina);
//            } else if (FechaSol.compareTo(FechaTerm) < 0) {
//                resultado = "0";
//                System.out.println("'b' Solicita " + Solicita + " Caduca " + Termina);
//            } else if (FechaSol.compareTo(FechaTerm) == 0) {
//                resultado = "0";
//                System.out.println("'e' Solicita " + Solicita + " Caduca " + Termina);
//            } else {
//                System.out.println("How to get here?");
//            }

//            if (FechaSol.after(FechaTerm)) {
//                resultado = "1";
//                System.out.println("'a' Solicita " + Solicita + " Caduca " + Termina);
//            } else {
//                if (FechaSol.before(FechaTerm)) {
//                    resultado = "0";
//                    System.out.println("'b' Solicita " + Solicita + " Caduca " + Termina);
//                } else {
//                    if (Solicita.equals(Termina)) {
//                        System.out.println("'e' Solicita " + Solicita + " Caduca " + Termina);
//                        resultado = "0";
//                    }
//                }
//            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return resultado;
    }

    public static void main(String[] args) {
        Date fac = new Date();
        Util f = new Util();
        f.setHora(fac);
        System.out.println(Main.compararFechasConDate("2019-01-25", f.getFechaActual()));
    }
}
