package ar.edu.utn.frba.dds.impactoambiental.exceptions;

import ar.edu.utn.frba.dds.impactoambiental.models.validaciones.Either;

public class ValidacionFallidaException extends RuntimeException {
  private Either<?> fallido;

  public ValidacionFallidaException(Either<?> fallido) {
    super("Errores: " + String.join(", ", fallido.getErrores()));
    this.fallido = fallido;
  }

  public Either<?> getEither() {
    return fallido;
  }
}
