package DataBase;

import java.text.ParseException;
import java.util.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.security.MessageDigest;
import java.util.Arrays;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.binary.Base64;

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

    public static String Encriptar(String texto) {
        String secretKey = "Zi0n5yst3ms.Key"; //llave para encriptar datos
        String base64EncryptedString = "";

        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] digestOfPassword = md.digest(secretKey.getBytes("utf-8"));
            byte[] keyBytes = Arrays.copyOf(digestOfPassword, 24);

            SecretKey key = new SecretKeySpec(keyBytes, "DESede");
            Cipher cipher = Cipher.getInstance("DESede");
            cipher.init(Cipher.ENCRYPT_MODE, key);

            byte[] plainTextBytes = texto.getBytes("utf-8");
            byte[] buf = cipher.doFinal(plainTextBytes);
            byte[] base64Bytes = Base64.encodeBase64(buf);
            base64EncryptedString = new String(base64Bytes);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return base64EncryptedString;
    }

    public static String Desencriptar(String textoEncriptado) {
        String secretKey = "Zi0n5yst3ms.Key"; //llave para encriptar datos
        String base64EncryptedString = "";

        try {
            byte[] message = Base64.decodeBase64(textoEncriptado.getBytes("utf-8"));
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] digestOfPassword = md.digest(secretKey.getBytes("utf-8"));
            byte[] keyBytes = Arrays.copyOf(digestOfPassword, 24);
            SecretKey key = new SecretKeySpec(keyBytes, "DESede");

            Cipher decipher = Cipher.getInstance("DESede");
            decipher.init(Cipher.DECRYPT_MODE, key);

            byte[] plainText = decipher.doFinal(message);

            base64EncryptedString = new String(plainText, "UTF-8");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return base64EncryptedString;
    }

    public static void main(String[] args) {
        //AmLab - SgBa1Q0kRyE= - 
        //        5P0Am-74b0r470r1O
        //GpoAm - HImZqRm+YBw=
        String str="Vieja";
        String strEncr=Fecha.Encriptar(str);
        System.out.println(strEncr);
        System.out.println(Fecha.Desencriptar(strEncr));
    }
}
