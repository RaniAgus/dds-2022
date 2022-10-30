package ar.edu.utn.frba.dds.impactoambiental.models.validaciones;

import ar.edu.utn.frba.dds.impactoambiental.exceptions.ValidacionFallidaException;
import io.vavr.control.Either;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static io.vavr.control.Either.right;

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

  public Either<String, T> validar(T valor) {
    List<Either<String, T>> validacionesResultado = validaciones
        .stream()
        .map(x -> x.validar(valor))
        .collect(Collectors.toList());

    if (hasNotPassAllValidations(validacionesResultado)) {
      throw new ValidacionFallidaException(getErrores(validacionesResultado));
    }
    return right(valor);
  }

  private boolean hasNotPassAllValidations(List<Either<String, T>> results) {
    return results.stream().anyMatch(Either::isLeft);
  }
  private List<String> getErrores(List<Either<String, T>> results) {
    return results.stream()
        .filter(Either::isLeft)
        .map(Either::getLeft)
        .collect(Collectors.toList());
  }

}
