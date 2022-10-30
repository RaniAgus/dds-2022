package ar.edu.utn.frba.dds.impactoambiental.exceptions;

import java.util.List;
import java.util.stream.Collectors;

public class ValidacionFallidaException extends RuntimeException {


  public ValidacionFallidaException(List<String> fallido) {
    super("Errores: " + String.join(", ", fallido.stream().collect(Collectors.joining(". \n"))));
  }

}
