package ar.edu.utn.frba.dds.impactoambiental.models.chequeos;

import java.util.Optional;

public interface Chequeo<T> {
  default Optional<String> getError(T valor) {
    return validar(valor) ? Optional.empty() : Optional.of(getMensajeDeError());
  }

  boolean validar(T valor);
  String getMensajeDeError();
}
