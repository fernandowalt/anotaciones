package anotaciones.ejemplos;
import anotaciones.ejemplos.modelos.Producto;
import anotaciones.ejemplos.procesador.JsonSerializador;
import java.time.LocalDate;


public class EjemploAnotacion {
public static void main (String[] args) {

    Producto p = new Producto();
    p.setFecha(LocalDate.now());
    p.setNombre("mesa centro roble");
    p.setPrecio(1000L);

    System.out.println(JsonSerializador.convertirJson(p));
}


}
