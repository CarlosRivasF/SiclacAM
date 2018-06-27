package DataAccesObject;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ZionSystems
 */
public class Semana {

    public String getDia() {
        return dia;
    }

    public void setDia(String dia) {
        this.dia = dia;
    }

    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input;
    }

    protected String dia;
    protected String input;
    String lunesC = "<div class='col custom-control custom-checkbox form-check-inline' >"
            + "<input type='checkbox' class='custom-control-input' checked name='dia' id='lunes' value='1'>"
            + "<label class='col custom-control-label' for='lunes'>&emsp13;Lunes</label>"
            + "</div>";
    String martesC = "<div class='col custom-control custom-checkbox form-check-inline'>"
            + "<input class='custom-control-input' checked type='checkbox' name='dia' id='martes' value='2'>"
            + "<label class='col custom-control-label' for='martes'>&emsp13;Martes</label>"
            + "</div>";
    String miercolesC = "<div class='col custom-control custom-checkbox form-check-inline'>"
            + "<input class='custom-control-input' checked type='checkbox' name='dia' id='miercoles' value='3'>"
            + "<label class='col custom-control-label' for='miercoles'>Miércoles</label>"
            + "</div>";
    String juevesC = "<div class='col custom-control custom-checkbox form-check-inline'>"
            + "<input class='custom-control-input' checked type='checkbox' name='dia' id='jueves' value='4'>"
            + "<label class='col custom-control-label' for='jueves'>&emsp13;Jueves</label>"
            + "</div> ";
    String viernesC = "<div class='col custom-control custom-checkbox form-check-inline'>"
            + "<input class='custom-control-input' checked type='checkbox' name='dia' id='viernes' value='5'>"
            + "<label class='col custom-control-label' for='viernes'>&emsp13;Viernes</label>"
            + "</div>";
    String sabadoC = "<div class='col custom-control custom-checkbox form-check-inline'>"
            + "<input class='custom-control-input' checked type='checkbox' name='dia' id='sabado' value='6'>"
            + "<label class='col custom-control-label' for='sabado'>&emsp13;Sábado</label>"
            + "</div>";
    String domingoC = "<div class='col custom-control custom-checkbox form-check-inline'>"
            + "<input class='custom-control-input' checked type='checkbox' name='dia' id='domingo' value='7'>"
            + "<label class='col custom-control-label' for='domingo'>&emsp13;Domingo</label>"
            + "</div>";

    public List<Semana> semana() {
        List<Semana> sem = new ArrayList<>();
        String Lunes = "<div class='col custom-control custom-checkbox form-check-inline' >"
                + "<input type='checkbox' class='custom-control-input' name='dia' id='lunes' value='1'>"
                + "<label class='col custom-control-label' for='lunes'>&emsp13;Lunes</label>"
                + "</div>";
        Semana s1 = new Semana();
        s1.setDia("Lunes");
        s1.setInput(Lunes);
        sem.add(s1);
        String Martes = "<div class='col custom-control custom-checkbox form-check-inline'>"
                + "<input class='custom-control-input' type='checkbox' name='dia' id='martes' value='2'>"
                + "<label class='col custom-control-label' for='martes'>&emsp13;Martes</label>"
                + "</div>";
        Semana s2 = new Semana();
        s2.setDia("Martes");
        s2.setInput(Martes);
        sem.add(s2);
        String Miércoles = "<div class='col custom-control custom-checkbox form-check-inline'>"
                + "<input class='custom-control-input' type='checkbox' name='dia' id='miercoles' value='3'>"
                + "<label class='col custom-control-label' for='miercoles'>Miércoles</label>"
                + "</div>";
        Semana s3 = new Semana();
        s3.setDia("Miércoles");
        s3.setInput(Miércoles);
        sem.add(s3);
        String Jueves = "<div class='col custom-control custom-checkbox form-check-inline'>"
                + "<input class='custom-control-input' type='checkbox' name='dia' id='jueves' value='4'>"
                + "<label class='col custom-control-label' for='jueves'>&emsp13;Jueves</label>"
                + "</div> ";
        Semana s4 = new Semana();
        s4.setDia("Jueves");
        s4.setInput(Jueves);
        sem.add(s4);
        String Viernes = "<div class='col custom-control custom-checkbox form-check-inline'>"
                + "<input class='custom-control-input' type='checkbox' name='dia' id='viernes' value='5'>"
                + "<label class='col custom-control-label' for='viernes'>&emsp13;Viernes</label>"
                + "</div>";
        Semana s5 = new Semana();
        s5.setDia("Viernes");
        s5.setInput(Viernes);
        sem.add(s5);
        String Sábado = "<div class='col custom-control custom-checkbox form-check-inline'>"
                + "<input class='custom-control-input' type='checkbox' name='dia' id='sabado' value='6'>"
                + "<label class='col custom-control-label' for='sabado'>&emsp13;Sábado</label>"
                + "</div>";
        Semana s6 = new Semana();
        s6.setDia("Sábado");
        s6.setInput(Sábado);
        sem.add(s6);
        String Domingo = "<div class='col custom-control custom-checkbox form-check-inline'>"
                + "<input class='custom-control-input' type='checkbox' name='dia' id='domingo' value='7'>"
                + "<label class='col custom-control-label' for='domingo'>&emsp13;Domingo</label>"
                + "</div>";
        Semana s7 = new Semana();
        s7.setDia("Domingo");
        s7.setInput(Domingo);
        sem.add(s7);
        return sem;
    }

    public List<Semana> semanaChecked() {
        List<Semana> sem = new ArrayList<>();
        String Lunes = "<div class='col custom-control custom-checkbox form-check-inline' >"
                + "<input type='checkbox' class='custom-control-input' checked name='dia' id='lunes' value='1'>"
                + "<label class='col custom-control-label' for='lunes'>&emsp13;Lunes</label>"
                + "</div>";
        Semana s1 = new Semana();
        s1.setDia("Lunes");
        s1.setInput(Lunes);
        sem.add(s1);
        String Martes = "<div class='col custom-control custom-checkbox form-check-inline'>"
                + "<input class='custom-control-input' checked type='checkbox'  name='dia' id='martes' value='2'>"
                + "<label class='col custom-control-label' for='martes'>&emsp13;Martes</label>"
                + "</div>";
        Semana s2 = new Semana();
        s2.setDia("Martes");
        s2.setInput(Martes);
        sem.add(s2);
        String Miércoles = "<div class='col custom-control custom-checkbox form-check-inline'>"
                + "<input class='custom-control-input' checked type='checkbox' name='dia' id='miercoles' value='3'>"
                + "<label class='col custom-control-label' for='miercoles'>Miércoles</label>"
                + "</div>";
        Semana s3 = new Semana();
        s3.setDia("Miércoles");
        s3.setInput(Miércoles);
        sem.add(s3);
        String Jueves = "<div class='col custom-control custom-checkbox form-check-inline'>"
                + "<input class='custom-control-input' checked type='checkbox' name='dia' id='jueves' value='4'>"
                + "<label class='col custom-control-label' for='jueves'>&emsp13;Jueves</label>"
                + "</div> ";
        Semana s4 = new Semana();
        s4.setDia("Jueves");
        s4.setInput(Jueves);
        sem.add(s4);
        String Viernes = "<div class='col custom-control custom-checkbox form-check-inline'>"
                + "<input class='custom-control-input' checked type='checkbox' name='dia' id='viernes' value='5'>"
                + "<label class='col custom-control-label' for='viernes'>&emsp13;Viernes</label>"
                + "</div>";
        Semana s5 = new Semana();
        s5.setDia("Viernes");
        s5.setInput(Viernes);
        sem.add(s5);
        String Sábado = "<div class='col custom-control custom-checkbox form-check-inline'>"
                + "<input class='custom-control-input' checked type='checkbox' name='dia' id='sabado' value='6'>"
                + "<label class='col custom-control-label' for='sabado'>&emsp13;Sábado</label>"
                + "</div>";
        Semana s6 = new Semana();
        s6.setDia("Sábado");
        s6.setInput(Sábado);
        sem.add(s6);
        String Domingo = "<div class='col custom-control custom-checkbox form-check-inline'>"
                + "<input class='custom-control-input' checked type='checkbox' name='dia' id='domingo' value='7'>"
                + "<label class='col custom-control-label' for='domingo'>&emsp13;Domingo</label>"
                + "</div>";
        Semana s7 = new Semana();
        s7.setDia("Domingo");
        s7.setInput(Domingo);
        sem.add(s7);
        return sem;
    }

    public List<Semana> semanaCheckDisabled() {
        List<Semana> sem = new ArrayList<>();
        String Lunes = "<div class='col custom-control custom-checkbox form-check-inline' >"
                + "<input type='checkbox' class='custom-control-input' checked name='dia' id='lunes' value='1' disabled>"
                + "<label style=' color: white' class='col custom-control-label' for='lunes'>&emsp13;Lunes</label>"
                + "</div>";
        Semana s1 = new Semana();
        s1.setDia("Lunes");
        s1.setInput(Lunes);
        sem.add(s1);
        String Martes = "<div class='col custom-control custom-checkbox form-check-inline'>"
                + "<input class='custom-control-input' checked type='checkbox'  name='dia' id='martes' value='2' disabled>"
                + "<label style=' color: white' class='col custom-control-label' for='martes'>&emsp13;Martes</label>"
                + "</div>";
        Semana s2 = new Semana();
        s2.setDia("Martes");
        s2.setInput(Martes);
        sem.add(s2);
        String Miércoles = "<div class='col custom-control custom-checkbox form-check-inline'>"
                + "<input class='custom-control-input' checked type='checkbox' name='dia' id='miercoles' value='3' disabled>"
                + "<label style=' color: white' class='col custom-control-label' for='miercoles'>Miércoles</label>"
                + "</div>";
        Semana s3 = new Semana();
        s3.setDia("Miércoles");
        s3.setInput(Miércoles);
        sem.add(s3);
        String Jueves = "<div class='col custom-control custom-checkbox form-check-inline'>"
                + "<input class='custom-control-input' checked type='checkbox' name='dia' id='jueves' value='4' disabled>"
                + "<label style=' color: white' class='col custom-control-label' for='jueves'>&emsp13;Jueves</label>"
                + "</div> ";
        Semana s4 = new Semana();
        s4.setDia("Jueves");
        s4.setInput(Jueves);
        sem.add(s4);
        String Viernes = "<div class='col custom-control custom-checkbox form-check-inline'>"
                + "<input class='custom-control-input' checked type='checkbox' name='dia' id='viernes' value='5' disabled>"
                + "<label style=' color: white' class='col custom-control-label' for='viernes'>&emsp13;Viernes</label>"
                + "</div>";
        Semana s5 = new Semana();
        s5.setDia("Viernes");
        s5.setInput(Viernes);
        sem.add(s5);
        String Sábado = "<div class='col custom-control custom-checkbox form-check-inline'>"
                + "<input class='custom-control-input' checked type='checkbox' name='dia' id='sabado' value='6' disabled>"
                + "<label style=' color: white' class='col custom-control-label' for='sabado'>&emsp13;Sábado</label>"
                + "</div>";
        Semana s6 = new Semana();
        s6.setDia("Sábado");
        s6.setInput(Sábado);
        sem.add(s6);
        String Domingo = "<div class='col custom-control custom-checkbox form-check-inline'>"
                + "<input  class='custom-control-input' checked type='checkbox' name='dia' id='domingo' value='7' disabled>"
                + "<label style=' color: white' class='col custom-control-label' for='domingo'>&emsp13;Domingo</label>"
                + "</div>";
        Semana s7 = new Semana();
        s7.setDia("Domingo");
        s7.setInput(Domingo);
        sem.add(s7);
        return sem;
    }

}
