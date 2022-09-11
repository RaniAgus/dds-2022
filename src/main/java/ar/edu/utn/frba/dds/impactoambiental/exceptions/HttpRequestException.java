package ar.edu.utn.frba.dds.impactoambiental.exceptions;

public class HttpRequestException extends RuntimeException {
  public HttpRequestException(String message) {
    super(message);
  }

  public HttpRequestException(String message, Throwable cause) {
    super(message, cause);
  }
}
