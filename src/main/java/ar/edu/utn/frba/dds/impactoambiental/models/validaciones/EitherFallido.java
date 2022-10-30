package ar.edu.utn.frba.dds.impactoambiental.models.validaciones;

import ar.edu.utn.frba.dds.impactoambiental.exceptions.ValidacionFallidaException;
import java.util.List;
import java.util.function.Function;

public class EitherFallido<T> implements Either<T> {
  private List<String> errores;

  protected EitherFallido(List<String> errores) {
    this.errores = errores;
  }

  public T getValor() {
    throw new ValidacionFallidaException(this);
  }

  public List<String> getErrores() {
    return errores;
  }

  public <R> Either<R> map(Function<T, R> function, String error) {
    return Either.fallido(errores);
  }
}
