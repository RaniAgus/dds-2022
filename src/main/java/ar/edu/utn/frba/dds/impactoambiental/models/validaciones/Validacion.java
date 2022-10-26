package ar.edu.utn.frba.dds.impactoambiental.models.validaciones;

import java.util.Optional;

public interface Validacion<T> {
  default Optional<String> getError(T valor) {
    return validar(valor) ? Optional.empty() : Optional.of(getMensajeDeError());
  }

  boolean validar(T valor);
  String getMensajeDeError();
}
