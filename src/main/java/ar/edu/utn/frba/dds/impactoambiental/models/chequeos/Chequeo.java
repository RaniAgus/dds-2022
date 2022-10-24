package ar.edu.utn.frba.dds.impactoambiental.models.chequeos;

import java.util.Optional;
import java.util.function.Predicate;

public class Chequeo<T> {
  private Predicate<T> chequeo;
  private String mensajeDeError;

  public Chequeo(Predicate<T> chequeo, String mensajeDeError) {
    this.chequeo = chequeo;
    this.mensajeDeError = mensajeDeError;
  }

  public Optional<String> getError(T valor) {
    return chequeo.test(valor) ? Optional.empty() : Optional.of(mensajeDeError);
  }
}
