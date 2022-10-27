package ar.edu.utn.frba.dds.impactoambiental.models.validaciones;

import java.util.Optional;
import java.util.function.Predicate;

public interface Validacion<T> {
  default Optional<String> getError(T valor) {
    return validar(valor) ? Optional.empty() : Optional.of(getMensajeDeError());
  }

  boolean validar(T valor);
  String getMensajeDeError();

  static <T> Validacion<T> create(Predicate<T> chequeo, String mensajeDeError) {
    return new Validacion<T>() {
      @Override
      public boolean validar(T valor) {
        return chequeo.test(valor);
      }

      @Override
      public String getMensajeDeError() {
        return mensajeDeError;
      }
    };
  }
}
