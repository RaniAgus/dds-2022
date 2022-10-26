package ar.edu.utn.frba.dds.impactoambiental.models.validaciones;

import ar.edu.utn.frba.dds.impactoambiental.exceptions.ChequeoFallidoException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Validador<T> {
  private List<Validacion<T>> validaciones = new ArrayList<>();

  public Validador<T> agregarValidacion(Predicate<T> chequeo, String mensajeDeError) {
    validaciones.add(new Validacion<T>() {
      @Override
      public boolean validar(T valor) {
        return chequeo.test(valor);
      }

      @Override
      public String getMensajeDeError() {
        return mensajeDeError;
      }
    });
    return this;
  }

  public Validador<T> agregarValidaciones(List<Validacion<T>> validaciones) {
    this.validaciones.addAll(validaciones);
    return this;
  }

  public Try<T> validar(T valor) {
    List<String> errores = getErrores(valor);
    if (!errores.isEmpty()) {
      throw new ChequeoFallidoException(Try.fallido(errores));
    }
    return Try.exitoso(valor);
  }

  private List<String> getErrores(T valor) {
    return validaciones.stream()
        .map(validacion -> validacion.getError(valor))
        .filter(Optional::isPresent)
        .map(Optional::get)
        .collect(Collectors.toList());
  }

}
