package DataBase;

import java.util.regex.Pattern;

/**
 *
 * @author ZionSystems
 */
public class RgxValidate {

    private boolean validarCURP(String curp) {
        String regex
                = "[A-Z]{1}[AEIOU]{1}[A-Z]{2}[0-9]{2}"
                + "(0[1-9]|1[0-2])(0[1-9]|1[0-9]|2[0-9]|3[0-1])"
                + "[HM]{1}"
                + "(AS|BC|BS|CC|CS|CH|CL|CM|DF|DG|GT|GR|HG|JC|MC|MN|MS|NT|NL|OC|PL|QT|QR|SP|SL|SR|TC|TS|TL|VZ|YN|ZS|NE)"
                + "[B-DF-HJ-NP-TV-Z]{3}"
                + "[0-9A-Z]{1}[0-9]{1}$";
        Pattern patron = Pattern.compile(regex);
        return patron.matcher(curp).matches();
    }

    public static void main(String[] args) {
        RgxValidate rgx = new RgxValidate();
        String n = "Carlos";
        String ap = "Rivas";
        String am = "Futero";
        String fn = "1996-09-22";
        String cp="PAPP910205-1";
        System.out.println(cp.substring(0,4));
    }

}
