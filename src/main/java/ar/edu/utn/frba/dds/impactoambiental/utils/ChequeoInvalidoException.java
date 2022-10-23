package ar.edu.utn.frba.dds.impactoambiental.utils;

import java.util.List;

public class ChequeoInvalidoException extends RuntimeException {
  private List<String> errores;

  public ChequeoInvalidoException(List<String> errores) {
    super("Errores: " + String.join(", ", errores));
    this.errores = errores;
  }

  public List<String> getErrores() {
    return errores;
  }
}
