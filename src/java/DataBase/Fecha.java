package DataBase;

import java.text.ParseException;
import java.util.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Fecha {

    private static Date hora;

    public void setHora(Date aHora) {
        hora = aHora;
    }

    public String getFechaActual() {
        SimpleDateFormat formateador = new SimpleDateFormat("YYYY-MM-dd");
        return formateador.format(hora);
    }

    public String getFecha_X(Date h) {
        SimpleDateFormat formateador = new SimpleDateFormat("YYYY-MM-dd");
        return formateador.format(h);
    }

    public String getHoraActual() {
        SimpleDateFormat formateador = new SimpleDateFormat("HH:mm");
        return formateador.format(hora);
    }

    public String getFechaFinal(int dias) {
        SimpleDateFormat formateador = new SimpleDateFormat("YYYY-MM-dd");
        return formateador.format(SumarDias(dias));
    }

    public String getHoraFinal() {
        SimpleDateFormat formateador = new SimpleDateFormat("HH:mm");
        return formateador.format(SumarHoras());
    }

    public Date SumarHoras() {
        Date nuevaFecha;
        Calendar cal = Calendar.getInstance();
        cal.setTime(hora);
        cal.add(Calendar.HOUR, 12);
        nuevaFecha = cal.getTime();
        return nuevaFecha;
    }

    public String SumarDias(int dias) {
        Date nuevaFecha;
        Calendar cal = Calendar.getInstance();
        cal.setTime(hora);
        cal.add(Calendar.DATE, dias);
        nuevaFecha = cal.getTime();
        return getFecha_X(nuevaFecha);
    }

    public Long DateToLong(String fecha) {
        Long time = null;
        Date d;
        try {
            SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
            d = f.parse(fecha);
            time = d.getTime();

        } catch (ParseException ex) {
            throw new RuntimeException(ex);
        }
        return time;
    }

    public String TransFecha(String fecha) {
        String fechaF = null;
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        try {
            fechaF = getFecha_X(f.parse(fecha));
        } catch (ParseException ex) {
            Logger.getLogger(Fecha.class.getName()).log(Level.SEVERE, null, ex);
        }
        return fechaF;
    }

    public String FechaChrome(String fecha) {
        String fechaF = null;
        SimpleDateFormat f = new SimpleDateFormat("dd-MM-yyyy");
        try {
            fechaF = getFecha_X(f.parse(fecha));
        } catch (ParseException ex) {
            Logger.getLogger(Fecha.class.getName()).log(Level.SEVERE, null, ex);
        }
        return fechaF;
    }

    public Period getEdad(String fecha_nac) {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate fn = LocalDate.parse(fecha_nac, fmt);
        LocalDate ah = LocalDate.now();
        Period ed = Period.between(fn, ah);
        return ed;
    }

    public Boolean IsValid(String FechaExp) {
        Boolean r = false;
        try {
            Date date1, date2;
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            date1 = sdf.parse(getFechaActual());
            date2 = sdf.parse(FechaExp);
            if (date2.equals(date1)) {
                r = date2.equals(date1);
            } else {
                r = date2.after(date1);
            }

        } catch (ParseException ex) {
            throw new RuntimeException(ex);
        }
        return r;
    }

    public static void main(String[] args) {
        Date fac = new Date();
        Fecha f = new Fecha();
        f.setHora(fac);
        Scanner sc = new Scanner(System.in);
        String codeBar = sc.nextLine();
        String[] bar = codeBar.split("-");

        int id_Cotizacion = Integer.parseInt(bar[0]);

        System.out.println("id_Cotizacion:" + id_Cotizacion);

    }
}
