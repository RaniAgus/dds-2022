package exceptions;

public class RespuestaNoObtenidaException extends RuntimeException {
  public RespuestaNoObtenidaException(String message) {
    super(message);
  }


  public RespuestaNoObtenidaException(String message, Throwable cause) {
    super(message, cause);
  }
}
