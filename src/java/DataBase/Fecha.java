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
    
    public static void main(String[]args){
    Fecha f=new Fecha();
    Period ed=f.getEdad("1996-09-22");
    System.out.print(ed.getYears()+" "+ ed.getMonths()+" "+ed.getDays());
    }
}
