package ar.edu.utn.frba.dds.impactoambiental.models.validaciones;

import static ar.edu.utn.frba.dds.impactoambiental.utils.EitherUtil.allRight;
import static ar.edu.utn.frba.dds.impactoambiental.utils.EitherUtil.collectLefts;
import static io.vavr.control.Either.left;
import static io.vavr.control.Either.right;

import io.vavr.control.Either;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Validador<T> {
  private List<Validacion<T>> validaciones = new ArrayList<>();

  public Validador<T> agregarValidacion(Predicate<T> chequeo, String mensajeDeError) {
    validaciones.add(Validacion.create(chequeo, mensajeDeError));
    return this;
  }

  public Validador<T> agregarValidaciones(List<Validacion<T>> validaciones) {
    this.validaciones.addAll(validaciones);
    return this;
  }

  public Either<List<String>, T> validar(T valor) {
    List<Either<String, T>> resultados = getResultados(valor);
    return allRight(resultados) ? right(valor) : left(collectLefts(resultados));
  }

  private List<Either<String, T>> getResultados(T valor) {
    return validaciones.stream()
        .map(x -> x.validar(valor))
        .collect(Collectors.toList());
  }
}
