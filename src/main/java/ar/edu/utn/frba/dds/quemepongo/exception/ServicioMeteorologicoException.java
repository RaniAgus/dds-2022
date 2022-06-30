package ar.edu.utn.frba.dds.quemepongo.exception;

public class ServicioMeteorologicoException extends RuntimeException {
  private static String message = "Ocurrió un error al intentar consultar las condiciones climáticas.";

  public ServicioMeteorologicoException() {
    super(message);
  }

  public ServicioMeteorologicoException(Throwable reason) {
    super(message, reason);
  }
}
