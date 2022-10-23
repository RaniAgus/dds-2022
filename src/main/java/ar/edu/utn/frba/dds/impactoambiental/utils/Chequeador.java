package ar.edu.utn.frba.dds.impactoambiental.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Chequeador<T> {
  private String nombre;
  private Chequeo<T> chequeoSiEsNulo = Chequeo.valido();
  private List<Chequeo<T>> chequeos = new ArrayList<>();

  public Chequeador(String nombre) {
    this.nombre = nombre;
  }

  public Chequeador<T> agregarChequeoNoNulo(String mensajeDeError) {
    chequeoSiEsNulo = new Chequeo<T>(Objects::nonNull, mensajeDeError);
    return this;
  }

  public Chequeador<T> agregarChequeo(Predicate<T> chequeo, String mensajeDeError) {
    chequeos.add(new Chequeo<>(chequeo, mensajeDeError));
    return this;
  }

  public Try<T> chequearValor(T valor) {
    List<String> errores = getErrores(valor);
    return errores.isEmpty() ? Try.exitoso(valor) : Try.fallido(errores);
  }

  public Try<T> chequearValor(String valor, Function<String, T> parser, String mensajeDeError) {
    try {
      return chequearValor(parser.apply(valor));
    } catch (Exception e) {
      return Try.fallido(Collections.singletonList(mensajeDeError));
    }
  }

  public String getNombre() {
    return nombre;
  }

  private List<String> getErrores(T valor) {
    return Optional.ofNullable(valor)
        .map(v -> chequeos.stream()
              .map(chequeo -> chequeo.getError(v))
              .filter(Optional::isPresent)
              .map(Optional::get)
              .collect(Collectors.toList()))
        .orElseGet(() -> chequeoSiEsNulo.getError(null)
            .map(Collections::singletonList)
            .orElse(Collections.emptyList()));
  }

}
