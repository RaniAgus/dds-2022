package ar.edu.utn.frba.dds.quemepongo.exception;

public class NotificadorException extends RuntimeException {
  private static String template = "Ocurrió un error al intentar enviar la notificación: %s";

  public NotificadorException(String mensaje) {
    super(String.format(template, mensaje));
  }

  public NotificadorException(String mensaje, Throwable reason) {
    super(String.format(template, mensaje), reason);
  }
}
