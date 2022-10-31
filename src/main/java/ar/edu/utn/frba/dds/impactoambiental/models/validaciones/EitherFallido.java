package ar.edu.utn.frba.dds.impactoambiental.models.validaciones;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.function.Function;

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
    throw new NoSuchElementException();
  }

  @Override
  public List<String> getErrores() {
    return errores;
  }

  @Override
  public <R> Either<R> flatMap(Function<T, Either<R>> function) {
    return Either.fallido(errores);
  }

  @Override
  public <R> Either<R> map(Function<T, R> function, String error) {
    return Either.fallido(errores);
  }

  @Override
  public <R> R fold(Function<List<String>, R> error, Function<T, R> exito) {
    return error.apply(errores);
  }
}
