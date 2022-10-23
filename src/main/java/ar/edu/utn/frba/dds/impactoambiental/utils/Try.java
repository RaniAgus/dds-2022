package ar.edu.utn.frba.dds.impactoambiental.utils;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public interface Try<T> {
  T getValor();
  List<String> getErrores();

  static <T> Try<T> exitoso(T valor) {
    return new TryExitoso<>(valor);
  }

  static <T> Try<T> fallido(List<String> errores) {
    return new TryFallido<>(errores);
  }

  static List<String> colectarErrores(Try<?>... tries) {
    return Stream.of(tries)
      .flatMap(t -> t.getErrores().stream())
      .collect(Collectors.toList());
  }
}
