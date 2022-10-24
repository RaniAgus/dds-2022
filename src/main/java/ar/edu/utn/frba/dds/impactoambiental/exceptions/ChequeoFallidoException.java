package ar.edu.utn.frba.dds.impactoambiental.exceptions;

import ar.edu.utn.frba.dds.impactoambiental.utils.Try;

public class ChequeoFallidoException extends RuntimeException {
  private Try<?> fallido;

  public ChequeoFallidoException(Try<?> fallido) {
    super("Errores: " + String.join(", ", fallido.getErrores()));
    this.fallido = fallido;
  }

  public Try<?> getTry() {
    return fallido;
  }
}
