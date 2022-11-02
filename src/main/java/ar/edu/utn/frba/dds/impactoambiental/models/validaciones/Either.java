package ar.edu.utn.frba.dds.impactoambiental.models.validaciones;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public interface Either<T> {
  boolean esExitoso();
  T getValor();
  List<String> getErrores();
  <R> Either<R> flatMap(Function<T, Either<R>> function);
  <R> Either<R> map(Function<T, R> function);
  <R> Either<R> apply(Function<T, R> function, String error);
  <R> Either<R> flatApply(Function<T, Optional<R>> function, String error);
  <R> R fold(Function<List<String>, R> error, Function<T, R> exito);

  static <T> Either<T> desde(Supplier<T> supplier, String error) {
    try {
      return exitoso(supplier.get());
    } catch (Exception e) {
      return fallido(error);
    }
  }

  static <T> Either<T> exitoso(T valor) {
    return new EitherExitoso<>(valor);
  }

  static <T> Either<T> fallido(List<String> errores) {
    return new EitherFallido<>(errores);
  }

  static <T> Either<T> fallido(String error) {
    return fallido(Collections.singletonList(error));
  }

  static <T> List<String> colectarErrores(List<Either<T>> eithers) {
    return eithers.stream()
        .flatMap(t -> t.getErrores().stream())
        .collect(Collectors.toList());
  }

  static <T> Either<T> concatenar(Supplier<T> valorExitoso, Either<?>... eithers) {
    List<String> errores = Stream.of(eithers)
        .flatMap(t -> t.getErrores().stream())
        .collect(Collectors.toList());

    return errores.isEmpty()
        ? Either.exitoso(valorExitoso.get())
        : Either.fallido(errores);
  }
}
