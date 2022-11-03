package ar.edu.utn.frba.dds.impactoambiental.controllers.validaciones;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Validador<T> {
  private List<Validacion<T>> validaciones = new ArrayList<>();
  private T valor;

  public Validador(T valor) {
    this.valor = valor;
  }

  public Validador<T> agregarValidacion(Predicate<T> chequeo, String mensajeDeError) {
    validaciones.add(Validacion.create(chequeo, mensajeDeError));
    return this;
  }

  public Validador<T> agregarValidaciones(List<Validacion<T>> validaciones) {
    this.validaciones.addAll(validaciones);
    return this;
  }

  public Either<T> validar() {
    List<Either<T>> resultados = getResultados(valor);
    return resultados.stream().allMatch(Either::esExitoso)
        ? Either.exitoso(valor) : Either.fallido(Either.colectarErrores(resultados));
  }

  private List<Either<T>> getResultados(T valor) {
    return validaciones.stream()
        .map(x -> x.validar(valor))
        .collect(Collectors.toList());
  }
}
