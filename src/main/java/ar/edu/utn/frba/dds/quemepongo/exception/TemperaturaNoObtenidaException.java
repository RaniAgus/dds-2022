package ar.edu.utn.frba.dds.quemepongo.exception;

public class TemperaturaNoObtenidaException extends RuntimeException {
  public TemperaturaNoObtenidaException() {
    super("No se pudo obtener la temperatura actual");
  }

  public TemperaturaNoObtenidaException(Throwable reason) {
    super("No se pudo obtener la temperatura actual", reason);
  }
}
