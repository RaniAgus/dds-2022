package ar.edu.utn.frba.dds.impactoambiental.controllers.validaciones;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;

public class EitherFallido<T> implements Either<T> {
  private List<String> errores;

  protected EitherFallido(List<String> errores) {
    this.errores = errores;
  }

  @Override
  public boolean esExitoso() {
    return false;
  }

  @Override
  public T getValor() {
    throw new NoSuchElementException(String.join(", ", errores));
  }

  @Override
  public List<String> getErrores() {
    return errores;
  }

  @Override
  public Either<T> filter(Predicate<T> predicado, String mensajeDeError) {
    return this;
  }

  @Override
  public <R> Either<R> flatMap(Function<T, Either<R>> function) {
    return Either.fallido(errores);
  }

  @Override
  public <R> Either<R> map(Function<T, R> function) {
    return Either.fallido(errores);
  }

  @Override
  public <R> Either<R> apply(Function<T, R> function, String error) {
    return Either.fallido(errores);
  }

  @Override
  public <R> Either<R> flatApply(Function<T, Optional<R>> function, String error) {
    return Either.fallido(errores);
  }

  @Override
  public <R> R fold(Function<List<String>, R> error, Function<T, R> exito) {
    return error.apply(errores);
  }
}
