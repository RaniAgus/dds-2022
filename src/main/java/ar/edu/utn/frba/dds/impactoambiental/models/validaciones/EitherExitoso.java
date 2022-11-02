package ar.edu.utn.frba.dds.impactoambiental.models.validaciones;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class EitherExitoso<T> implements Either<T> {
  private T valor;

  protected EitherExitoso(T valor) {
    this.valor = valor;
  }

  @Override
  public boolean esExitoso() {
    return true;
  }

  @Override
  public T getValor() {
    return valor;
  }

  @Override
  public List<String> getErrores() {
    return Collections.emptyList();
  }

  @Override
  public <R> Either<R> flatMap(Function<T, Either<R>> function) {
    return function.apply(valor);
  }

  @Override
  public <R> Either<R> map(Function<T, R> function) {
    return Either.exitoso(function.apply(valor));
  }
  
  @Override
  public <R> Either<R> apply(Function<T, R> function, String error) {
    return Either.desde(() -> function.apply(valor), error);
  }

  @Override
  public <R> Either<R> flatApply(Function<T, Optional<R>> function, String error) {
    return apply(function, error).flatMap(o -> o.map(Either::exitoso).orElseGet(() -> Either.fallido(error)));
  }

  @Override
  public <R> R fold(Function<List<String>, R> error, Function<T, R> exito) {
    return exito.apply(valor);
  }
}
