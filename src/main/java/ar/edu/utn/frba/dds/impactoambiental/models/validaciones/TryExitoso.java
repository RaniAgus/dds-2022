package ar.edu.utn.frba.dds.impactoambiental.models.validaciones;

import java.util.Collections;
import java.util.List;
import java.util.function.Function;

public class TryExitoso<T> implements Try<T> {
  private T valor;

  protected TryExitoso(T valor) {
    this.valor = valor;
  }

  public T getValor() {
    return valor;
  }

  public List<String> getErrores() {
    return Collections.emptyList();
  }

  public <R> Try<R> map(Function<T, R> function, String error) {
    return Try.desde(() -> function.apply(valor), error);
  }
}
