
package adrianchao_p01_java_swing;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Empleado {
    private String nombre;
    private Date fechaNac;
    private Double Salario;
    
    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    public Empleado(String nombre, String fechaNac, String Salario) throws ParseException, NumberFormatException{
        this.nombre = nombre;
        this.fechaNac = sdf.parse(fechaNac); //De string a Date     
        this.Salario = Double.parseDouble(Salario); //De string a Double
    }

    public String getNombre() {
        return nombre;
    }

    public String getFechaNac() {
        return sdf.format(fechaNac);
    }

    public Double getSalario() {
        return Salario;
    }
}
