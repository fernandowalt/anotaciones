package anotaciones.ejemplos.procesador;
import anotaciones.ejemplos.JsonAtributo;
import anotaciones.ejemplos.procesador.exception.SerializadorException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;


public class JsonSerializador {

public static void inicializarObjeto (Object objeto) {

    if (Objects.isNull(objeto)) {
        throw new SerializadorException("El objeto a serializar no puede ser nulo");
    }

    Method[] metodos = objeto.getClass().getDeclaredMethods();

    Arrays.stream(metodos).filter(m -> m.isAnnotationPresent(Init.class)).forEach(m -> {
        m.setAccessible(true);
        try {
            m.invoke(objeto);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new SerializadorException(
                    "Error al serializar, no se puede inicializar el objeto " + e.getMessage());
        }
    });

}


public static String convertirJson (Object objeto) {

    if (Objects.isNull(objeto)) {
        throw new SerializadorException("El objeto a serializar no puede ser nulo");
    }

    inicializarObjeto(objeto);

    Field[] atributos = objeto.getClass().getDeclaredFields();

    return Arrays.stream(atributos)
                 .filter(f -> f.isAnnotationPresent(JsonAtributo.class))
                 .map(f -> {
                     f.setAccessible(true);
                     String nombre = f.getAnnotation(JsonAtributo.class)
                                      .nombre()
                                      .equals("") ? f.getName() : f.getAnnotation(
                             JsonAtributo.class).nombre();

                     try {
                         Object valor = f.get(objeto);
                         if (f.getAnnotation(JsonAtributo.class)
                              .capitalizar() && valor instanceof String nuevoValor) {


                             nuevoValor = Arrays.stream(String.valueOf(valor).split(" "))
                                                .map(p -> p.substring(0, 1)
                                                           .toUpperCase() + p.substring(1)
                                                                             .toLowerCase())
                                                .collect(Collectors.joining(" "));
                             f.set(objeto, nuevoValor);
                         }


                         return "\"" + nombre + "\":\"" + f.get(objeto) + "\"";
                     } catch (IllegalAccessException e) {
                         throw new RuntimeException(
                                 "Error al serializar el Json: " + e.getMessage());
                     }
                 })
                 .reduce("{", (a, b) -> {
                     if ("{".equals(a)) {
                         return a + b;
                     }
                     return a + ", " + b;
                 })
                 .concat("}");


}


}
