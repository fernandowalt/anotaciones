package anotaciones.ejemplos.modelos;

import anotaciones.ejemplos.JsonAtributo;
import anotaciones.ejemplos.procesador.Init;

import java.time.LocalDate;

public class Producto {

@JsonAtributo()
private String nombre;
@JsonAtributo
private Long precio;

private LocalDate fecha;

@Init
private void init () {

}

public LocalDate getFecha () {
    return fecha;
}

public void setFecha (LocalDate fecha) {
    this.fecha = fecha;
}

public String getNombre () {
    return nombre;
}

public void setNombre (String nombre) {
    this.nombre = nombre;
}

public Long getPrecio () {
    return precio;
}

public void setPrecio (Long precio) {
    this.precio = precio;
}
}
