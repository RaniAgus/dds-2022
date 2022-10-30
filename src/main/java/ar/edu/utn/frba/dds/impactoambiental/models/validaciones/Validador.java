package ar.edu.utn.frba.dds.impactoambiental.models.validaciones;

import ar.edu.utn.frba.dds.impactoambiental.exceptions.ValidacionFallidaException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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

  public Either<T> validar(T valor) {
    List<String> errores = getErrores(valor);
    if (!errores.isEmpty()) {
      throw new ValidacionFallidaException(Either.fallido(errores));
    }
    return Either.exitoso(valor);
  }

  private List<String> getErrores(T valor) {
    return validaciones.stream()
        .map(validacion -> validacion.getError(valor))
        .filter(Optional::isPresent)
        .map(Optional::get)
        .collect(Collectors.toList());
  }

}
