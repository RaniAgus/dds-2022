package ar.edu.utn.frba.dds.impactoambiental.utils;

import java.util.Optional;
import java.util.function.Predicate;

// TODO: Hacer que los validadores y chequeos sean polimórficos
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

  public static <T> Chequeo<T> valido() {
    return new Chequeo<>(v -> true, "");
  }
}
