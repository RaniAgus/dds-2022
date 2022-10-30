package ar.edu.utn.frba.dds.impactoambiental.models.validaciones;

import java.util.Collections;
import java.util.List;
import java.util.function.Function;

public class EitherExitoso<T> implements Either<T> {
  private T valor;

  protected EitherExitoso(T valor) {
    this.valor = valor;
  }

  public T getValor() {
    return valor;
  }

  public List<String> getErrores() {
    return Collections.emptyList();
  }

  public <R> Either<R> map(Function<T, R> function, String error) {
    return Either.desde(() -> function.apply(valor), error);
  }
}
