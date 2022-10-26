package ar.edu.utn.frba.dds.impactoambiental.models.validaciones;

import ar.edu.utn.frba.dds.impactoambiental.exceptions.ChequeoFallidoException;
import java.util.List;
import java.util.function.Function;

public class TryFallido<T> implements Try<T> {
  private List<String> errores;

  protected TryFallido(List<String> errores) {
    this.errores = errores;
  }

  public T getValor() {
    throw new ChequeoFallidoException(this);
  }

  public List<String> getErrores() {
    return errores;
  }

  public <R> Try<R> map(Function<T, R> function, String error) {
    return Try.fallido(errores);
  }
}
