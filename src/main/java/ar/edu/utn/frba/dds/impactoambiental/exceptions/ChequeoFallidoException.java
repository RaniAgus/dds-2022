package ar.edu.utn.frba.dds.impactoambiental.exceptions;

import java.util.List;

public class ChequeoFallidoException extends RuntimeException {
  private List<String> errores;

  public ChequeoFallidoException(List<String> errores) {
    super("Errores: " + String.join(", ", errores));
    this.errores = errores;
  }

  public List<String> getErrores() {
    return errores;
  }
}
