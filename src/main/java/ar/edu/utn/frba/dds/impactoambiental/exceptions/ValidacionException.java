package ar.edu.utn.frba.dds.impactoambiental.exceptions;

import java.util.List;

public class ValidacionException extends RuntimeException {
  private List<String> errores;

  public ValidacionException(List<String> errores) {
    super(String.join(", ", errores));
    this.errores = errores;
  }

  public List<String> getErrores() {
    return errores;
  }
}
