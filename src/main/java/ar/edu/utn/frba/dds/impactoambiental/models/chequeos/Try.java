package ar.edu.utn.frba.dds.impactoambiental.models.chequeos;

import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public interface Try<T> {
  T getValor();
  List<String> getErrores();
  <R> Try<R> map(Function<T, R> function, String error);

  static <T> Try<T> desde(Supplier<T> supplier, String error) {
    try {
      return exitoso(supplier.get());
    } catch (Exception e) {
      return fallido(error);
    }
  }

  static <T> Try<T> exitoso(T valor) {
    return new TryExitoso<>(valor);
  }

  static <T> Try<T> fallido(List<String> errores) {
    return new TryFallido<>(errores);
  }

  static <T> Try<T> fallido(String error) {
    return fallido(Collections.singletonList(error));
  }

  static List<String> colectarErrores(Try<?>... tries) {
    return Stream.of(tries).distinct()
        .flatMap(t -> t.getErrores().stream())
        .collect(Collectors.toList());
  }
}
